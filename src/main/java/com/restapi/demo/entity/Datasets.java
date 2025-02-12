package com.restapi.demo.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.JoinColumnOrFormula;

import java.time.LocalDateTime;

@Entity
@Table(name = "datasets")
public class Datasets {

    @Id
    private String id;

    @Column(columnDefinition = "json")
    private String dataSchema;

    @Column(columnDefinition = "json")
    private String routerConfig;

    @Enumerated(EnumType.STRING)
    private Status status;

    private String createdBy;
    private String updatedBy;

    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdDate;

    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime updatedDate;
}

enum Status {
    LIVE,
    DRAFT,
    RETIRED
}
