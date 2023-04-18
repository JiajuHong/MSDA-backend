package com.jiaju.project.model.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户视图
 *
 * @TableName user
 */
@Data
public class HumidityVO implements Serializable {
    private String name;
    private Float humidity;
    private String date;
    private static final long serialVersionUID = 1L;
}