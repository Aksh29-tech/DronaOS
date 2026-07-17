package com.droneos.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.UUID;

@Entity
@Table(name = "roles") // Notice: No schema is defined here. Hibernate will resolve it dynamically.
@Getter
@Setter
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id; //[cite: 1]

    @Column(name = "role_name", nullable = false, unique = true)
    private String roleName; //[cite: 1]

    @Column(nullable = false)
    private Boolean permissions; //[cite: 1]
}