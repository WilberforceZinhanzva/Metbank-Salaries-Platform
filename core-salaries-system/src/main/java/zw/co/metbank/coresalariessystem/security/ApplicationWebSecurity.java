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
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import zw.co.metbank.coresalariessystem.jwt.JwtTokenVerifier;
import zw.co.metbank.coresalariessystem.jwt.JwtUsernamePasswordAuthenticationFilter;

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
            "/authenticate",
            "/v3/api-docs",
            "/swagger-resources/**",
            "/swagger-ui/**",
            "/webjars/**"
    };




    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(SWAGGER_WHITELIST);
    }



    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http
//                .cors().and().csrf().disable()
//                .headers()
//                .frameOptions()
//                .deny()
                .cors()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilter(new JwtUsernamePasswordAuthenticationFilter(authenticationManager()))
                .addFilterAfter(new JwtTokenVerifier(),JwtUsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers(SWAGGER_WHITELIST).permitAll()
                .anyRequest()
                .authenticated();

//                .authorizeRequests()
//                .antMatchers(SWAGGER_WHITELIST).permitAll()
//        .antMatchers("/api/v1/login_handler").permitAll()
//                .anyRequest()
//                .authenticated()
//                .and()
//                //.exceptionHandling().accessDeniedHandler(restAccessDeniedHandler).authenticationEntryPoint(restAuthenticationEntryPoint)
//                //.and()
//                .formLogin()
//                    .loginPage("/api/v1/login")
//                    .permitAll()
//                    .successHandler(new RefererRedirectAuthenticationSuccessHandler())
//                    .failureHandler(authenticationFailureHandler())
//
//                    //.failureUrl("/api/v1/login-failed")
//                .and()
//                .logout()
//                .logoutUrl("/api/v1/logout").permitAll()
//                .logoutRequestMatcher(new AntPathRequestMatcher("/api/v1/logout","GET"))
//                .clearAuthentication(true)
//                .invalidateHttpSession(true)
//                .deleteCookies("JSESSIONID")
//                .logoutSuccessUrl("/api/v1/login?logout");
    }

    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler(){
        return new CustomAuthenticationFailureHandler();
    }

    @Bean
    public WebMvcConfigurer corsConfigurer(){
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/login").allowedOrigins("http://192.167.1.203:8000");
            }
        };
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
