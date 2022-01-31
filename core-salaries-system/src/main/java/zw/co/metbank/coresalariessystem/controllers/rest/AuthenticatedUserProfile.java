package zw.co.metbank.coresalariessystem.controllers.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import zw.co.metbank.coresalariessystem.security.StreamlinedAuthenticatedUser;


@RestController
@RequestMapping("/api/v1/authenticated-user-profile")
public class AuthenticatedUserProfile {



    @PreAuthorize("isAuthenticated()")
    @GetMapping
    public ResponseEntity<StreamlinedAuthenticatedUser> userProfile(){
        StreamlinedAuthenticatedUser authenticatedUser = (StreamlinedAuthenticatedUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return new ResponseEntity<>(authenticatedUser, HttpStatus.OK);
    }
}
