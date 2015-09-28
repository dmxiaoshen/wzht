package com.juhuifu.quartz.common.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.juhuifu.quartz.base.constants.AppConstants;
import com.juhuifu.quartz.base.exception.BusinessException;
import com.juhuifu.quartz.base.service.ServiceSupport;
import com.juhuifu.quartz.common.service.QuartzJobService;
import com.juhuifu.quartz.common.service.QuartzService;
import com.juhuifu.quartz.entity.quartz.dto.QuartzJob;

@Service
public class QuartzJobServiceImpl extends ServiceSupport implements QuartzJobService {
    @Autowired
    private QuartzService quartzService;

    @Override
    public Map<String, String> addJob(QuartzJob quartzJob) throws BusinessException {
        //保存JOB
        String jobName = quartzService.saveJob(quartzJob.getJobName(), quartzJob.getDescription());
        quartzService.addTrigger(jobName, quartzJob.getCronExpression());
        Map<String, String> map = new HashMap<String, String>();
        map.put(AppConstants.CODE, AppConstants.SUCCESS);
        return map;
    }
    
    @Override
    public List<QuartzJob> listJobs() {
        String sql = "select a.JOB_NAME,a.JOB_CLASS_NAME,b.TRIGGER_NAME, " +
        		"NEXT_FIRE_TIME,PREV_FIRE_TIME,TRIGGER_STATE,TRIGGER_TYPE, " +
        		"START_TIME,END_TIME,a.DESCRIPTION,c.CRON_EXPRESSION " +
        		"from QRTZ_TRIGGERS b ,QRTZ_JOB_DETAILS a ,QRTZ_CRON_TRIGGERS c where " +
        		"b.JOB_NAME = a.JOB_NAME and c.TRIGGER_NAME= b.TRIGGER_NAME order by start_time";
        List<Object[]> objsList = baseHibernateDAO.findByNativeSql(sql,new HashMap<String,Object>());
        List<QuartzJob> res = new ArrayList<QuartzJob>();
        for(Object[] objs : objsList){
            QuartzJob job = new QuartzJob();
            fillJob(objs,job);
            res.add(job);
        }
        return res;
    }

    private void fillJob(Object[] objs, QuartzJob job) {
        int i=0;
        job.setJobName(objs[i++].toString());
        job.setClassName(objs[i++].toString());
        job.setTriggerName(objs[i++].toString());
        job.setNextFireTime(getDate(objs[i++].toString()));
        job.setPrevFireTime(getDate(objs[i++].toString()));
        job.setTriggerState(objs[i++].toString());
        job.setTriggerType(objs[i++].toString());
        job.setStartTime(getDate(objs[i++].toString()));
        job.setEndTime(getDate(objs[i++].toString()));
        job.setDescription(objs[i++].toString());
        job.setCronExpression(objs[i++].toString());
    }
    
    private Date getDate(String timeMillis){
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(Long.parseLong(timeMillis));
        return cal.getTime();
    }
    
    @Override
    public Map<String, String> updateJob(QuartzJob quartzJob) throws BusinessException {
        quartzService.saveOrUpdateJob(quartzJob.getJobName(), quartzJob.getClassName(), quartzJob.getDescription());
        quartzService.updateTrigger(quartzJob.getTriggerName(), quartzJob.getCronExpression());
        Map<String, String> map = new HashMap<String, String>();
        map.put(AppConstants.CODE, AppConstants.SUCCESS);
        return map;
    }

    @Override
    public void pauseJob(String jobName) throws BusinessException {
        quartzService.pauseJob(jobName);
    }

    @Override
    public void resumeJob(String jobName) throws BusinessException {
        quartzService.resumeJob(jobName);
    }

    @Override
    public void addRunNowTrigger(String jobName) throws BusinessException {
        quartzService.addRunNowTrigger(jobName);
    }

    @Override
    public void delJob(String jobName) throws BusinessException {
        quartzService.deleteJob(jobName);
        
    }

}
