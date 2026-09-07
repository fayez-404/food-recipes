package fayes.food.recipes.recipe.search_terms;

import fayes.food.recipes.recipe.Recipe;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class SearchTerm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;
    @Column(name = "search_term")
    private String searchTerm;

    @ManyToMany
    @JoinTable(
            name = "recipe_search_term",
            joinColumns = @JoinColumn(name = "search_term_id"),
            inverseJoinColumns = @JoinColumn(name = "recipe_id")
    )
    private List<Recipe> recipe ;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSearchTerm() {
        return searchTerm;
    }

    public void setSearchTerm(String searchTerm) {
        this.searchTerm = searchTerm;
    }

    public List<Recipe> getRecipe() {
        return recipe;
    }

    public void setRecipe(List<Recipe> recipe) {
        this.recipe = recipe;
    }

    @Override
    public String toString() {
        return "SearchTerm{" +
                "id=" + id +
                ", searchTerm='" + searchTerm + '\'' +
                ", recipe=" + recipe +
                '}';
    }
}
