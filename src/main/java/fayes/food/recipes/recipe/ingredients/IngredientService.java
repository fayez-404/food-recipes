package fayes.food.recipes.recipe.ingredients;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IngredientService {

    private IngredientRepository ingredientRepository ;
    IngredientService(IngredientRepository ingredientRepository){
        this.ingredientRepository = ingredientRepository ;
    }

    public List<Ingredient> getIngredientAndSimilarIngredient(String ingredient){
        return ingredientRepository.findSimilarIngredients(ingredient);
    }
    public List<Ingredient> getIngredientsContainTheName(String name ){
        return ingredientRepository.findIngredientsStartWithName(name);
    }

}
