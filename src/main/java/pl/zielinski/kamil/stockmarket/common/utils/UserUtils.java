package pl.zielinski.kamil.stockmarket.common.utils;

import org.springframework.security.core.context.SecurityContextHolder;
import pl.zielinski.kamil.stockmarket.user.UserEntity;

public class UserUtils {
    public static UserEntity getCurrentUser() {
        return (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
