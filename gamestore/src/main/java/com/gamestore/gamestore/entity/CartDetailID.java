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
public class CartDetailID implements Serializable{
    private Integer cartID;
    private Integer gameID;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CartDetailID that = (CartDetailID) o;

        if (cartID != null ? !cartID.equals(that.cartID) : that.cartID != null) return false;
        return gameID != null ? gameID.equals(that.gameID) : that.gameID == null;
    }

    @Override
    public int hashCode() {
        int result = cartID != null ? cartID.hashCode() : 0;
        result = 31 * result + (gameID != null ? gameID.hashCode() : 0);
        return result;
    }
}
