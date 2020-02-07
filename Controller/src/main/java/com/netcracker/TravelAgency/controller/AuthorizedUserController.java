package com.netcracker.TravelAgency.controller;

import com.netcracker.TravelAgency.dto.AuthorizedUserDto;
import com.netcracker.TravelAgency.service.impl.AuthorizedUserServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class AuthorizedUserController {

    private AuthorizedUserServiceImpl userService;
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthorizedUserController.class);
    @Autowired
    public AuthorizedUserController(AuthorizedUserServiceImpl userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}/profile")
    @PreAuthorize("hasAuthority('OPERATOR')")
    public ResponseEntity<AuthorizedUserDto> getProfile(@PathVariable int id) {
        LOGGER.info("REST request. Path:/users/{}/profile method: GET.", id);
        if (id <= 0) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        String username = getUsernameOfPrincipal();

        if (userService.findByLogin(username).isPresent()) {
            if (userService.findByLogin(username).get().getId() == id) {
                return new ResponseEntity<>(userService.findById(id), HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    private String getUsernameOfPrincipal() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;
        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }
        return username;
    }

}
