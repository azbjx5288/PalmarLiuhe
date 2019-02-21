package com.liuheonline.la.entity;

public class UpdateEntity {

    /**
     * id : 5
     * version : 1.0.0
     * create_time : 1533291862
     * file_url : file/20180803/5b642d5675bcb.apk
     * type : 1
     * remark : 222
     * is_updata : 1
     * url :
     */

    private int id;
    private String version;
    private int create_time;
    private String file_url;
    private int type;
    private String remark;

    public String getFile_url_link() {
        return file_url_link;
    }

    public void setFile_url_link(String file_url_link) {
        this.file_url_link = file_url_link;
    }
    private String file_url_link;
    private int is_updata;
    private String url;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public int getCreate_time() {
        return create_time;
    }

    public void setCreate_time(int create_time) {
        this.create_time = create_time;
    }

    public String getFile_url() {
        return file_url;
    }

    public void setFile_url(String file_url) {
        this.file_url = file_url;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getIs_updata() {
        return is_updata;
    }

    public void setIs_updata(int is_updata) {
        this.is_updata = is_updata;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "UpdateEntity{" +
                "id=" + id +
                ", version='" + version + '\'' +
                ", create_time=" + create_time +
                ", file_url='" + file_url + '\'' +
                ", type=" + type +
                ", remark='" + remark + '\'' +
                ", file_url_link='" + file_url_link + '\'' +
                ", is_updata=" + is_updata +
                ", url='" + url + '\'' +
                '}';
    }
}
