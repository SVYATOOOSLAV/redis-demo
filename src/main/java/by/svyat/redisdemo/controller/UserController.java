package by.svyat.redisdemo.controller;

import by.svyat.redisdemo.dto.UserDto;
import by.svyat.redisdemo.entity.UserEntity;
import by.svyat.redisdemo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/users/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable Long id) {
        UserEntity entity = userService.getUser(id);
        return ResponseEntity.ok(new UserDto(
                entity.getFirstName(),
                entity.getLastName(),
                entity.getDateOfBirth()
        ));
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserDto>> getUsers() {
        List<UserEntity> entities = userService.getAllUsers();
        return ResponseEntity.ok(
                entities.stream()
                        .map(entity ->  new UserDto(
                                entity.getFirstName(),
                                entity.getLastName(),
                                entity.getDateOfBirth()))
                        .toList()
        );
    }

    @PostMapping("/users")
    public void saveUser(@RequestBody UserDto userDto) {
        userService.saveUser(userDto);
    }

    @PatchMapping("/users/{id}")
    public void deleteUser(
            @PathVariable Long id,
            @RequestBody UserDto userDto
    ) {
        userService.updateLastNameForUser(id, userDto);
    }

    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }
}
