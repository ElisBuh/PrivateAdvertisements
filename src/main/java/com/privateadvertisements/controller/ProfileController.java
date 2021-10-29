package com.privateadvertisements.controller;

import com.privateadvertisements.api.service.IUserService;
import com.privateadvertisements.model.Address;
import com.privateadvertisements.model.CreditCard;
import com.privateadvertisements.model.PersonalUserInfo;
import com.privateadvertisements.model.dto.AddressDto;
import com.privateadvertisements.model.dto.CreditCardDto;
import com.privateadvertisements.model.dto.PersonalUserInfoDto;
import com.privateadvertisements.util.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/profiles")
public class ProfileController {
    private static final Logger log = LoggerFactory.getLogger(ProfileController.class);

    private final IUserService userService;
    private final Mapper mapper;

    public ProfileController(IUserService userService, Mapper mapper) {
        this.userService = userService;
        this.mapper = mapper;
    }

    @PostMapping("/{id}/updatePassword")
    public ResponseEntity<?> updatePasswords(@PathVariable(name = "id") Integer id,
                                             @RequestBody String password) {
        log.info("updatePasswords idUser{}", id);
        userService.updatePasswords(id, password);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/{id}/changeAddress")
    public ResponseEntity<?> changeAddress(@PathVariable(name = "id") Integer id,
                                           @RequestBody AddressDto addressDto) {
        log.info("changeAddress idUser{}:", id);
        Address address = mapper.convertAddressDtoToAddress(addressDto);
        userService.changeAddress(address, id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/{id}/changePersonalInfo")
    private ResponseEntity<?> changePersonalInfo(@PathVariable(name = "id") Integer id,
                                                 @RequestBody PersonalUserInfoDto personalUserInfoDto) {
        log.info("changePersonalInfo UserId{}", id);
        PersonalUserInfo personalUserInfo = mapper.convertPersonalUserInfoDtoToPersonalUserInfo(personalUserInfoDto);
        userService.changePersonalInfo(personalUserInfo, id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/{id}/changeCreditCard")
    private ResponseEntity<?> changeCreditCard(@PathVariable(name = "id") Integer id,
                                               @RequestBody CreditCardDto creditCardDto) {
        log.info("changeCreditCard UserId{}", id);
        CreditCard creditCard = mapper.convertCreditCardDtoToCreditCard(creditCardDto);
        userService.changeCreditCard(id, creditCard);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
