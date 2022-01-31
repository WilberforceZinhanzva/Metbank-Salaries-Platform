package zw.co.metbank.coresalariessystem.security;

import lombok.Data;
import lombok.NoArgsConstructor;
import zw.co.metbank.coresalariessystem.models.enums.Roles;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Data
@NoArgsConstructor
public class StreamlinedAuthenticatedUser {
    private String username;
    private boolean accountNonLocked;
    private String fullname;
    private String userId;
    private List<Roles> roles = new ArrayList<>();
    private List<AuthenticatedUserInfo> info = new ArrayList<>();

    public StreamlinedAuthenticatedUser(AuthenticatedUser authenticatedUser){
        this.username = authenticatedUser.getUsername();
        this.accountNonLocked = authenticatedUser.isAccountNonLocked();
        this.fullname = authenticatedUser.getFullname();
        this.userId = authenticatedUser.getUserId();
        this.roles = authenticatedUser.getRoles();

        for(String key : authenticatedUser.getInfo().keySet()){
            this.info.add( new AuthenticatedUserInfo(key,authenticatedUser.getInfo().get(key)));
        }
    }


    public String findInfoByKey(String key){
        Optional<AuthenticatedUserInfo> userInfo = this.info.stream().filter(a -> a.getKey().contentEquals(key)).findFirst();
        return userInfo.isPresent()? userInfo.get().getValue(): "";
    }
}
