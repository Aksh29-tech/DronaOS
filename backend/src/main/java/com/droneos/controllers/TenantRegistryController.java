package com.droneos.controllers;

import com.droneos.entities.TenantRegistry;
import com.droneos.services.TenantRegistryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tenants")
public class TenantRegistryController {

    private final TenantRegistryService tenantRegistryService;

    public TenantRegistryController(TenantRegistryService tenantRegistryService) {
        this.tenantRegistryService = tenantRegistryService;
    }

    @PostMapping("/register")
    public ResponseEntity<TenantRegistry> registerTenant(@RequestBody TenantRegistry tenant) {
        TenantRegistry registeredTenant = tenantRegistryService.registerTenant(tenant);
        return new ResponseEntity<>(registeredTenant, HttpStatus.CREATED);
    }
}
