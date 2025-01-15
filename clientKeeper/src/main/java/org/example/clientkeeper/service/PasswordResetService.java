package org.example.clientkeeper.service;

public interface PasswordResetService {
    void sendPasswordResetLink(String email);

    void resetPassword(String token, String newPassword);
}
