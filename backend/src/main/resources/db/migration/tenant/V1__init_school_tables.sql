-- V1__init_school_tables.sql
-- This script runs automatically inside the newly created school schema

CREATE TABLE roles (
    id UUID PRIMARY KEY,
    role_name VARCHAR(255) NOT NULL UNIQUE,
    permissions JSONB NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP,
    is_removed BOOLEAN DEFAULT FALSE
);

CREATE TABLE users (
    id UUID PRIMARY KEY,
    role_id UUID NOT NULL REFERENCES roles(id),
    first_name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP,
    is_removed BOOLEAN DEFAULT FALSE
);

-- Add the rest of your 10 isolated tables here (students, marks, etc.)