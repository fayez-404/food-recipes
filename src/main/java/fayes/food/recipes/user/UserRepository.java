package fayes.food.recipes.user;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserPrincipal,Long> {
    Optional<UserPrincipal> findByEmail(String email);
    boolean existsByEmail(String email);
}
