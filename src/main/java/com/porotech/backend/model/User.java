package com.porotech.backend.model;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;
}
