package fayes.food.recipes.recipe.search_terms;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class SearchTermController {

    private SearchTermService searchTermService ;
    public SearchTermController(SearchTermService searchTermService){
        this.searchTermService = searchTermService ;
    }

    @GetMapping("/search-term")
    @ApiResponse(responseCode = "200",description = "used for the user when writing the searchTerm he wants in the input.")
    public ResponseEntity<List<SearchTerm>> getSearchTermAndSimilarSearchTerm(@RequestParam String searchTerm){
        return ResponseEntity.ok().body(searchTermService.getSearchTermAndSimilar(searchTerm));
    }

    @ApiResponse(responseCode = "200",description = "used for the user when writing the searchTerm he wants in the input.")
    @GetMapping("/search-term/searching")
    public ResponseEntity<List<SearchTerm>> getIngredientThatContainsName(@RequestParam @NotBlank String name){
        return ResponseEntity.ok().body(searchTermService.getSearchTermsContainTheName(name));
    }
}
