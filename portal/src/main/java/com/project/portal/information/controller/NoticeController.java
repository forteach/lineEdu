package com.project.portal.information.controller;


import cn.hutool.core.bean.BeanUtil;
import com.project.information.service.NoticeService;
import com.project.portal.information.request.notice.ByIdNoticeRequest;
import com.project.portal.information.request.notice.FindCerterIdListRequest;
import com.project.portal.information.request.notice.FindIsValListRequest;
import com.project.portal.information.request.notice.SaveNoticeRequest;
import com.project.portal.information.response.notice.ListNoticeResponse;
import com.project.portal.request.SortVo;
import com.project.portal.response.WebResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static java.util.stream.Collectors.toList;

/**
 * 简单公告
 */
@RestController
@RequestMapping(path = "/notice", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Api(value = "简单公告")
public class NoticeController {
	@Autowired
	private NoticeService noticeService;

	@ApiOperation(value = "保存修改公告", tags = {"noticeId 赋值，修改公告信息"})
	@ApiImplicitParams({
			@ApiImplicitParam(name = "centerId", value = "中心编号", dataType = "string"),
			@ApiImplicitParam(name = "content", value = "公告内容", dataType = "string"),
			@ApiImplicitParam(name = "area", value = "1 全部中心领域 2 单个中心领域.", dataType = "int")
	})
	@PostMapping("/save")
	public WebResult save(@RequestBody SaveNoticeRequest request) {
		return WebResult.okResult(noticeService.save(request.getNoticeId(),request.getContent(),request.getCenterId(),request.getArea()));
	}

	@PostMapping("/findById")
	public WebResult findById(@RequestBody ByIdNoticeRequest request) {
 		return WebResult.okResult(noticeService.findById(request.getNoticeId()));
	}

	/**
	 * 根据Id删除公告
	 * @param request
	 * @return
	 */
	@PostMapping("/delNotice")
	public WebResult deleteId(@RequestBody ByIdNoticeRequest request) {

		return WebResult.okResult(noticeService.deleteByNoticeId(request.getNoticeId()));
	}


	/**
	 * 获得分页倒序列表记录
	 * @param request
	 * @return
	 */
	@ApiOperation(value = "所有公告倒序分页获取", tags = {"所有公告倒序分页获取"})
	@PostMapping("/findAllDesc")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "page", value = "分页", dataType = "int", example = "0", paramType = "query"),
			@ApiImplicitParam(name = "size", value = "每页数量", dataType = "int", example = "15", paramType = "query")
	})
	public WebResult findAll(@RequestBody FindIsValListRequest request) {
		SortVo sortVo = request.getSortVo();
		PageRequest page = PageRequest.of(sortVo.getPage(), sortVo.getSize());
		return WebResult.okResult(noticeService.findByIsValidatedDesc("0",page)
				.stream()
				.map(item -> {
					ListNoticeResponse ar = new ListNoticeResponse();
					BeanUtil.copyProperties(item, ar);
					return ar;
				})
				.collect(toList()));
	}

	/**
	 * 获得分页倒序列表记录
	 * @param request
	 * @return
	 */
	@ApiOperation(value = "所有公告倒序分页获取", tags = {"所有公告倒序分页获取"})
	@ApiImplicitParams({
			@ApiImplicitParam(name = "cennterId", value = "中心Id", dataType = "string", example = "0", paramType = "query"),
			@ApiImplicitParam(name = "page", value = "分页", dataType = "int", example = "0", paramType = "query"),
			@ApiImplicitParam(name = "size", value = "每页数量", dataType = "int", example = "15", paramType = "query")
	})
	@PostMapping("/findCenterId")
	public WebResult findCenterId(@RequestBody FindCerterIdListRequest request) {
		SortVo sortVo = request.getSortVo();
		PageRequest page = PageRequest.of(sortVo.getPage(), sortVo.getSize());
		return WebResult.okResult(noticeService.findByCerterIdDesc(request.getCenterId(),"0",page)
				.stream()
				.map(item -> {
					ListNoticeResponse ar = new ListNoticeResponse();
					BeanUtil.copyProperties(item, ar);
					return ar;
				})
				.collect(toList()));
	}

}
