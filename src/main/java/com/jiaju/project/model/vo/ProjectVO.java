package com.jiaju.project.model.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;

/**
 * 用户视图
 *
 * @TableName user
 */
@Data
public class ProjectVO implements Serializable {
    /**
     * id
     */
    private int projectId;

    /**
     * 项目名称
     */
    private String title;

    /**
     * 负责人
     */
    private String leader;

    /**
     * cover
     */
    private String cover;

    /**
     * 项目描述
     */
    private String description;

    /**
     * 项目成员
     */
    private String member;

    /**
     * 成员头像
     */
    private String memberAvatar;

    private static final long serialVersionUID = 1L;
}