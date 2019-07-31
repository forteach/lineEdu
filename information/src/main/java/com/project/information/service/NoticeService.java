package com.project.information.service;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.project.information.domain.Notice;
import com.project.information.repository.NoticeDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 简单公告S
 */
@Service
public class NoticeService {


	@Autowired
	private NoticeDao noticeDao;

	@Transactional
	public Notice save(String noticeId, String content, String centerId,int area) {

		Notice nc=null;
		if(StrUtil.isNotBlank(noticeId)){
			nc=findById(noticeId);
		}else{
			nc=new Notice();
			nc.setNoticeId(IdUtil.fastSimpleUUID());
		}

		nc.setCenterAreaId(centerId);
		nc.setContent(content);
		nc.setArea(area);
		return noticeDao.save(nc);
	}

	public Notice findById(String id){
		return noticeDao.findByNoticeId(id);
	}


	/**
	 * 所有公告 1 全部领域 2 单个领域
	 * @param isVal
	 * @param pageable
	 * @return
	 */
	public List<Notice> findByIsValidatedDesc(String isVal, Pageable pageable){
		return noticeDao.findByAreaAndIsValidatedOrderByCreateTimeDesc(1,isVal,pageable).getContent();
	}

	/**
	 * 根据中心ID的所有公告
	 * @param isVal
	 * @param pageable
	 * @return
	 */
	public List<Notice> findByCerterIdDesc(String isVal,String centerId, Pageable pageable){
		return noticeDao.findByCenterAreaIdOrAreaAndIsValidatedOrderByCreateTimeDesc(centerId,1,isVal,pageable).getContent();
	}

	@Transactional
	public String deleteByNoticeId(String noticeId){
		 noticeDao.deleteByNoticeId(noticeId);
		 return "Y";
	}

}
