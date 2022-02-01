package zw.co.metbank.coresalariessystem.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import zw.co.metbank.coresalariessystem.exceptions.InvalidPasswordException;
import zw.co.metbank.coresalariessystem.exceptions.ResourceNotFoundException;
import zw.co.metbank.coresalariessystem.models.entities.User;
import zw.co.metbank.coresalariessystem.models.interfaces.Transferable;
import zw.co.metbank.coresalariessystem.repositories.UserRepository;
import zw.co.metbank.coresalariessystem.security.StreamlinedAuthenticatedUser;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Boolean deleteUser(String id){
        Optional<User> user = userRepository.findById(id);
        if(user.isEmpty())
            throw new ResourceNotFoundException("User not found!");
        userRepository.delete(user.get());
        return true;
    }

    public Boolean lockAccount(String id){
        Optional<User> user = userRepository.findById(id);
        if(user.isEmpty())
            throw new ResourceNotFoundException("User not found!");
        user.get().setAccountLocked(true);
        userRepository.save(user.get());
        return true;
    }

    public Boolean unlockAccount(String id){
        Optional<User> user = userRepository.findById(id);
        if(user.isEmpty())
            throw new ResourceNotFoundException("User not found!");
        user.get().setAccountLocked(false);
        userRepository.save(user.get());
        return true;
    }


    public Transferable changePassword(String newPassword){
        StreamlinedAuthenticatedUser authenticatedUser = (StreamlinedAuthenticatedUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<User> user = userRepository.findById(authenticatedUser.getUserId());
        if(user.isEmpty())
            throw new ResourceNotFoundException("User not found!");

        String encodedPassword = passwordEncoder.encode(newPassword);

        if(passwordEncoder.matches(user.get().getUsername(),encodedPassword))
            throw new InvalidPasswordException("Password cannot be similar to your username!");
        if(newPassword.length() < 8)
            throw new InvalidPasswordException("Password must be at least 8 characters long!");

        user.get().setPassword(encodedPassword);
        user.get().setPasswordRequired(false);

        return userRepository.save(user.get()).serializeForTransfer();

    }


}
