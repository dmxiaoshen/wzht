package com.juhuifu.quartz.common.service;

import java.util.Map;

import org.quartz.JobDetail;
import org.quartz.Trigger;

import com.juhuifu.quartz.base.exception.BusinessException;

public interface QuartzService {

    /**
     * 
     * @function:获得任务
     * @param jobName 任务名称
     * @param jobGroup 任务分组
     * @return JobDetail 
     * @throws BusinessException 异常
     */
    JobDetail getJob(String jobName, String jobGroup) throws BusinessException;
    
    /**
     * 
     * @function:获得任务
     * @param jobName 任务名称
     * @return JobDetail 
     * @throws BusinessException 异常
     */
    JobDetail getJob(String jobName) throws BusinessException;

    /**
     * 
     * @function:添加任务
     * @param jobDetail 任务详情
     * @throws BusinessException 异常
     */
    void addJob(JobDetail jobDetail) throws BusinessException;

    /**
     * 
     * @function:保存、修改任务
     * @param jobName 任务名称
     * @param jobClass 类
     * @param description 描述
     * @throws BusinessException 异常
     */
    void saveOrUpdateJob(String jobName, String jobClass, String description)
            throws BusinessException;

    /**
     * 
     * @function:保存、修改任务
     * @param jobName 任务名称
     * @param jobGroup 任务分组
     * @param jobClass 类
     * @param description 描述
     * @throws BusinessException 异常
     */
    void saveOrUpdateJob(String jobName, String jobGroup, String jobClass, String description)
            throws BusinessException;
    
    /**
     * 
     * @function:修改触发器
     * @param triggerName 触发器名称
     * @param groupName 分组名称
     * @param cronExpression Quartz Cron 表达式
     * @throws BusinessException 异常
     */
    void updateTrigger(String triggerName,String groupName, String cronExpression)
            throws BusinessException;
    
    /**
     * 
     * @function:修改触发器
     * @param triggerName 触发器名称
     * @param cronExpression Quartz Cron 表达式
     * @throws BusinessException 异常
     */
    void updateTrigger(String triggerName, String cronExpression)throws BusinessException;
    
    /**
     * 
     * @function:保存任务
     * @param jobClass 类
     * @param description 描述
     * @return String
     * @throws BusinessException 异常
     */
    String saveJob(String jobClass, String description)throws BusinessException;

    /**
     * 
     * @function:添加定时
     * @param trigger 触发器
     * @throws BusinessException 异常
     */
    void addTrigger(Trigger trigger) throws BusinessException;
    
    /**
     * 
     * @function:添加定时
     * @param jobName 任务名称
     * @param cronExpressionStr  Quartz Cron 表达式，如 "0/10 * * ? * * *"等
     * @throws BusinessException 异常
     */
    void addTrigger(String jobName,String cronExpressionStr) throws BusinessException;

    /**
     * 
     * @function:删除任务
     * @param jobName 任务名称
     * @param jobGroup 任务分组
     * @throws BusinessException 异常
     */
    void deleteJob(String jobName, String jobGroup) throws BusinessException;

    
    /**
      * deleteJob(删除任务)
      *
      * @Title: deleteJob
      * @param jobName 任务名称
      * @throws BusinessException 异常
      */
    void deleteJob(String jobName) throws BusinessException;
    
    /**
     * 
     * @function:立即运行
     * @param jobName 任务名称
     * @param jobGroup 任务分组
     * @throws BusinessException 异常
     */
    void addRunNowTrigger(String jobName, String jobGroup) throws BusinessException;
    
    /**
     * 
     * @function:立即运行
     * @param jobName 任务名称
     * @throws BusinessException 异常
     */
    void addRunNowTrigger(String jobName) throws BusinessException;

    /**
     * 删除定时
     * 
     * @param triggerName 触发器名称
     * @param group 分组
     * @throws BusinessException 异常
     */
    void deleteTrigger(String triggerName, String group) throws BusinessException;

    /**
     * 暂停触发器
     * 
     * @param triggerName 触发器名称
     * @param group 分组
     * @throws BusinessException 异常
     */
    void updatePauseTrigger(String triggerName, String group) throws BusinessException;
    
    /**
     * 暂停定时任务
     * 
     * @param jobName 任务名称
     * @throws BusinessException 异常
     */
    void pauseJob(String jobName) throws BusinessException;
    
    /**
     * 暂停定时任务
     * 
     * @param jobName 任务名称
     * @param groupName 分组
     * @throws BusinessException 异常
     */
    void pauseJob(String jobName,String groupName) throws BusinessException;

    /**
     * 恢复Trigger
     * 
     * @param triggerName 触发器名称
     * @param group 分组
     * @throws BusinessException 异常
     */
    void updateResumeTrigger(String triggerName, String group) throws BusinessException;
    
    /**
     * 恢复定时任务
     * 
     * @param jobName 任务名称
     * @throws BusinessException 异常
     */
    void resumeJob(String jobName) throws BusinessException;
    
    /**
     * 恢复定时任务
     * 
     * @param jobName 任务名称
     * @param groupName 分组
     * @throws BusinessException 异常
     */
    void resumeJob(String jobName,String groupName) throws BusinessException;

    /**
     * 
     * @function:批量删除任务
     * @param jobNames 任务名称
     * @param jobGroup 任务分组
     * @throws BusinessException 异常
     */
    void deleteJobs(String jobNames, String jobGroup) throws BusinessException;

    /**
     * 
     * @function:查询任务参数
     * @param jobName 任务名称
     * @param jobGroup 任务分组
     * @return String
     * @throws BusinessException 异常
     */
    String findJobParams(String jobName, String jobGroup) throws BusinessException;

    /**
     * 
     * @function:保存任务参数
     * @param jobName 任务名称
     * @param jobGroup 任务分组
     * @param key key
     * @param value 值
     * @return String
     * @throws BusinessException 异常
     */
    String saveJobParam(String jobName, String jobGroup, String key, String value)
            throws BusinessException;

    /**
     * 
     * @function:删除任务参数
     * @param jobName 任务名称
     * @param jobGroup 任务分组
     * @param keys 
     * @return String
     * @throws BusinessException 异常
     */
    String deleteJobParams(String jobName, String jobGroup, String keys) throws BusinessException;

    /**
     * 
     * @function:保存cron
     * @param params 参数
     * @throws BusinessException 异常
     */
    void saveCron(Map<String, Object> params) throws BusinessException;

    /**
     * 
     * @function:保存simple
     * @param params 参数
     * @throws BusinessException 异常
     */
    void saveSimple(Map<String, Object> params) throws BusinessException;

    /**
     * 
     * @function:批量删除定时
     * @param jobName 任务名称
     * @param group 分组
     * @param triggerNames 触发器名称
     * @throws BusinessException 异常
     */
    void deleteTriggers(String jobName, String group, String triggerNames)
            throws BusinessException;

    /**
     * 根据名称和组别启动和暂停Tigger
     * 
     * @param triggerName 触发器名称
     * @param group 分组
     * @return String
     * @throws BusinessException 异常
     */
    String updateStartOrStop(String triggerName, String group) throws BusinessException;

}
