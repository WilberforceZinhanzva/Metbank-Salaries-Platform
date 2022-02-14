package zw.co.metbank.coresalariessystem.controllers.rest;

import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;
import zw.co.metbank.coresalariessystem.models.dtos.transferables.TransferablePermission;
import zw.co.metbank.coresalariessystem.models.dtos.transferables.TransferableRole;
import zw.co.metbank.coresalariessystem.models.extras.Password;
import zw.co.metbank.coresalariessystem.models.extras.PasswordConfirmation;
import zw.co.metbank.coresalariessystem.models.interfaces.Transferable;
import zw.co.metbank.coresalariessystem.services.UserService;

import java.security.Principal;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired
    private UserService userService;


    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    public ResponseEntity<Boolean> deleteUser(@PathVariable("id") String id){
        Boolean result = userService.deleteUser(id);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PutMapping("/{id}/lock")
    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    public ResponseEntity<Boolean> lockAccount(@PathVariable("id") String id){
        Boolean result = userService.lockAccount(id);
        return new ResponseEntity<>(result,HttpStatus.OK);
    }

    @PutMapping("/{id}/unlock")
    @PreAuthorize("hasRole('ROLE_SUPER_ADMIN')")
    public ResponseEntity<Boolean> unlockAccount(@PathVariable("id") String id){
        Boolean result = userService.unlockAccount(id);
        return new ResponseEntity<>(result,HttpStatus.OK);
    }

    @PostMapping(value="/password-reset",consumes = MediaType.APPLICATION_JSON_VALUE)
    //@PreAuthorize("isAuthenticated()")
    public ResponseEntity<Transferable> passwordReset(@RequestBody Password password){

        Transferable result = userService.changePassword(password.getPassword());
        return new ResponseEntity<>(result,HttpStatus.OK);
    }

    @PostMapping(value="/confirm-password", consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_LITE_ADMIN')")
    public ResponseEntity<PasswordConfirmation> confirmPassword(@RequestBody Password password){
        return new ResponseEntity<>(userService.confirmPassword(password.getPassword()),HttpStatus.OK);
    }


    //[ROLES & PERMISSIONS
    @GetMapping("/{userId}/roles")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN','LITE_ADMIN')")
    public ResponseEntity<List<TransferableRole>> userRoles(@PathVariable("userId") String userId){
        return ResponseEntity.ok(userService.userRoles(userId));
    }

    @GetMapping("/{userId}/permissions")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN','LITE_ADMIN')")
    public ResponseEntity<List<TransferablePermission>> userPermissions(@PathVariable("userId") String userId){
        return ResponseEntity.ok(userService.userPermissions(userId));
    }
    @PutMapping("/{userId}/revoke-roles")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<Transferable> revokeRoles(@PathVariable("userId") String userId,@RequestBody List<String> roleNames){
        Transferable result = userService.revokeRoles(userId, roleNames);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/{userId}/revoke-permissions")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<Transferable> revokePermissions(@PathVariable("userId") String userId,@RequestBody List<String> permissionNames){
        Transferable result = userService.revokePermissions(userId, permissionNames);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/{userId}/add-roles")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<Transferable> addRoles(@PathVariable("userId") String userId, @RequestBody List<String> roleNames ){
        Transferable result = userService.addRoles(userId, roleNames);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/{userId}/add-permissions")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<Transferable> addPermissions(@PathVariable("userId") String userId, @RequestBody List<String> permissionNames){
        Transferable result = userService.addPermissions(userId,permissionNames);
        return ResponseEntity.ok(result);
    }
}
