package com.liuheonline.la.entity;

public class BankCard1Entity {

    /**
     * message : 下单成功
     * respCode : 00
     * merchno : 333120154110001
     * refno : z2180927100000101557
     * traceno : 160591386308759296
     * barCode : http://47.75.153.103:6001/url?r=z2180927100000101557
     * signature : 6E191FDAD652FFFCF9F4D533D4971F54
     * code : 200
     */

    private String message;
    private String respCode;
    private String merchno;
    private String refno;
    private String traceno;
    private String barCode;
    private String signature;
    private int code;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getRespCode() {
        return respCode;
    }

    public void setRespCode(String respCode) {
        this.respCode = respCode;
    }

    public String getMerchno() {
        return merchno;
    }

    public void setMerchno(String merchno) {
        this.merchno = merchno;
    }

    public String getRefno() {
        return refno;
    }

    public void setRefno(String refno) {
        this.refno = refno;
    }

    public String getTraceno() {
        return traceno;
    }

    public void setTraceno(String traceno) {
        this.traceno = traceno;
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "BankCard1Entity{" +
                "message='" + message + '\'' +
                ", respCode='" + respCode + '\'' +
                ", merchno='" + merchno + '\'' +
                ", refno='" + refno + '\'' +
                ", traceno='" + traceno + '\'' +
                ", barCode='" + barCode + '\'' +
                ", signature='" + signature + '\'' +
                ", code=" + code +
                '}';
    }
}
