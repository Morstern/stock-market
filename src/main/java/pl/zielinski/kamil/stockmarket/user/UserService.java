package pl.zielinski.kamil.stockmarket.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pl.zielinski.kamil.stockmarket.common.constants.APIEndpoints;
import pl.zielinski.kamil.stockmarket.common.constants.UserRoles;
import pl.zielinski.kamil.stockmarket.common.exceptions.DatabaseException;
import pl.zielinski.kamil.stockmarket.common.utils.HeaderBuilder;
import pl.zielinski.kamil.stockmarket.common.utils.URICreator;
import pl.zielinski.kamil.stockmarket.common.utils.UserUtils;
import pl.zielinski.kamil.stockmarket.security.WebSecurityConfig;

import java.util.Optional;

@Service
public class UserService {
    UserRepository userRepository;
    WebSecurityConfig webSecurityConfig;

    @Autowired
    public UserService(UserRepository userRepository, WebSecurityConfig webSecurityConfig) {
        this.userRepository = userRepository;
        this.webSecurityConfig = webSecurityConfig;
    }

    public ResponseEntity<?> registerUser(UserCredentialsFormDTO userCredentialsFormDTO) {
        try {
            UserEntity createdUser = userRepository.save(UserEntity.builder()
                    .email(userCredentialsFormDTO.getEmail())
                    .password(webSecurityConfig.passwordEncoder().encode(userCredentialsFormDTO.getPassword()))
                    .role(UserRoles.USER)
                    .nonExpired(Boolean.TRUE)
                    .nonLocked(Boolean.TRUE)
                    .credentialsNonExpired(Boolean.TRUE)
                    .enabled(Boolean.TRUE)
                    .build());

            var headers = new HeaderBuilder()
                    .setLocation(new URICreator(APIEndpoints.API_USERS, createdUser.getIdUser()).getURI())
                    .setETag(createdUser.hashCode())
                    .build();

            return new ResponseEntity<>(new UserDTO(createdUser), headers, HttpStatus.CREATED);

        } catch (Exception e) {
            throw new DatabaseException();
        }
    }

    public ResponseEntity<?> loginUser(UserCredentialsFormDTO userCredentialsFormDTO) {
        Optional<UserEntity> userByEmail = userRepository.findUserEntityByEmail(userCredentialsFormDTO.getEmail());
        if (userByEmail.isPresent()) {
            if (webSecurityConfig.passwordEncoder().matches(userCredentialsFormDTO.getPassword(), userByEmail.get().getPassword())) {
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                throw new PasswordUnmatchedException();
            }
        } else {
            throw new UserNotFoundException();
        }
    }

    public ResponseEntity<?> deleteUser(Long id, int hashcode) {
        UserEntity currentUser = UserUtils.getCurrentUser();
        UserEntityRequestValidator userEntityRequestValidator = new UserEntityRequestValidator(id);
        userEntityRequestValidator.validateRequest(currentUser, hashcode);
        userRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    public ResponseEntity<?> updateUser(Long id, int hashcode, UserEntity userEntity) {
        UserEntity currentUser = UserUtils.getCurrentUser();
        UserEntityRequestValidator userEntityRequestValidator = new UserEntityRequestValidator(id);
        userEntityRequestValidator.validateRequest(currentUser, hashcode);
        userRepository.save(userEntity);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity<?> getUser(Long id) {
        UserEntity currentUser = UserUtils.getCurrentUser();
        UserEntityRequestValidator userEntityRequestValidator = new UserEntityRequestValidator(id);
        userEntityRequestValidator.validateRequest(currentUser);

        Optional<UserEntity> optionalUserEntity = userRepository.findById(id);

        if (!optionalUserEntity.isPresent()) {
            throw new UserNotFoundException();
        }

        UserEntity userEntity = optionalUserEntity.get();

        var headers = new HeaderBuilder()
                .setETag(userEntity.hashCode())
                .build();

        return new ResponseEntity<>(new UserDTO(userEntity), headers, HttpStatus.OK);
    }
}
