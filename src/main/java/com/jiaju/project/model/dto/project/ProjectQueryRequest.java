package com.jiaju.project.model.dto.project;

import com.jiaju.project.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * 查询请求
 *
 * @author jiaju
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ProjectQueryRequest extends PageRequest implements Serializable {
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