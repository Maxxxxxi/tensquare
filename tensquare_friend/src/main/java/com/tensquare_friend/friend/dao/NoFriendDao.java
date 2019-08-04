package com.tensquare_friend.friend.dao;

import com.tensquare_friend.friend.entity.NoFriend;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoFriendDao extends JpaRepository<NoFriend, String> {
    public NoFriend findByUseridAndFriendid(String userid,String nofriendid);

}
