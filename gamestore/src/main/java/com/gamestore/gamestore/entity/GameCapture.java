package com.gamestore.gamestore.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@IdClass(GameCaptureID.class)
public class GameCapture {

    @Id
    private Integer gameID;

    @Id
    private Integer numberOrder;

    private String url;
}
