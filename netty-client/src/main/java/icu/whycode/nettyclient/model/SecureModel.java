package icu.whycode.nettyclient.model;

import java.io.Serializable;

public class SecureModel implements Serializable {

    private static final long serialVersionUID = 7135682066021329548L;

    /**
     * 验证 token
     */
    private String token;

    private boolean autoSuccess;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public boolean isAutoSuccess() {
        return autoSuccess;
    }

    public void setAutoSuccess(boolean autoSuccess) {
        this.autoSuccess = autoSuccess;
    }
}
