package com.privateadvertisements.controller;

import com.privateadvertisements.api.service.IUserService;
import com.privateadvertisements.model.User;
import com.privateadvertisements.model.dto.UserDto;
import com.privateadvertisements.util.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@RestController
@RequestMapping(value = "/adminPanels")
public class AdminControlPanelController {
    private static final Logger log = LoggerFactory.getLogger(AdminControlPanelController.class);

    private final IUserService userService;
    private final Mapper mapper;

    public AdminControlPanelController(IUserService userService, Mapper mapper) {
        this.userService = userService;
        this.mapper = mapper;
    }

    @PostMapping("/{id}/updateRating")
    public ResponseEntity<?> updateRating(@PathVariable(name = "id") Integer id,
                                          @RequestParam(name = "rating") @Size(min = 1, max = 100) Integer rating) {
        log.info("update rating to UserId: {} on {}", id, rating);
        userService.updateRating(id, rating);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/{id}/updateStatus")
    public ResponseEntity<?> updateStatus(@PathVariable(name = "id") Integer id,
                                          @RequestParam(name = "status") Boolean status) {
        log.info("Update status userId: {} on {}", id, status);
        userService.updateStatus(id, status);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable(name = "id") Integer id) {
        log.info("delete user: {}", id);
        userService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> get(@PathVariable(name = "id") Integer id) {
        log.info("get user: {}", id);
        User user = userService.get(id);
        UserDto userDto = mapper.convertUserToUserDto(user);
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    @GetMapping("/find")
    public ResponseEntity<UserDto> findByUserLogin(@RequestParam(name = "login") @Email String login) {
        log.info("get user: {}", login);
        User user = userService.findByUserLogin(login);
        UserDto userDto = mapper.convertUserToUserDto(user);
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    @PostMapping("/{id}/changeRole")
    public ResponseEntity<?> changeRole(@PathVariable(name = "id") Integer id,
                                         @RequestParam(name = "role") @NotBlank String role) {
        log.info("add role to userId: {}, on {}", id, role);

        userService.changeRole(id, role);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<List<UserDto>> getAllPagesAndSort(@RequestParam(name = "page", defaultValue = "0") @Min(0) Integer page,
                                                            @RequestParam(name = "size", defaultValue = "10") @Max(100) Integer size,
                                                            @RequestParam(name = "sort", defaultValue = "login") String sort) {
        log.info("get all users");
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
        Page<User> userPage = userService.getAllPagesAndSort(pageable);
        List<User> userList = userPage.getContent();
        List<UserDto> userDtoList = Mapper.convertList(userList, mapper::convertUserToUserDto);
        return new ResponseEntity<>(userDtoList, HttpStatus.OK);
    }

}
