package fayes.food.recipes.recipe.ingredients;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class IngredientController {

    private IngredientService ingredientService ;
    public  IngredientController(IngredientService ingredientService){
        this.ingredientService = ingredientService ;
    }

    @GetMapping("/ingredient")
    @ApiResponse(responseCode = "200",description = "used for the user when he enters a value for ingredient.")
    public ResponseEntity<List<Ingredient>> getIngredientAndSimilarIngredients(@RequestParam @NotBlank String ingredient){
        return ResponseEntity.ok().body(ingredientService.getIngredientAndSimilarIngredient(ingredient));
    }

    @ApiResponse(responseCode = "200",description = "used for the user when writing the ingredient he wants in the input.")
    @GetMapping("/ingredient/searching")
    public ResponseEntity<List<Ingredient>> getIngredientThatContainsName(@RequestParam @NotBlank String name){
        return ResponseEntity.ok().body(ingredientService.getIngredientsContainTheName(name));
    }

}
