package com.juhuifu.quartz.common.service.impl;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.quartz.CronExpression;
import org.quartz.CronTrigger;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.juhuifu.quartz.base.exception.BusinessException;
import com.juhuifu.quartz.base.service.ServiceSupport;
import com.juhuifu.quartz.common.constants.QuartzConstants;
import com.juhuifu.quartz.common.service.QuartzService;
import com.juhuifu.quartz.utils.DateUtil;

@Service
public class QuartzServiceImpl extends ServiceSupport implements QuartzService {

    @Autowired
    private Scheduler scheduler;

    @Override
    public void addJob(JobDetail jobDetail) throws BusinessException {
        try {
            scheduler.addJob(jobDetail, true);
        } catch (SchedulerException e) {
            throw new BusinessException("添加任务失败", e);
        }
    }

    @Override
    public void saveOrUpdateJob(String jobName, String jobClass, String description)
            throws BusinessException {
        this.saveOrUpdateJob(jobName,Scheduler.DEFAULT_GROUP,jobClass,description);
    }

    @Override
    public JobDetail getJob(String jobName, String jobGroup) throws BusinessException {
        JobDetail jobDetail;
        try {
            jobDetail = scheduler.getJobDetail(jobName, jobGroup);
        } catch (SchedulerException e) {
            throw new BusinessException("获取任务失败", e);
        }
        return jobDetail;
    }
    
    @Override
    public JobDetail getJob(String jobName) throws BusinessException {
        return this.getJob(jobName,Scheduler.DEFAULT_GROUP);
    }

    @Override
    public String saveJob(String jobName, String description) throws BusinessException {
        JobDetail jobDetail = new JobDetail();
        try {
            //Class<?> ownerClass = Class.forName(QuartzConstants.QUARTZCONSTANTS);
            Class<?> ownerClass = QuartzConstants.class;
            Field field = ownerClass.getField(jobName.toUpperCase());
            String jobClass = field.get(ownerClass).toString();
            jobDetail = new JobDetail(jobName, Class.forName(jobClass));
        } catch (ClassNotFoundException e) {
            throw new BusinessException("添加修改任务失败", e);
        } catch(NoSuchFieldException e){
            throw new BusinessException("添加修改任务失败", e);
        } catch (IllegalArgumentException e) {
            throw new BusinessException("添加修改任务失败", e);
        } catch (IllegalAccessException e) {
            throw new BusinessException("添加修改任务失败", e);
        }
        jobDetail.setDescription(description);
        this.addJob(jobDetail);
        return jobName;
    }
    
    @Override
    public void saveOrUpdateJob(String jobName, String jobGroup, String jobClass,
            String description) throws BusinessException {
        JobDetail jobDetail = new JobDetail();
        if (StringUtils.isBlank(jobName)) {
            jobName = UUID.randomUUID().toString();
            try {
                jobDetail = new JobDetail(jobName, jobGroup,Class.forName(jobClass));
            } catch (ClassNotFoundException e) {
                throw new BusinessException("添加修改任务失败", e);
            }
        } else {
            jobDetail = this.getJob(jobName, jobGroup);
        }
        jobDetail.setDescription(description);
        this.addJob(jobDetail);
    }

    @Override
    public void updateTrigger(String triggerName, String groupName, String cronExpression)
            throws BusinessException {
        try {
            CronTrigger trigger = (CronTrigger)scheduler.getTrigger(triggerName, groupName);
            trigger.setCronExpression(cronExpression);
            scheduler.rescheduleJob(triggerName,groupName,trigger);
        } catch (SchedulerException e) {
            throw new BusinessException("修改触发器失败", e);
        } catch (ParseException e) {
            throw new BusinessException("转换表达式失败", e);
        }
    }
    
    @Override
    public void updateTrigger(String triggerName, String cronExpression) throws BusinessException {
        this.updateTrigger(triggerName, Scheduler.DEFAULT_GROUP, cronExpression);
    }
    
    @Override
    public void deleteJob(String jobName, String jobGroup) throws BusinessException {
        try {
            scheduler.deleteJob(jobName, jobGroup);
        } catch (SchedulerException e) {
            throw new BusinessException("删除任务失败", e);
        }
    }

    @Override
    public void addRunNowTrigger(String jobName, String jobGroup) throws BusinessException {
        try {
            scheduler.triggerJob(jobName, jobGroup);
        } catch (SchedulerException e) {
            throw new BusinessException("立即运行任务失败", e);
        }
    }
    
    @Override
    public void addRunNowTrigger(String jobName) throws BusinessException {
        this.addRunNowTrigger(jobName, Scheduler.DEFAULT_GROUP);
    }

    @Override
    public void addTrigger(Trigger trigger) throws BusinessException {
        try {
            trigger.setVolatility(false);
            scheduler.scheduleJob(trigger);
        } catch (SchedulerException e) {
            throw new BusinessException("添加定时器失败", e);
        }
    }
    
    @Override
    public void addTrigger(String jobName,String cronExpressionStr) throws BusinessException {
        try {
            //转换表达式
            CronExpression cronExpression = new CronExpression(cronExpressionStr);
            CronTrigger cronTrigger = new CronTrigger(UUID.randomUUID().toString(), Scheduler.DEFAULT_GROUP,
                    jobName, Scheduler.DEFAULT_GROUP);
            cronTrigger.setCronExpression(cronExpression);
            cronTrigger.setVolatility(false);
            scheduler.scheduleJob(cronTrigger);
        } catch (ParseException e) {
            throw new BusinessException("转换表达式失败", e);
        } catch (SchedulerException e) {
            throw new BusinessException("添加定时器失败", e);
        }
    }

    @Override
    public void deleteTrigger(String triggerName, String group) throws BusinessException {
        this.updatePauseTrigger(triggerName, group);
        try {
            scheduler.unscheduleJob(triggerName, group);
        } catch (SchedulerException e) {
            throw new BusinessException("删除定时任务失败", e);
        }
    }

    @Override
    public void updatePauseTrigger(String triggerName, String group) throws BusinessException {
        try {
            scheduler.pauseTrigger(triggerName, group);
        } catch (SchedulerException e) {
            throw new BusinessException("停止定时任务失败", e);
        }
    }
    
    @Override
    public void pauseJob(String jobName) throws BusinessException {
        this.pauseJob(jobName, Scheduler.DEFAULT_GROUP);
    }
    
    @Override
    public void pauseJob(String jobName,String groupName) throws BusinessException {
        try {
            scheduler.pauseJob(jobName, groupName);
        } catch (SchedulerException e) {
            throw new BusinessException("停止定时任务失败", e);
        }
    }

    @Override
    public void updateResumeTrigger(String triggerName, String group) throws BusinessException {
        try {
            scheduler.resumeTrigger(triggerName, group);
        } catch (SchedulerException e) {
            throw new BusinessException("恢复定时任务失败", e);
        }
    }

    @Override
    public void resumeJob(String jobName) throws BusinessException {
        this.resumeJob(jobName, Scheduler.DEFAULT_GROUP);
    }
    
    @Override
    public void resumeJob(String jobName,String groupName) throws BusinessException {
        try {
            scheduler.resumeJob(jobName, groupName);
        } catch (SchedulerException e) {
            throw new BusinessException("停止定时任务失败", e);
        }
    }
    
    @Override
    public void deleteJobs(String jobNames, String jobGroup) throws BusinessException {
        String[] jobs = jobNames.split(",");
        for (int i = 0; i < jobs.length; i++) {
            this.deleteJob(jobs[i], jobGroup);
        }
    }

    /**
      * paramMapToList(这里用一句话描述这个方法的作用)
      *
      * @Title: paramMapToList
      * @param params 参数
      * @return String    返回类型
      */
    private String paramMapToList(JobDataMap params) {
        ArrayList<List<String>> listJobData = new ArrayList<List<String>>();
        for (Object e : params.entrySet()) {
            Map.Entry<?, ?> entry = (Entry<?, ?>) e;
            ArrayList<String> list = new ArrayList<String>();
            list.add("'" + entry.getKey() + "'");
            list.add("'" + entry.getValue() + "'");
            listJobData.add(list);
        }
        return listJobData.toString();
    }

    @Override
    public String findJobParams(String jobName, String jobGroup) throws BusinessException {
        JobDetail jobDetail = this.getJob(jobName, jobGroup);
        return jobDetail == null ? "{}" : paramMapToList(jobDetail.getJobDataMap());
    }

    @Override
    public String saveJobParam(String jobName, String jobGroup, String key, String value)
            throws BusinessException {
        JobDetail jobDetail = this.getJob(jobName, jobGroup);
        JobDataMap params = jobDetail.getJobDataMap();
        params.put(key, value);
        this.addJob(jobDetail);
        return paramMapToList(params);
    }

    @Override
    public String deleteJobParams(String jobName, String jobGroup, String keys)
            throws BusinessException {
        JobDetail jobDetail = this.getJob(jobName, jobGroup);
        JobDataMap params = jobDetail.getJobDataMap();
        String[] key = keys.split(",");
        for (int i = 0; i < key.length; i++) {
            params.remove(key[i]);
        }
        this.addJob(jobDetail);
        return paramMapToList(params);
    }

    /**
      * initTrigger(这里用一句话描述这个方法的作用)
      *
      * @Title: initTrigger
      * @param trigger 触发器
      * @param params 参数
      */
    private void initTrigger(Trigger trigger, Map<String, Object> params) {
        trigger.setDescription(params.get("label").toString());
        trigger.setJobName(params.get("jobName").toString());
        trigger.setJobGroup(params.get("group").toString());
        String startTime = params.get("startTime") == null ? "" : params.get("startTime")
                .toString();
        String stopTime = params.get("stopTime") == null ? "" : params.get("stopTime").toString();
        if (!StringUtils.isBlank(startTime)) {
            trigger.setStartTime(DateUtil.stringToDate(startTime, "yyyy-MM-dd HH:mm:ss"));
        }
        if (!StringUtils.isBlank(stopTime)) {
            trigger.setEndTime(DateUtil.stringToDate(stopTime, "yyyy-MM-dd HH:mm:ss"));
        }
    }

    @Override
    public void saveCron(Map<String, Object> params) throws BusinessException {
        Trigger trigger = null;
        try {
            trigger = new CronTrigger(UUID.randomUUID().toString(),
                    params.get("group").toString(), params.get("cronExpression").toString());
        } catch (ParseException e) {
            throw new BusinessException("添加cron失败", e);
        }
        this.initTrigger(trigger, params);
        this.addTrigger(trigger);
    }

    @Override
    public void saveSimple(Map<String, Object> params) throws BusinessException {
        int repeatCount = Integer.parseInt(params.get("repeatCount").toString());
        long repeatInterval = Long.parseLong(params.get("repeatInterval").toString());
        Trigger trigger = new SimpleTrigger(UUID.randomUUID().toString(), params.get("group")
                .toString(), repeatCount, repeatInterval);
        this.initTrigger(trigger, params);
        this.addTrigger(trigger);
    }

    @Override
    public void deleteTriggers(String jobName, String group, String triggerNames)
            throws BusinessException {
        String[] trigger = triggerNames.split(",");
        for (int i = 0; i < trigger.length; i++) {
            this.deleteTrigger(trigger[i], group);
        }
    }

    @Override
    public String updateStartOrStop(String triggerName, String group) throws BusinessException {
        int flag = -1;
        try {
            flag = scheduler.getTriggerState(triggerName, group);
            switch (flag) {
            case 0:
                scheduler.pauseTrigger(triggerName, group);
                flag = 1;
                break;
            case 1:
                scheduler.resumeTrigger(triggerName, group);
                flag = 0;
                break;
            }
        } catch (SchedulerException e) {
            throw new BusinessException("修改定时状态失败", e);
        }
        return "{state:'" + flag + "'}";
    }

    @Override
    public void deleteJob(String jobName) throws BusinessException {
       this.deleteJob(jobName, Scheduler.DEFAULT_GROUP);     
    }

}
