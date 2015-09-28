
package com.juhuifu.quartz.common.service;

import java.util.List;
import java.util.Map;

import com.juhuifu.quartz.base.exception.BusinessException;
import com.juhuifu.quartz.entity.quartz.dto.QuartzJob;

public interface QuartzJobService {
    /**
     * 
     * @function:保存任务
     * @param quartzJob 定时任务详情
     * @return Map<String, String> 返回类型
     * @throws BusinessException 异常
     */
    Map<String, String> addJob(QuartzJob quartzJob)throws BusinessException;
    
    /**
      * listJobs(显示所有的定时任务)
      *
      * @Title: listJobs
      * @return List<QuartzJob>    返回类型
      */
    List<QuartzJob> listJobs();
    
    /**
     * 
     * @function:修改任务
     * @param quartzJob 定时任务详情
     * @return Map<String, String> 返回类型
     * @throws BusinessException 异常
     */
    Map<String, String> updateJob(QuartzJob quartzJob)throws BusinessException;
    
    /**
     * 暂停定时任务
     * 
     * @param jobName 任务名称
     * @throws BusinessException 异常
     */
    void pauseJob(String jobName) throws BusinessException;
    
    /**
     * 恢复定时任务
     * 
     * @param jobName 任务名称
     * @throws BusinessException 异常
     */
    void resumeJob(String jobName) throws BusinessException;
    
    /**
     * 
     * @function:立即运行
     * @param jobName 任务名称
     * @throws BusinessException 异常
     */
    void addRunNowTrigger(String jobName) throws BusinessException;
    
    /**
      * delJob(删除job)
      *
      * @Title: delJob
      * @param jobName 任务名称
      * @throws BusinessException 异常
      */
    void delJob(String jobName) throws BusinessException;
}
