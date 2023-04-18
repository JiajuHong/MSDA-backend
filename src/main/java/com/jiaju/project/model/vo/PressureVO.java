package com.jiaju.project.model.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户视图
 *
 * @TableName user
 */
@Data
public class PressureVO implements Serializable {
    private String name;
    private Float pressure;
    private String time;
    private static final long serialVersionUID = 1L;
}