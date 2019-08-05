package com.tensquare.base.controller;

import com.tensquare.base.pojo.Label;
import com.tensquare.base.service.LabelService;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.HttpRequestHandler;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


@RestController
@CrossOrigin
@RequestMapping("/label")
public class LabelController {

    @Autowired
    private LabelService labelService;

    @Autowired
    private HttpServletRequest request;

    @RequestMapping(method = RequestMethod.GET)
    public Result findAll(){
    //    int i=1/
        String header = request.getHeader("Authorization");
        System.out.println(header);//若是通过网关访问的话，会丢失头信息，
        //前端的过滤器和服务器端的过滤器要求的安全性而言，前端的稍微低一些。后台必须要管理员的身份，前端的不需要管理员身份，前端不需要验证
        return new Result(true, StatusCode.OK,"查询成功",
                labelService.findAll());
    }

    @RequestMapping(value = "/{labelId}",method = RequestMethod.GET)
    public Result findById(@PathVariable("labelId") String labelId){

        return new Result(true, StatusCode.OK,"查询成功",
                labelService.findById(labelId));
    }

    @RequestMapping(method = RequestMethod.POST)
    public Result save(@RequestBody Label label){
        labelService.save(label);
        return new Result(true, StatusCode.OK,"添加成功");
    }

    @RequestMapping(value = "/{labelId}",method = RequestMethod.PUT)
    public Result update(@PathVariable String labelId,@RequestBody Label label){
        label.setId(labelId);
        labelService.update(label);
        return new Result(true, StatusCode.OK,"更新成功");
    }

    @RequestMapping(value = "/{labelId}",method = RequestMethod.DELETE)
    public  Result deleteById(@PathVariable String labelId){
        labelService.deleteById(labelId);
        return new Result(true, StatusCode.OK,"删除成功");
    }

    @RequestMapping(value = "/search",method = RequestMethod.POST)
    public Result findSearch(@RequestBody Label label){
        List<Label> list = labelService.findSearch(label);
        return new Result(true, StatusCode.OK,"查询成功",list);
    }

    @RequestMapping(value = "/search/{page}/{size}")
    public Result pageQuery(@RequestBody Label label,@PathVariable int page,
                            @PathVariable int size){
        Page<Label> pageData = labelService.pageQuery(label,page,size);
        return new Result(true,StatusCode.OK,"查询成功",
                new PageResult<Label>(pageData.getTotalElements(),pageData.getContent()));
    }

}
