package com.privateadvertisements;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

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
