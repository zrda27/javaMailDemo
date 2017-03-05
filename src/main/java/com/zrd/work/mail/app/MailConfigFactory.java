package com.zrd.work.mail.app;

import java.util.NoSuchElementException;

/**
 * Created by zrd on 2017/3/5.
 */
public class MailConfigFactory {
    /**
     * @param type gmail or 163
     * @return
     */
    public static MailConfig create(String type){
        switch(type){
            case "gmail":
                return new MailConfig("imaps", "imap.gmail.com", null, "smtp.gmail.com", null);
            case "163":
                return new MailConfig("imaps", "imap.163.com", null, "smtp.163.com", null);
            default:
                throw new NoSuchElementException("no such pre defined email server call:" + type);
        }
    }
}
