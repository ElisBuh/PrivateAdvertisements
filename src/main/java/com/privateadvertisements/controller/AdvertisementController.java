package com.privateadvertisements.controller;


import com.privateadvertisements.api.service.IAdvertisementService;
import com.privateadvertisements.model.Advertisement;
import com.privateadvertisements.model.Comment;
import com.privateadvertisements.model.dto.AdvertisementDto;
import com.privateadvertisements.model.dto.AdvertisementNewDto;
import com.privateadvertisements.model.dto.CommentDto;
import com.privateadvertisements.model.dto.PhotographNewDto;
import com.privateadvertisements.util.DateTimeUtil;
import com.privateadvertisements.util.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import java.util.List;

@RestController
@RequestMapping(value = "/advertisements")
public class AdvertisementController {
    private static final Logger log = LoggerFactory.getLogger(AdvertisementController.class);

    private final Mapper mapper;
    private final IAdvertisementService advertisementService;

    public AdvertisementController(Mapper mapper, IAdvertisementService advertisementService) {
        this.mapper = mapper;
        this.advertisementService = advertisementService;
    }

    @PostMapping("/")
    public ResponseEntity<?> create(@RequestBody AdvertisementNewDto advertisementNewDto,
                                    @RequestParam(value = "idUser") Integer userId) {
        log.info("creat adTitle: {}", advertisementNewDto.getTitle());
        Advertisement advertisement = mapper.convertAdvertisementNewDtoToAdvertisement(advertisementNewDto);
        advertisementService.save(advertisement, userId, advertisementNewDto.getCategory());

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping(value = "/{id}")
    private ResponseEntity<?> update(@PathVariable(name = "id") Integer id,
                                     @RequestBody AdvertisementNewDto advertisementNewDto) {
        log.info("update adID: {}", id);
        Advertisement advertisement = mapper.convertAdvertisementNewDtoToAdvertisement(advertisementNewDto);
        advertisementService.update(advertisement, id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}/{idAd}")
    public ResponseEntity<?> delete(@PathVariable(name = "id") Integer userId,
                                    @PathVariable(name = "idAd") Integer adId) {
        log.info("delete ad by adId: {}", adId);
        advertisementService.delete(adId, userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AdvertisementDto> read(@PathVariable(name = "id") Integer id) {
        log.info("Get ad with id: {}", id);
        Advertisement advertisement = advertisementService.get(id);
        AdvertisementDto advertisementDto = mapper.convertAdvertisementToAdvertisementDto(advertisement);
        return new ResponseEntity<>(advertisementDto, HttpStatus.OK);
    }

    @GetMapping("/find")
    public ResponseEntity<AdvertisementDto> findAdvertisementByTitle(@RequestParam(name = "title") String title) {
        log.info("Get ad with title: {}", title);
        Advertisement advertisement = advertisementService.findAdvertisementByTitle(title);
        AdvertisementDto advertisementDto = mapper.convertAdvertisementToAdvertisementDto(advertisement);
        return new ResponseEntity<>(advertisementDto, HttpStatus.OK);
    }

    @PostMapping(value = "/{id}/addComment")
    private ResponseEntity<?> addComment(@PathVariable(name = "id") Integer id,
                                         @RequestBody CommentDto commentDto,
                                         @RequestParam(name = "userId") Integer userId) {
        log.info("add Comment to adId: {}", id);
        Comment comment = mapper.convertCommentDtoToConvert(commentDto);
        advertisementService.addComment(comment, id, userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{id}/comments")
    public ResponseEntity<List<CommentDto>> readAll(@PathVariable(name = "id") Integer id) {
        log.info("get comments by adId: {}", id);
        List<Comment> comments = advertisementService.readCommentsOfAd(id);
        List<CommentDto> commentDtoList = Mapper.convertList(comments, mapper::convertCommentToConvertDto);
        return new ResponseEntity<>(commentDtoList, HttpStatus.OK);
    }


    @GetMapping("/")
    public ResponseEntity<List<AdvertisementDto>> readAll(@RequestParam(name = "page", defaultValue = "0") Integer page,
                                                          @RequestParam(name = "size", defaultValue = "10") Integer size,
                                                          @RequestParam(name = "sort", defaultValue = "title") String sort,
                                                          @RequestParam(name = "dateStart", defaultValue = "31.01.1000") String starDate,
                                                          @RequestParam(name = "dateEnd", defaultValue = "31.01.3000") String endDate,
                                                          @RequestParam(name = "idUser", defaultValue = "0") Integer userId) {
        log.info("ReadAll ad");
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
        List<Advertisement> advertisementList;
        if (!userId.equals(0)) {
            advertisementList = advertisementService.getBetweenHalfOpenOfUser(userId, DateTimeUtil.stringToLocalDate(starDate),
                    DateTimeUtil.stringToLocalDate(endDate),
                    pageable).getContent();
        } else {
            advertisementList = advertisementService.getAllBetweenHalfOpen(DateTimeUtil.stringToLocalDate(starDate),
                    DateTimeUtil.stringToLocalDate(endDate),
                    pageable).getContent();
        }
        List<AdvertisementDto> advertisementDtoList = Mapper.convertList(advertisementList, mapper::convertAdvertisementToAdvertisementDto);
        return new ResponseEntity<>(advertisementDtoList, HttpStatus.OK);
    }

    @PostMapping(value = "/{id}/top")
    public ResponseEntity<?> topUpAd(@PathVariable(name = "id") Integer id,
                                     @RequestParam(name = "day") Integer day) {
        log.info("topUpAd {} on day: {}", id, day);
        advertisementService.topUpAdvertisement(id, day);
        return new ResponseEntity<>(HttpStatus.OK);

    }

    @PostMapping(value = "/{id}/addPhoto")
    private ResponseEntity<?> addPhoto(@PathVariable(name = "id") Integer id,
                                       @RequestBody PhotographNewDto photographNewDto) {
        log.info("Add photo adId: {}", id);
        advertisementService.addPhoto(id, photographNewDto.getPaths());
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
