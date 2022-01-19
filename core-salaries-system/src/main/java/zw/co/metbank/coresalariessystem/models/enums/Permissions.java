package zw.co.metbank.coresalariessystem.models.enums;

public enum Permissions {
    RegisterClients("Register Clients","User can register clients into the system"),
    RegisterAdmins("Register Admins","User can register admins into the system"),
    RegisterBanks("Register Banks","User can register banks into the system"),
    DeleteClients("Delete Clients","User can delete clients from the system"),
    DeleteAdmins("Delete Admins","User can delete admins from the system"),
    DeleteBanks("Delete Banks","User can delete banks from the system"),
    ChangeUserPermissions("Change User Permissions","User can change user permissions"),

    InitiateSalaryRequest("Initiate Salary Request","Client can initiate a salary request"),
    AuthorizeSalaryRequest("Authorize Salary Request","Client can authorize a salary request"),
    ReviewSalaryRequest("Review Salary Request","Admin can review a salary request"),
    ApproveSalaryRequest("Approve Salary Request","Admin can approve a salary request"),
    DeclineSalaryRequest("Decline Salary Request","Admin can decline a salary request"),
    DownloadFiles("Download Files","User can download files");

    public final String name;
    public final String description;

    Permissions(String name, String description) {
        this.name = name;
        this.description = description;
    }

}
