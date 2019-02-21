package com.liuheonline.la.entity;

public class DownLoadAPkEntity {

    /**
     * type : 1
     * url : http://app.liyababy.com/10-66ap
     * file_url : null
     * version : 1.2.2
     * remark : 1：修复注册时验证码能换行
     2：修复聊天室UID读取不成功
     3：美化部分UI
     * file_size : 0 B
     * create_time : 1536642282
     * file_url_link :
     */

    private int type;
    private String url;
    private Object file_url;
    private String version;
    private String remark;
    private String file_size;
    private int create_time;
    private String file_url_link;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Object getFile_url() {
        return file_url;
    }

    public void setFile_url(Object file_url) {
        this.file_url = file_url;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getFile_size() {
        return file_size;
    }

    public void setFile_size(String file_size) {
        this.file_size = file_size;
    }

    public int getCreate_time() {
        return create_time;
    }

    public void setCreate_time(int create_time) {
        this.create_time = create_time;
    }

    public String getFile_url_link() {
        return file_url_link;
    }

    public void setFile_url_link(String file_url_link) {
        this.file_url_link = file_url_link;
    }

    @Override
    public String toString() {
        return "DownLoadAPkEntity{" +
                "type=" + type +
                ", url='" + url + '\'' +
                ", file_url=" + file_url +
                ", version='" + version + '\'' +
                ", remark='" + remark + '\'' +
                ", file_size='" + file_size + '\'' +
                ", create_time=" + create_time +
                ", file_url_link='" + file_url_link + '\'' +
                '}';
    }
}
