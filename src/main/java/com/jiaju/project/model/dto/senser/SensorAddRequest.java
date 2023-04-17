package com.jiaju.project.model.dto.senser;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 创建请求
 *
 * @TableName product
 */
@Data
public class SensorAddRequest implements Serializable {

    /**
     * 传感器名称
     */
    private String name;

    /**
     * 传感器类型，如温度传感器、倾角传感器等
     */
    private String type;

    /**
     * 传感器状态
     */
    private String status;

    /**
     * 传感器安装位置
     */
    private String location;

    /**
     * 传感器所属的结构物
     */
    private String structure_name;

    /**
     * 创建该记录的用户
     */
    private String created_by;

    /**
     * 传感器所属的工作组
     */
    private String group_name;

    private static final long serialVersionUID = 1L;
}