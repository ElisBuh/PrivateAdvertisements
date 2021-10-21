package com.privateadvertisements.controller;


import com.privateadvertisements.api.service.IAdvertisementService;
import com.privateadvertisements.model.Advertisement;
import com.privateadvertisements.model.Comment;
import com.privateadvertisements.model.dto.AdvertisementDto;
import com.privateadvertisements.model.dto.AdvertisementNewDto;
import com.privateadvertisements.model.dto.CommentDto;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
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

    @PostMapping("/")
    public ResponseEntity<?> create(@RequestBody AdvertisementNewDto advertisementNewDto) {
        Advertisement advertisement = mapper.convertAdvertisementNewDtoToAdvertisement(advertisementNewDto);
        System.out.println(advertisement);
        advertisementService.save(advertisement, 100006, "Техника");

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping(value = "/{id}")
    private ResponseEntity<?> update(@PathVariable(name = "id") Integer id,
                                     @RequestBody AdvertisementNewDto advertisementNewDto) {
        Advertisement advertisement = mapper.convertAdvertisementNewDtoToAdvertisement(advertisementNewDto);
        advertisementService.update(advertisement, id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(value = "/{id}/addComment")
    private ResponseEntity<?> addComment(@PathVariable(name = "id") Integer id,
                                         @RequestBody CommentDto commentDto,
                                         @RequestParam(name = "userId") Integer userId) {
        Comment comment = mapper.convertCommentDtoToConvert(commentDto);
        advertisementService.addComment(comment, id, userId);
        return new ResponseEntity<>(HttpStatus.OK);
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
        List<Advertisement> page = advertisementService.getAllPagesAndSort(pageable);
        List<AdvertisementDto> advertisementDtoList = Mapper.convertList(page, mapper::convertAdvertisementToAdvertisementDto);
        return new ResponseEntity<>(advertisementDtoList, HttpStatus.OK);
    }

    @DeleteMapping("/{id}/{idAd}")
    public ResponseEntity<?> delete(@PathVariable(name = "id") Integer userId,
                                    @PathVariable(name = "idAd") Integer adId) {
        advertisementService.delete(adId, userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @GetMapping("/test")
    public ResponseEntity<List<AdvertisementDto>> testAll() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("title"));
        LocalDateTime startDate = LocalDateTime.of(2021, 10, 15, 0, 0);
        LocalDateTime endDate = LocalDateTime.of(2021, 10, 18, 0, 0);
        Page<Advertisement> advertisementList = advertisementService.getAllBetweenHalfOpen(startDate, endDate, pageable);
        List<AdvertisementDto> advertisementDtoList = Mapper.convertList(advertisementList.getContent(), mapper::convertAdvertisementToAdvertisementDto);
        return new ResponseEntity<>(advertisementDtoList, HttpStatus.OK);
    }

    @GetMapping("/test2")
    public ResponseEntity<List<AdvertisementDto>> testAll2() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("title"));
        LocalDateTime startDate = LocalDateTime.of(2021, 10, 15, 0, 0);
        LocalDateTime endDate = LocalDateTime.of(2021, 10, 20, 0, 0);
        Page<Advertisement> advertisementList = advertisementService.getBetweenHalfOpenOfUser(100006, startDate, endDate, pageable);
        List<AdvertisementDto> advertisementDtoList = Mapper.convertList(advertisementList.getContent(), mapper::convertAdvertisementToAdvertisementDto);
        return new ResponseEntity<>(advertisementDtoList, HttpStatus.OK);
    }


}
