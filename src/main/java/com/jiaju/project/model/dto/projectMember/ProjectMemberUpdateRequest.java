package com.jiaju.project.model.dto.projectMember;

import lombok.Data;

import java.io.Serializable;

/**
 * 更新请求
 *
 * @TableName product
 */
@Data
public class ProjectMemberUpdateRequest implements Serializable {

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
    private static final long serialVersionUID = 1L;
}