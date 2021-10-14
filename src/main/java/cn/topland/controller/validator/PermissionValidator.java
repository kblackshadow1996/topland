package cn.topland.controller.validator;

import cn.topland.entity.DirectusPermissions;
import cn.topland.entity.Role;
import cn.topland.service.DirectusPermissionsService;
import cn.topland.util.AccessException;
import org.springframework.beans.factory.annotation.Autowired;
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

    private static final String ACTION_CREATE = "create";

    private static final String ACTION_UPDATE = "update";

    @Autowired
    private DirectusPermissionsService directusPermissionsService;

    public void validDepartmentPermissions(Role role) throws AccessException {

        validateRole(hasPermission(role, DEPARTMENT, ACTION_CREATE));
    }

    public void validateUserPermissions(Role role) throws AccessException {

        validateRole(hasPermission(role, USER, ACTION_CREATE));
    }

    public void validateCustomerCreatePermissions(Role role) throws AccessException {

        validateRole(hasPermission(role, CUSTOMER, ACTION_CREATE));
    }

    public void validateCustomerUpdatePermissions(Role role) throws AccessException {

        validateRole(hasPermission(role, CUSTOMER, ACTION_UPDATE, "name"));
    }

    public void validateCustomerLostPermissions(Role role) throws AccessException {

        validateRole(hasPermission(role, CUSTOMER, ACTION_UPDATE, "status"));
    }

    public void validateCustomerRetrievePermissions(Role role) throws AccessException {

        validateRole(hasPermission(role, CUSTOMER, ACTION_UPDATE, "status"));
    }

    public void validateBrandCreatePermissions(Role role) throws AccessException {

        validateRole(hasPermission(role, BRAND, ACTION_CREATE));
    }

    public void validateBrandUpdatePermissions(Role role) throws AccessException {

        validateRole(hasPermission(role, BRAND, ACTION_UPDATE));
    }

    public void validateQuotationCreatePermission(Role role) throws AccessException {

        validateRole(hasPermission(role, QUOTATION, ACTION_CREATE));
    }

    public void validateQuotationUpdatePermission(Role role) throws AccessException {

        validateRole(hasPermission(role, QUOTATION, ACTION_UPDATE));
    }

    public void validateContractCreatePermissions(Role role) throws AccessException {

        validateRole(hasPermission(role, CONTRACT, ACTION_CREATE));
    }

    public void validateContractReceivePaperPermissions(Role role) throws AccessException {

        validateRole(hasPermission(role, CONTRACT, ACTION_UPDATE, "paper_date"));
    }

    public void validateContractReviewPermissions(Role role) throws AccessException {

        validateRole(hasPermission(role, CONTRACT, ACTION_UPDATE, "status"));
    }

    public void validateSettlementCreatePermissions(Role role) throws AccessException {

        validateRole(hasPermission(role, SETTLEMENT_CONTRACT, ACTION_UPDATE));
    }

    public void validatePackageCreatePermissions(Role role) throws AccessException {

        validateRole(hasPermission(role, PACKAGE, ACTION_CREATE));
    }

    public void validatePackageUpdatePermissions(Role role) throws AccessException {

        validateRole(hasPermission(role, PACKAGE, ACTION_UPDATE));
    }

    private boolean hasPermission(Role role, String collection, String action, String fields) throws AccessException {

        if (role == null) {

            throw new AccessException();
        }
        List<DirectusPermissions> directusPermissions = directusPermissionsService.listRolesPermissions(role.getRole().getId());
        return directusPermissions.stream().anyMatch(p -> matchCollectionActionFields(p, collection, action, fields));
    }

    private boolean hasPermission(Role role, String collection, String action) throws AccessException {

        if (role == null) {

            throw new AccessException();
        }
        List<DirectusPermissions> directusPermissions = directusPermissionsService.listRolesPermissions(role.getRole().getId());
        return directusPermissions.stream().anyMatch(p -> matchCollectionAction(p, collection, action));
    }

    private boolean matchCollectionAction(DirectusPermissions permission, String collection, String action) {

        return collection.equals(permission.getCollection())
                && action.equals(permission.getAction());
    }

    private boolean matchCollectionActionFields(DirectusPermissions permission, String collection, String action, String fields) {

        return collection.equals(permission.getCollection())
                && action.equals(permission.getAction())
                && permission.getFields().contains(fields);
    }

    private void validateRole(boolean hasPermission) throws AccessException {

        if (!hasPermission) {

            throw new AccessException();
        }
    }
}