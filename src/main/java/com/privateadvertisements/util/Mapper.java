package com.privateadvertisements.util;

import com.privateadvertisements.model.Address;
import com.privateadvertisements.model.Advertisement;
import com.privateadvertisements.model.Category;
import com.privateadvertisements.model.Chat;
import com.privateadvertisements.model.City;
import com.privateadvertisements.model.Comment;
import com.privateadvertisements.model.Country;
import com.privateadvertisements.model.CreditCard;
import com.privateadvertisements.model.Messages;
import com.privateadvertisements.model.PersonalUserInfo;
import com.privateadvertisements.model.Photograph;
import com.privateadvertisements.model.Role;
import com.privateadvertisements.model.User;
import com.privateadvertisements.model.dto.AddressDto;
import com.privateadvertisements.model.dto.AdvertisementDto;
import com.privateadvertisements.model.dto.CategoryDto;
import com.privateadvertisements.model.dto.ChatDto;
import com.privateadvertisements.model.dto.ChatDtoWithUser;
import com.privateadvertisements.model.dto.CityDto;
import com.privateadvertisements.model.dto.CommentDto;
import com.privateadvertisements.model.dto.CountryDto;
import com.privateadvertisements.model.dto.CreditCardDto;
import com.privateadvertisements.model.dto.MessagesDto;
import com.privateadvertisements.model.dto.PersonalUserInfoDto;
import com.privateadvertisements.model.dto.PhotographDto;
import com.privateadvertisements.model.dto.RoleDto;
import com.privateadvertisements.model.dto.UserDto;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class Mapper {

    private final ModelMapper modelMapper;

    public Mapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public UserDto convertUserToUserDto(User user) {
        UserDto userDto = modelMapper.map(user, UserDto.class);
        Set<RoleDto> roleDtoSet = convertSet(user.getRoles(), this::convertRoleToRoleDto);
        userDto.setRoles(roleDtoSet);
        userDto.setPersonalUserInfoDto(convertPersonalUserInfoToPersonalUserIndoDTO(user.getPersonalUserInfo()));
        userDto.setAddressDto(convertAddressToAddressDto(user.getAddress()));
        userDto.setCreditCardsDto(convertList(user.getCreditCards(), this::convertCreditCardToCreditCardDto));
        userDto.setAdvertisementDtoList(convertList(user.getAdvertisements(), this::convertAdvertisementToAdvertisementDto));
        userDto.setCommentDtoList(convertList(user.getComments(), this::convertCommentToConvertDto));
        userDto.setChats(convertSet(user.getChats(), this::convertChatToChatDto));
        return userDto;
    }

    public PersonalUserInfoDto convertPersonalUserInfoToPersonalUserIndoDTO(PersonalUserInfo personalUserInfo) {
        return modelMapper.map(personalUserInfo, PersonalUserInfoDto.class);
    }

    public CityDto convertCityToCityDTO(City city) {
        return modelMapper.map(city, CityDto.class);
    }

    public CountryDto convertCountryToCountryDto(Country country) {
        return modelMapper.map(country, CountryDto.class);
    }

    public AddressDto convertAddressToAddressDto(Address address) {
        AddressDto addressDto = modelMapper.map(address, AddressDto.class);
        addressDto.setCountryDto(convertCountryToCountryDto(address.getCountry()));
        addressDto.setCityDto(convertCityToCityDTO(address.getCity()));
        return addressDto;
    }

    public CreditCardDto convertCreditCardToCreditCardDto(CreditCard creditCard) {
        return modelMapper.map(creditCard, CreditCardDto.class);
    }

    public RoleDto convertRoleToRoleDto(Role role) {
        return modelMapper.map(role, RoleDto.class);
    }

    public AdvertisementDto convertAdvertisementToAdvertisementDto(Advertisement advertisement) {
        AdvertisementDto advertisementDto = modelMapper.map(advertisement, AdvertisementDto.class);
        advertisementDto.setComments(convertList(advertisement.getComments(), this::convertCommentToConvertDto));
        advertisementDto.setCategoryDto(convertCategoryToCategoryDto(advertisement.getCategory()));
        advertisementDto.setPhotographs(convertList(advertisement.getPhotographs(), this::convertPhotographToPhotographDto));
        return advertisementDto;
    }

    public CommentDto convertCommentToConvertDto(Comment comment) {
        return modelMapper.map(comment, CommentDto.class);
    }

    public CategoryDto convertCategoryToCategoryDto(Category category) {
        return modelMapper.map(category, CategoryDto.class);
    }

    public PhotographDto convertPhotographToPhotographDto(Photograph photograph) {
        return modelMapper.map(photograph, PhotographDto.class);
    }

    public ChatDtoWithUser convertChatToChatDtoWithUser(Chat chat) {
        ChatDtoWithUser chatDto = modelMapper.map(chat, ChatDtoWithUser.class);
        chatDto.setUsers(convertList(chat.getUsers(), this::convertUserToUserDto));
        chatDto.setMessagesDtoList(convertList(chat.getMessages(), this::convertMessageToMessageDto));
        return chatDto;
    }

    public ChatDto convertChatToChatDto(Chat chat) {
        ChatDto chatDto = modelMapper.map(chat, ChatDto.class);
        chatDto.setMessagesDtoList(convertList(chat.getMessages(), this::convertMessageToMessageDto));
        return chatDto;
    }

    public MessagesDto convertMessageToMessageDto(Messages messages) {
        return modelMapper.map(messages, MessagesDto.class);
    }


    public static <R, E> List<R> convertList(List<E> list, Function<E, R> converter) {
        return list.stream().map(converter).collect(Collectors.toList());
    }

    public static <R, E> Set<R> convertSet(Set<E> list, Function<E, R> converter) {
        return list.stream().map(converter).collect(Collectors.toSet());
    }
}
