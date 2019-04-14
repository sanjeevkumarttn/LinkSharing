package com.ttn.LinkSharing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("com.ttn.LinkSharing.repositories")
@EntityScan("com.ttn.LinkSharing.entities")
public class LinkSharingApplication {

	public static void main(String[] args) {
		SpringApplication.run(LinkSharingApplication.class, args);
	}

}
