package com.gmail.gak.artem;

import com.gmail.gak.artem.backend.entity.Contact;
import com.gmail.gak.artem.backend.service.ContactService;
import com.gmail.gak.artem.backend.service.GroupService;
import com.gmail.gak.artem.backend.entity.Group;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * The entry point of the Spring Boot application.
 */
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public CommandLineRunner demo(final ContactService contactService, final GroupService groupService) {
        return new CommandLineRunner() {
            @Override
            public void run(String... strings) throws Exception {
                Contact contact;

                Group group = new Group("Tes #1");
                groupService.save(group);
                for (int i = 1; i <= 100; i++) {
                    contact = new Contact(group, "Name" + i, "Surname" + i, "1234567" + i, "user" + i + "@test.com");
                    contactService.save(contact);
                }

                for (int i = 1; i <= 100; i++) {
                    contact = new Contact(null, "Other" + i, "OtherSurname" + i, "5569874" + i, "user" + i + "@other.com");
                    contactService.save(contact);
                }

                group = new Group("Tes #2");
                groupService.save(group);
                for (int i = 1; i <= 100; i++) {
                    contact = new Contact(group, "Name" + i, "Surname" + i, "7654321" + i, "user" + i + "@other.com");
                    contactService.save(contact);
                }

                group = new Group("Test #3");
                groupService.save(group);

            }
        };
    }
}
