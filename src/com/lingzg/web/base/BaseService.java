package com.lingzg.web.base;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.lingzg.web.common.Config;
import com.lingzg.web.common.PageInfo;

public class BaseService {

    protected final Logger log = Logger.getLogger(getClass().getName());
    
    @Autowired
    protected BaseDao sqlDao;
    @Autowired
    protected Config config;
    
    public PageInfo findPage(BaseEntityVO vo,String sqlMapper,String countSqlMapper) {
        PageInfo page = new PageInfo(vo);
        List<?> rows = sqlDao.query(sqlMapper, vo);
        int total = sqlDao.queryTotal(countSqlMapper, vo);
        page.setRows(rows);
        page.setTotal(total);
        return page;
    }
    
    public List<Object> find(BaseEntityVO vo,String sqlMapper){
        return sqlDao.query(sqlMapper, vo);
    }
    
    public Object get(BaseEntityVO vo,String sqlMapper){
        return sqlDao.queryOne(sqlMapper, vo);
    }
    
    public void save(BaseEntity entity,String sqlMapper){
        sqlDao.insert(sqlMapper, entity);
    }
    
    public void update(BaseEntity entity,String sqlMapper){
        sqlDao.update(sqlMapper, entity);
    }
    
    public void delete(BaseEntity entity,String sqlMapper){
        sqlDao.delete(sqlMapper, entity);
    }
}
