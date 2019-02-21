package com.liuheonline.la.event;

/**
 * @author: aini
 * @date 2018/8/8 14:33
 * @description 登录
 */
public class LoginEvent {

    private boolean isLogin;

    public LoginEvent(boolean isLogin) {
        this.isLogin = isLogin;
    }

    public boolean isLogin() {
        return isLogin;
    }

    public void setLogin(boolean login) {
        isLogin = login;
    }

}
