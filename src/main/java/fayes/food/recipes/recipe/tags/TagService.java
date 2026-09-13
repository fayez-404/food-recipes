package fayes.food.recipes.recipe.tags;

import fayes.food.recipes.recipe.search_terms.SearchTerm;
import fayes.food.recipes.recipe.search_terms.SearchTermRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagService {

    private TagRepository tagRepository ;
    public TagService(TagRepository tagRepository){
        this.tagRepository = tagRepository ;
    }

    public List<Tag> getTagAndSimilar(String tag){
        return tagRepository.findSimilarTag(tag);
    }

    public List<Tag> getTagContainTheName(String name){
        return tagRepository.findTagStartWithName(name);
    }

}
