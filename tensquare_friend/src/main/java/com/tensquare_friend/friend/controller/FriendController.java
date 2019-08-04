package com.tensquare_friend.friend.controller;


import com.tensquare_friend.friend.client.UserClient;
import com.tensquare_friend.friend.service.FriendService;
import entity.Result;
import entity.StatusCode;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/friend")
public class FriendController {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private FriendService friendService;

    @Autowired
    private UserClient userClient;

    @RequestMapping("/like/{friendid}/{type}")
    public Result addFriend(@PathVariable String friendid, @PathVariable String type){
        Claims claims = (Claims)request.getAttribute("claim_user");
        if(claims == null){
            //表明当前没有user权限
            return new Result(false, StatusCode.ACCESSERROR,"权限不足");
        }
        String userid = claims.getId();
        if(type != null){
            if(type.equals("1")){
                //添加好友
                System.out.println("userid: "+userid+" friendid:　"+friendid);
                int flag = friendService.addFriend(userid,friendid);
                if(flag == 0)
                    return new Result(false,StatusCode.ERROR,"不能重复添加");
                if(flag == 1) {
                    //事务控制
               //     userClient.updatefanscountandfollowcount(userid,friendid,1);
                    return new Result(true, StatusCode.OK, "添加成功");
                }
            }else if(type.equals("2")){
                //添加非好友
                int flag = friendService.addNoFriend(userid,friendid);
                if(flag == 0)
                    return new Result(false,StatusCode.ERROR,"不能重复添加非好友");
                return new Result(true,StatusCode.OK,"添加成功");
            }
            return new Result(false, StatusCode.ACCESSERROR,"参数不正确");
        }else{
            return new Result(false, StatusCode.ACCESSERROR,"参数不正确");
        }
    }
    @RequestMapping(value = "/{friendid}",method = RequestMethod.DELETE)
    public Result deleteFriend(@PathVariable String friendid){
        Claims claims = (Claims) request.getAttribute("claim_user");
        if(claims == null){
            return new Result(false, StatusCode.ACCESSERROR,"权限不足");
        }
        String userid = claims.getId();
        friendService.deleteFriend(userid, friendid);
        return new Result(true,StatusCode.OK,"删除成功");
    }
}
