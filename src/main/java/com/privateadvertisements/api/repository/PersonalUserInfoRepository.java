package com.privateadvertisements.api.repository;

import com.privateadvertisements.model.PersonalUserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonalUserInfoRepository extends JpaRepository<PersonalUserInfo, Integer> {
}
