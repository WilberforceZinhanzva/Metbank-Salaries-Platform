package zw.co.metbank.coresalariessystem.models.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import zw.co.metbank.coresalariessystem.models.dtos.transferables.TransferableClientCompany;
import zw.co.metbank.coresalariessystem.models.interfaces.Serializable;
import zw.co.metbank.coresalariessystem.util.GlobalMethods;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="client_company")
@Data
@NoArgsConstructor
public class ClientCompany implements Serializable {
    @Id
    private String id;
    private String name;
    @Column(name="registered_on")
    private LocalDate registeredOn;
    @Column(name="account_number")
    private String accountNumber;
    @Column(name="person_in_charge")
    private String personInCharge;
    private String address;
    private String email;
    private String phone;
    @Column(name="contact_person_name")
    private String contactPersonName;
    @Column(name="contact_person_phone")
    private String contactPersonPhone;
    @Column(name="contact_person_title")
    private String contactPersonTitle;
    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY,mappedBy = "clientCompany")
    private List<ClientProfile> usersForCompany = new ArrayList<>();
    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY,mappedBy = "clientCompany")
    private List<ClientCompanyActionLogger> actionLogs = new ArrayList<>();
    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<SalaryDisbursementRequest> salaryRequests = new ArrayList<>();



    @Override
    public TransferableClientCompany serializeForTransfer() {
        return new TransferableClientCompany(this);
    }
}
