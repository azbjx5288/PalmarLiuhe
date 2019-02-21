package com.liuheonline.la.entity;

import java.io.Serializable;

public class PaysEntity implements Serializable {
    @Override
    public String toString() {
        return "PaysEntity{" +
                "payAppName='" + payAppName + '\'' +
                ", sign='" + sign + '\'' +
                ", addon=" + addon +
                ", order=" + order +
                '}';
    }

    /**
     * payAppName : 支付宝
     * sign : 1b3a7f651fbabda621e52869e7bb1fc8
     * addon : {"contacttips":"支付即时到账，未到账请与我们联系&lt;br /&gt;订单号：{out_order_id}","expiretips":"二维码已过期,请点击这里刷新后重新尝试支付!","jumptips":"支付成功!2秒后将自动跳转!","notifyurl":"","returnurl":"","successtips":"支付成功!请关闭当前窗口以便于继续操作!"}
     * order : {"order_id":113,"out_order_id":520588357497950166,"price":"1.01","type":"alipay","remainseconds":300,"status":"0","qrcodeurl":"wxp://f2f0ckxW23A_fTogOrrDabL0XmDj063yEb5k"}
     */

    private String payAppName;
    private String sign;
    private AddonBean addon;
    private OrderBean order;

    public String getPayAppName() {
        return payAppName;
    }

    public void setPayAppName(String payAppName) {
        this.payAppName = payAppName;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public AddonBean getAddon() {
        return addon;
    }

    public void setAddon(AddonBean addon) {
        this.addon = addon;
    }

    public OrderBean getOrder() {
        return order;
    }

    public void setOrder(OrderBean order) {
        this.order = order;
    }

    public static class AddonBean implements Serializable{
        @Override
        public String toString() {
            return "AddonBean{" +
                    "contacttips='" + contacttips + '\'' +
                    ", expiretips='" + expiretips + '\'' +
                    ", jumptips='" + jumptips + '\'' +
                    ", notifyurl='" + notifyurl + '\'' +
                    ", returnurl='" + returnurl + '\'' +
                    ", successtips='" + successtips + '\'' +
                    '}';
        }

        /**
         * contacttips : 支付即时到账，未到账请与我们联系&lt;br /&gt;订单号：{out_order_id}
         * expiretips : 二维码已过期,请点击这里刷新后重新尝试支付!
         * jumptips : 支付成功!2秒后将自动跳转!
         * notifyurl :
         * returnurl :
         * successtips : 支付成功!请关闭当前窗口以便于继续操作!
         */

        private String contacttips;
        private String expiretips;
        private String jumptips;
        private String notifyurl;
        private String returnurl;
        private String successtips;

        public String getContacttips() {
            return contacttips;
        }

        public void setContacttips(String contacttips) {
            this.contacttips = contacttips;
        }

        public String getExpiretips() {
            return expiretips;
        }

        public void setExpiretips(String expiretips) {
            this.expiretips = expiretips;
        }

        public String getJumptips() {
            return jumptips;
        }

        public void setJumptips(String jumptips) {
            this.jumptips = jumptips;
        }

        public String getNotifyurl() {
            return notifyurl;
        }

        public void setNotifyurl(String notifyurl) {
            this.notifyurl = notifyurl;
        }

        public String getReturnurl() {
            return returnurl;
        }

        public void setReturnurl(String returnurl) {
            this.returnurl = returnurl;
        }

        public String getSuccesstips() {
            return successtips;
        }

        public void setSuccesstips(String successtips) {
            this.successtips = successtips;
        }
    }

    public static class OrderBean implements Serializable{
        @Override
        public String toString() {
            return "OrderBean{" +
                    "order_id=" + order_id +
                    ", out_order_id=" + out_order_id +
                    ", price='" + price + '\'' +
                    ", type='" + type + '\'' +
                    ", remainseconds=" + remainseconds +
                    ", status='" + status + '\'' +
                    ", qrcodeurl='" + qrcodeurl + '\'' +
                    '}';
        }

        /**
         * order_id : 113
         * out_order_id : 520588357497950166
         * price : 1.01
         * type : alipay
         * remainseconds : 300
         * status : 0
         * qrcodeurl : wxp://f2f0ckxW23A_fTogOrrDabL0XmDj063yEb5k
         */

        private int order_id;
        private long out_order_id;
        private String price;
        private String type;
        private int remainseconds;
        private String status;
        private String qrcodeurl;

        public int getOrder_id() {
            return order_id;
        }

        public void setOrder_id(int order_id) {
            this.order_id = order_id;
        }

        public long getOut_order_id() {
            return out_order_id;
        }

        public void setOut_order_id(long out_order_id) {
            this.out_order_id = out_order_id;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public int getRemainseconds() {
            return remainseconds;
        }

        public void setRemainseconds(int remainseconds) {
            this.remainseconds = remainseconds;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getQrcodeurl() {
            return qrcodeurl;
        }

        public void setQrcodeurl(String qrcodeurl) {
            this.qrcodeurl = qrcodeurl;
        }
    }
}
