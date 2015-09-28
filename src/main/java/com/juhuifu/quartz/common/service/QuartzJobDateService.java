
package com.juhuifu.quartz.common.service;

import java.util.Date;


public interface QuartzJobDateService {
    /**
      * addQuartzJobDate(这里用一句话描述这个方法的作用)
      *
      * @Title: addQuartzJobDate
      * @param jobName jobName
      * @param date date
      */
    void addQuartzJobDate(String jobName, Date date);
    
    /**
      * updateQuartzJobDate(这里用一句话描述这个方法的作用)
      *
      * @Title: queryQuartzJobDate
      * @param jobName jobName
      * @return Date    返回类型
      */
    Date updateQuartzJobDate(String jobName);
}
