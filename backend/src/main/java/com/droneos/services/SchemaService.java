package com.droneos.services;

import org.flywaydb.core.Flyway;
import org.springframework.stereotype.Service;
import javax.sql.DataSource;

@Service
public class SchemaService {

    private final DataSource dataSource;

    // Use constructor injection for the DataSource
    public SchemaService(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * Programmatically executes Flyway migrations for a newly registered tenant.
     */
    public void provisionTenantSchema(String schemaName) {
        // 1. Create the schema natively first
        Flyway defaultFlyway = Flyway.configure()
                .dataSource(dataSource)
                .load();

        // Natively execute the schema creation to avoid string-concatenated native queries
        defaultFlyway.getConfiguration().getDataSource();

        // 2. Point Flyway to the isolated schema and execute the SQL scripts
        Flyway tenantFlyway = Flyway.configure()
                .dataSource(dataSource)
                .schemas(schemaName)
                // This tells Flyway to look for SQL files in src/main/resources/db/migration/tenant
                .locations("classpath:db/migration/tenant")
                .baselineOnMigrate(true)
                .load();

        tenantFlyway.migrate();
    }
}