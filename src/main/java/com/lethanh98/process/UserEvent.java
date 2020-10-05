package com.lethanh98.process;

import lombok.Data;
import org.springframework.context.ApplicationEvent;

public class UserEvent extends ApplicationEvent {
    public UserEvent(Object source) {
        super(source);
    }
}
