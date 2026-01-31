package lab4.middle.service;

import lab4.middle.dto.LoginRequest;
import lab4.middle.dto.LoginResponse;
import lab4.middle.dto.RegisterRequest;
import lab4.middle.dto.UserResponse;
import lab4.middle.entity.User;
import lab4.middle.repository.UserRepository;
import lab4.middle.security.JwtUtil;
import lab4.middle.security.UserPrincipal;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    @Transactional
    public UserResponse register(RegisterRequest req) {
        if (userRepository.existsByEmail(req.getEmail())) {
            throw new IllegalArgumentException("Email уже зарегистрирован");
        }
        if (userRepository.existsByUsername(req.getUsername())) {
            throw new IllegalArgumentException("Имя пользователя занято");
        }
        User u = new User();
        u.setUsername(req.getUsername().trim());
        u.setEmail(req.getEmail().trim().toLowerCase());
        u.setPhone(req.getPhone() != null ? req.getPhone().trim() : null);
        u.setPasswordHash(passwordEncoder.encode(req.getPassword()));
        u = userRepository.save(u);
        return UserResponse.from(u);
    }

    public LoginResponse login(LoginRequest req) {
        User u = userRepository.findByEmail(req.getEmail().trim().toLowerCase())
                .orElseThrow(() -> new BadCredentialsException("Неверный email или пароль"));
        if (!passwordEncoder.matches(req.getPassword(), u.getPasswordHash())) {
            throw new BadCredentialsException("Неверный email или пароль");
        }
        String token = jwtUtil.generate(u.getEmail());
        return new LoginResponse(token, UserResponse.from(u));
    }
}
