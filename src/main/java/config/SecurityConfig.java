package config;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import service.CustomUserDetailsService;

@Slf4j
@Configuration
@EnableWebSecurity
@AllArgsConstructor
@ComponentScan(basePackages = "controller dao config dto exception model service util validation mapper")
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomUserDetailsService userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        log.info("Configuring AuthenticationManagerBuilder");
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/", "/register", "/login", "/css/**", "/js/**", "/images/**").permitAll()
                .antMatchers("/posts/add").hasRole("BUYER")
                .antMatchers("/posts/edit/**").hasRole("BUYER")
                .antMatchers("/posts/save").hasRole("BUYER")
                .antMatchers("/posts/update").hasRole("BUYER")
                .antMatchers("/bids/add/**").hasRole("FREELANCER")
//                .antMatchers("/bids/save").hasRole("FREELANCER")
//                .antMatchers("/bids/view/**").hasAnyRole("FREELANCER", "BUYER")
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/users/block/**").hasRole("ADMIN")
                .antMatchers("/users/unblock/**").hasRole("ADMIN")
                .antMatchers("/posts/delete/**").hasAnyRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login-form")
                .loginProcessingUrl("/login")
                .defaultSuccessUrl("/posts", true)
                .failureUrl("/login-form?error=true")
                .permitAll()
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/login-form?logout=true")
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .permitAll();
    }
}
