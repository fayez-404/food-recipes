package fayes.food.recipes.recipe;

import fayes.food.recipes.exception.IngredientsAndTagsEmptyException;
import fayes.food.recipes.exception.RecipeNotFoundException;
import fayes.food.recipes.recipe.ingredients.IngredientService;
import fayes.food.recipes.recipe.tags.TagService;
import fayes.food.recipes.user.UserPrincipal;
import fayes.food.recipes.user.UserRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class RecipeService {

    private TagService tagService ;
    private IngredientService ingredientService ;
    private RecipeRepository recipeRepository ;
    private UserRepository userRepository ;

    public RecipeService(TagService tagService
            ,IngredientService ingredientService
            ,RecipeRepository recipeRepository
            ,UserRepository userRepository){
        this.tagService = tagService ;
        this.ingredientService = ingredientService ;
        this.recipeRepository = recipeRepository ;
        this.userRepository = userRepository ;
    }

    public List<Recipe> retriveRecipes(Set<String> tags , Set<String> ingredients, Pageable pageable){
        if((ingredients == null || ingredients.isEmpty())&&(tags == null || tags.isEmpty())){
            throw new IngredientsAndTagsEmptyException("you can't leave tags and ingredients null or empty at least one is given ");
        }
        List<Long> ingredientsIds = new ArrayList<>();
        List<Long> tagsIds = new ArrayList<>();
        if (tags != null && !tags.isEmpty()){
            tags.forEach(
                    tag -> tagsIds.add(tagService.getTagAndSimilar(tag).getId())
            );
        }
        if (ingredients != null && !ingredients.isEmpty()){
            ingredients.forEach(
                    ingredient ->  ingredientsIds.add(ingredientService.getIngredient(ingredient).getId())
            );
        }
        if (ingredients == null || ingredients.isEmpty()){
            return recipeRepository.findAllByTags(tagsIds,tags.size(),pageable);
        }
        else if (tags == null || tags.isEmpty()){
            return recipeRepository.findAllByIngredients(ingredientsIds,pageable);
        }
        else{
            return recipeRepository.findAllByTagsAndIngredients(tagsIds,tagsIds.size(),ingredientsIds,pageable);
        }
    }

    public List<Recipe> findRecipeAndSimilarRecipes(String recipe, Pageable pageable){
        return recipeRepository.findRecipeAndSimilarRecipes(recipe,pageable);
    }

    public List<Recipe> findRecipeContainsName(String name,Pageable pageable){
        return recipeRepository.findRecipeContainName(name,pageable);
    }

    public Recipe findById(Long id) {
        Optional<Recipe> recipe = recipeRepository.findById(id);
        if(recipe.isEmpty()){
            throw new RecipeNotFoundException("recipe with id: "+id+" not found");
        }
        return recipe.get();
    }

    public void addRecipeToFavorites(UserPrincipal userPrincipal, Long id) {
        Recipe recipe = findById(id) ;
        userPrincipal.getFavourites().add(recipe);
        recipe.getUsers().add(userPrincipal);
        userRepository.save(userPrincipal);
        recipeRepository.save(recipe);
    }
}
