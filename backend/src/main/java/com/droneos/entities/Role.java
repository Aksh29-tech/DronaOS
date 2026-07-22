//package com.droneos.entities;

//import com.droneos.entities.BaseEntity;
//import jakarta.persistence.*;
//import lombok.Getter;
//import lombok.Setter;
//import org.hibernate.annotations.JdbcTypeCode;
//import org.hibernate.type.SqlTypes;
//
//import java.util.List;
//import java.util.UUID;
//
//@Entity
//@Table(name = "roles")
//@Getter
//@Setter
//public class Role extends BaseEntity {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.UUID)
//    private UUID id;
//
//    @Column(name = "role_name", nullable = false, unique = true)
//    private String roleName;
//
//    /**
//     * Modernized JSONB mapping.
//     * Leveraging native Hibernate 6+ support instead of third-party libraries.
//     * This instructs Hibernate to treat this List natively as a JSON structure in PostgreSQL.
//     */
//    @JdbcTypeCode(SqlTypes.JSON)
//    @Column(name = "permissions", columnDefinition = "jsonb", nullable = false)
//    private List<String> permissions;
//}