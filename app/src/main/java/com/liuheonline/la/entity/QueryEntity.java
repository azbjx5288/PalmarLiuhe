package com.liuheonline.la.entity;

public class QueryEntity {

    /**
     * pdr_payment_state : 0
     */

    private String pdr_payment_state;

    public String getPdr_payment_state() {
        return pdr_payment_state;
    }

    public void setPdr_payment_state(String pdr_payment_state) {
        this.pdr_payment_state = pdr_payment_state;
    }

    @Override
    public String toString() {
        return "QueryEntity{" +
                "pdr_payment_state='" + pdr_payment_state + '\'' +
                '}';
    }
}
