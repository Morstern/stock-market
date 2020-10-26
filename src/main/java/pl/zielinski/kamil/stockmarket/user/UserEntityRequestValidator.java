package pl.zielinski.kamil.stockmarket.user;

import pl.zielinski.kamil.stockmarket.common.constants.UserRoles;
import pl.zielinski.kamil.stockmarket.common.exceptions.ForbiddenRequestException;
import pl.zielinski.kamil.stockmarket.common.exceptions.PreconditionFailedException;
import pl.zielinski.kamil.stockmarket.common.utils.RequestValidator;

public class UserEntityRequestValidator implements RequestValidator<UserEntity> {
    private Long resourceId;

    public UserEntityRequestValidator(Long resourceId) {
        this.resourceId = resourceId;
    }


    @Override
    public boolean validateRequest(UserEntity userEntity, int hashcode) {
        if (!validateHashcode(userEntity.hashCode(), hashcode)) {
            throw new PreconditionFailedException();
        }

        if (!checkForPrivilege(userEntity, resourceId)) {
            throw new ForbiddenRequestException();
        }

        return true;

    }

    public boolean validateRequest(UserEntity userEntity) {
        if (!checkForPrivilege(userEntity, resourceId)) {
            throw new ForbiddenRequestException();
        }

        return true;
    }

    @Override
    public boolean validateHashcode(int hashcode, int hashcode2) {
        return hashcode == hashcode2;
    }

    private boolean checkForPrivilege(UserEntity userEntity, Long id) {
        return userEntity.getIdUser() == id || userEntity.getRole().equals(UserRoles.ADMIN);
    }
}
