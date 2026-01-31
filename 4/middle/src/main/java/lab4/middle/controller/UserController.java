package lab4.middle.controller;

import lab4.middle.dto.UserResponse;
import lab4.middle.dto.UserUpdateRequest;
import lab4.middle.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponse> getCurrent() {
        return ResponseEntity.ok(userService.getCurrent());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getById(id));
    }

    @PutMapping("/me")
    public ResponseEntity<UserResponse> updateCurrent(@RequestBody UserUpdateRequest req) {
        return ResponseEntity.ok(userService.updateCurrent(req));
    }
}
