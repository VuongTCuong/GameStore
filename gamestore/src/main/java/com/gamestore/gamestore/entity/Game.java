package com.gamestore.gamestore.entity;


import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer gameID;
    
    private String gameName;
    private String poster;
    private String genre;

    @Column(columnDefinition = "TEXT")
    private String gameDescription;
    private String platform;
    private String publisher;
    private LocalDate releaseDate;
    private Integer stockQuantity;
    private float price;

}
