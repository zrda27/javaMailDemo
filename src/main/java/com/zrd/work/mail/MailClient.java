package com.zrd.work.mail;

import com.sun.istack.internal.NotNull;

import javax.mail.*;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;

/**
 * Created by zrd on 2017/2/20.
 */
public class MailClient {
    private String receiveProtocol = null;
    private String receiveHost = null;
    private Integer receivePort = null;
    private String sendHost = null;
    private Integer sendPort = null;
    private String user = null;
    private String password = null;
    private String url = null;
    private String root = null;
    private String pattern = "%";
    private boolean recursive = false;
    private boolean verbose = false;
    private boolean debug = false;
    private Session session;
    private Properties props;

    MailClient(){}

    MailClient(@NotNull String receiveProtocol, String receiveHost, Integer receivePort, String sendHost,
               Integer sendPort, String user, String password){
        this.receiveProtocol = receiveProtocol;
        this.receiveHost = receiveHost;
        this.receivePort = receivePort;
        this.sendHost = sendHost;
        this.sendPort = sendPort;
        this.user = user;
        this.password = password;
        init();
    }

    public void init() {
        props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.host", sendHost);
        props.put("mail.store.protocol", receiveProtocol);
        props.put("mail.store.host", receiveHost);
        props.put("mail.store.port", receivePort);
        if(sendPort != null){
            props.put("mail.smtp.port", sendPort);
        }
        Authenticator authenticator = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(user, password);
            }
        };
        this.session = Session.getInstance(props, authenticator);
    }

    public void sendMsg() throws MessagingException {
        MimeMessage msg = new MimeMessage(session);
        msg.setFrom("zrda27@163.com");
        msg.setRecipients(Message.RecipientType.TO, "419505403@qq.com");
        msg.setSubject("你好", "utf-8");
        msg.setSentDate(new Date());
        msg.setText("你好", "utf-8");
        Transport.send(msg);
    }

    public void readMsg() throws Exception {
        Store store = session.getStore(receiveProtocol);
        store.connect(user, password);
        System.out.println(store.isConnected());
        Folder pns = store.getDefaultFolder();
        Folder[] folders = pns.list();
        for(Folder folder: folders){
            System.out.println(folder.getName());
        }
        Folder offers = pns.getFolder("已发送");
        System.out.println(offers.getMessageCount());
        offers.open(Folder.READ_ONLY);
        Message[] msgs = offers.getMessages();
        for(Message msg: msgs){
            System.out.println(msg.getSubject());
        }
        store.close();
    }

    public static void main(String[] args) throws Exception {
        /*Properties props = System.getProperties();
        props.put("mail.smtp.host", "smtp.163.com");
        Session session = Session.getInstance(System.getProperties());
        Store store = session.getStore("pop3s");
        store.connect("pop.163.com", "zrda27", "wy901213");
        System.out.println(store.isConnected());
        Folder pns = store.getDefaultFolder();
        Folder[] folders = pns.list();
        for(Folder folder: folders){
            System.out.println(folder.getName());
        }
        Folder offers = pns.getFolder("INBOX");
        System.out.println(offers.getMessageCount());
        offers.open(Folder.READ_ONLY);
        Message[] msgs = offers.getMessages();
        for(Message msg: msgs){
            System.out.println(msg.getSubject());
        }
        store.close();*/
        MailClient client = new MailClient("imaps", "imap.163.com", 993,
                "smtp.163.com", null, "zrda27", "wy901213");
        //client.sendMsg();
        client.readMsg();
    }
}
