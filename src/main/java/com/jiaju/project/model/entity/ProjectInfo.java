package com.jiaju.project.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 项目信息表
 * @TableName project_info
 */
@TableName(value ="project_info")
@Data
public class ProjectInfo implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 项目名称
     */
    private String name;

    /**
     * 项目负责人
     */
    private String principal;

    /**
     * 项目状态
     */
    private String status;

    /**
     * 项目描述
     */
    private String description;

    /**
     * 项目封面
     */
    private String cover;

    /**
     * 项目信息记录的创建时间
     */
    private Date created_time;

    /**
     * 项目信息记录的更新时间
     */
    private Date updated_time;

    /**
     * 是否删除
     */
    private Integer isDelete;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}