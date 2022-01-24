package zw.co.metbank.coresalariessystem.models.entities;

import lombok.Data;
import zw.co.metbank.coresalariessystem.models.dtos.transferables.TransferableClientProfile;

import javax.persistence.*;

@Data
@Entity
@Table(name="client_profiles")
public class ClientProfile extends UserProfile{

    @Column(name="fullname", nullable = false)
    private String fullName;
    @Column(name="email",nullable = false)
    private String email;
    @Column(name="phonenumber",nullable = false)
    private String phoneNumber;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="client_company")
    private ClientCompany clientCompany;

    @Override
    public TransferableClientProfile serializeForTransfer() {
        return new TransferableClientProfile(this);
    }
}
