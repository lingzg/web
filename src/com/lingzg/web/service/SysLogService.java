package com.lingzg.web.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lingzg.web.base.BaseService;
import com.lingzg.web.model.SysLog;

@Service
@Transactional
public class SysLogService extends BaseService {

	public void save(SysLog log){
		this.log.info(log);
	}
}
