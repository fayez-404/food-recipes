package fayes.food.recipes.recipe.search_terms;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchTermService {

    private SearchTermRepository searchTermRepository ;
    public SearchTermService(SearchTermRepository searchTermRepository){
        this.searchTermRepository = searchTermRepository ;
    }

    public List<SearchTerm> getSearchTermAndSimilar(String searchTerm){
        return searchTermRepository.findSimilarSearchTerms(searchTerm);
    }

    public List<SearchTerm> getSearchTermsContainTheName(String name){
        return searchTermRepository.findSearchTermsContainsName(name);
    }
}
