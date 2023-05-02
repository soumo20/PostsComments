package fr.postsComments.authentification.security.services;

import fr.postsComments.authentification.models.UserApp;
import fr.postsComments.authentification.repository.IUserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserDetailsServicesImpl implements UserDetailsService {

    private final IUserRepository IUserRepository;

    public UserDetailsServicesImpl(IUserRepository IUserRepository) {
        this.IUserRepository = IUserRepository;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserApp user = IUserRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found with the given email " + email));
        return UserDetailsImpl.build(user);
    }
}
