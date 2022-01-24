package zw.co.metbank.coresalariessystem.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import zw.co.metbank.coresalariessystem.models.enums.Roles;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class RefererRedirectAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    public RefererRedirectAuthenticationSuccessHandler(){
        super();
        setUseReferer(true);
    }


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        handle(request,response,authentication);
        clearAuthenticationAttributes(request);
    }

    @Override
    protected void handle(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String targetUrl = determineTargetUrl(authentication);
        if(response.isCommitted())
            return;
        redirectStrategy.sendRedirect(request,response,targetUrl);
    }

    protected String determineTargetUrl(final Authentication authentication){
        Map<String,String> roleTargetUrlMap = new HashMap<>();
        roleTargetUrlMap.putIfAbsent("ROLE_"+ Roles.SUPER_ADMIN.name(),"/api/v1/login_handler");
        roleTargetUrlMap.putIfAbsent("ROLE_"+ Roles.LITE_ADMIN.name(),"/api/v1/login_handler");
        roleTargetUrlMap.putIfAbsent("ROLE_"+ Roles.SUPER_CLIENT.name(),"/api/v1/login_handler");
        roleTargetUrlMap.putIfAbsent("ROLE_"+ Roles.LITE_CLIENT.name(),"/api/v1/login_handler");
        roleTargetUrlMap.putIfAbsent("ROLE_"+ Roles.SUPER_BANK_USER.name(),"/api/v1/login_handler");
        roleTargetUrlMap.putIfAbsent("ROLE_"+ Roles.LITE_BANK_USER.name(),"/api/v1/login_handler");

        final Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        for(final GrantedAuthority grantedAuthority : authorities){
            String authorityName = grantedAuthority.getAuthority();
            if(roleTargetUrlMap.containsKey(authorityName)){
                return roleTargetUrlMap.get(authorityName);
            }
        }
        throw new IllegalStateException();
    }
}
