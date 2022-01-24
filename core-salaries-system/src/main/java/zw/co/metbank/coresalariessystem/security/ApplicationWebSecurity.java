package zw.co.metbank.coresalariessystem.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
public class ApplicationWebSecurity extends WebSecurityConfigurerAdapter {


    @Autowired
    private ApplicationUserDetailsService applicationUserDetailsService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    protected void configureGlobal(AuthenticationManagerBuilder auth) throws Exception{
        auth
                .userDetailsService(applicationUserDetailsService)
                .passwordEncoder(passwordEncoder);
    }

    public static final String[] SWAGGER_WHITELIST ={
            "/v2/api-docs",
            "/configuration/ui",
            "/swagger-resources/**",
            "/configuration/security",
            "/swagger-ui.html",
            "/webjars/**"
    };

    public static final String[] RESOURCES_WHITELIST={
            "/assets/**",
            "/javascript/**",
            "/styles/**"
    };


    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(SWAGGER_WHITELIST);
    }



    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers(SWAGGER_WHITELIST).permitAll()
                .antMatchers(RESOURCES_WHITELIST).permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .formLogin()
                    .loginPage("/api/v1/login")
                    .permitAll()
                    .successHandler(new RefererRedirectAuthenticationSuccessHandler())
                    .failureUrl("/api/v1/login?failure")
                .and()
                .logout()
                .logoutUrl("/api/v1/logout").permitAll()
                .logoutRequestMatcher(new AntPathRequestMatcher("/api/v1/logout","GET"))
                .clearAuthentication(true)
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .logoutSuccessUrl("/api/v1/login?logout");
    }



}
