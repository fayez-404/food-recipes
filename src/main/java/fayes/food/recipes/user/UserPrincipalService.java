package fayes.food.recipes.user;

import fayes.food.recipes.exception.EmailAlreadyExistsException;
import fayes.food.recipes.security.authentication.request.RegisterRequest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserPrincipalService implements UserDetailsService {

    private UserRepository userRepository ;
    private PasswordEncoder passwordEncoder ;

    public UserPrincipalService(UserRepository userRepository,PasswordEncoder passwordEncoder){
        this.userRepository = userRepository ;
        this.passwordEncoder = passwordEncoder ;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<UserPrincipal> userPrincipal = userRepository.findByEmail(email);
        if (userPrincipal.isEmpty()){
            throw new UsernameNotFoundException("there is not user with this email: "+email);
        }
        return  userPrincipal.get();
    }


    public void registerUser(RegisterRequest request) {
        if(userRepository.existsByEmail(request.getEmail())){
            throw new EmailAlreadyExistsException("there is account with this email: "+request.getEmail());
        }
        UserPrincipal newUser = new UserPrincipal(request.getUsername(),request.getEmail(),passwordEncoder.encode(request.getPassword()));
        userRepository.save(newUser);
    }

    public UserResponse retriveUserInfo(UserPrincipal userPrincipal){
        UserResponse userResponse = new UserResponse(userPrincipal.getUsername(),userPrincipal.getEmail(),userPrincipal.getFavourites());
        return userResponse ;
    }
}
