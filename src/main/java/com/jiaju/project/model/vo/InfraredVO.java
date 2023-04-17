package com.jiaju.project.model.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户视图
 *
 * @TableName user
 */
@Data
public class InfraredVO implements Serializable {
    private String name;
    private Integer distance;
    private String date;
    private static final long serialVersionUID = 1L;
}