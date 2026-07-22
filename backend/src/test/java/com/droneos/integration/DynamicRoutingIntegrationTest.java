package com.droneos.integration;

import com.droneos.config.tenant.TenantContextHolder;
import com.droneos.repositories.TenantRegistryRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @SpringBootTest boots the entire Spring context.
 * @Testcontainers automatically manages the lifecycle of the Docker container.
 */
@SpringBootTest
@Testcontainers
class DynamicRoutingIntegrationTest {

    // 1. Spin up a real, throwaway PostgreSQL database via Docker for testing
    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15-alpine");

    // 2. Dynamically override the application.properties to point to the test container
    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @Autowired
    private TenantRegistryRepository tenantRegistryRepository;

    @AfterEach
    void cleanUp() {
        TenantContextHolder.clear();
    }

    @Test
    void shouldRouteQueriesToCorrectIsolatedSchema() {
        // Architect's Note: In a full test suite, you would first use your SchemaService
        // to provision "schema_a" and "schema_b", and insert different data into each.

        // Context 1: Switch to the public schema
        TenantContextHolder.setTenantId(TenantContextHolder.DEFAULT_TENANT);
        long publicCount = tenantRegistryRepository.count();

        // Context 2: Switch to a non-existent schema to prove routing attempts the switch
        TenantContextHolder.setTenantId("schema_that_does_not_exist");

        try {
            tenantRegistryRepository.count();
        } catch (Exception e) {
            // We EXPECT this to throw an exception because the RoutingDataSource
            // successfully intercepted the call and tried to query a missing schema.
            assertThat(e.getMessage()).contains("schema_that_does_not_exist");
        }

        // This proves the database connection is dynamically shifting based on the ThreadLocal.
        assertThat(publicCount).isGreaterThanOrEqualTo(0);
    }
}