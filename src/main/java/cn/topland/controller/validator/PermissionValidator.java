package cn.topland.controller.validator;

import cn.topland.entity.DirectusPermissions;
import cn.topland.entity.Role;
import cn.topland.entity.User;
import cn.topland.util.exception.AccessException;
import cn.topland.util.exception.InvalidException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 权限验证工具
 */
@Component
public class PermissionValidator {

    private static final String DEPARTMENT = "department";

    private static final String USER = "user";

    private static final String CUSTOMER = "customer";

    private static final String BRAND = "brand";

    private static final String QUOTATION = "quotation";

    private static final String CONTRACT = "contract";

    private static final String SETTLEMENT_CONTRACT = "settlement_contract";

    private static final String PACKAGE = "package";

    private static final String EXCEPTION = "exception";

    private static final String ROLE = "role";

    private static final String ACTION_CREATE = "create";

    private static final String ACTION_UPDATE = "update";

    public void validDepartmentPermissions(User user, String token) throws AccessException, InvalidException {

        validateToken(token, user.getAccessToken());
        validateRole(hasPermission(user.getRole(), DEPARTMENT, ACTION_CREATE));
    }

    public void validateUserPermissions(User user, String token) throws AccessException, InvalidException {

        validateToken(token, user.getAccessToken());
        validateRole(hasPermission(user.getRole(), USER, ACTION_CREATE));
    }

    public void validateUserAuthPermissions(User user, String token) throws AccessException, InvalidException {

        validateToken(token, user.getAccessToken());
        validateRole(hasPermission(user.getRole(), USER, ACTION_UPDATE, "role"));
    }

    public void validateUserRefreshPermissions(User user, String token) throws AccessException, InvalidException {

        validateToken(token, user.getAccessToken());
        validateRole(hasPermission(user.getRole(), USER, ACTION_UPDATE, "access_token"));
    }

    public void validateCustomerCreatePermissions(User user, String token) throws AccessException, InvalidException {

        validateToken(token, user.getAccessToken());
        validateRole(hasPermission(user.getRole(), CUSTOMER, ACTION_CREATE));
    }

    public void validateCustomerUpdatePermissions(User user, String token) throws AccessException, InvalidException {

        validateToken(token, user.getAccessToken());
        validateRole(hasPermission(user.getRole(), CUSTOMER, ACTION_UPDATE, "name"));
    }

    public void validateCustomerLostPermissions(User user, String token) throws AccessException, InvalidException {

        validateToken(token, user.getAccessToken());
        validateRole(hasPermission(user.getRole(), CUSTOMER, ACTION_UPDATE, "status"));
    }

    public void validateCustomerRetrievePermissions(User user, String token) throws AccessException, InvalidException {

        validateToken(token, user.getAccessToken());
        validateRole(hasPermission(user.getRole(), CUSTOMER, ACTION_UPDATE, "status"));
    }

    public void validateBrandCreatePermissions(User user, String token) throws AccessException, InvalidException {

        validateToken(token, user.getAccessToken());
        validateRole(hasPermission(user.getRole(), BRAND, ACTION_CREATE));
    }

    public void validateBrandUpdatePermissions(User user, String token) throws AccessException, InvalidException {

        validateToken(token, user.getAccessToken());
        validateRole(hasPermission(user.getRole(), BRAND, ACTION_UPDATE));
    }

    public void validateQuotationCreatePermission(User user, String token) throws AccessException, InvalidException {

        validateToken(token, user.getAccessToken());
        validateRole(hasPermission(user.getRole(), QUOTATION, ACTION_CREATE));
    }

    public void validateQuotationUpdatePermission(User user, String token) throws AccessException, InvalidException {

        validateToken(token, user.getAccessToken());
        validateRole(hasPermission(user.getRole(), QUOTATION, ACTION_UPDATE));
    }

    public void validateContractCreatePermissions(User user, String token) throws AccessException, InvalidException {

        validateToken(token, user.getAccessToken());
        validateRole(hasPermission(user.getRole(), CONTRACT, ACTION_CREATE));
    }

    public void validateContractReceivePaperPermissions(User user, String token) throws AccessException, InvalidException {

        validateToken(token, user.getAccessToken());
        validateRole(hasPermission(user.getRole(), CONTRACT, ACTION_UPDATE, "paper_date"));
    }

    public void validateContractReviewPermissions(User user, String token) throws AccessException, InvalidException {

        validateToken(token, user.getAccessToken());
        validateRole(hasPermission(user.getRole(), CONTRACT, ACTION_UPDATE, "status"));
    }

    public void validateSettlementCreatePermissions(User user, String token) throws AccessException, InvalidException {

        validateToken(token, user.getAccessToken());
        validateRole(hasPermission(user.getRole(), SETTLEMENT_CONTRACT, ACTION_UPDATE));
    }

    public void validateSettlementReviewPermissions(User user, String token) throws AccessException, InvalidException {

        validateToken(token, user.getAccessToken());
        validateRole(hasPermission(user.getRole(), SETTLEMENT_CONTRACT, ACTION_UPDATE));
    }

    public void validatePackageCreatePermissions(User user, String token) throws AccessException, InvalidException {

        validateToken(token, user.getAccessToken());
        validateRole(hasPermission(user.getRole(), PACKAGE, ACTION_CREATE));
    }

    public void validatePackageUpdatePermissions(User user, String token) throws AccessException, InvalidException {

        validateToken(token, user.getAccessToken());
        validateRole(hasPermission(user.getRole(), PACKAGE, ACTION_UPDATE));
    }

    public void validateExceptionCreatePermissions(User user, String token) throws AccessException, InvalidException {

        validateToken(token, user.getAccessToken());
        validateRole(hasPermission(user.getRole(), EXCEPTION, ACTION_CREATE));
    }

    public void validateExceptionUpdatePermissions(User user, String token) throws AccessException, InvalidException {

        validateToken(token, user.getAccessToken());
        validateRole(hasPermission(user.getRole(), EXCEPTION, ACTION_UPDATE, "type"));
    }

    public void validateExceptionSolvePermissions(User user, String token) throws AccessException, InvalidException {

        validateToken(token, user.getAccessToken());
        validateRole(hasPermission(user.getRole(), EXCEPTION, ACTION_UPDATE, "close_date"));
    }

    public void validateRoleCreatePermissions(User user, String token) throws AccessException, InvalidException {

        validateToken(token, user.getAccessToken());
        validateRole(hasPermission(user.getRole(), ROLE, ACTION_CREATE));
    }

    public void validateRoleUpdatePermissions(User user, String token) throws AccessException, InvalidException {

        validateToken(token, user.getAccessToken());
        validateRole(hasPermission(user.getRole(), ROLE, ACTION_UPDATE));
    }

    private boolean hasPermission(Role role, String collection, String action, String fields) throws AccessException {

        if (role == null) {

            throw new AccessException("该用户没有操作权限");
        }
        List<DirectusPermissions> directusPermissions = role.getRole().getPermissions();
        return directusPermissions.stream().anyMatch(p -> matchCollectionActionFields(p, collection, action, fields));
    }

    private boolean hasPermission(Role role, String collection, String action) throws AccessException {

        if (role == null) {

            throw new AccessException("该用户没有操作权限");
        }
        List<DirectusPermissions> directusPermissions = role.getRole().getPermissions();
        return directusPermissions.stream().anyMatch(p -> matchCollectionAction(p, collection, action));
    }

    private boolean matchCollectionAction(DirectusPermissions permission, String collection, String action) {

        return collection.equals(permission.getCollection())
                && action.equals(permission.getAction());
    }

    private boolean matchCollectionActionFields(DirectusPermissions permission, String collection, String action, String fields) {

        return collection.equals(permission.getCollection())
                && action.equals(permission.getAction())
                && (permission.getFields().contains(fields) || "*".equals(permission.getFields()));
    }

    private void validateRole(boolean hasPermission) throws AccessException {

        if (!hasPermission) {

            throw new AccessException("该用户没有操作权限");
        }
    }

    private void validateToken(String token, String cacheToken) throws InvalidException {

        if (StringUtils.isBlank(token) || StringUtils.isBlank(cacheToken) || !cacheToken.equals(token)) {

            throw new InvalidException("用户口令已过期,请刷新后重试");
        }
    }
}