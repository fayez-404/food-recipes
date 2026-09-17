package fayes.food.recipes.recipe;

import fayes.food.recipes.recipe.ingredients.Ingredient;
import fayes.food.recipes.recipe.tags.Tag;
import java.util.Set;
import java.util.stream.Collectors;

public class RecipeResponse {

    private Long id ;
    private String name ;
    private String description ;
    private String ingredients_raw_str;
    private int serving_size ;
    private int servings ;
    private String steps ;
    private Set<String> tags ;
    private Set<String> ingredients ;

    public RecipeResponse(Long id, String name, String description, String ingredients_raw_str, int serving_size, int servings, String steps, Set<String> tags, Set<String> ingredients) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.ingredients_raw_str = ingredients_raw_str;
        this.serving_size = serving_size;
        this.servings = servings;
        this.steps = steps;
        this.tags = tags;
        this.ingredients = ingredients;
    }
    public RecipeResponse(Recipe recipe){
        this.id = recipe.getId() ;
        this.name = recipe.getName() ;
        this.description = recipe.getDescription() ;
        this.ingredients_raw_str = recipe.getIngredients_raw_str() ;
        this.servings = recipe.getServings();
        this.serving_size = recipe.getServing_size() ;
        this.steps = recipe.getSteps() ;
        this.tags = recipe.getTags().stream().map(Tag::getTag).collect(Collectors.toSet());
        this.ingredients =  recipe.getIngredients().stream().map(Ingredient::getIngredient).collect(Collectors.toSet());
    }

    public RecipeResponse() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIngredients_raw_str() {
        return ingredients_raw_str;
    }

    public void setIngredients_raw_str(String ingredients_raw_str) {
        this.ingredients_raw_str = ingredients_raw_str;
    }

    public int getServing_size() {
        return serving_size;
    }

    public void setServing_size(int serving_size) {
        this.serving_size = serving_size;
    }

    public int getServings() {
        return servings;
    }

    public void setServings(int servings) {
        this.servings = servings;
    }

    public String getSteps() {
        return steps;
    }

    public void setSteps(String steps) {
        this.steps = steps;
    }

    public Set<String> getTag() {
        return tags;
    }

    public void setTag(Set<String> tags) {
        this.tags = tags;
    }

    public Set<String> getIngredients() {
        return ingredients;
    }

    public void setIngredient(Set<String> ingredients) {
        this.ingredients = ingredients;
    }

    @Override
    public String toString() {
        return "RecipeResponse{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", ingredients_raw_str='" + ingredients_raw_str + '\'' +
                ", serving_size=" + serving_size +
                ", servings=" + servings +
                ", steps='" + steps + '\'' +
                ", tag=" + tags +
                ", ingredient=" + ingredients +
                '}';
    }
}
