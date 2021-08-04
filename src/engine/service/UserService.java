package engine.service;


import engine.entity.UserPrincipal;
import engine.repository.UserRepository;
import engine.entity.User;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    private final PasswordEncoder encoder;

    private final UserRepository userRepo;

    public UserService(PasswordEncoder encoder, UserRepository userRepo) {
        this.encoder = encoder;
        this.userRepo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String email) {
        return findByEmail(email)
                .map(UserPrincipal::new)
                .orElseThrow(() -> new UsernameNotFoundException(email));
    }

    public Optional<User> findByEmail(String email) {
        return userRepo.findById(email);
    }

    public User registerNewUser(User user) {
        String email = user.getEmail();
        String password = user.getPassword();
        userRepo.findById(email)
                .ifPresentOrElse(
                        u -> {
                            throw new EmailAlreadyExistsException(email);
                        },
                        () -> {
                            user.setPassword(encoder.encode(password));
                            userRepo.save(user);
                        }
                );
        return user;
    }
}

@ResponseStatus(HttpStatus.BAD_REQUEST)
class EmailAlreadyExistsException extends RuntimeException {

    private String email;

    public EmailAlreadyExistsException(String email) {
        super(String.format("Email %s already exists", email));
    }
}