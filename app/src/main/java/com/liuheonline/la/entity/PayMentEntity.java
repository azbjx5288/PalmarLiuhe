package com.liuheonline.la.entity;


import java.io.Serializable;

public class PayMentEntity implements Serializable {

    /**
     * id : 2
     * code : alipay
     * name : 支付宝
     * pic : img/20180802/5b62803d51ca3.png
     * qrcode : {"id":50,"title":"支付宝二","pic":"img/20180730/5b5e93cec7da3.jpg","pic_link":"http://120.79.189.244/images/img/20180730/5b5e93cec7da3.jpg"}
     * pic_link : http://120.79.189.244/images/img/20180802/5b62803d51ca3.png
     */

    private int id;
    private String code;
    private String name;
    private String pic;
    private QrcodeBean qrcode;
    private ConfigBean config;
    private String pic_link;

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    private double price;

    private boolean isSelected;

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        this.isSelected = selected;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public QrcodeBean getQrcode() {
        return qrcode;
    }

    public void setQrcode(QrcodeBean qrcode) {
        this.qrcode = qrcode;
    }

    public ConfigBean getConfig() {
        return config;
    }

    public void setConfig(ConfigBean config) {
        this.config = config;
    }

    public String getPic_link() {
        return pic_link;
    }

    public void setPic_link(String pic_link) {
        this.pic_link = pic_link;
    }


    public static class ConfigBean implements Serializable{

        /**
         * bank_account_name : 心情
         * bank_account_number : 1111111
         * bank_id : 中国银行
         * bank_name : 北京市分行
         * bank_address : 北京市
         */

        private String bank_account_name;
        private String bank_account_number;
        private String bank_id;
        private String bank_name;
        private String bank_address;

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

        public String getBank_id() {
            return bank_id;
        }

        public void setBank_id(String bank_id) {
            this.bank_id = bank_id;
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
    }

    public static class QrcodeBean implements Serializable{
        /**
         * id : 50
         * title : 支付宝二
         * pic : img/20180730/5b5e93cec7da3.jpg
         * pic_link : http://120.79.189.244/images/img/20180730/5b5e93cec7da3.jpg
         */

        private int id;
        private String title;
        private String pic;
        private String pic_link;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getPic() {
            return pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }

        public String getPic_link() {
            return pic_link;
        }

        public void setPic_link(String pic_link) {
            this.pic_link = pic_link;
        }
    }
}
