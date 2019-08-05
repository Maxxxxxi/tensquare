package com.tensquare.qa.client.impl;

import com.tensquare.qa.client.LabelClient;
import entity.Result;
import entity.StatusCode;
import org.springframework.stereotype.Component;


@Component//不加到容器中也会报错的
public class LabelClientImpl implements LabelClient {
    @Override
    public Result findById(String labelId) {
        return new Result(false, StatusCode.ERROR,"熔断器Hystrix启动了");
    }
}
