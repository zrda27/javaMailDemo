package test.zrd.work.mail;

import com.zrd.work.mail.MailClient;
import org.junit.Test;

/**
 * Created by zrd on 2017/2/21.
 */
public class TestMailClient {
    private MailClient client;

    {
        //see construts for the argument descriptions
        client = new MailClient("imaps", "imap.163.com", null,
                "smtp.163.com", null, "zrda27@163.com", "wy901213");
    }

    @Test
    public void testSendMsg() throws Exception {
        client.sendMsg(new String[]{"419505403@qq.com"}, new String[]{"zrda27@163.com"},
                null, "你好", "<h1>你好吗</h1>",
                new String[]{TestMailClient.class.getResource("/11.txt").getPath(), TestMailClient.class.getResource("/11.txt").getPath()});
    }

    @Test
    public void testRead() throws Exception {
        client.readMsg();
    }
}
