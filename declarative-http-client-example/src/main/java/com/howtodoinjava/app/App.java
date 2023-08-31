package com.howtodoinjava.app;

import com.howtodoinjava.app.model.User;
import com.howtodoinjava.app.web.UserClient;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.core.publisher.Mono;

@Slf4j
@SpringBootApplication
public class App implements CommandLineRunner {

  public static void main(String[] args) {
    SpringApplication.run(App.class, args);
  }

  @Autowired
  UserClient userClient;

  @Override
  public void run(String... args) throws Exception {

    //Get All Users
    String headerValue = UUID.randomUUID().toString();
    userClient.getAll(headerValue).subscribe(
        data -> log.info("User: {}", data)
    );

    //Get User By Id
    userClient.getById(1L).subscribe(
        data -> log.info("User: {}", data)
    );

    //Create a New User
    userClient.save(new User(null, "Lokesh", "lokesh", "admin@email.com"))
        .log()
        .flatMap(user -> {
              var uri = user.getHeaders().getLocation().toString();
              var strings = uri.split("/");
              return userClient.getById(Long.valueOf(strings[strings.length - 1]))
                  .log()
                  .map(u -> {
                    log.debug("User: {}", u);
                    return u;
                  });
            }
        ).subscribe(
            data -> log.info("User: {}", data)
        );
    ;

    //Delete User By Id
    userClient.delete(1L).subscribe(
        data -> log.info("User: {}", data)
    );
  }
}
