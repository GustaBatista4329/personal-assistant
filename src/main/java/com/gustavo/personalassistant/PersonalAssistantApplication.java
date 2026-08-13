package com.gustavo.personalassistant;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PersonalAssistantApplication {

	public static void main(String[] args) {
		SpringApplication.run(PersonalAssistantApplication.class, args);
	}

}

/*@SpringBootApplication
public class PersonalAssistantApplication implements CommandLineRunner {
    @Autowired
    private UserRepository repository;

    @Autowired
    private UserService service;

    public static void main(String[] args) {
        SpringApplication.run(PersonalAssistantApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        repository.findById(UUID.fromString("11111111-1111-1111-1111-111111111111"))
                .ifPresent(u -> System.out.println(u.getName() + " - " + u.getEmail()));

        UserRegistrationDto dto = new UserRegistrationDto("gustavo", LocalDate.parse("2005-04-04"), "62986309743", "gustavo@gmail.com", "teste123");

        service.userRegistration(dto);
    }
}*/