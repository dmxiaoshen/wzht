package com.juhuifu.quartz.common.job;

import java.util.Date;

import org.quartz.CronTrigger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.Scheduler;
import org.quartz.SchedulerContext;
import org.quartz.SchedulerException;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.juhuifu.quartz.base.exception.BusinessException;
import com.juhuifu.quartz.common.service.QuartzJobDateService;
import com.juhuifu.quartz.common.service.QuartzJobHisService;
import com.juhuifu.quartz.entity.quartz.QuartzJobHis;
import com.juhuifu.quartz.enums.JobStatusEnum;

public abstract class AbstractJob extends QuartzJobBean{
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    
    private QuartzJobHisService quartzJobHisService;
    
    private QuartzJobDateService quartzJobDateService;
    
    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException{
        // 获取JobExecutionContext中的service对象
        QuartzJobHis his = new QuartzJobHis();
        his.setJobComment("");
        try {
            SchedulerContext ctx = context.getScheduler().getContext();
            // 获取SchedulerContext中的service
            // 这里的service就是通过配置文件 配置的
            quartzJobHisService = (QuartzJobHisService) ctx.get("quartzJobHisService");
            
            quartzJobDateService = (QuartzJobDateService) ctx.get("quartzJobDateService");
            
            String jobName = context.getTrigger().getJobName();
            //新建定时任务历史记录
            Trigger[] triggers = context.getScheduler().getTriggersOfJob(jobName, Scheduler.DEFAULT_GROUP);
            for(int i =0;i<triggers.length;i++){
                Trigger trigger = triggers[i];
                if(trigger instanceof CronTrigger){
                    CronTrigger quatrzTrigger = (CronTrigger)trigger;
                    his.setNextTime(quatrzTrigger.getNextFireTime());
                    his.setCronExpression(quatrzTrigger.getCronExpression());
                }
                if(trigger instanceof SimpleTrigger){
                    his.setJobComment("M");
                }
            }
            
            Date date = new Date();
            his.setCreateTime(date);
            his.setJobName(context.getTrigger().getJobName());
            his.setDescription(context.getJobDetail().getDescription());
            his.setStartTime(context.getFireTime());
            his.setStatus(JobStatusEnum.Executing);
            his.setUpdateTime(date);
            quartzJobHisService.addQuartzJobHis(his);
            
            Date quartzJobDate = quartzJobDateService.updateQuartzJobDate(jobName);
            execute(ctx,quartzJobDate);
            
            //保存定时任务历史记录
            his.setEndTime(new Date());
            his.setUpdateTime(new Date());
            his.setStatus(JobStatusEnum.Executed);
            quartzJobHisService.updateQuartzJobHis(his);
            
        } catch (SchedulerException e) {
            logger.error("获取spring资源异常！", e);
        } catch (BusinessException e) {
            //保存定时任务历史记录
            his.setEndTime(new Date());
            his.setUpdateTime(new Date());
            his.setStatus(JobStatusEnum.ExecuteFail);
            quartzJobHisService.updateQuartzJobHis(his);
        }
    }
    
    protected abstract void execute(SchedulerContext ctx,Date date) throws BusinessException;
}
