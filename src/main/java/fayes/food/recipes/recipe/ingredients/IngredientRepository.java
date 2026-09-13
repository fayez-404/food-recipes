package fayes.food.recipes.recipe.ingredients;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IngredientRepository extends JpaRepository<Ingredient,Long>  {

    @Query(value = """
        SELECT * FROM ingredient WHERE ingredient % :ingredient LIMIT 10
    """,nativeQuery = true)
    public List<Ingredient> findSimilarIngredients(String ingredient);

}
