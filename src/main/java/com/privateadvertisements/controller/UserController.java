package com.privateadvertisements.controller;

import com.privateadvertisements.api.dataJpa.CrudUser;
import com.privateadvertisements.model.User;
import com.privateadvertisements.model.dto.UserDto;
import com.privateadvertisements.util.Mapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/users")
public class UserController {
    private CrudUser crudUser;
    private Mapper mapper;

    public UserController(CrudUser userDao, Mapper mapper) {
        this.crudUser = userDao;
        this.mapper = mapper;
    }

    @GetMapping("/")
    public ResponseEntity<List<UserDto>> readAll() {
        Pageable pageable = PageRequest.of(0,10);
        Page<User> page = crudUser.findAll(pageable);
        List<UserDto> userDtoList = Mapper.convertList(page.getContent(), mapper::convertUserToUserDto);
        return new ResponseEntity<>(userDtoList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> read(@PathVariable(name = "id") Integer id) {
//        log.info("read");
        User user = crudUser.getById(id);
        UserDto userDto = mapper.convertUserToUserDto(user);
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable(name = "id") Integer id) {
//        log.info("delete");
        crudUser.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
