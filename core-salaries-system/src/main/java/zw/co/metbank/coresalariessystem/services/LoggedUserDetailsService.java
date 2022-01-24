package zw.co.metbank.coresalariessystem.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zw.co.metbank.coresalariessystem.exceptions.ResourceNotFoundException;
import zw.co.metbank.coresalariessystem.models.entities.AdminProfile;
import zw.co.metbank.coresalariessystem.models.entities.BankUserProfile;
import zw.co.metbank.coresalariessystem.models.entities.ClientProfile;
import zw.co.metbank.coresalariessystem.models.entities.User;
import zw.co.metbank.coresalariessystem.models.extras.LoggedUserDetails;
import zw.co.metbank.coresalariessystem.repositories.UserRepository;

import java.util.Optional;

@Service
public class LoggedUserDetailsService {


    @Autowired
    private UserRepository userRepository;

    public LoggedUserDetails loggedUserDetails(String username){
        LoggedUserDetails details = new LoggedUserDetails();

        Optional<User> user = userRepository.findByUsername(username);
        if(user.isEmpty())
            throw new ResourceNotFoundException("Username "+ username+ " not found!");

        details.setId(user.get().getId());

        if(user.get().getProfile() instanceof AdminProfile) {
           AdminProfile adminProfile = (AdminProfile) user.get().getProfile();
           details.setFullname(adminProfile.getFullName());
        }else if(user.get().getProfile() instanceof ClientProfile){
            ClientProfile clientProfile = (ClientProfile) user.get().getProfile();
            details.setFullname(clientProfile.getFullName());
        }else if(user.get().getProfile() instanceof BankUserProfile){
            BankUserProfile bankUserProfile = (BankUserProfile)user.get().getProfile();
            details.setFullname(bankUserProfile.getFullName());
        }
        return details;
    }

}
