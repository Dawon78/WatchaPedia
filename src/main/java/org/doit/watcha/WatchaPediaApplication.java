package org.doit.watcha;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class WatchaPediaApplication {

   public static void main(String[] args) {
      SpringApplication.run(WatchaPediaApplication.class, args);
   }

}