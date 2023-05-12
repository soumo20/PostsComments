package fr.postscomments.authentification.security.services.user;

import fr.postscomments.authentification.models.UserApp;
import fr.postscomments.authentification.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserDetailsServicesImpl implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailsServicesImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserApp user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found with the given email " + email));
        return UserDetailsImpl.build(user);
    }


}
