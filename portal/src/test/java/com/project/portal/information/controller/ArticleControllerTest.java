package com.project.portal.information.controller;

import cn.hutool.core.lang.Console;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSON;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.project.portal.information.request.article.SaveArticleRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;
@SpringBootTest
@RunWith(SpringRunner.class)
public class ArticleControllerTest {

    @Test
    public void save() {
        SaveArticleRequest request = new SaveArticleRequest();
        request.setArticleId("bef32ab92e294edcbe52048e082574b4");
        request.setTitle("rgfe");
        request.setUserId("re");
        request.setArticleType("tge");
        request.setImgUrl("http://pic41.nipic.com/20140508/18609517_112216473140_2.jpg");
        request.setLinkUrl("https://pic.sogou.com/");
        JSON json = JSONUtil.parse(request);
        HttpRequest httpRequest = HttpUtil.createPost("http://127.0.0.1:7080/article/saveOrUpdate")
                .body(json);
        String body = httpRequest.execute().body();
        Console.log(JSONUtil.formatJsonStr(body));
    }

    @Test
    public void findAllDesc() {
        Map map = new HashMap();
        map.put("articleType", "");
        map.put("page", 0);
        map.put("size", 10);
        JSON json = JSONUtil.parse(map);
        HttpRequest httpRequest = HttpUtil.createPost("http://127.0.0.1:7080/article/findAllDesc")
                .body(json);
        HttpResponse httpResponse = httpRequest.execute();
        System.out.println("----------------------");
        System.out.println(httpRequest.toString());

        System.out.println("------------------");
        System.out.println(httpResponse.toString());
        System.out.println("--------------");

        Console.log(JSONUtil.formatJsonStr(httpResponse.body()));
    }
}