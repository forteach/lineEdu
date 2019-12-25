package com.project.base.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author: zhangyy
 * @email: zhang10092009@hotmail.com
 * @date: 2019/12/23 14:41
 * @version: 1.0
 * @description:
 */
@Slf4j
@Service
public class HttpClientFileService {

//    @Value("${http.file.url}")
//    private String fileUrl;

//    @Async
//    public void deleteFile(String url){
//        System.out.println(fileUrl);
//        System.out.println("url");
//        HttpRequest.delete(fileUrl)
//                .header(Header.USER_AGENT, "Hutool http")//头信息，多个头信息多次调用此方法即可
                //表单内容
//                .form(MapUtil.of("url", url))
                //超时，毫秒
//                .timeout(20000)
//                .execute()
//                .body();
//    }
}