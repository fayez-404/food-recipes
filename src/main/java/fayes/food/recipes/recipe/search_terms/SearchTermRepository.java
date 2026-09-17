package fayes.food.recipes.recipe.search_terms;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SearchTermRepository extends JpaRepository<SearchTerm,Long> {

    @Query(value = "SELECT * FROM search_term WHERE search_term % :search_term LIMIT 10",nativeQuery = true)
    public List<SearchTerm> findSimilarSearchTerms(String search_term);

    @Query(value = "SELECT * FROM search_term WHERE search_term LIKE '%'||:name || '%' LIMIT 10",nativeQuery = true)
    public List<SearchTerm> findSearchTermsContainsName(String name);
}
