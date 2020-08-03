package icu.whycode.nettyserver.model;

import java.io.Serializable;

public class ReceiveMessage implements Serializable {

    private static final long serialVersionUID = 172966509772612278L;

    private short msgType;

    private String data;

    public short getMsgType() {
        return msgType;
    }

    public void setMsgType(short msgType) {
        this.msgType = msgType;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
