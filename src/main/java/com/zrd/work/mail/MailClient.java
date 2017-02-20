package com.zrd.work.mail;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Store;

/**
 * Created by zrd on 2017/2/20.
 */
public class MailClient {
    private String protocol = null;
    private String host = null;
    private String user = null;
    private String password = null;
    private String url = null;
    private String root = null;
    private String pattern = "%";
    private boolean recursive = false;
    private boolean verbose = false;
    private boolean debug = false;

    public static void main(String[] args) throws Exception {
        Session session = Session.getInstance(System.getProperties());
        session.setDebug(true);
        Store store = session.getStore("imap");
        store.connect("imap.163.com", "zrda27", "wy901213");
        System.out.println(store.isConnected());
        Folder[] pns = store.getSharedNamespaces();
        for(Folder folder: pns){
            Message[] msgs = store.getDefaultFolder().getMessages();
            for(Message msg: msgs){
                System.out.println(msgs);
            }
            System.out.println(folder.getURLName());
            System.out.println(folder.getMessageCount());
        }
        store.close();
    }
}
