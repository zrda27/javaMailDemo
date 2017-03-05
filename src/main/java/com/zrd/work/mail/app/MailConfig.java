package com.zrd.work.mail.app;

/**
 * Created by zrd on 2017/3/5.
 */
public class MailConfig {
    //receive email server arguments
    private String receiveProtocol = null;
    private String receiveHost = null;
    private Integer receivePort = null;
    //send email server arguments
    private String sendHost = null;
    private Integer sendPort = null;

    public MailConfig(){}

    public MailConfig(String receiveProtocol, String receiveHost, Integer receivePort, String sendHost,
                      Integer sendPort){
        this.receiveProtocol = receiveProtocol;
        this.receiveHost = receiveHost;
        this.receivePort = receivePort;
        this.sendHost = sendHost;
        this.sendPort = sendPort;
    }

    public String getReceiveProtocol() {
        return receiveProtocol;
    }

    public void setReceiveProtocol(String receiveProtocol) {
        this.receiveProtocol = receiveProtocol;
    }

    public String getReceiveHost() {
        return receiveHost;
    }

    public void setReceiveHost(String receiveHost) {
        this.receiveHost = receiveHost;
    }

    public Integer getReceivePort() {
        return receivePort;
    }

    public void setReceivePort(Integer receivePort) {
        this.receivePort = receivePort;
    }

    public String getSendHost() {
        return sendHost;
    }

    public void setSendHost(String sendHost) {
        this.sendHost = sendHost;
    }

    public Integer getSendPort() {
        return sendPort;
    }

    public void setSendPort(Integer sendPort) {
        this.sendPort = sendPort;
    }
}
