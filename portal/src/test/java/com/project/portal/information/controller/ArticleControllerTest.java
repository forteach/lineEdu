package com.project.portal.information.controller;

import cn.hutool.core.lang.Console;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSON;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.project.portal.information.request.article.SaveArticleRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;
@SpringBootTest
@RunWith(SpringRunner.class)
public class ArticleControllerTest {

    @Test
    public void save() {
        SaveArticleRequest request = new SaveArticleRequest();
        request.setTitle("test title 标题");
        request.setUserId("12123");
        request.setArticleType("11");
        request.setImgUrl("http://pic41.nipic.com/20140508/18609517_112216473140_2.jpg");
        request.setLinkUrl("https://pic.sogou.com/");
        JSON json = JSONUtil.parse(request);
        HttpRequest httpRequest = HttpUtil.createPost("http://127.0.0.1:7080/article/saveOrUpdate")
                .body(json);
        String body = httpRequest.execute().body();
        Console.log(JSONUtil.formatJsonStr(body));
    }
}