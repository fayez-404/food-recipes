package fayes.food.recipes.recipe;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe,Long>{

    @Query(value = """
            SELECT recipe.id ,name,description,ingredients_raw_str,serving_size,servings,steps
            FROM recipe
            INNER JOIN recipe_tag
            ON recipe.id = recipe_tag.recipe_id
            WHERE recipe_tag.tag_id IN (:tags)
            GROUP BY recipe.id ,name,description,ingredients_raw_str,serving_size,servings,steps
            HAVING COUNT(recipe_tag.tag_id) = :tagsSize
        """,nativeQuery = true)
    public Page<Recipe> findAllByTags(List<Long> tags, int tagsSize, Pageable pageable);

    @Query(value = """
        SELECT recipe.id ,name,description,ingredients_raw_str,serving_size,servings,steps
        FROM recipe
        INNER JOIN recipe_ingredient
        ON recipe.id = recipe_ingredient.recipe_id
        WHERE recipe_ingredient.ingredient_id IN (:ingredients)
    """,nativeQuery = true)
    public Page<Recipe> findAllByIngredients(List<Long> ingredients,Pageable pageable);

    @Query(value = """
        SELECT recipe.id ,name,description,ingredients_raw_str,serving_size,servings,steps
        FROM recipe_tag
        INNER JOIN recipe_ingredient
        ON recipe_tag.recipe_id = recipe_ingredient.recipe_id
        INNER JOIN recipe
        ON recipe_tag.recipe_id = recipe.id
        WHERE recipe_tag.tag_id IN (:tags) AND recipe_ingredient.ingredient_id IN (:ingredients)
        GROUP BY recipe.id ,name,description,ingredients_raw_str,serving_size,servings,steps
        HAVING COUNT(DISTINCT recipe_tag.tag_id) = :tagsSize
    """,nativeQuery = true)
    public Page<Recipe> findAllByTagsAndIngredients(List<Long> tags, int tagsSize, List<Long> ingredients, Pageable pageable);

    @Query(value = """
        SELECT * FROM recipe WHERE name % :recipeName ORDER BY SIMILARITY(name,:recipeName) DESC 
        """,nativeQuery = true)
    public Page<Recipe> findRecipeAndSimilarRecipes(String recipeName,Pageable pageable);

    @Query(value = """
        SELECT * FROM recipe WHERE name LIKE '%'||:name||'%' 
        """,nativeQuery = true)
    public Page<Recipe> findRecipesContainsName(String name, Pageable pageable);

}
