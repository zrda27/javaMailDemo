package test.zrd.work.mail;

import com.zrd.work.mail.MailClient;
import org.junit.Test;

import javax.mail.MessagingException;

/**
 * Created by zrd on 2017/2/21.
 */
public class TestMailClient {
    private MailClient client;

    {
        //see construts for the argument descriptions
        try {
            client = new MailClient("imaps", "imap.163.com", null,
                    "smtp.163.com", null, "zrda27@163.com", "wy901213.");
            client.init();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testSendMsg() throws Exception {
        client.sendMsg(new String[]{"419505403@qq.com"}, null,
                null, "你好", "<h1>你好吗</h1>",
                new String[]{TestMailClient.class.getResource("/11.txt").getPath(), TestMailClient.class.getResource("/11.txt").getPath()});
    }

    public void testRead() throws Exception {
    }
}
