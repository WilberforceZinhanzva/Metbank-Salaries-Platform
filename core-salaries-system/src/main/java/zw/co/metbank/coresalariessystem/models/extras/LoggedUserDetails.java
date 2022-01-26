package zw.co.metbank.coresalariessystem.models.extras;

import lombok.Data;
import lombok.NoArgsConstructor;
import zw.co.metbank.coresalariessystem.models.enums.Roles;
import zw.co.metbank.coresalariessystem.security.AuthenticatedUser;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class LoggedUserDetails {
    private String fullname;
    private String userId;
    private Boolean passwordRequired;
    private List<Roles> roles = new ArrayList<>();


    public LoggedUserDetails(AuthenticatedUser user){
        this.fullname = user.getFullname();
        this.userId = user.getUserId();
        this.roles = user.getRoles();
        this.passwordRequired = user.getInfo().get("passwordRequired").contentEquals("true");

    }
}
