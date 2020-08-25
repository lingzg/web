package com.lingzg.web.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.lingzg.web.model.SysUser;

public interface SysUserDao {

    List<SysUser> findAll();
    
    SysUser getById(@Param("userId")Long userId);
    
    void save();
    
    void update();
    
    void delete();
}
