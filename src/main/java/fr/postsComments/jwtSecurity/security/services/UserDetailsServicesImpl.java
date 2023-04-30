package fr.postsComments.jwtSecurity.security.services;

import fr.postsComments.jwtSecurity.models.UserApp;
import fr.postsComments.jwtSecurity.repository.UserRepo;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserDetailsServicesImpl implements UserDetailsService {

    private final UserRepo userRepo;

    public UserDetailsServicesImpl(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserApp user = userRepo.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found with the given email " + email));
        return UserDetailsImpl.build(user);
    }
}
