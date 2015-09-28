package com.juhuifu.quartz.entity.quartz;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.juhuifu.quartz.base.entity.BaseEntity;
import com.juhuifu.quartz.enums.JobStatusEnum;

@Entity
@Table(name = "quartz_job_date")
public class QuartzJobDate extends BaseEntity {

    private static final long serialVersionUID = -3475109688311475246L;
    private String jobName;
    private Date processDate;
    private JobStatusEnum status;
    private String jobComment;

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public JobStatusEnum getStatus() {
        return status;
    }

    public void setStatus(JobStatusEnum status) {
        this.status = status;
    }

    public String getJobComment() {
        return jobComment;
    }

    public void setJobComment(String jobComment) {
        this.jobComment = jobComment;
    }

    public Date getProcessDate() {
        return processDate;
    }

    public void setProcessDate(Date processDate) {
        this.processDate = processDate;
    }
}
