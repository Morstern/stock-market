package pl.zielinski.kamil.stockmarket.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserCredentialsFormDTO {
    private String email;
    private String password;
}
