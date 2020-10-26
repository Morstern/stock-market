package pl.zielinski.kamil.stockmarket.user;

import lombok.Getter;

@Getter
class UserDTO {
    private Long idUser;
    private String email;
    private String role;

    public UserDTO(UserEntity userEntity) {
        this.idUser = userEntity.getIdUser();
        this.email = userEntity.getEmail();
        this.role = userEntity.getRole();
    }
}
