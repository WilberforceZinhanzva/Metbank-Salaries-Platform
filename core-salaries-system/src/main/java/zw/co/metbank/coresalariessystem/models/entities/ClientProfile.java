package zw.co.metbank.coresalariessystem.models.entities;

import lombok.Data;
import zw.co.metbank.coresalariessystem.models.dtos.transferables.TransferableClientProfile;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
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

    @Override
    public TransferableClientProfile serializeForTransfer() {
        return new TransferableClientProfile(this);
    }
}
