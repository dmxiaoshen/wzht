package com.juhuifu.quartz.common.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.hibernate.criterion.Order;
import org.springframework.stereotype.Service;

import com.juhuifu.quartz.base.page.Page;
import com.juhuifu.quartz.base.page.PageUtil;
import com.juhuifu.quartz.base.page.Pagination;
import com.juhuifu.quartz.base.service.ServiceSupport;
import com.juhuifu.quartz.common.service.QuartzJobHisService;
import com.juhuifu.quartz.entity.quartz.QuartzJobHis;

@Service(value = "quartzJobHisService")
public class QuartzJobHisServiceImpl extends ServiceSupport implements QuartzJobHisService {

    @Override
    public void addQuartzJobHis(QuartzJobHis quartzJobHis) {
        baseHibernateDAO.add(quartzJobHis);
    }

    @Override
    public void updateQuartzJobHis(QuartzJobHis quartzJobHis) {
        baseHibernateDAO.save(quartzJobHis);
    }

    @Override
    public Pagination<QuartzJobHis> query(String jobName, Page page) {
        StringBuffer hqlSql = new StringBuffer("from QuartzJobHis where jobName=:jName");
        Map<String,Object> paramMap = new HashMap<String,Object>(1);
        paramMap.put("jName", jobName);
        Order order = PageUtil.getOrder(page);
        if(order!=null){
            hqlSql.append(" order by " + order.toString());
        }
        return baseHibernateDAO.list(hqlSql.toString(), paramMap, page.getPage(), page.getRows());
    }

}
