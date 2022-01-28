package zw.co.metbank.coresalariessystem.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

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
        .antMatchers("/api/v1/login_handler").permitAll()
                .anyRequest()
                .authenticated()
                .and()
                //.exceptionHandling().accessDeniedHandler(restAccessDeniedHandler).authenticationEntryPoint(restAuthenticationEntryPoint)
                //.and()
                .formLogin()
                    .loginPage("/api/v1/login")
                    .permitAll()
                    .successHandler(new RefererRedirectAuthenticationSuccessHandler())
                    .failureHandler(authenticationFailureHandler())

                    //.failureUrl("/api/v1/login-failed")
                .and()
                .logout()
                .logoutUrl("/api/v1/logout").permitAll()
                .logoutRequestMatcher(new AntPathRequestMatcher("/api/v1/logout","GET"))
                .clearAuthentication(true)
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .logoutSuccessUrl("/api/v1/login?logout");
    }

    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler(){
        return new CustomAuthenticationFailureHandler();
    }

//    @Bean
//    CorsConfigurationSource corsConfigurationSource()
//    {
//        CorsConfiguration configuration = new CorsConfiguration();
//        configuration.setAllowedOrigins(Arrays.asList("*"));
//        configuration.setAllowCredentials(true);
//        configuration.setAllowedMethods(Arrays.asList("GET","POST"));
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", configuration);
//        return source;
//    }



}
