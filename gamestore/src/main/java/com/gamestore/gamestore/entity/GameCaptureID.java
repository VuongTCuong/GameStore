package com.gamestore.gamestore.entity;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GameCaptureID implements Serializable {
    private Integer gameID;
    private Integer numberOrder;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GameCaptureID that = (GameCaptureID) o;

        if (gameID != null ? !gameID.equals(that.gameID) : that.gameID != null) return false;
        return numberOrder != null ? numberOrder.equals(that.numberOrder) : that.numberOrder == null;
    }

    @Override
    public int hashCode() {
        int result = gameID != null ? gameID.hashCode() : 0;
        result = 31 * result + (gameID != null ? gameID.hashCode() : 0);
        return result;
    }
}
