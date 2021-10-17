package com.privateadvertisements.controller;

import com.privateadvertisements.api.repository.CrudUser;
import com.privateadvertisements.api.service.IUserService;
import com.privateadvertisements.exception.ServiceException;
import com.privateadvertisements.model.Address;
import com.privateadvertisements.model.Role;
import com.privateadvertisements.model.User;
import com.privateadvertisements.model.dto.AddressDto;
import com.privateadvertisements.model.dto.UserDto;
import com.privateadvertisements.model.dto.UserNewDto;
import com.privateadvertisements.service.UserService;
import com.privateadvertisements.util.Mapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/users")
public class UserController {
    private IUserService userService;
    private Mapper mapper;

    public UserController(IUserService userService, Mapper mapper) {
        this.userService = userService;
        this.mapper = mapper;
    }

    @GetMapping("/")
    public ResponseEntity<List<UserDto>> readAll() {
        Pageable pageable = PageRequest.of(0,10, Sort.by("login"));
        Page<User> page = userService.getAllPagesAndSort(pageable);
        List<UserDto> userDtoList = Mapper.convertList(page.getContent(), mapper::convertUserToUserDto);
        return new ResponseEntity<>(userDtoList, HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<?> create(@RequestBody UserNewDto userNewDto){
        System.out.println();
        User user = mapper.convertUserNewDtoToUser(userNewDto);
        userService.save(user);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> read(@PathVariable(name = "id") Integer id) {
//        log.info("read");
        User user = userService.get(id);
        UserDto userDto = mapper.convertUserToUserDto(user);
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable(name = "id") Integer id) {
//        log.info("delete");
        userService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/test")
    public ResponseEntity<?> test(){
        userService.changeRole("petr@gmail.com","role_admin");
        userService.changeRole("petr@gmail.com","role_admin");

        return new  ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/test")
    public ResponseEntity<?> testPost(@RequestBody AddressDto addressDto){
        Address address = mapper.convertAddressDtoToAddress(addressDto);
        userService.changeAddress(address,100011);

        return new  ResponseEntity<>(HttpStatus.OK);
    }
}
