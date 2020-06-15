package com.zc.wechatanalysis.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.zc.wechatanalysis.domains.ESMessage;
import com.zc.wechatanalysis.domains.Friend;
import com.zc.wechatanalysis.domains.Message;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;

@Service
public class SendESMessageReactor {
    private static final Log logger = LogFactory.getLog(SendESMessageReactor.class);
    @Autowired
    private WechatDataReadService wechatDataReadService;

    @Autowired
    private IndexWechatDocumentService indexWechatDocumentService;

    public void send() {
        final List<Friend> readFriends = wechatDataReadService.readFriends();
        Flux<Friend> fFriends = Flux.fromIterable(readFriends);
        // .parallel().runOn(Schedulers.parallel())
        fFriends.subscribe((friend) -> {
            // System.out.println(Thread.currentThread().getName() + "->" +
            // friend.getUserNameMd5());
            final Optional<List<Message>> ofriendMessages = wechatDataReadService.readMessagesByFriend(friend);

            if (ofriendMessages.isPresent() && ofriendMessages.get().size() > 0) {
                // logger.info("friend: " + friend.getDbContactRemark() + " has :" +
                // ofriendMessages.get().size());

                // Flux<ESMessage> fESMessages =
                // Flux.fromIterable(ofriendMessages.get()).map((message) -> {
                // // System.out.println(message.getMessage());
                // return wechatDataReadService.friendMessageToESMessage(message, friend);
                // });

                List<ESMessage> fESMessages = ofriendMessages.get().stream()
                        .filter(message -> message.getMessage().length() > 0).map((message) -> {
                            // System.out.println(message.getMessage());
                            return wechatDataReadService.friendMessageToESMessage(message, friend);
                        }).collect(Collectors.toList());

                indexWechatDocumentService.indexMessages(fESMessages);
                /**
                 * .subscribe((message) -> { // System.out.println(message.getUserName());
                 * indexWechatDocumentService.indexMessage(message); });
                 */
            }
        });

    }

}