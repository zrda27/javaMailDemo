package com.zrd.work.mail;

import javax.activation.DataHandler;
import javax.mail.*;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
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
    private Session session;
    private Properties props;

    private Folder inbox;

    public MailClient(){}

    /**
     * @param receiveProtocol notNull, receive mail protocol, use imap, imaps, pop3 or pop3s
     * @param receiveHost notNull, receive mail server host
     * @param receivePort nullable, receive mail server port, default value depends on protocol
     * @param sendHost notNull, send mail server host, use smtp protocol
     * @param sendPort nullable, send mail server port, default value depends on smtp protocol
     * @param user notNull, email account, such as "someone@gmail.com"
     * @param password notNull, email account password
     */
    public MailClient(String receiveProtocol, String receiveHost, Integer receivePort, String sendHost,
               Integer sendPort, String user, String password) throws MessagingException {
        this.receiveProtocol = receiveProtocol;
        this.receiveHost = receiveHost;
        this.receivePort = receivePort;
        this.sendHost = sendHost;
        this.sendPort = sendPort;
        this.user = user;
        this.password = password;
    }

    /**
     * init mail server and authention
     */
    public void init() throws MessagingException {
        this.checkArgs();

        props = new Properties();
        //send mail server args setup
        props.put("mail.smtp.auth", true);
        props.put("mail.smtp.host", sendHost);
        if(sendPort != null){
            props.put("mail.smtp.port", sendPort);
        }
        props.put("mail.from", user);
        //receive mail server args setup
        props.put("mail.store.protocol", receiveProtocol);
        props.put("mail." + receiveProtocol + ".host", receiveHost);
        if(receivePort != null){
            props.put("mail." + receiveProtocol + ".port", receivePort);
        }
        Authenticator authenticator = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(user, password);
            }
        };
        //create session instance use props
        this.session = Session.getInstance(props, authenticator);

        //try to open the INBOX
        this.initInbox();
    }

    /**
     *
     */
    private void checkArgs(){
        if(this.receiveProtocol == null){
            throw new NullPointerException("missing receiveProtocol");
        }
        if(this.receiveHost == null){
            throw new NullPointerException("missing receiveHost");
        }
        if(this.sendHost == null){
            throw new NullPointerException("missing sendHost");
        }
        if(this.user == null){
            throw new NullPointerException("missing user");
        }
        if(this.password == null){
            throw new NullPointerException("missing password");
        }
    }

    /**
     * @throws MessagingException
     */
    private void initInbox() throws MessagingException {
        Store store = session.getStore();
        store.connect();
        Folder pns = store.getDefaultFolder();
        this.inbox = pns.getFolder("INBOX");
        this.inbox.open(Folder.READ_ONLY);
    }

    /**
     * complete method to send msg
     * @param recipients notNull
     * @param ccRecipients nullable, send copys to someones
     * @param bccRecipients nullable, send copys secretly to someones
     * @param subject nullable, email title
     * @param content email content, can be html
     * @param filenames nullable, attach files
     * @throws MessagingException
     * @throws UnsupportedEncodingException
     */
    public void sendMsg(String[] recipients, String[] ccRecipients, String[] bccRecipients,
                        String subject, String content, String[] filenames) throws MessagingException, IOException {
        if(content == null && filenames == null){
            throw new IllegalArgumentException("either content or filenames " +
                    "should be not null");
        }
        MimeMessage mimeMessage = new MimeMessage(session);
        //set recipient
        for(String recipient: recipients){
            mimeMessage.addRecipients(Message.RecipientType.TO, recipient);
        }
        //set ccRecipient
        if(ccRecipients != null){
            for(String recipient: ccRecipients){
                mimeMessage.addRecipients(Message.RecipientType.CC, recipient);
            }
        }
        //set bccRecipient
        if(bccRecipients != null){
            for(String recipient: bccRecipients){
                mimeMessage.addRecipients(Message.RecipientType.BCC, recipient);
            }
        }
        //set subject
        mimeMessage.setSubject(subject, "utf-8");
        mimeMessage.setSentDate(new Date());

        //set mail body
        Multipart multipart = new MimeMultipart();
        if(content != null){
            MimeBodyPart contentPart = new MimeBodyPart();
            contentPart.setDataHandler(new DataHandler(content, "text/html;charset=utf-8"));
            multipart.addBodyPart(contentPart);
        }
        //set mail attach file
        if(filenames != null){
            for(String filename: filenames){
                MimeBodyPart filesPart = new MimeBodyPart();
                filesPart.attachFile(filename);
                multipart.addBodyPart(filesPart);
            }
        }
        mimeMessage.setContent(multipart);
        Transport.send(mimeMessage);
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

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Folder getInbox() {
        return inbox;
    }

    public void setInbox(Folder inbox) {
        this.inbox = inbox;
    }
}
