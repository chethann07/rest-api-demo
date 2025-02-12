package com.restapi.demo.entity;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Map;

@Entity
@Table(name = "datasets")
public class Datasets {

    @Id
    private String id;

    @JdbcTypeCode(SqlTypes.JSON) // Use this for Hibernate 6+
    @Column(columnDefinition = "jsonb")
    private Map<String, Object> dataSchema;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private Map<String, Object> routeConfig;

    @Enumerated(EnumType.STRING)
    private Status status;

    private String createdBy;

    private String updatedBy;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @CreationTimestamp
    private LocalDateTime updatedAt;
}

enum Status {
    LIVE,
    DRAFT,
    RETIRED
}
