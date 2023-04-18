package com.jiaju.project.model.dto.projectMember;

import com.jiaju.project.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 查询请求
 *
 * @author jiaju
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ProjectMemberQueryRequest extends PageRequest implements Serializable {
    /**
     * 项目ID，外键
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