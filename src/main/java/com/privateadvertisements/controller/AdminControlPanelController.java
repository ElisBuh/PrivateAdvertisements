package com.privateadvertisements.controller;

import com.privateadvertisements.api.service.IUserService;
import com.privateadvertisements.model.User;
import com.privateadvertisements.model.dto.UserDto;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/adminPanels")
public class AdminControlPanelController {

    private final IUserService userService;
    private final Mapper mapper;

    public AdminControlPanelController(IUserService userService, Mapper mapper) {
        this.userService = userService;
        this.mapper = mapper;
    }

    @PostMapping("/{id}/updateRating")
    public ResponseEntity<?> updateRating(@PathVariable(name = "id") Integer id,
                                          @RequestParam(name = "rating") Integer rating) {

        userService.updateRating(id, rating);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/{id}/updateStatus")
    public ResponseEntity<?> updateStatus(@PathVariable(name = "id") Integer id,
                                          @RequestParam(name = "status") Boolean status) {
        userService.updateStatus(id, status);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}/")
    public ResponseEntity<?> delete(@PathVariable(name = "id") Integer id) {
        userService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> get(@PathVariable(name = "id") Integer id) {
        User user = userService.get(id);
        UserDto userDto = mapper.convertUserToUserDto(user);
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    @GetMapping("/{login}")
    public ResponseEntity<UserDto> findByUserLogin(@PathVariable(name = "login") String login) {
        User user = userService.findByUserLogin(login);
        UserDto userDto = mapper.convertUserToUserDto(user);
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    @PostMapping("/{id}/changeRole")
    private ResponseEntity<?> changeRole(@PathVariable(name = "id") Integer id,
                                         @RequestParam(name = "role") String role) {
        userService.changeRole(id, role);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<List<UserDto>> getAllPagesAndSort(@RequestParam(name = "page", defaultValue = "0") Integer page,
                                                            @RequestParam(name = "size", defaultValue = "10") Integer size,
                                                            @RequestParam(name = "sort", defaultValue = "login") String sort) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
        Page<User> userPage = userService.getAllPagesAndSort(pageable);
        List<UserDto> userDtoList = Mapper.convertList(userPage.getContent(), mapper::convertUserToUserDto);
        return new ResponseEntity<>(userDtoList, HttpStatus.OK);
    }

}
