package com.juhuifu.quartz.entity.quartz;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.juhuifu.quartz.base.entity.BaseEntity;
import com.juhuifu.quartz.base.serializer.SuperEnumSerialize;
import com.juhuifu.quartz.base.serializer.TimeSerializer;
import com.juhuifu.quartz.enums.JobStatusEnum;

@Entity
@Table(name = "quartz_job_his")
public class QuartzJobHis extends BaseEntity {

    private static final long serialVersionUID = -7216008188502081685L;
    private String jobName;
    private String description;
    private String cronExpression;
    private Date startTime;
    private Date endTime;
    private Date nextTime;
    private JobStatusEnum status;
    private String jobComment;

    public String getJobName() {
        return jobName;
    }

    public String getDescription() {
        return description;
    }

    public String getCronExpression() {
        return cronExpression;
    }

    @JsonSerialize(using=TimeSerializer.class)
    public Date getStartTime() {
        return startTime;
    }
    @JsonSerialize(using=TimeSerializer.class)
    public Date getEndTime() {
        return endTime;
    }
    @JsonSerialize(using=TimeSerializer.class)
    public Date getNextTime() {
        return nextTime;
    }

    @JsonSerialize(using=SuperEnumSerialize.class)
    public JobStatusEnum getStatus() {
        return status;
    }

    public String getJobComment() {
        return jobComment;
    }

    public void setJobComment(String jobComment) {
        this.jobComment = jobComment;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCronExpression(String cronExpression) {
        this.cronExpression = cronExpression;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public void setNextTime(Date nextTime) {
        this.nextTime = nextTime;
    }

    public void setStatus(JobStatusEnum status) {
        this.status = status;
    }

}
