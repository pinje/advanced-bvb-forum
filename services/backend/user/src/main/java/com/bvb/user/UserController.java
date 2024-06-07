package com.bvb.user;

import com.bvb.user.business.DeleteUserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("user")
@AllArgsConstructor
@Tag(name = "User Management")
public class UserController {
    private final DeleteUserService deleteUserService;

    @PreAuthorize("hasAnyAuthority('[MEMBER]','[ADMIN]')")
    @DeleteMapping("protected/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable long userId) {
        deleteUserService.deleteUser(userId);
        return ResponseEntity.ok().build();
    }
}
