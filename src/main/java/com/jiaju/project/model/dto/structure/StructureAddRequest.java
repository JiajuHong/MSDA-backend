package com.jiaju.project.model.dto.structure;

import lombok.Data;

import java.io.Serializable;

/**
 * 创建请求
 *
 * @TableName product
 */
@Data
public class StructureAddRequest implements Serializable {

    private String name;

    /**
     *
     */
    private String location;

    /**
     *
     */
    private String created_by;

    private static final long serialVersionUID = 1L;
}