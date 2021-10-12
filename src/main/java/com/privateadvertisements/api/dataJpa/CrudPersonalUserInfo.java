package com.privateadvertisements.api.dataJpa;

import com.privateadvertisements.model.PersonalUserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CrudPersonalUserInfo extends JpaRepository<PersonalUserInfo, Integer> {
}
