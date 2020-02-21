package com.project.information.service;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.project.information.domain.Notice;
import com.project.information.repository.NoticeDao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.project.base.common.keyword.Dic.TAKE_EFFECT_OPEN;

/**
 * 简单公告S
 */
@Service
public class NoticeService {


	private final NoticeDao noticeDao;

	public NoticeService(NoticeDao noticeDao) {
		this.noticeDao = noticeDao;
	}

	@Transactional(rollbackFor = Exception.class)
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
	 * @param pageable
	 * @return
	 */
	public Page<Notice> findByIsValidatedDesc(Pageable pageable){
		return noticeDao.findByAreaAndIsValidatedOrderByCreateTimeDesc(1,TAKE_EFFECT_OPEN,pageable);
	}

	/**
	 * 根据中心ID的所有公告
	 * @param pageable
	 * @return
	 */
	public Page<Notice> findByCerterIdDesc(String centerId, Pageable pageable){
		return noticeDao.findByCenterAreaIdOrAreaAndIsValidatedOrderByCreateTimeDesc(centerId,1,TAKE_EFFECT_OPEN,pageable);
	}

	@Transactional(rollbackFor = Exception.class)
	public String deleteByNoticeId(String noticeId){
		 noticeDao.deleteByNoticeId(noticeId);
		 return "Y";
	}

}
