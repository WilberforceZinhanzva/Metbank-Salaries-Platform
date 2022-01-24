package zw.co.metbank.coresalariessystem.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import zw.co.metbank.coresalariessystem.models.entities.Permission;
import zw.co.metbank.coresalariessystem.models.entities.Role;
import zw.co.metbank.coresalariessystem.models.entities.User;
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
        if(user.isPresent()){
            user.get().print();
        }

        if(user.isEmpty()){
            throw new UsernameNotFoundException("Username not found");
        }

        return new org.springframework.security.core.userdetails.User(
                user.get().getUsername(),
                user.get().getPassword(),
                true,
                true,
                true,
                !user.get().getAccountLocked(),
                getAuthorities(user.get())
        );
    }

    private Collection<GrantedAuthority> getAuthorities(User user){

        List<Role> roles =user.getRoles();
        List<Permission> permissions = user.getPermissions();

        Set<GrantedAuthority> grantedAuthoritySet = permissions.stream().map(p->new SimpleGrantedAuthority(p.getName())).collect(Collectors.toSet());
        grantedAuthoritySet.addAll(roles.stream().map(r-> new SimpleGrantedAuthority("ROLE_"+r.getName())).collect(Collectors.toSet()));


        return grantedAuthoritySet;
    }
}
