package com.lethanh98.controller;

import com.lethanh98.entity.User;
import com.lethanh98.process.NewUserEventProcessor;
import com.lethanh98.process.UserEvent;
import com.lethanh98.repo.UserRepo;
import com.lethanh98.request.PostUserRQ;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import javax.transaction.Transactional;
import javax.validation.Valid;

@RestController
@RequestMapping(value = "/api/users")
@Slf4j
public class UserController {
    private final Flux<UserEvent> events;
    @Autowired
    UserRepo userRepo;
    @Autowired
    private NewUserEventProcessor publisher;

    public UserController(NewUserEventProcessor newUserEventProcessor) {
        events = Flux.create(newUserEventProcessor).share();
        publisher = newUserEventProcessor;
        publisher.onApplicationEvent(new UserEvent(new User()));
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public User post(@Valid @RequestBody() PostUserRQ userRQ,
                     @PathVariable(value = "id", required = false) Integer id
    ) {
        User user = new User();
        user.setFirstName(userRQ.getFirstName());
        user.setLastName(userRQ.getFirstName());
        userRepo.save(user);
        publisher.onApplicationEvent(new UserEvent(user));
        return user;
    }
    // get event khii có user mới
    @GetMapping(value = "/course/sse", produces = "text/event-stream;charset=UTF-8")
    public Flux<User> stream() {
        log.info("Start listening to the course collection.");
        return this.events.map(event -> {
            User dto = (User) event.getSource();
            return dto;
        });
    }

}
