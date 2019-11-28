package com.project.portal.information.request.notice;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 保存收藏
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SaveNoticeRequest implements Serializable {

	/** 公告ID. */
	private String noticeId;

	/** 公告内容. */
	private String content;

	private String centerId;

	/** 1 全部领域 2 单个领域.*/
    private int area;
}