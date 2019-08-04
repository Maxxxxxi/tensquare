package com.tensquare_friend.friend.dao;

import com.tensquare_friend.friend.entity.Friend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface FriendDao extends JpaRepository<Friend,String> {

    public Friend findByUseridAndFriendid(String userid,String riendid);


    @Modifying
    @Query(value = "UPDATE tb_friend SET islike = ? WHERE userid =? AND friendid =?",nativeQuery = true)
    void updateIslike(String s, String userid, String friendid);

    @Modifying
    @Query(value = "DELETE FROM tb_friend WHERE userid = ? AND friendid = ?",nativeQuery = true)
    public void deleteFriend(String userid, String friendid);
}
