package com.droneos.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.UUID;

@Entity
@Table(name = "tenant_registry", schema = "public")
@Getter
@Setter
public class TenantRegistry extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(updatable = false, nullable = false)
    private UUID id; //

    @Column(name = "school_name", nullable = false)
    private String schoolName; //

    @Column(name = "db_schema_name", nullable = false, unique = true)
    private String dbSchemaName; // Points backend to the correct school schema

    @Column(nullable = false, unique = true)
    private String subdomain; //

    @Column(name = "subscription_status", nullable = false)
    private String subscriptionStatus; //[cite: 1]
}