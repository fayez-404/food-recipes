package fayes.food.recipes.recipe.ingredients;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class IngredientController {

    private IngredientService ingredientService ;
    public  IngredientController(IngredientService ingredientService){
        this.ingredientService = ingredientService ;
    }

    @GetMapping()
    public ResponseEntity<List<Ingredient>> getIngredientAndSimilarIngredients(@RequestParam String ingredient){
        return ResponseEntity.status(HttpStatus.OK).body(ingredientService.getIngredientAndSimilarIngredient(ingredient));
    }

}
