package fayes.food.recipes.recipe;

import fayes.food.recipes.recipe.ingredients.Ingredient;
import fayes.food.recipes.recipe.search_terms.SearchTerm;
import fayes.food.recipes.recipe.tags.Tag;
import fayes.food.recipes.user.UserPrincipal;
import jakarta.persistence.*;

import java.util.Set;

@Entity
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;
    private String name ;
    private String description ;
    private String ingredients_raw_str;
    private int serving_size ;
    private int servings ;
    private String steps ;

    @ManyToMany(mappedBy = "recipe")
    private Set<Tag> tags;

    @ManyToMany(mappedBy = "recipe")
    private Set<Ingredient> ingredients;

    @ManyToMany(mappedBy = "recipe")
    private Set<SearchTerm> searchTerms;

    @ManyToMany(mappedBy = "favourites",fetch = FetchType.LAZY)
    private Set<UserPrincipal> users ;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }

    public Set<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(Set<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public Set<SearchTerm> getSearchTerms() {
        return searchTerms;
    }

    public void setSearchTerms(Set<SearchTerm> searchTerms) {
        this.searchTerms = searchTerms;
    }

    public Set<UserPrincipal> getUsers() {
        return users;
    }

    public void setUsers(Set<UserPrincipal> users) {
        this.users = users;
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

    @Override
    public String toString() {
        return "Recipe{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", ingredienst_raw_str='" + ingredients_raw_str + '\'' +
                ", serving_size=" + serving_size +
                ", servings=" + servings +
                ", steps='" + steps + '\'' +
                '}';
    }
}
