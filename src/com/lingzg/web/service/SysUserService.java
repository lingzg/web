package com.lingzg.web.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lingzg.web.base.BaseService;
import com.lingzg.web.common.PageInfo;
import com.lingzg.web.dao.SysUserDao;
import com.lingzg.web.model.SysUser;
import com.lingzg.web.model.vo.SysUserVO;

@Service
@Transactional
public class SysUserService extends BaseService{

    @Autowired
    private SysUserDao userDao;
    
    public List<SysUser> findList(){
        return userDao.findAll();
    }
    
    public PageInfo findPage(SysUserVO vo){
        String sqlMapper = "com.lingzg.web.dao.SysUserDao.findPage";
        String countSqlMapper = "com.lingzg.web.dao.SysUserDao.findPageCount";
        return findPage(vo, sqlMapper, countSqlMapper);
    }
}
