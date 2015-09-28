package com.juhuifu.quartz.common.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.juhuifu.quartz.base.service.ServiceSupport;
import com.juhuifu.quartz.common.service.QuartzJobDateService;
import com.juhuifu.quartz.entity.quartz.QuartzJobDate;
import com.juhuifu.quartz.enums.JobStatusEnum;

@Service(value="quartzJobDateService")
public class QuartzJobDateServiceImpl extends ServiceSupport implements QuartzJobDateService {
    @Override
    public void addQuartzJobDate(String jobName, Date date) {
        QuartzJobDate quartzJobDate = new QuartzJobDate();
        quartzJobDate.setJobName(jobName);
        quartzJobDate.setProcessDate(date);
        quartzJobDate.setStatus(JobStatusEnum.Waiting);
        quartzJobDate.setJobComment("");
        quartzJobDate.setCreateTime(new Date());
        quartzJobDate.setUpdateTime(new Date());
        baseHibernateDAO.add(quartzJobDate);
    }
    
    @Override
    public Date updateQuartzJobDate(String jobName) {
        String sql = "from QuartzJobDate where jobName=:jobName and status = :status order by createTime";
        Map<String,Object> paramMap = new HashMap<String,Object>();
        paramMap.put("jobName", jobName);
        paramMap.put("status", JobStatusEnum.Waiting);
        List<QuartzJobDate> list = baseHibernateDAO.find(sql, paramMap);
        if(list!=null && list.size()>0){
            QuartzJobDate quartzJobDate = list.get(0);
            quartzJobDate.setStatus(JobStatusEnum.Executed);
            quartzJobDate.setUpdateTime(new Date());
            baseHibernateDAO.add(quartzJobDate);
            return quartzJobDate.getProcessDate();
        }else{
            return null;
        }
    }
}
