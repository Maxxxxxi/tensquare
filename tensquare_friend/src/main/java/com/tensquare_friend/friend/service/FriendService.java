package com.tensquare_friend.friend.service;

import com.tensquare_friend.friend.client.UserClient;
import com.tensquare_friend.friend.dao.FriendDao;
import com.tensquare_friend.friend.dao.NoFriendDao;
import com.tensquare_friend.friend.entity.Friend;
import com.tensquare_friend.friend.entity.NoFriend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class FriendService {

    @Autowired
    private FriendDao friendDao;

    @Autowired
    private NoFriendDao noFriendDao;

    @Autowired
    private UserClient userClient;



    public int addFriend(String userid, String friendid) {
        //先判断user到friend是否有数据，有就是重复添加好友
        Friend friend = friendDao.findByUseridAndFriendid(userid, friendid);

        if (friend != null)
            return 0;//避免重复添加

        //直接添加好友，先让好友表中userid到friendid方向的type为0
        friend = new Friend();
        friend.setUserid(userid);
        friend.setFriendid(friendid);
        friend.setIslike("0");
        friendDao.save(friend);
        //判断从friendid到userid是否存在数据，如果有，就将双方的状态改为1

        userClient.updatefanscountandfollowcount(userid,friendid,1);

        if(friendDao.findByUseridAndFriendid(friendid,userid) != null) {
            //把双发的isLike都改为1
            friendDao.updateIslike("1", userid, friendid);
            friendDao.updateIslike("1",friendid,userid);
        }
        return 1;
    }

    public int addNoFriend(String userid, String friendid) {

        NoFriend noFriend = noFriendDao.findByUseridAndFriendid(userid, friendid);
        if(noFriend != null){
            return 0;//不能重复添加非好友
        }
        noFriend =new NoFriend();
        noFriend.setUserid(userid);
        noFriend.setFriendid(friendid);
        noFriendDao.save(noFriend);
        return 1;
    }

    public void deleteFriend(String userid, String friendid) {
        //删除好友表中的userid到friendid这条数据
        friendDao.deleteFriend(userid,friendid);
        //更新friendid到userid的islike为 0
        friendDao.updateIslike("0",userid,friendid);
        //非好友表中添加数据
        NoFriend noFriend = new NoFriend();
        noFriend.setUserid(userid);
        noFriend.setFriendid(friendid);
        noFriendDao.save(noFriend);

        userClient.updatefanscountandfollowcount(userid,friendid,-1);
    }
}
