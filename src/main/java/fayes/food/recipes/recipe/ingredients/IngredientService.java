package fayes.food.recipes.recipe.ingredients;

import fayes.food.recipes.exception.IngredientNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IngredientService {

    private IngredientRepository ingredientRepository ;
    IngredientService(IngredientRepository ingredientRepository){
        this.ingredientRepository = ingredientRepository ;
    }

    public Ingredient getIngredient(String ingredientRequest){
        Optional<Ingredient> ingredient = ingredientRepository.findIngredient(ingredientRequest) ;
        if (ingredient.isEmpty()){
            throw new IngredientNotFoundException("ingredient: "+ingredientRequest+" Not found");
        }
        return ingredient.get();
    }
    public List<Ingredient> getIngredientsContainTheName(String name ){
        return ingredientRepository.findIngredientsStartWithName(name);
    }

}
