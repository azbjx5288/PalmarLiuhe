package com.liuheonline.la.entity;

public class WebEntity {


    /**
     * lucky_url : http://120.79.189.244/index.html#/activity
     * chat_url : http://m.zs2525.com/chat/im-chat.html
     * share_url : http://m.zs2525.com/#/reg?uid=
     * service_url : https://f88.live800.com/live800/chatClient/chatbox.jsp?companyID=1037614&amp;configID=151374&amp;jid=8087301998&amp;skillId=7863&amp;s=1
     * agent_announcement : dfgdfggdgdfg
     * agent_pwd : 1234567
     * memberagreement :       掌上六合APP属彩票委托投注资金管理平台（以下简称“本平台”）。在本平台登记注册的用户，并同意以下条款，方有资格享受本平台提供的相关服务，并受本规则条款的约束。用户使用本平台服务即意味着同意本规则，使用前请认真阅读。本平台对本规则拥有最终解释权。
     1、	使用本公司APP的客户，请留意你所在的国家或居住地的相关法律规定，如有疑问应就相关问题，寻求当地法律意见。
     2、	若发生遭黑客入侵破坏行为或不可抗拒之灾害导致APP故障或资料损坏、资料丢失等情况，我们将以本公司之后备资料为最后处理依据；为确保各方利益，请各会员投注后列印资料。本平台不会接受没有列印资料的投诉。
     3、	为避免纠纷，各会员在投注之后，务必进入下注状况检查及列印资料。若发现任何异常，请立即与代理商联系查证，一切投注将以本公司资料库的资料为准，不得异议。如出现特殊网络情况或线不稳定导致不能下注或下注失败。本公司概不负责。
     4、	开奖结果以官方公面的结果为准。
     5、	我们将竭力提供准确可靠的开奖统计等资料，但并不保资料无误，统计资料只供参考，并非是对客户行为的指引，本平台也不接受关于统计数据产生错误而引起的相关投诉。
     6、	本平台拥有一切判决及注消任何涉嫌以非正常方式下注之权利，在进行更深入调查期间将停止发放与其有关之任何彩金。客户有责任确保自己的账号及密码保密，如果客户怀疑自己的资料被盗用，应立即通过客服联系本公司，并须更改其个人详细资料。所有被盗用账号之损失由客户自行负责。
     7、	使用本服务的同时受本平台《购彩服务协议》的约束。平台以此声明作为对平本平用户隐私保护的许诺。平台的隐私声明正在不断改进中，随着本平台服务范围的扩大，会随时更新隐私声明。
     8、	您使用本服务即视为您已阅读并同意受本协议的约束。本平台有权在必要时修改本协议条款。您可以在本平台的最新版本中查阅相关协议条款。本协议条款变更后，如果您继续使用本服务，即视为你已接受修改后的协议。如果您不接受修改后的协议，应当停止使用本服务。

     * app_img : {"id":4107,"pic":"img/20181009/5bbc5bad464b4.jpg","img_url":"www.taobao.com","status":1,"pic_link":"http://120.79.189.244/images/img/20181009/5bbc5bad464b4.jpg"}
     * announcement : {"announcement":"wwww","status":"1"}
     * exp_obtain : 0
     */

    private String lucky_url;
    private String chat_url;
    private String share_url;
    private String service_url;
    private String agent_announcement;
    private String agent_pwd;

    public String getApp_betting_url() {
        return app_betting_url;
    }

    public void setApp_betting_url(String app_betting_url) {
        this.app_betting_url = app_betting_url;
    }

    private String app_betting_url;
    private String memberagreement;
    private AppImgBean app_img;
    private AnnouncementBean announcement;
    private String exp_obtain;

    public String getLucky_url() {
        return lucky_url;
    }

    public void setLucky_url(String lucky_url) {
        this.lucky_url = lucky_url;
    }

    public String getChat_url() {
        return chat_url;
    }

    public void setChat_url(String chat_url) {
        this.chat_url = chat_url;
    }

    public String getShare_url() {
        return share_url;
    }

    public void setShare_url(String share_url) {
        this.share_url = share_url;
    }

    public String getService_url() {
        return service_url;
    }

    public void setService_url(String service_url) {
        this.service_url = service_url;
    }

    public String getAgent_announcement() {
        return agent_announcement;
    }

    public void setAgent_announcement(String agent_announcement) {
        this.agent_announcement = agent_announcement;
    }

    public String getAgent_pwd() {
        return agent_pwd;
    }

    public void setAgent_pwd(String agent_pwd) {
        this.agent_pwd = agent_pwd;
    }

    public String getMemberagreement() {
        return memberagreement;
    }

    public void setMemberagreement(String memberagreement) {
        this.memberagreement = memberagreement;
    }

    public AppImgBean getApp_img() {
        return app_img;
    }

    public void setApp_img(AppImgBean app_img) {
        this.app_img = app_img;
    }

    public AnnouncementBean getAnnouncement() {
        return announcement;
    }

    public void setAnnouncement(AnnouncementBean announcement) {
        this.announcement = announcement;
    }

    public String getExp_obtain() {
        return exp_obtain;
    }

    public void setExp_obtain(String exp_obtain) {
        this.exp_obtain = exp_obtain;
    }

    public static class AppImgBean {
        /**
         * id : 4107
         * pic : img/20181009/5bbc5bad464b4.jpg
         * img_url : www.taobao.com
         * status : 1
         * pic_link : http://120.79.189.244/images/img/20181009/5bbc5bad464b4.jpg
         */

        private int id;
        private String pic;
        private String img_url;
        private int status;
        private String pic_link;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getPic() {
            return pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }

        public String getImg_url() {
            return img_url;
        }

        public void setImg_url(String img_url) {
            this.img_url = img_url;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getPic_link() {
            return pic_link;
        }

        public void setPic_link(String pic_link) {
            this.pic_link = pic_link;
        }
    }

    public static class AnnouncementBean {
        /**
         * announcement : wwww
         * status : 1
         */

        private String announcement;
        private String status;

        public String getAnnouncement() {
            return announcement;
        }

        public void setAnnouncement(String announcement) {
            this.announcement = announcement;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }
}
