package com.droneos.services;

import com.droneos.entities.TenantRegistry;
import com.droneos.repositories.TenantRegistryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TenantRegistryService {

    private final SchemaService schemaService;
    private final TenantRegistryRepository tenantRegistryRepository;

    public TenantRegistryService(SchemaService schemaService, TenantRegistryRepository tenantRegistryRepository) {
        this.schemaService = schemaService;
        this.tenantRegistryRepository = tenantRegistryRepository;
    }

    @Transactional
    public TenantRegistry registerTenant(TenantRegistry tenant) {
        // 1. Save tenant details to the global registry first
        TenantRegistry savedTenant = tenantRegistryRepository.save(tenant);

        // 2. Let Flyway provision the schema and all tables dynamically
        schemaService.provisionTenantSchema(tenant.getDbSchemaName());

        return savedTenant;
    }
}