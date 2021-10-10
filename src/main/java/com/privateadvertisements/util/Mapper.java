package com.privateadvertisements.util;

import com.privateadvertisements.model.PersonalUserInfo;
import com.privateadvertisements.model.Role;
import com.privateadvertisements.model.User;
import com.privateadvertisements.model.dto.PersonalUserInfoDto;
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

    public UserDto convertUserToUserDto(User user){
        UserDto userDto = modelMapper.map(user, UserDto.class);
        Set<RoleDto> roleDtoSet = convertSet(user.getRoles(), this::convertRoleToRoleDto);
        userDto.setRoles(roleDtoSet);
        System.out.println(user.getPersonalUserInfo());
        userDto.setPersonalUserInfoDto(convertPersonalUserInfoToPersonalUserIndoDTO(user.getPersonalUserInfo()));
        return userDto;
    }

    public PersonalUserInfoDto convertPersonalUserInfoToPersonalUserIndoDTO(PersonalUserInfo personalUserInfo){
       return modelMapper.map(personalUserInfo, PersonalUserInfoDto.class);
    }



    public RoleDto convertRoleToRoleDto(Role role){
        return modelMapper.map(role, RoleDto.class);
    }




    public static <R, E> List<R> convertList(List<E> list, Function<E, R> converter) {
        return list.stream().map(converter).collect(Collectors.toList());
    }
    public static <R, E> Set<R> convertSet(Set<E> list, Function<E, R> converter) {
        return list.stream().map(converter).collect(Collectors.toSet());
    }
}
