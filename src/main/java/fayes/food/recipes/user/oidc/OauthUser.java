package fayes.food.recipes.user.oidc;

import fayes.food.recipes.user.UserPrincipal;
import jakarta.persistence.*;

@Entity
public class OauthUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;
    private String provider ;
    private String providerUserId;
    @OneToOne(fetch = FetchType.EAGER)
    private UserPrincipal userPrincipal ;

    public OauthUser() {
    }

    public OauthUser(String provider, String providerUserId, UserPrincipal userPrincipal) {
        this.provider = provider;
        this.providerUserId = providerUserId;
        this.userPrincipal = userPrincipal;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getProviderUserId() {
        return providerUserId;
    }

    public void setProviderUserId(String providerUserId) {
        this.providerUserId = providerUserId;
    }

    public UserPrincipal getUserPrincipal() {
        return userPrincipal;
    }

    public void setUserPrincipal(UserPrincipal userPrincipal) {
        this.userPrincipal = userPrincipal;
    }

    @Override
    public String toString() {
        return "OauthUser{" +
                "provider='" + provider + '\'' +
                ", providerUserId='" + providerUserId + '\'' +
                ", userPrincipal=" + userPrincipal +
                '}';
    }
}
