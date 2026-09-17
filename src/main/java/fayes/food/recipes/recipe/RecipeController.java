package fayes.food.recipes.recipe;


import fayes.food.recipes.user.UserPrincipal;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/v1")
public class RecipeController {

    private RecipeService recipeService ;

    public RecipeController(RecipeService recipeService){
        this.recipeService = recipeService ;
    }

    @GetMapping("/recipe/tags-ingredients")
    @Operation(description = "retrives recipes that have these tags and have any ingredient from specified ingredients")
    public ResponseEntity<Page<RecipeResponse>> getRecipesByTagsAndIngredients(@RequestParam(required = false) Set<String> ingredients
            , @RequestParam(required = false) Set<String> tags
            , @RequestParam(required = false,defaultValue = "0") int page
            , @RequestParam(required = false,defaultValue = "10") int size){
        Pageable pageable = PageRequest.of(page,size);
        return ResponseEntity.ok().body(recipeService.retrieveRecipes(tags,ingredients,pageable));
    }

    @GetMapping("/recipe/{recipeName}")
    @Operation(description = "retrives recipe with specified name or the most close name if the name doesn't exist")
    public ResponseEntity<Page<RecipeResponse>> getRecipesByName(@PathVariable String recipeName
            ,@RequestParam(required = false,defaultValue = "0") int page
            ,@RequestParam(required = false,defaultValue = "10") int size){
        return ResponseEntity.ok(recipeService.findRecipeAndSimilarRecipes(recipeName,PageRequest.of(page,size)));
    }

    @GetMapping("/recipe/{recipeName}/contains")
    @Operation(description = "retrives recipe that contains the specified name ")
    public ResponseEntity<Page<RecipeResponse>> getRecipesContainsName(@PathVariable String recipeName
            ,@RequestParam(required = false,defaultValue = "0") int page
            ,@RequestParam(required = false,defaultValue = "10") int size){
        return ResponseEntity.ok(recipeService.findRecipeContainsName(recipeName,PageRequest.of(page,size)));
    }

    @PutMapping("/recipe/{id}")
    @Operation(description = "add the recipe to user favorites using the id of the recipe")
    public ResponseEntity<Object> addRecipeToFavourites(@AuthenticationPrincipal UserPrincipal userPrincipal
            ,@PathVariable Long id){
        recipeService.addRecipeToFavorites(userPrincipal,id);
        return ResponseEntity.ok().build() ;
    }

}
