
package hrms.ai.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PreAuthorize("hasRole('HR')")
    @PostMapping("/create")
    public User createUser(@RequestParam String username,
            @RequestParam String password,
            @RequestParam Role role) {

        return userService.createUser(username, password, role);
    }

    @PreAuthorize("hasRole('HR')")
    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @PreAuthorize("hasRole('HR')")
    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @PreAuthorize("hasRole('HR')")
    @PutMapping("/{id}")
    public User updateUser(@PathVariable Long id,
            @RequestParam String username,
            @RequestParam Role role,
            @RequestParam boolean active) {
        return userService.updateUser(id, username, role, active);
    }

    @PreAuthorize("hasRole('HR')")
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }
}
