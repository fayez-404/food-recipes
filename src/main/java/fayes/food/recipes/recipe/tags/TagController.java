package fayes.food.recipes.recipe.tags;

import fayes.food.recipes.recipe.search_terms.SearchTerm;
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
public class TagController {

    private TagService tagService ;
    public TagController(TagService tagService){
        this.tagService = tagService ;
    }
    @GetMapping("/tag")
    @ApiResponse(responseCode = "200",description = "used for the user when writing the tag he wants in the input.")
    public ResponseEntity<List<Tag>> getTagAndSimilarTag(@RequestParam String tag){
        return ResponseEntity.ok().body(tagService.getTagAndSimilar(tag));
    }

    @ApiResponse(responseCode = "200",description = "used for the user when writing the tag he wants in the input.")
    @GetMapping("/tag/searching")
    public ResponseEntity<List<Tag>> getTagThatContainsName(@RequestParam @NotBlank String name){
        return ResponseEntity.ok().body(tagService.getTagContainTheName(name));
    }

}
