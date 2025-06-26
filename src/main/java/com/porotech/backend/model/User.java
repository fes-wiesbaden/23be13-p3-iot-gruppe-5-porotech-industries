package com.porotech.backend.model;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.context.annotation.Profile;

import java.util.UUID;

@Profile("!test")
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2")
    @Column(columnDefinition = "UUID", updatable = false, nullable = false)
    private UUID id;


    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;
}
