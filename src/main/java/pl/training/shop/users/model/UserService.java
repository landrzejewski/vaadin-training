package pl.training.shop.users.model;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private static final String DEFAULT_ROLE_NAME = "ROLE_USER";

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public User addUser(User user) {
        encodePassword(user);
        assignDefaultRole(user);
        return userRepository.saveAndFlush(user);
    }

    private void assignDefaultRole(User user) {
        Optional<Role> role = roleRepository.getByAuthority(DEFAULT_ROLE_NAME);
        if (!role.isPresent()) {
            Role defaultRole = new Role(DEFAULT_ROLE_NAME);
            roleRepository.saveAndFlush(defaultRole);
            user.addRole(defaultRole);
        } else {
            user.addRole(role.get());
        }
    }

    private void encodePassword(User user) {
        String rawPassword = user.getPassword();
        String encodedPassword = passwordEncoder.encode(rawPassword);
        user.setPassword(encodedPassword);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.getByLogin(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

}
