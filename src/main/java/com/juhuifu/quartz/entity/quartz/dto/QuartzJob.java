package com.juhuifu.quartz.entity.quartz.dto;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.juhuifu.quartz.base.serializer.TimeSerializer;

public class QuartzJob implements Serializable{
    
    private static final long serialVersionUID = -8303375929031863939L;
    private String jobName;
    private String triggerName;
    private Date nextFireTime;
    private Date prevFireTime;
    private String triggerState;
    private String triggerType;
    private Date startTime;
    private Date endTime;
    private String className;
    private String description;
    private String cronExpression;
    
    public String getJobName() {
        return jobName;
    }
    public void setJobName(String jobName) {
        this.jobName = jobName;
    }
    public String getTriggerName() {
        return triggerName;
    }
    public void setTriggerName(String triggerName) {
        this.triggerName = triggerName;
    }
    @Temporal(TemporalType.DATE)
    @JsonSerialize(using = TimeSerializer.class)
    public Date getNextFireTime() {
        return nextFireTime;
    }
    public void setNextFireTime(Date nextFireTime) {
        this.nextFireTime = nextFireTime;
    }
    @Temporal(TemporalType.DATE)
    @JsonSerialize(using = TimeSerializer.class)
    public Date getPrevFireTime() {
        return prevFireTime;
    }
    public void setPrevFireTime(Date prevFireTime) {
        this.prevFireTime = prevFireTime;
    }
    public String getTriggerState() {
        return triggerState;
    }
    public void setTriggerState(String triggerState) {
        this.triggerState = triggerState;
    }
    public String getTriggerType() {
        return triggerType;
    }
    public void setTriggerType(String triggerType) {
        this.triggerType = triggerType;
    }
    @Temporal(TemporalType.DATE)
    @JsonSerialize(using = TimeSerializer.class)
    public Date getStartTime() {
        return startTime;
    }
    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }
    @Temporal(TemporalType.DATE)
    @JsonSerialize(using = TimeSerializer.class)
    public Date getEndTime() {
        return endTime;
    }
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
    public String getClassName() {
        return className;
    }
    public void setClassName(String className) {
        this.className = className;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getCronExpression() {
        return cronExpression;
    }
    public void setCronExpression(String cronExpression) {
        this.cronExpression = cronExpression;
    }
    
}
