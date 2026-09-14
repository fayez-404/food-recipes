package fayes.food.recipes.user;

import fayes.food.recipes.recipe.Recipe;

import java.util.List;

public class UserResponse {

    private String username ;
    private String email ;
    private List<Recipe> favorites ;

    public UserResponse(String username, String email, List<Recipe> favorites) {
        this.username = username;
        this.email = email;
        this.favorites = favorites;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Recipe> getFavorites() {
        return favorites;
    }

    public void setFavorites(List<Recipe> favorites) {
        this.favorites = favorites;
    }

    @Override
    public String toString() {
        return "UserResponse{" +
                "username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", favorites=" + favorites +
                '}';
    }
}
