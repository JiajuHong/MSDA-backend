package com.jiaju.project.model.dto.project;

import lombok.Data;

import java.io.Serializable;

/**
 * 更新请求
 *
 * @TableName product
 */
@Data
public class ProjectUpdateRequest implements Serializable {

    /**
     * 项目id
     */
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
    private static final long serialVersionUID = 1L;
}