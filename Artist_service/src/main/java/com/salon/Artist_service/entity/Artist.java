package com.salon.Artist_service.entity;

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
    private String ContactNumber;
    private String Email;
    private String Specialization;
    private boolean isAvailable;

}
