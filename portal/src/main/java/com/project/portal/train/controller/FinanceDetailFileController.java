package com.project.portal.train.controller;

import com.project.train.service.FinanceDetailFileService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: zhangyy
 * @email: zhang10092009@hotmail.com
 * @date: 19-8-15 18:40
 * @version: 1.0
 * @description:
 */
@RestController
@Api(value = "财务类文件详细管理", tags = {"财务类文件详细管理"})
@RequestMapping(path = "/financeDetailFile", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class FinanceDetailFileController {
    private final FinanceDetailFileService financeDetailFileService;

    public FinanceDetailFileController(FinanceDetailFileService financeDetailFileService) {
        this.financeDetailFileService = financeDetailFileService;
    }
}
