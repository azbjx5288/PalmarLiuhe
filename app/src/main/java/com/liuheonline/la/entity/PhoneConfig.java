package com.liuheonline.la.entity;

/**
 * @author: aini
 * @date 2018/8/24 09:43
 * @description 手机系统信息
 */
public class PhoneConfig {

    private String system_version;

    private String system_model;

    private String device_brand;

    private String imei;


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    private String type;

    public String getSystem_version() {
        return system_version;
    }

    public void setSystem_version(String system_version) {
        this.system_version = system_version;
    }

    public String getSystem_model() {
        return system_model;
    }

    public void setSystem_model(String system_model) {
        this.system_model = system_model;
    }

    public String getDevice_brand() {
        return device_brand;
    }

    public void setDevice_brand(String device_brand) {
        this.device_brand = device_brand;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getApk_version() {
        return apk_version;
    }

    public void setApk_version(String apk_version) {
        this.apk_version = apk_version;
    }

    private String apk_version;

    public PhoneConfig(String system_version, String system_model, String device_brand, String imei, String apk_version,String type) {
        this.system_version = system_version;
        this.system_model = system_model;
        this.device_brand = device_brand;
        this.imei = imei;
        this.apk_version = apk_version;
        this.type = type;
    }
}
