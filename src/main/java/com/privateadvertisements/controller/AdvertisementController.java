package com.privateadvertisements.controller;


import com.privateadvertisements.api.service.IAdvertisementService;
import com.privateadvertisements.model.Advertisement;
import com.privateadvertisements.model.dto.AdvertisementDto;
import com.privateadvertisements.util.Mapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/advertisements")
public class AdvertisementController {
//    private static final Logger log = LoggerFactory.getLogger(AdvertisementController.class);

    private final Mapper mapper;
    private final IAdvertisementService advertisementService;

    public AdvertisementController(Mapper mapper, IAdvertisementService advertisementService) {
        this.mapper = mapper;
        this.advertisementService = advertisementService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<AdvertisementDto> read(@PathVariable(name = "id") Integer id) {
        Advertisement advertisement = advertisementService.get(id);
        AdvertisementDto advertisementDto = mapper.convertAdvertisementToAdvertisementDto(advertisement);
        return new ResponseEntity<>(advertisementDto, HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<List<AdvertisementDto>> readAll() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("title"));
        Page<Advertisement> page = advertisementService.getAllPagesAndSort(pageable);
        List<AdvertisementDto> advertisementDtoList = Mapper.convertList(page.getContent(), mapper::convertAdvertisementToAdvertisementDto);
        return new ResponseEntity<>(advertisementDtoList, HttpStatus.OK);
    }


}
