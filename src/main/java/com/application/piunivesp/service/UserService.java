package com.application.piunivesp.service;

import com.application.piunivesp.exception.type.BusinessException;
import com.application.piunivesp.model.User;
import com.application.piunivesp.repository.PasswordResetTokenRepository;
import com.application.piunivesp.repository.UserRepository;
import com.application.piunivesp.resources.AbstractService;
import com.application.piunivesp.security.model.PasswordResetToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.servlet.http.HttpServletRequest;
import java.util.Calendar;

@Service
public class UserService extends AbstractService<User> {

    private final UserRepository userRepository;

    private final PasswordEncoder encoder;

    private final MailService mailService;

    private final PasswordResetTokenRepository passwordResetTokenRepository;

    public UserService(UserRepository userRepository, PasswordEncoder encoder, MailService mailService, PasswordResetTokenRepository passwordResetTokenRepository) {
        this.userRepository = userRepository;
        this.encoder = encoder;
        this.mailService = mailService;
        this.passwordResetTokenRepository = passwordResetTokenRepository;
    }

    @Override
    public JpaRepository getRepository() {
        return this.userRepository;
    }

    @Override
    public void validateCreateOrUpdate(User entity) {
        if (ObjectUtils.isEmpty(entity.getUsername())) {
            throw new BusinessException("Usuário não pode estar com Nome vazio.");
        }

        if (ObjectUtils.isEmpty(entity.getEmail())) {
            throw new BusinessException("Usuário não pode estar com E-mail vazio.");
        }

        if (ObjectUtils.isEmpty(entity.getPassword())) {
            throw new BusinessException("Usuário não pode estar com Senha vazia.");
        }

        try {
            InternetAddress emailAddr = new InternetAddress(entity.getEmail());
            emailAddr.validate();
        } catch (AddressException e) {
            throw new BusinessException("E-mail inválido.");
        }

        if (userRepository.countByEmailIgnoreCaseAndIdNotLike(entity.getEmail(), ObjectUtils.isEmpty(entity.getId()) ? 0 : entity.getId()) != 0L) {
            throw new BusinessException("Já existe usuário com email:".concat(entity.getEmail()).concat(" cadastrado."));
        }

        this.encodePassword(entity);
    }

    private void encodePassword(User user) {
        user.setPassword(encoder.encode(user.getPassword()));
    }

    public void resetPassword(HttpServletRequest request, String userEmail) {
        User user = userRepository.findByEmail(userEmail);
        if (ObjectUtils.isEmpty(user)) {
            throw new BusinessException("Usuário não encontrado");
        }
        PasswordResetToken passwordResetToken = createPasswordResetTokenForUser(user);
        sendTokenEmail(passwordResetToken);
    }

    @Transactional
    public void resetPassword(User user, String token) {
        PasswordResetToken passwordResetToken = validatePasswordResetToken(token);
        User userBD = userRepository.findByEmail(passwordResetToken.getUser().getEmail());
        if (ObjectUtils.isEmpty(userBD)) {
            throw new BusinessException("Usuário não encontrado");
        }
        userBD.setPassword(user.getPassword());
        this.encodePassword(userBD);
        userRepository.save(userBD);
    }

    @Transactional
    PasswordResetToken createPasswordResetTokenForUser(User user) {
        PasswordResetToken passwordResetToken = new PasswordResetToken(user);
        return passwordResetTokenRepository.save(passwordResetToken);
    }

    private PasswordResetToken validatePasswordResetToken(String token) {
        final PasswordResetToken passToken = passwordResetTokenRepository.findByToken(token);

        if (ObjectUtils.isEmpty(passToken)) {
            throw new BusinessException("Token inválido");
        }
        if (passToken.getExpiryDate().before(Calendar.getInstance().getTime())) {
            throw new BusinessException("Token expirado");
        }
        return passToken;
    }

    private void sendTokenEmail(PasswordResetToken passwordResetToken) {
        mailService.resetPasswordEmail(passwordResetToken);
    }

}
