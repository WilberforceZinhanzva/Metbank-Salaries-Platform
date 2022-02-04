package zw.co.metbank.coresalariessystem.models.extras;

import lombok.Data;

@Data
public class PasswordConfirmation {
    public Boolean status;

    public PasswordConfirmation(Boolean status) {
        this.status = status;
    }
}
