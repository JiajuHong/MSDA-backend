package com.jiaju.project.model.vo;

import lombok.Data;
import net.sf.jsqlparser.expression.DateTimeLiteralExpression;

import java.io.Serializable;

/**
 * 用户视图
 *
 * @TableName user
 */
@Data
public class TemperatureVO implements Serializable {
    private String name;
    private Float temperature;
    private String date;
    private static final long serialVersionUID = 1L;
}