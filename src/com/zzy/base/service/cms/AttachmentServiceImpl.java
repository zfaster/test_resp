package com.zzy.base.service.cms;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zzy.base.entity.cms.Attachment;
import com.zzy.base.repository.cms.AttachmentDao;
@Service("attachmentService")
public class AttachmentServiceImpl implements AttachmentService {

	@Resource
	AttachmentDao attachmentDao;
	
	@Override
	public void add(Attachment attachment) {
		attachmentDao.add(attachment);
	}

}
