package com.zc.wechatanalysis.services;

import java.util.List;
import java.util.Optional;

import com.zc.wechatanalysis.domains.ESMessage;
import com.zc.wechatanalysis.domains.Friend;
import com.zc.wechatanalysis.domains.Message;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

@Service
public class WechatDataReadService {
    private static final Log logger = LogFactory.getLog(WechatDataReadService.class);

    @Autowired
    private JdbcTemplate contactJdbcTemplate;

    @Autowired
    private JdbcTemplate messageJdbcTemplate;

    public List<Friend> readFriends() {
        return contactJdbcTemplate
                .query("SELECT dbContactProfile,dbContactRemark,type,userName FROM Friend where type !=4", (rs, rn) -> {
                    Friend friend = new Friend();
                    String profile = rs.getString("dbContactProfile");
                    friend.setDbContactProfile(profile.replaceAll("[^\u4E00-\u9FA5]", ""));

                    String remark = rs.getString("dbContactRemark").replaceAll("[^\u4E00-\u9FA5]", "");
                    if (remark.length() == 0) {
                        // remark = remark.trim().replaceAll("\n", "");
                        String enName = rs.getString("dbContactRemark").trim().replaceAll("\n", "");
                        remark = enName.length() < 11 ? enName : enName.substring(0, 10);
                    }
                    friend.setDbContactRemark(remark);

                    // friend.setDbContactProfile(rs.getString("dbContactProfile"));
                    // friend.setDbContactRemark(rs.getString("dbContactRemark"));

                    friend.setType(rs.getByte("type"));
                    String userName = rs.getString("userName");
                    friend.setUserName(userName);
                    friend.setUserNameMd5(DigestUtils.md5DigestAsHex(userName.getBytes()));
                    return friend;
                });
    }

    /**
     *
     */
    public Optional<List<Message>> readMessagesByFriend(Friend friend) {
        String messageSql = String.format("SELECT CreateTime, Des, Message, Status, Type FROM Chat_%s where type =1",
                friend.getUserNameMd5());

        try {
            return Optional.of(messageJdbcTemplate.query(messageSql, (rs, rn) -> {
                Message message = new Message();
                message.setCreateTime(rs.getLong("CreateTime"));
                message.setDes(rs.getByte("Des"));
                message.setMessage(rs.getString("Message").replaceAll("[^\u4E00-\u9FA5]", ""));
                message.setStatus(rs.getByte("Status"));
                message.setType(rs.getByte("Type"));
                return message;
            }));
        } catch (DataAccessException e) {
            // logger.info(String.format(" Friend %s table isn't exist! %s",
            // friend.getDbContactRemark(),
            // friend.getUserNameMd5()));
        }
        return Optional.empty();

    }

    public ESMessage friendMessageToESMessage(Message message, Friend friend) {
        ESMessage esMessage = new ESMessage();
        esMessage.setCreateTime(Long.valueOf(message.getCreateTime() + "000"));
        esMessage.setDbContactRemark(friend.getDbContactRemark());
        esMessage.setDes(message.getDes());
        esMessage.setMessage(message.getMessage());
        esMessage.setStatus(message.getStatus());
        esMessage.setType(message.getType());
        esMessage.setUserName(friend.getUserName());
        esMessage.setFriendType(friend.getType());
        return esMessage;
    }

}