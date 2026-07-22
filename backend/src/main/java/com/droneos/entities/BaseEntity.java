package com.droneos.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

/**
 * MappedSuperclass designates this as a parent class for entities,
 * telling Hibernate NOT to create a separate 'base_entity' table,
 * but to merge these columns into the child tables (e.g., students, marks).
 */
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public abstract class BaseEntity {

    // Utilizing LocalDateTime for precise, timezone-aware audit logging.
    @Column(name = "created_at", nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    /**
     * Soft Delete Implementation.
     * Defaults to false. When a school admin "deletes" a record (like a fee invoice),
     * we toggle this to true instead of removing the row from the database.
     */
    @Column(name = "is_removed", nullable = false)
    private boolean isRemoved = false;
}