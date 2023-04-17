package com.jiaju.project.model.dto.structure;

import lombok.Data;

import java.io.Serializable;

/**
 * 更新请求
 *
 * @TableName product
 */
@Data
public class StructureUpdateRequest implements Serializable {

    /**
     * id
     */
    private Integer id;

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