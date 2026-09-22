package fayes.food.recipes.user.oidc;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OauthUserRepository extends JpaRepository<OauthUser,Long> {

    public Optional<OauthUser> findByProviderAndProviderUserId(String provider, String providerUserId);
}
