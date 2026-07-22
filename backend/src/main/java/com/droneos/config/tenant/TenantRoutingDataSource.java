package com.droneos.config.tenant;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * Intercepts all database connection requests and dynamically routes them
 * based on the identifier stored in the TenantContextHolder.
 */
public class TenantRoutingDataSource extends AbstractRoutingDataSource {

    @Override
    protected Object determineCurrentLookupKey() {
        // Hibernate will call this automatically before firing any query.
        // It retrieves the schema name (e.g., "greenwood_schema") set during the API request.
        return TenantContextHolder.getTenantId();
    }
}