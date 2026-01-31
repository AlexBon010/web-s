package lab4.middle.service;

import lab4.middle.dto.UserResponse;
import lab4.middle.dto.UserUpdateRequest;
import lab4.middle.entity.User;
import lab4.middle.repository.UserRepository;
import lab4.middle.security.UserPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getCurrentUser() {
        UserPrincipal p = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return p.getUser();
    }

    @Transactional(readOnly = true)
    public UserResponse getCurrent() {
        return UserResponse.from(getCurrentUser());
    }

    @Transactional(readOnly = true)
    public UserResponse getById(Long id) {
        User u = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Пользователь не найден"));
        return UserResponse.from(u);
    }

    @Transactional
    public UserResponse updateCurrent(UserUpdateRequest req) {
        User u = getCurrentUser();
        if (req.getUsername() != null && !req.getUsername().isBlank()) {
            if (userRepository.existsByUsername(req.getUsername().trim()) && !req.getUsername().trim().equalsIgnoreCase(u.getUsername())) {
                throw new IllegalArgumentException("Имя пользователя занято");
            }
            u.setUsername(req.getUsername().trim());
        }
        if (req.getPhone() != null) u.setPhone(req.getPhone().trim());
        if (req.getProfilePhotoBase64() != null) u.setProfilePhotoBase64(req.getProfilePhotoBase64());
        u = userRepository.save(u);
        return UserResponse.from(u);
    }
}
