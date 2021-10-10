package com.privateadvertisements.controller;

import com.privateadvertisements.api.dao.IUserDao;
import com.privateadvertisements.model.User;
import com.privateadvertisements.model.dto.UserDto;
import com.privateadvertisements.util.Mapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/users")
public class UserController {
    private IUserDao userDao;
    private Mapper mapper;

    public UserController(IUserDao userDao, Mapper mapper) {
        this.userDao = userDao;
        this.mapper = mapper;
    }

    @GetMapping("/")
    public ResponseEntity<List<UserDto>> readAll(){
        List<User> users = userDao.findAll();
        List<UserDto> userDtoList = Mapper.convertList(users,mapper::convertUserToUserDto);
        return new ResponseEntity<>(userDtoList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> read(@PathVariable(name = "id") Integer id) {
//        log.info("read");
        User user = userDao.findById(id).get();
        UserDto userDto = mapper.convertUserToUserDto(user);
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }
}
