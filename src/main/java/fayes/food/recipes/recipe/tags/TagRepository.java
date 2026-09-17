package fayes.food.recipes.recipe.tags;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TagRepository extends JpaRepository<Tag,Long> {
    @Query(value = "SELECT * FROM tag WHERE tag % :tag ORDER BY (tag = :tag) DESC,SIMILARITY(tag,:tag) DESC LIMIT 1",nativeQuery = true)
    public Optional<Tag> findTag(String tag);

    @Query(value = "SELECT * FROM tag WHERE tag LIKE '%'||:name || '%' LIMIT 10",nativeQuery = true)
    public List<Tag> findTagsContainsName(String name);
}
