package com.droneos.config.tenant;

/**
 * Acts as a thread-safe vault to store the active tenant's schema name for the current HTTP request.
 */
public class TenantContextHolder {

    private static final ThreadLocal<String> CURRENT_TENANT = new ThreadLocal<>();

    // Default to public schema for login/registration workflows
    public static final String DEFAULT_TENANT = "public";

    public static void setTenantId(String tenantId) {
        CURRENT_TENANT.set(tenantId);
    }

    public static String getTenantId() {
        String tenantId = CURRENT_TENANT.get();
        return (tenantId != null) ? tenantId : DEFAULT_TENANT;
    }

    /**
     * CRITICAL ARCHITECTURAL SAFEGUARD:
     * Tomcat pools and reuses threads. You must clear the context at the end of every request.
     * Failing to call this leads to severe memory leaks and cross-tenant data corruption.
     */
    public static void clear() {
        CURRENT_TENANT.remove();
    }
}