package zw.co.metbank.coresalariessystem.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import zw.co.metbank.coresalariessystem.models.entities.*;
import zw.co.metbank.coresalariessystem.models.enums.Roles;
import zw.co.metbank.coresalariessystem.repositories.UserRepository;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ApplicationUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUsername(username);

        if(user.isEmpty()){
            throw new UsernameNotFoundException("Username not found");
        }

        String fullname = "";
        List<Roles> roles = user.get().getRoles().stream().map(u-> Roles.valueOf(u.getName())).collect(Collectors.toList());
        Map<String,String> info = new HashMap<>();

        info.putIfAbsent("passwordRequired",user.get().getPasswordRequired().toString());

        if(user.get().getProfile() instanceof ClientProfile){
            ClientProfile profile = (ClientProfile) user.get().getProfile();
            fullname = profile.getFullName();
            info.putIfAbsent("companyId",profile.getClientCompany().getId());
        }
        if(user.get().getProfile() instanceof AdminProfile){
            AdminProfile profile = (AdminProfile) user.get().getProfile();
            fullname = profile.getFullName();
        }

        AuthenticatedUser authenticatedUser = new AuthenticatedUser(
                user.get().getUsername(),
                user.get().getPassword(),
                true,
                true,
                true,
                !user.get().getAccountLocked(),
                getAuthorities(user.get()),
                fullname,
                user.get().getId(),
                roles,
                info

        );

//
        return authenticatedUser;
    }

    private Collection<GrantedAuthority> getAuthorities(User user){

        List<Role> roles =user.getRoles();
        List<Permission> permissions = user.getPermissions();

        Set<GrantedAuthority> grantedAuthoritySet = permissions.stream().map(p->new SimpleGrantedAuthority(p.getName())).collect(Collectors.toSet());
        grantedAuthoritySet.addAll(roles.stream().map(r-> new SimpleGrantedAuthority("ROLE_"+r.getName())).collect(Collectors.toSet()));


        return grantedAuthoritySet;
    }
}
