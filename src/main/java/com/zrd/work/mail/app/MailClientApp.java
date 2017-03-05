package com.zrd.work.mail.app;

import com.zrd.work.mail.MailClient;
import org.apache.commons.io.IOUtils;

import javax.mail.Message;
import javax.mail.MessagingException;
import java.io.FileInputStream;
import java.io.InputStream;

/**
 * Created by zrd on 2017/3/5.
 */
public class MailClientApp {
    private static MailClient mailClient = new MailClient();
    public static void main(String[] argv) {
        String type = null;
        String recipients = null;
        String sendMsg = null;
        String sendMsgFromFile = null;
        String subject = null;

        /*
         * Process command line arguments.
         */
        for (int optind = 0; optind < argv.length; optind++) {
            if (argv[optind].equals("-rp")) {
                mailClient.setReceiveProtocol(argv[++optind]);
            } else if (argv[optind].equals("-rh")) {
                mailClient.setReceiveHost(argv[++optind]);
            } else if (argv[optind].equals("-rP")) {
                try {
                    mailClient.setReceivePort(Integer.parseInt(argv[++optind]));
                }catch (NumberFormatException e){
                    throw new NumberFormatException("receivePort must be a number");
                }
            } else if (argv[optind].equals("-sh")) {
                mailClient.setSendHost(argv[++optind]);
            } else if (argv[optind].equals("-sP")) {
                try {
                    mailClient.setSendPort(Integer.parseInt(argv[++optind]));
                }catch (NumberFormatException e){
                    throw new NumberFormatException("receivePort must be a number");
                }
            } else if (argv[optind].equals("-u")) {
                mailClient.setUser(argv[++optind]);
            } else if (argv[optind].equals("-p")) {
                mailClient.setPassword(argv[++optind]);
            } else if (argv[optind].equals("-s")) {
                MailConfig config = MailConfigFactory.create(argv[++optind]);
                mailClient.setReceiveHost(config.getReceiveHost());
                mailClient.setReceivePort(config.getReceivePort());
                mailClient.setReceiveProtocol(config.getReceiveProtocol());
                mailClient.setSendPort(config.getSendPort());
                mailClient.setSendHost(config.getSendHost());
            } else if (argv[optind].equals("-t")) {
                type = argv[++optind];
            } else if (argv[optind].equals("-to")) {
                recipients = argv[++optind];
            } else if (argv[optind].equals("-msg")) {
                sendMsg = argv[++optind];
            } else if (argv[optind].equals("-msg4f")) {
                sendMsgFromFile = argv[++optind];
            } else if (argv[optind].equals("-subject")) {
                subject = argv[++optind];
            } else if (argv[optind].startsWith("-")) {
                printHelp();
            } else {
                break;
            }
        }

        try {
            mailClient.init();
            System.out.println("login success：" + mailClient.getUser());
        } catch (Exception e) {
            System.err.println("login failed, cause by: " + e.getMessage());
            printHelp();
        }

        if(type == null){
            System.err.println("error:type must be set");
            printHelp();
        }

        switch (type){
            case "send":  // to send an email
                try {
                    if(recipients == null){
                        throw new IllegalArgumentException("recipients should be set when to send an email, use -to");
                    }
                    if(sendMsg == null){  //if msg is null, then read msg from a file
                        if(sendMsgFromFile == null){
                            throw new IllegalArgumentException("either msg or file path should be set when to send an email, use -msg or -msg4f");
                        }else{
                            try(InputStream is = new FileInputStream(sendMsgFromFile)){
                                sendMsg = IOUtils.toString(is, "utf-8");
                            }
                        }
                    }
                    mailClient.sendMsg(new String[]{recipients}, null, null, subject, sendMsg, null);
                    System.out.println("send email success：\t" + recipients + "\t" + subject);
                } catch (Exception e) {
                    System.err.println("send email failed, cause by: " + e.getMessage());
                    printHelp();
                }
            case "receive":
                try {
                    //receive email from inbox
                    int count = mailClient.getInbox().getMessageCount();
                    Message[] msgs = mailClient.getInbox().getMessages();
                    System.out.println("there are " + count + "emails");
                    if(msgs != null){
                        for(int i=0; i<msgs.length; i++){
                            System.out.println(i + 1 + ". sender: " + msgs[i].getFrom()[0] + "\tsubject:" + msgs[i].getSubject());
                        }
                    }
                } catch (MessagingException e) {
                    e.printStackTrace();
                }
            default:

        }
    }

    public static void printHelp(){
        System.out.println(
                "Usage: mailClient [-s email server 'gmail' or '163'] | ");
        System.out.println(
                "\t[[-rp receiveProtocol] [-rh receiveHost] [-rP receivePort] t[-sh sendHost] [-sP sendPort]]");
        System.out.println(
                "\t[-u user] [-p password]");
        System.out.println(
                "\t[-t type receive or send]");
        System.out.println(
                "\t[-to recipients when send a msg] [-subject a subject for email to send] [-msg msg content to send] [-msg4f file path for msg content to send]");
        System.exit(1);
    }
}
