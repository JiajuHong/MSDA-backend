package com.jiaju.project.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 项目成员表
 * @TableName project_member
 */
@TableName(value ="project_member")
@Data
public class ProjectMember implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 项目ID，外键
     */
    private Integer project_id;

    @TableField(exist = false)
    private String project_name;

    /**
     * 项目成员名称
     */
    private String user_name;

    /**
     * 项目成员头像
     */
    private String avatar;

    /**
     * 项目成员信息记录的创建时间
     */
    private Date created_time;

    /**
     * 项目成员信息记录的更新时间
     */
    private Date updated_time;

    /**
     * 是否删除
     */
    private Integer isDelete;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

}