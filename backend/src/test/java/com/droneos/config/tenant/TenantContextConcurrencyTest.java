package com.droneos.config.tenant;

import org.junit.jupiter.api.Test;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.assertj.core.api.Assertions.assertThat;

class TenantContextConcurrencyTest {

    @Test
    void shouldMaintainThreadIsolationUnderHighConcurrency() throws InterruptedException {
        int threadCount = 50;
        ExecutorService executor = Executors.newFixedThreadPool(threadCount);
        CountDownLatch latch = new CountDownLatch(threadCount);

        // Using AtomicBoolean because standard booleans are not thread-safe
        AtomicBoolean hasCrossTenantBleed = new AtomicBoolean(false);

        for (int i = 0; i < threadCount; i++) {
            final String expectedTenant = "schema_school_" + i;

            executor.submit(() -> {
                try {
                    // 1. Assign this specific thread a unique tenant
                    TenantContextHolder.setTenantId(expectedTenant);

                    // 2. Simulate a brief API processing delay (0-50ms)
                    Thread.sleep((long) (Math.random() * 50));

                    // 3. Verify the tenant hasn't been overwritten by a parallel thread
                    if (!expectedTenant.equals(TenantContextHolder.getTenantId())) {
                        hasCrossTenantBleed.set(true);
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } finally {
                    // Emulating the HTTP Interceptor clearing the context at the end of the request
                    TenantContextHolder.clear();
                    latch.countDown();
                }
            });
        }

        // Wait for all 50 threads to finish simulating concurrent API requests
        latch.await();
        executor.shutdown();

        // Architect's Guarantee: Assert that ZERO threads experienced data bleed
        assertThat(hasCrossTenantBleed.get()).isFalse();
    }
}