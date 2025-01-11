package daniel.nuud.reservationsystem.user.controller;

import daniel.nuud.reservationsystem.user.dto.UserCreateDTO;
import daniel.nuud.reservationsystem.user.dto.UserDTO;
import daniel.nuud.reservationsystem.user.dto.UserUpdateDTO;
import daniel.nuud.reservationsystem.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping(path = "/users")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        var users = userService.getAllUsers();

        return ResponseEntity.ok()
                .header("X-Total-Count", String.valueOf(users.size()))
                .body(users);
    }

    @PostMapping(path = "/users")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<UserDTO> createUser(@RequestBody UserCreateDTO userCreateDTO) {
        var user = userService.createUser(userCreateDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @PutMapping(path = "/users/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserDTO updateUser(@PathVariable Long id, @RequestBody UserUpdateDTO userUpdateDTO) {
        return userService.updateUser(userUpdateDTO, id);
    }

    @DeleteMapping(path = "/users/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }
}
