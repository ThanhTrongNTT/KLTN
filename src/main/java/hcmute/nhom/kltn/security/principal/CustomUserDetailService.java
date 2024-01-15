package hcmute.nhom.kltn.security.principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import hcmute.nhom.kltn.model.User;
import hcmute.nhom.kltn.repository.UserRepository;

/**
 * Class CustomUserDetailService.
 *
 * @author: ThanhTrong
 * @function_id:
 * @version:
 **/
@Service
public class CustomUserDetailService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElse(null);


        return UserPrincipal.create(user);
    }
}
