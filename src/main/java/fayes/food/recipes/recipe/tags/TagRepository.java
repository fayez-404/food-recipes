package fayes.food.recipes.recipe.tags;

import fayes.food.recipes.recipe.search_terms.SearchTerm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TagRepository extends JpaRepository<Tag,Long> {
    @Query(value = "SELECT * FROM tag WHERE tag % :tag",nativeQuery = true)
    public List<Tag> findSimilarTag(String tag);

    @Query(value = "SELECT * FROM tag WHERE tag LIKE '%'||:name || '%'",nativeQuery = true)
    public List<Tag> findTagStartWithName(String name);
}
