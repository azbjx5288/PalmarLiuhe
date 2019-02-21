package com.liuheonline.la.entity;

import java.io.Serializable;

public class BankCardEntity implements Serializable {

    /**
     * id : 11
     * bank_account_name : Tomsa
     * bank_account_number : 62220248764348484
     * bank_name : 中国工商银行.E时代卡
     * bank_address : 成都
     */

    private int id;
    private String bank_account_name;
    private String bank_account_number;
    private String bank_name;
    private String bank_address;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBank_account_name() {
        return bank_account_name;
    }

    public void setBank_account_name(String bank_account_name) {
        this.bank_account_name = bank_account_name;
    }

    public String getBank_account_number() {
        return bank_account_number;
    }

    public void setBank_account_number(String bank_account_number) {
        this.bank_account_number = bank_account_number;
    }

    public String getBank_name() {
        return bank_name;
    }

    public void setBank_name(String bank_name) {
        this.bank_name = bank_name;
    }

    public String getBank_address() {
        return bank_address;
    }

    public void setBank_address(String bank_address) {
        this.bank_address = bank_address;
    }

    @Override
    public String toString() {
        return "BankCardEntity{" +
                "id=" + id +
                ", bank_account_name='" + bank_account_name + '\'' +
                ", bank_account_number='" + bank_account_number + '\'' +
                ", bank_name='" + bank_name + '\'' +
                ", bank_address='" + bank_address + '\'' +
                '}';
    }
}
