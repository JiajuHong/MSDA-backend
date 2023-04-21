package com.jiaju.project.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 工作组信息表
 * @TableName work_group
 */
@TableName(value ="work_group")
@Data
public class WorkGroup implements Serializable {
    /**
     * 工作组唯一标识符
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 工作组名称
     */
    private String name;

    /**
     * 工作组信息记录的创建时间
     */
    private Date created_time;

    /**
     * 工作组信息记录的更新时间
     */
    private Date updated_time;

    /**
     * 是否删除
     */
    private Integer isDelete;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}