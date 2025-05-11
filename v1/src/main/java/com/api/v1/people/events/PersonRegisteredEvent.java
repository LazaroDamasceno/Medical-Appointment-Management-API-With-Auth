package com.api.v1.people.events;
import com.api.v1.people.domain.Person;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public final class PersonRegisteredEvent extends ApplicationEvent {

    private final Person person;

    private PersonRegisteredEvent(Object source, Person person) {
        super(source);
        this.person = person;
    }

    public static PersonRegisteredEvent createPersonRegisteredEvent(Object source, Person person) {
        return new PersonRegisteredEvent(source, person);
    }
}
