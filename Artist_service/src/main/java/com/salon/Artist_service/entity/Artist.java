package com.salon.Artist_service.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name="artist")
public class Artist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    private String contactNumber;
    private String specialization;
    @JsonProperty("isAvailable")
    private boolean isAvailable;

}
