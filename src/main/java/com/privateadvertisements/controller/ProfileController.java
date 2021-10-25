package com.privateadvertisements.controller;

import com.privateadvertisements.api.service.IUserService;
import com.privateadvertisements.model.Address;
import com.privateadvertisements.model.User;
import com.privateadvertisements.model.dto.AddressDto;
import com.privateadvertisements.model.dto.UserNewDto;
import com.privateadvertisements.util.Mapper;
import com.privateadvertisements.util.jwt.JwtRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/profiles")
public class ProfileController {

    private final IUserService userService;
    private final Mapper mapper;

    public ProfileController(IUserService userService, Mapper mapper) {
        this.userService = userService;
        this.mapper = mapper;
    }

    @PostMapping("/{id}/updatePassword")
    public ResponseEntity<?> updatePasswords(@PathVariable(name = "id") Integer id,
                                             @RequestBody String password) {
        User user = userService.get(id);
        user.setPasswords(password);
        userService.updatePasswords(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/{id}/changeAddress")
    public ResponseEntity<?> changeAddress(@PathVariable(name = "id") Integer id,
                                           @RequestBody AddressDto addressDto){
        Address address = mapper.convertAddressDtoToAddress(addressDto);
        userService.changeAddress(address, id);
        return new ResponseEntity<>(HttpStatus.OK);
    }




}
