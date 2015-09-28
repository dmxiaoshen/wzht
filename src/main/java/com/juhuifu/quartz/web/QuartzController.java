package com.juhuifu.quartz.web;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.juhuifu.quartz.base.exception.BusinessException;
import com.juhuifu.quartz.base.page.Page;
import com.juhuifu.quartz.base.page.Pagination;
import com.juhuifu.quartz.common.service.QuartzJobDateService;
import com.juhuifu.quartz.common.service.QuartzJobHisService;
import com.juhuifu.quartz.common.service.QuartzJobService;
import com.juhuifu.quartz.entity.quartz.QuartzJobHis;
import com.juhuifu.quartz.entity.quartz.dto.QuartzJob;


@Controller
@RequestMapping("/system/quartz")
public class QuartzController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private QuartzJobService quartzJobService;
    
    @Autowired
    private QuartzJobHisService hisService;
    
    @Autowired
    private QuartzJobDateService quartzJobDateService;
    
    /**
     * @return jsp
     */
	
    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String addJob() {
        return "/quartz/add";
    }

    /**
     * 提交新增
     * 
     * @param quartzJob quartzJob
     * @param request 请求
     * @return Object 返回类型
     */
	
	@ResponseBody
    @RequestMapping(value = "add")
    public Object addJob(HttpServletRequest request, QuartzJob quartzJob) {
        try {
            // quartzJob.setCronExpression("0/10 * * ? * * *");
            return quartzJobService.addJob(quartzJob);
        } catch (BusinessException e) {
            logger.error("添加任务失败", e);
            return null;
        }
    }

    /**
     * @return jsp
     */
	
    @RequestMapping("/list")
    public String list() {
        return "/quartz/list";
    }
    
    /**
      * @param request 请求
      * @param quartzJob s
      * @return Object 返回类型
      */
	
    @RequestMapping(value="/update", method = RequestMethod.POST)
    @ResponseBody
    public Object update(HttpServletRequest request,QuartzJob quartzJob){
        try {
            return quartzJobService.updateJob(quartzJob);
        } catch (BusinessException e) {
            logger.error("修改任务失败", e);
            return null;
            //return LocaleMessageUtil.getLocaleResponse(request, "margin_common_alert_operateFail");
        }
    }
    
    /**
     * @param request
     *            http请求
     * @return json
     */
	
    @RequestMapping(value = "/query", method = RequestMethod.POST)
    @ResponseBody
    public Object query(HttpServletRequest request) {
        try {
            Map<String, Object> map = new HashMap<String, Object>();
            // List list = qService.query(); 调用查询接口
            map.put("result", quartzJobService.listJobs());// 页面通过result获取结果集合
            return map;
        } catch (Exception e) {
            logger.error("查询失败", e);
            return null;
            //return LocaleMessageUtil.getLocaleResponse(request, "ajax_common_alert_query");
        }
    }

    /**
     * @param jobName 定时任务名称
     * @param date 重新执行日期
     */
	
    @RequestMapping("/execute")
    @ResponseBody
    public void execute(String jobName, Date date) {
        try {
            quartzJobDateService.addQuartzJobDate(jobName,date);
            quartzJobService.addRunNowTrigger(jobName);
        } catch (BusinessException e) {
            logger.error("立刻执行任务失败", e);
        }
    }

    /**
     * @param jobName 定时任务名称
     */
	
    @RequestMapping("/pause")
    @ResponseBody
    public void pauseJob(String jobName) {
        try {
            quartzJobService.pauseJob(jobName);
        } catch (BusinessException e) {
            logger.error("暂停任务失败", e);
        }
    }

    /**
     * @param jobName 定时任务名称
     */
	
    @RequestMapping("/resume")
    @ResponseBody
    public void resumeTrigger(String jobName) {
        try {
            quartzJobService.resumeJob(jobName);
        } catch (BusinessException e) {
            logger.error("暂停任务失败", e);
        }
    }
    
    /**
     * @param jobName 定时任务名称
     */
    @RequestMapping("/del")
    @ResponseBody
    public void delJob(String jobName) {
        try {
            quartzJobService.delJob(jobName);
        } catch (BusinessException e) {
            logger.error("删除任务失败", e);
        }
    }
    
	/**
	  * @param model 传递数据
	  * @param jobName 主键
	  * @return jsp url
	  */
	
	@RequestMapping("/history")
	public String history(Model model,String jobName){
	    model.addAttribute("jobName", jobName);
	    return "/quartz/history";
	}
	
	/**
	  * @param page 分页属性
	  * @param jobName jobName
	  * @return {@link Pagination}
	  */
	
    @RequestMapping("/hisQuery")
    @ResponseBody
    public Pagination<QuartzJobHis> hisQuery(@RequestParam String jobName, Page page) {
        logger.debug("query history of "+jobName);
        return hisService.query(jobName, page);
    }
	
    /**
      * binder(这里用一句话描述这个方法的作用)
      *
      * @Title: binder
      * @param binder binder
      */
    @InitBinder
    public void binder(WebDataBinder binder){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(
                sdf, true));
    }
}
