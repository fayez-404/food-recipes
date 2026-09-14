package fayes.food.recipes.recipe.ingredients;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IngredientRepository extends JpaRepository<Ingredient,Long>  {

    @Query(value = """
        SELECT * FROM ingredient  WHERE ingredient % :ingredient ORDER BY SIMILARITY(ingredient,:ingredient) DESC LIMIT 1
    """,nativeQuery = true)
    public Optional<Ingredient> findIngredient(String ingredient);

    @Query(value = """
    SELECT * FROM ingredient WHERE ingredient LIKE '%'||:name||'%' LIMIT 10
    """,nativeQuery = true)
    public List<Ingredient> findIngredientsStartWithName(String Name);

}
