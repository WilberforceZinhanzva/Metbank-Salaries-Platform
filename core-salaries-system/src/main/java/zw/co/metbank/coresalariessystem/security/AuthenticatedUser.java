package zw.co.metbank.coresalariessystem.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import zw.co.metbank.coresalariessystem.models.enums.Roles;

import java.util.Collection;
import java.util.List;
import java.util.Map;


public class AuthenticatedUser extends User {
    private static final long serialVersionUID = -3531439484732724601L;
    private final String fullname;
    private final String userId;
    private final List<Roles> roles ;
    private final Map<String,String> info ;

    public AuthenticatedUser(
            String username,
            String password,
            boolean enabled,
            boolean accountNonExpired,
            boolean credentialsNonExpired,
            boolean accountNonLocked,
            Collection<? extends GrantedAuthority> authorities,
            String fullname,
            String userId,
            List<Roles> roles,
            Map<String,String> info
            ) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        this.fullname = fullname;
        this.userId = userId;
        this.roles = roles;
        this.info = info;
    }


    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getFullname() {
        return fullname;
    }

    public String getUserId() {
        return userId;
    }

    public List<Roles> getRoles() {
        return roles;
    }

    public Map<String, String> getInfo() {
        return info;
    }
}
