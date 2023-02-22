package com.example.tracker.api.register.token;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor
public class VerificationTokenService {
    private final VerificationTokenRepository verificationTokenRepository;

    public void saveToken(VerificationToken verificationToken){
         verificationTokenRepository.save(verificationToken);
    }

    public Optional<VerificationToken> getToken(String token){
        return verificationTokenRepository.findByToken(token);
    }

   /* public int setConfirmedToken(String token){
        return verificationTokenRepository.updateToken(token, LocalDateTime.now());
    }*/

}
