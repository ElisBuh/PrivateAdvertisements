package com.privateadvertisements.api.dataJpa;

import com.privateadvertisements.model.PersonalUserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface CrudPersonalUserInfo extends JpaRepository<PersonalUserInfo, Integer> {
}
