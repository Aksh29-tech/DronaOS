-- V1__init_global_registry.sql
-- This runs automatically on application startup to build the infrastructure layer

CREATE TABLE public.tenant_registry (
    id UUID PRIMARY KEY,
    school_name VARCHAR(255) NOT NULL,
    db_schema_name VARCHAR(255) NOT NULL UNIQUE,
    subdomain VARCHAR(255) NOT NULL UNIQUE,
    subscription_status VARCHAR(50) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP,
    is_removed BOOLEAN DEFAULT FALSE
);