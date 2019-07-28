package com.tensquare.test;


import com.tensquare.rabbit.RabbitmqApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RabbitmqApplication.class)
public class ProductTest {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Test
    public void sendMsg(){
        rabbitTemplate.convertAndSend("itcast","直接测试模式2");
    }
    @Test
    public void sendMsg2(){
        rabbitTemplate.convertAndSend("chuanzhi","","分列模式测试");
    }



}
