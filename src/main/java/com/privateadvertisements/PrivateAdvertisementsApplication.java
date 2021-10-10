package com.privateadvertisements;

import com.privateadvertisements.api.dao.IUserDao;
import com.privateadvertisements.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
public class PrivateAdvertisementsApplication {

//    @Autowired
//    private IUserDao iUserDao;

    public static void main(String[] args) {
        SpringApplication.run(PrivateAdvertisementsApplication.class, args);
    }


//    @EventListener(ApplicationReadyEvent.class)
//    public void deSome(){
//        User user = iUserDao.findById(100006).get();
//        System.out.println(user);
//    }
}
