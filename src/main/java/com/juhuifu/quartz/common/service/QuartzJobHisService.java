
package com.juhuifu.quartz.common.service;

import com.juhuifu.quartz.base.page.Page;
import com.juhuifu.quartz.base.page.Pagination;
import com.juhuifu.quartz.entity.quartz.QuartzJobHis;

public interface QuartzJobHisService {
    /**
      * addSysJobHis(这里用一句话描述这个方法的作用)
      *
      * @Title: addSysJobHis
      * @param quartzJobHis sysJobHis
      */
    void addQuartzJobHis(QuartzJobHis quartzJobHis);
    
    /**
      * updateSysJobHis(这里用一句话描述这个方法的作用)
      *
      * @Title: updateSysJobHis
      * @param quartzJobHis sysJobHis
      */
    void updateQuartzJobHis(QuartzJobHis quartzJobHis);

    /**
      * @param jobName job
      * @param page 分页属性
      * @return 分页数据
      */
    Pagination<QuartzJobHis> query(String jobName, Page page);
}
