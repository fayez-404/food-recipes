package fayes.food.recipes.recipe.tags;

import fayes.food.recipes.exception.TagNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TagService {

    private TagRepository tagRepository ;
    public TagService(TagRepository tagRepository){
        this.tagRepository = tagRepository ;
    }

    public Tag getTagAndSimilar(String tagRequest){
        Optional<Tag> tag = tagRepository.findTag(tagRequest);
        if(tag.isEmpty()){
            throw new TagNotFoundException("tag: "+tagRequest+" found tag");
        }
        return tag.get() ;
    }

    public List<Tag> getTagContainTheName(String name){
        return tagRepository.findTagStartWithName(name);
    }

}
