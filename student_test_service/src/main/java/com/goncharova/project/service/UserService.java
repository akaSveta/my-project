package com.goncharova.project.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.time.Instant;
import java.util.UUID;

@Service
public class UserService implements UserDetailsService {

    private final PasswordEncoder passwordEncoder;

    public UserService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                "jdbc:postgresql://localhost:5433/ege_exam",
                "postgres",
                "pass123"
        );
    }

    public void register(String email, String password) {
        String normalizedEmail = email.trim().toLowerCase();

        String checkSql = "SELECT COUNT(*) FROM users WHERE email = ?";
        String insertSql = "INSERT INTO users (id, email, password_hash, created_at) VALUES (?, ?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {

            checkStmt.setString(1, normalizedEmail);
            ResultSet rs = checkStmt.executeQuery();
            rs.next();
            if (rs.getInt(1) > 0) {
                throw new RuntimeException("User already exists");
            }

            try (PreparedStatement insertStmt = conn.prepareStatement(insertSql)) {
                insertStmt.setObject(1, UUID.randomUUID());
                insertStmt.setString(2, normalizedEmail);
                insertStmt.setString(3, passwordEncoder.encode(password));
                insertStmt.setTimestamp(4, Timestamp.from(Instant.now()));
                insertStmt.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Registration failed: " + e.getMessage(), e);
        }
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        String sql = "SELECT email, password_hash FROM users WHERE email = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, email.trim().toLowerCase());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return User.builder()
                        .username(rs.getString("email"))
                        .password(rs.getString("password_hash"))
                        .authorities("USER")
                        .build();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        throw new UsernameNotFoundException("User not found");
    }
}