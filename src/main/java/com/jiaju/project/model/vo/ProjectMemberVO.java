package com.jiaju.project.model.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class ProjectMemberVO implements Serializable {
    private Integer id;

    /**
     * 项目
     */
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
    private static final long serialVersionUID = 1L;
}
