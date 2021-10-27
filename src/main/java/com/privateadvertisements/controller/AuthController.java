package com.privateadvertisements.controller;


import com.privateadvertisements.api.service.IUserService;
import com.privateadvertisements.model.User;
import com.privateadvertisements.model.dto.UserNewDto;
import com.privateadvertisements.util.Mapper;
import com.privateadvertisements.util.jwt.JwtProvider;
import com.privateadvertisements.util.jwt.JwtResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/")
public class AuthController {

    private final IUserService userService;
    private final JwtProvider jwtProvider;


    public AuthController(IUserService userService, JwtProvider jwtProvider, Mapper mapper) {
        this.userService = userService;
        this.jwtProvider = jwtProvider;
    }

    @PostMapping("/registration")
    public ResponseEntity<?> registerUser(@RequestBody UserNewDto userNewDto) {
        User user = new User();
        user.setPasswords(userNewDto.getPasswords());
        user.setLogin(userNewDto.getLogin());
        userService.save(user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/auth")
    public ResponseEntity<JwtResponse> auth(@RequestBody UserNewDto userNewDto) {
        User user = userService.findByUserLoginAndPassword(userNewDto.getLogin(), userNewDto.getPasswords());
        String token = jwtProvider.generateToken(user.getLogin());
        return new ResponseEntity<>(new JwtResponse(token), HttpStatus.OK);
    }
}