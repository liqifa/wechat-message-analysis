package com.zc.wechatanalysis;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

import com.zc.wechatanalysis.domains.ESMessage;
import com.zc.wechatanalysis.domains.Friend;
import com.zc.wechatanalysis.services.IndexWechatDocumentService;
import com.zc.wechatanalysis.services.SendESMessageReactor;
import com.zc.wechatanalysis.services.WechatDataReadService;

import org.elasticsearch.client.RestHighLevelClient;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.event.annotation.AfterTestExecution;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class WechatDataReadServiceTest {
    @Autowired
    private WechatDataReadService wechatDataReadService;

    @Autowired
    private SendESMessageReactor sendESMessageReactor;

    @Autowired
    private IndexWechatDocumentService indexWechatDocumentService;

    @Test
    void testReadFriends() throws SQLException {

        long start = System.currentTimeMillis();
        sendESMessageReactor.send();
        long end = System.currentTimeMillis();
        System.out.println(end - start);
        // List<Friend> readFriends = wechatDataReadService.readFriends();

        // readFriends.stream().forEach((f) -> {
        // System.out.println(f.getDbContactRemark());
        // });

    }

    @Test
    void closeResource() {

    }

}
