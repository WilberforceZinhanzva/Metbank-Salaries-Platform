package zw.co.metbank.coresalariessystem.jwt;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UsernameAndPassword {
    private String username;
    private String password;
}
