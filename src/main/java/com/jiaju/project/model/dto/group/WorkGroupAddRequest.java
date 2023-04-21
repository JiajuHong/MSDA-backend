package com.jiaju.project.model.dto.group;

import lombok.Data;

import java.io.Serializable;

/**
 * 创建请求
 *
 * @TableName product
 */
@Data
public class WorkGroupAddRequest implements Serializable {

    /**
     * 组名称
     */
    private String name;

    private static final long serialVersionUID = 1L;
}