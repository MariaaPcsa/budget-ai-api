package com.budgetai.infrastructure.security;

import com.budgetai.application.dto.AuthResponseDTO;
import com.budgetai.application.dto.LoginRequestDTO;
import com.budgetai.application.dto.RegisterRequestDTO;
import com.budgetai.domain.entity.User;
import com.budgetai.domain.repository.UserRepository;
import com.budgetai.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthResponseDTO register(RegisterRequestDTO request) {
        String email = normalizeEmail(request.email());

        if (userRepository.existsByEmail(email)) {
            throw new BusinessException("E-mail já cadastrado");
        }

        User user = userRepository.save(User.builder()
                .email(email)
                .passwordHash(passwordEncoder.encode(request.password()))
                .createdAt(LocalDateTime.now())
                .build());

        return new AuthResponseDTO(jwtService.generateToken(new AuthenticatedUser(user.getId(), user.getEmail())));
    }

    public AuthResponseDTO login(LoginRequestDTO request) {
        User user = userRepository.findByEmail(normalizeEmail(request.email()))
                .filter(candidate -> passwordEncoder.matches(request.password(), candidate.getPasswordHash()))
                .orElseThrow(() -> new BusinessException("E-mail ou senha inválidos"));

        return new AuthResponseDTO(jwtService.generateToken(new AuthenticatedUser(user.getId(), user.getEmail())));
    }

    private String normalizeEmail(String email) {
        return email.trim().toLowerCase(Locale.ROOT);
    }
}