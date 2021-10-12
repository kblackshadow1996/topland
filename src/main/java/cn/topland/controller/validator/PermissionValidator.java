package cn.topland.controller.validator;

import cn.topland.entity.DirectusPermissions;
import cn.topland.entity.Role;
import cn.topland.service.DirectusPermissionsService;
import cn.topland.util.AccessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

/**
 * 权限验证工具
 */
@Component
public class PermissionValidator {

    private static final String DEPARTMENT = "department";

    private static final String USER = "user";

    private static final String CUSTOMER = "customer";

    private static final String BRAND = "brand";

    private static final String ACTION_CREATE = "create";

    private static final String ACTION_UPDATE = "update";

    @Autowired
    private DirectusPermissionsService directusPermissionsService;

    public void validDepartmentPermissions(Role role) throws AccessException {

        if (Objects.isNull(role)) {

            throw new AccessException();
        }
        List<DirectusPermissions> directusPermissions = directusPermissionsService.listRolesPermissions(role.getRole().getId());
        boolean hasPermission = directusPermissions.stream().anyMatch(p -> hasDepartmentsCreatePermission(directusPermissions) || hasDepartmentUpdatePermission(directusPermissions));
        if (!hasPermission) {

            throw new AccessException();
        }
    }

    public void validateUserPermissions(Role role) throws AccessException {

        if (Objects.isNull(role)) {

            throw new AccessException();
        }

        List<DirectusPermissions> directusPermissions = directusPermissionsService.listRolesPermissions(role.getRole().getId());
        boolean hasPermission = directusPermissions.stream().anyMatch(p -> hasUserCreatePermission(directusPermissions) || hasUserUpdatePermission(directusPermissions));
        if (!hasPermission) {

            throw new AccessException();
        }
    }

    public void validateCustomerCreatePermissions(Role role) throws AccessException {

        if (Objects.isNull(role)) {

            throw new AccessException();
        }
        List<DirectusPermissions> directusPermissions = directusPermissionsService.listRolesPermissions(role.getRole().getId());
        boolean hasPermission = directusPermissions.stream().anyMatch(p -> hasCustomerCreatePermission(directusPermissions));
        if (!hasPermission) {

            throw new AccessException();
        }
    }

    public void validateCustomerUpdatePermissions(Role role) throws AccessException {

        if (Objects.isNull(role)) {

            throw new AccessException();
        }
        List<DirectusPermissions> directusPermissions = directusPermissionsService.listRolesPermissions(role.getRole().getId());
        boolean hasPermission = directusPermissions.stream().anyMatch(p -> hasCustomerUpdatePermission(directusPermissions));
        if (!hasPermission) {

            throw new AccessException();
        }
    }

    public void validateBrandCreatePermissions(Role role) throws AccessException {

        if (Objects.isNull(role)) {

            throw new AccessException();
        }
        List<DirectusPermissions> directusPermissions = directusPermissionsService.listRolesPermissions(role.getRole().getId());
        boolean hasPermission = directusPermissions.stream().anyMatch(p -> hasBrandCreatePermission(directusPermissions));
        if (!hasPermission) {

            throw new AccessException();
        }
    }

    public void validateBrandUpdatePermissions(Role role) throws AccessException {

        if (Objects.isNull(role)) {

            throw new AccessException();
        }
        List<DirectusPermissions> directusPermissions = directusPermissionsService.listRolesPermissions(role.getRole().getId());
        boolean hasPermission = directusPermissions.stream().anyMatch(p -> hasBrandUpdatePermission(directusPermissions));
        if (!hasPermission) {

            throw new AccessException();
        }
    }

    private boolean hasBrandUpdatePermission(List<DirectusPermissions> directusPermissions) {

        return directusPermissions.stream().anyMatch(p -> matchCollectionAction(p, BRAND, ACTION_UPDATE));
    }

    private boolean hasBrandCreatePermission(List<DirectusPermissions> directusPermissions) {

        return directusPermissions.stream().anyMatch(p -> matchCollectionAction(p, BRAND, ACTION_CREATE));
    }

    private boolean hasCustomerUpdatePermission(List<DirectusPermissions> directusPermissions) {

        return directusPermissions.stream().anyMatch(p -> matchCollectionAction(p, CUSTOMER, ACTION_UPDATE));
    }

    private boolean hasCustomerCreatePermission(List<DirectusPermissions> directusPermissions) {

        return directusPermissions.stream().anyMatch(p -> matchCollectionAction(p, CUSTOMER, ACTION_CREATE));
    }

    private boolean hasUserUpdatePermission(List<DirectusPermissions> directusPermissions) {

        return directusPermissions.stream().anyMatch(p -> matchCollectionAction(p, USER, ACTION_UPDATE));
    }

    private boolean hasUserCreatePermission(List<DirectusPermissions> directusPermissions) {

        return directusPermissions.stream().anyMatch(p -> matchCollectionAction(p, USER, ACTION_CREATE));
    }

    private boolean hasDepartmentUpdatePermission(List<DirectusPermissions> directusPermissions) {

        return directusPermissions.stream().anyMatch(p -> matchCollectionAction(p, DEPARTMENT, ACTION_UPDATE));
    }

    private boolean hasDepartmentsCreatePermission(List<DirectusPermissions> directusPermissions) {

        return directusPermissions.stream().anyMatch(p -> matchCollectionAction(p, DEPARTMENT, ACTION_UPDATE));
    }

    private boolean matchCollectionAction(DirectusPermissions permission, String collection, String action) {

        return collection.equals(permission.getCollection()) && action.equals(permission.getAction());
    }
}