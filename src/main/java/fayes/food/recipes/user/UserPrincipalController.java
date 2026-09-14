package fayes.food.recipes.user;

import fayes.food.recipes.recipe.Recipe;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class UserPrincipalController {

    private UserPrincipalService userPrincipalService ;
    public UserPrincipalController(UserPrincipalService userPrincipalService){
        this.userPrincipalService = userPrincipalService ;
    }

    @GetMapping("/user")
    public ResponseEntity<UserResponse> retiveUserInfo(@AuthenticationPrincipal UserPrincipal userPrincipal){
        UserResponse response = userPrincipalService.retriveUserInfo(userPrincipal);
        return ResponseEntity.ok(response);
    }


}
