package fayes.food.recipes.user;

import fayes.food.recipes.exception.EmailAlreadyExistsException;
import fayes.food.recipes.security.authentication.request.RegisterRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public class UserPrincipalServiceTest {

    @Autowired
    private UserPrincipalService userPrincipalService ;
    @Autowired
    private UserRepository userRepository ;

    @BeforeEach
    public void setUpDatabase(){
        userRepository.deleteAll();
        userPrincipalService.registerUser(
                new RegisterRequest("bananaJoe","example@email.com","P@ssword123")
        );
    }

    @Test
    public void loadUserTest_validEmailTest(){
        var user = userPrincipalService.loadUserByUsername("example@email.com");
        System.out.println(user);
        System.out.println(user.getPassword());
        Assertions.assertTrue(!user.getPassword().equals("P@ssword123")
                &&user.getUsername().equals("bananaJoe"));
    }
    @Test
    public void loadUserTest_EmailNotFoundTest(){
        Assertions.assertThrows(UsernameNotFoundException.class
                ,()->userPrincipalService.loadUserByUsername("example3@email.com"));
    }

    @Test
    public void registerUser_validRegisterRequestTest(){
        RegisterRequest request = new RegisterRequest("BigMango","mango.man@gmail.com","P@ssword123");
        userPrincipalService.registerUser(request);
        UserPrincipal user = (UserPrincipal) userPrincipalService.loadUserByUsername("mango.man@gmail.com");
        Assertions.assertTrue(user.getUsername().equals("BigMango")
        &&user.getEmail().equals("mango.man@gmail.com")
        &&!user.getPassword().equals("P@ssword123"));
    }
    @Test
    public void registerUser_emailAlreadyExistTest(){
        RegisterRequest request = new RegisterRequest("BigMango","example@email.com","P@ssword123");
        Assertions.assertThrows(EmailAlreadyExistsException.class
                ,()->userPrincipalService.registerUser(request));
    }

}
// todos
//1 - load user email exist
//2- load user email doens`t exist
//3- register user email already exist
//4- register user valid email