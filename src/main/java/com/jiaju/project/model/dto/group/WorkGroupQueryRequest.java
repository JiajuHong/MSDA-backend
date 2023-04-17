package com.jiaju.project.model.dto.group;

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
public class WorkGroupQueryRequest extends PageRequest implements Serializable {
    /**
     * 组名称
     */
    private String name;

    /**
     * 组管理员
     */

    private String admin;

    private static final long serialVersionUID = 1L;
}