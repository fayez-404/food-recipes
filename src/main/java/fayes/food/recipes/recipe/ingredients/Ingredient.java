package fayes.food.recipes.recipe.ingredients;

import com.fasterxml.jackson.annotation.JsonIgnore;
import fayes.food.recipes.recipe.Recipe;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class Ingredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;
    private String ingredient ;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "recipe_ingredient",
            joinColumns = @JoinColumn(name = "ingredient_id"),
            inverseJoinColumns = @JoinColumn(name = "recipe_id")
    )
    private List<Recipe> recipe ;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIngredient() {
        return ingredient;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }

    public List<Recipe> getRecipe() {
        return recipe;
    }

    public void setRecipe(List<Recipe> recipe) {
        this.recipe = recipe;
    }

    @Override
    public String toString() {
        return "Ingredient{" +
                "id=" + id +
                ", ingredient='" + ingredient + '\'' +
                '}';
    }
}
