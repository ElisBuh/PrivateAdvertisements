package com.privateadvertisements.controller;

import com.privateadvertisements.api.dao.IAdvertisementDao;
import com.privateadvertisements.model.Advertisement;
import com.privateadvertisements.model.dto.AdvertisementDto;
import com.privateadvertisements.util.Mapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/advertisements")
public class AdvertisementController {
//    private static final Logger log = LoggerFactory.getLogger(AdvertisementController.class);

    private final Mapper mapper;
    private final IAdvertisementDao advertisementDao;

    public AdvertisementController(Mapper mapper, IAdvertisementDao advertisementDao) {
        this.mapper = mapper;
        this.advertisementDao = advertisementDao;
    }

    @GetMapping("/{id}")
    public ResponseEntity<AdvertisementDto> read(@PathVariable(name = "id") Integer id) {
//        log.info("read");
        Advertisement advertisement = advertisementDao.get(id);
//        UserDto userDto = mapper.convertUserToUserDto(user);
        AdvertisementDto advertisementDto = mapper.convertAdvertisementToAdvertisementDto(advertisement);
        return new ResponseEntity<>(advertisementDto, HttpStatus.OK);
    }


}
