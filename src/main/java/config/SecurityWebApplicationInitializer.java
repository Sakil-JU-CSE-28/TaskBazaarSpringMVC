package config;

import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;


public class SecurityWebApplicationInitializer extends AbstractSecurityWebApplicationInitializer {
    /*
     * as it extends AbstractSecurityWebApplicationInitializer, the container automatically initializes it.
     * it registers DelegatingFilterProxy that delegates filtering to Spring-managed beans (like UsernamePasswordAuthenticationFilter, CsrfFilter, etc.)
     * springSecurityFilterChain is created Defined in SecurityConfig (@EnableWebSecurity class)
     */
}