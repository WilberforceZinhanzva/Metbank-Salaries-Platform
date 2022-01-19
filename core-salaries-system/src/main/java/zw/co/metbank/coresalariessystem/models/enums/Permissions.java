package zw.co.metbank.coresalariessystem.models.enums;

public enum Permissions {
    RegisterClients("User can register clients into the system"),
    RegisterAdmins("User can register admins into the system"),
    RegisterBanks("User can register banks into the system"),
    DeleteClients("User can delete clients from the system"),
    DeleteAdmins("User can delete admins from the system"),
    DeleteBanks("User can delete banks from the system"),
    ChangeUserPermissions("User can change user permissions"),

    InitiateSalaryRequest("Client can initiate a salary request"),
    AuthorizeSalaryRequest("Client can authorize a salary request"),
    ReviewSalaryRequest("Admin can review a salary request"),
    ApproveSalaryRequest("Admin can approve a salary request"),
    DeclineSalaryRequest("Admin can decline a salary request"),
    DownloadFiles("User can download files");

    public final String description;

    Permissions(String description) {
        this.description = description;
    }
}
