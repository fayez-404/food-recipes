package fayes.food.recipes.recipe;

import fayes.food.recipes.exception.IngredientsAndTagsEmptyException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Set;
import java.util.logging.Logger;

@SpringBootTest
public class RecipeServiceTest {

    @Autowired
    private RecipeService recipeService ;

    @Test
    public void retrieveRecipes_ingredientsAndTagsNullTest(){
        Assertions.assertThrows(IngredientsAndTagsEmptyException.class
                ,()->recipeService.retrieveRecipes(null,null, PageRequest.of(0,10)));
    }

    @Test
    public void retrieveRecipes_tagsNullTest(){
        Page<RecipeResponse> recipeResponses = recipeService.retrieveRecipes(null, Set.of("moval extract"),PageRequest.of(0,10));
        Assertions.assertTrue(recipeResponses.getNumberOfElements() == 1 && recipeResponses.getContent().getFirst().getName().equals("Rista (Meatballs in Red Gravy)"));
    }

    @Test
    public void retrieveRecipes_ingredientsNullTest(){
        Page<RecipeResponse> recipeResponses = recipeService.retrieveRecipes(Set.of("shrimp-main-dish"),null,PageRequest.of(0,10));
        Assertions.assertEquals(2, recipeResponses.getNumberOfElements());
    }

    @Test
    public void retrieveRecipes_ingredientsAndTagsNotNullTest(){
        Page<RecipeResponse> recipeResponses = recipeService.retrieveRecipes(Set.of("pasta-elbow-macaroni"),Set.of("sour cream","sweet onion","steed tomatoes"),PageRequest.of(0,50));
        Assertions.assertEquals(4, recipeResponses.getNumberOfElements());
    }

    @Test
    public void retriveRecipes_similarNamesNotFoundTest(){
        Page<RecipeResponse> recipeResponses = recipeService.findRecipeAndSimilarRecipes("youssef fayez",PageRequest.of(0,10));
        Assertions.assertEquals(0,recipeResponses.getNumberOfElements());
    }

    @Test
    public void retriveRecipes_similarNamesExistTest(){
        Page<RecipeResponse> recipeResponses = recipeService.findRecipeAndSimilarRecipes("bisara",PageRequest.of(0,10));
        Assertions.assertEquals("Bissara",recipeResponses.getContent().getFirst().getName());
    }

    @Test
    public void retriveRecipes_recipesContainsTheNameExistTest(){
        Page<RecipeResponse> recipeResponses = recipeService.findRecipeContainsName("kofta",PageRequest.of(0,10));
        Assertions.assertEquals("Kofta Bi Tahini (kofta With Tahini)",recipeResponses.getContent().getFirst().getName());
    }

    @Test
    public void retriveRecipes_recipesContainsTheNameNotFoundTest(){
        Page<RecipeResponse> recipeResponses = recipeService.findRecipeContainsName("youssef fayez",PageRequest.of(0,10));
        Assertions.assertEquals(0,recipeResponses.getNumberOfElements());
    }
}

// todos

// 6- recipes are similar to the given one
// 7- recipes contains the name
