package pl.zielinski.kamil.stockmarket.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.zielinski.kamil.stockmarket.common.constants.APIEndpoints;

@RestController
@RequestMapping(APIEndpoints.API_USERS)
public class UserController {

    UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping("register")
    public ResponseEntity<?> registerUser(@RequestBody UserCredentialsFormDTO userCredentialsFormDTO) {
        return userService.registerUser(userCredentialsFormDTO);
    }

    @PostMapping("login")
    public ResponseEntity<?> loginUser(@RequestBody UserCredentialsFormDTO userCredentialsFormDTO) {
        return userService.loginUser(userCredentialsFormDTO);
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getUser(@PathVariable("id") Long id) {
        return userService.getUser(id);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id") Long id, @RequestHeader("If-Match") int hashcode) {
        return userService.deleteUser(id, hashcode);
    }

    @PutMapping("{id}")
    public ResponseEntity<?> updateUser(@PathVariable("id") Long id, @RequestHeader("If-Match") int hashcode, @RequestBody UserEntity userEntity) {
        return userService.updateUser(id, hashcode, userEntity);
    }
}
