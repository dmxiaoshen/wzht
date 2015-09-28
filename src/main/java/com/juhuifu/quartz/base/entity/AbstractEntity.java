
package com.juhuifu.quartz.base.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.juhuifu.quartz.base.serializer.TimeSerializer;

@MappedSuperclass
public abstract class AbstractEntity implements Serializable, Cloneable {

    private static final long serialVersionUID = -6931127194705248414L;

    private Date createTime;

    private Date updateTime;

    @Column(updatable = false)
    @JsonSerialize(using=TimeSerializer.class)
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @JsonSerialize(using=TimeSerializer.class)
    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
    
    @Override
    public Object clone() {
        try { 
            return super.clone(); 
        } catch (CloneNotSupportedException e) {
            return null;
        }
    }
}
