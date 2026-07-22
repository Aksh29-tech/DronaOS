package com.droneos.repositories;

import com.droneos.entities.TenantRegistry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TenantRegistryRepository extends JpaRepository<TenantRegistry, UUID> {
}
