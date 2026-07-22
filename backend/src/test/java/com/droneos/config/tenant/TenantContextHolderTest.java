package com.droneos.config.tenant;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class TenantContextHolderTest {

    @AfterEach
    void tearDown() {
        // Architect's Note: Always clean up the thread local after every test
        // to prevent test pollution where one test affects the outcome of another.
        TenantContextHolder.clear();
    }

    @Test
    void shouldSetAndRetrieveTenantId() {
        String tenantId = "schema_greenwood";
        TenantContextHolder.setTenantId(tenantId);

        assertThat(TenantContextHolder.getTenantId()).isEqualTo("schema_greenwood");
    }

    @Test
    void shouldReturnDefaultTenantWhenNotSet() {
        // If no API interceptor sets the tenant, it must default to the public schema for safety
        assertThat(TenantContextHolder.getTenantId()).isEqualTo(TenantContextHolder.DEFAULT_TENANT);
    }

    @Test
    void shouldClearTenantId() {
        TenantContextHolder.setTenantId("schema_sunrise");
        TenantContextHolder.clear();

        // Verifies the thread is completely wiped clean
        assertThat(TenantContextHolder.getTenantId()).isEqualTo(TenantContextHolder.DEFAULT_TENANT);
    }
}
