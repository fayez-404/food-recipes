package fayes.food.recipes.security;

import fayes.food.recipes.security.jwt.JwtFilter;
import fayes.food.recipes.user.UserPrincipalService;
import fayes.food.recipes.user.oidc.OidcSuccessHandler;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.servlet.util.matcher.PathPatternRequestMatcher;


@Configuration
public class SecurityConfiguration {

    private JwtFilter jwtFilter ;
    private CustomAuthenticationEntryPoint authenticationEntryPoint ;
    private OidcSuccessHandler oidcSuccessHandler ;

    SecurityConfiguration(JwtFilter jwtFilter,CustomAuthenticationEntryPoint authenticationEntryPoint,OidcSuccessHandler oidcSuccessHandler){
        this.jwtFilter = jwtFilter;
        this.authenticationEntryPoint = authenticationEntryPoint ;
        this.oidcSuccessHandler = oidcSuccessHandler ;
    }

    @Bean
    public SecurityFilterChain securityFilterChain (HttpSecurity http){
        return http.authorizeHttpRequests(
                request -> request
                        .requestMatchers(HttpMethod.PUT,"/api/v1/recipe/*").authenticated()
                        .requestMatchers("/oauth2/**"
                                ,"/login/oauth2/**"
                                ,"/api/v1/login"
                                ,"/api/v1/register"
                                ,"/swagger-ui/**"
                                ,"/v3/api-docs/**"
                                ,"/v3/api-docs"
                                ,"/api/v1/refresh-token"
                                ,"/api/v1/recipe/**"
                                ,"/api/v1/tags/**"
                                ,"/api/v1/ingredients/**").permitAll()
                        .anyRequest().authenticated()
                )
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .sessionManagement(
                        session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .exceptionHandling(
                        exception -> exception.defaultAuthenticationEntryPointFor(
                                authenticationEntryPoint,
                                PathPatternRequestMatcher.withDefaults().matcher("/api/**")
                        )
                )
                .oauth2Login(oauth -> oauth.successHandler(oidcSuccessHandler))
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
    @Bean
    public DaoAuthenticationProvider authenticationProvider(PasswordEncoder passwordEncoder, UserPrincipalService userService){
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider(userService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
        return daoAuthenticationProvider ;
    }


}
