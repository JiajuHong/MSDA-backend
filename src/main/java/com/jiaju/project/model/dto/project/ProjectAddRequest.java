package com.jiaju.project.model.dto.project;

import lombok.Data;

import java.io.Serializable;

/**
 * 创建请求
 *
 * @TableName product
 */
@Data
public class ProjectAddRequest implements Serializable {

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

    private static final long serialVersionUID = 1L;
}