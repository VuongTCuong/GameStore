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
public class OrderDetailID implements Serializable{
    private Integer orderID;
    private Integer gameID;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrderDetailID that = (OrderDetailID) o;

        if (orderID != null ? !orderID.equals(that.orderID) : that.orderID != null) return false;
        return gameID != null ? gameID.equals(that.gameID) : that.gameID == null;
    }

    @Override
    public int hashCode() {
        int result = orderID != null ? orderID.hashCode() : 0;
        result = 31 * result + (gameID != null ? gameID.hashCode() : 0);
        return result;
    }
}
