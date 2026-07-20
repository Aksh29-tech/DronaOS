//package com.droneos.entities;
//
//import jakarta.persistence.*;
//import lombok.Getter;
//import lombok.Setter;
//import java.util.UUID;
//
//@Entity
//@Table(name = "users")
//@Getter
//@Setter
//public class User {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.UUID)
//    private UUID id; //[cite: 1]
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "role_id", nullable = false)
//    private Role role; // Assigned to a role[cite: 1]
//
//    @Column(name = "first_name", nullable = false)
//    private String firstName; //[cite: 1]
//
//    @Column(nullable = false, unique = true)
//    private String email; //[cite: 1]
//}