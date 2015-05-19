package com.zzy.base.repository.cms;

import com.zzy.base.entity.cms.Attachment;
import com.zzy.base.repository.MyBatisRepository;

@MyBatisRepository
public interface AttachmentDao {
	public abstract void add(Attachment attach); 
}
