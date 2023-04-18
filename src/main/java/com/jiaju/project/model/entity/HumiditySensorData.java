package com.jiaju.project.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 湿度传感器数据表
 * @TableName humidity_sensor_data
 */
@TableName(value ="humidity_sensor_data")
@Data
public class HumiditySensorData implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 传感器ID，外键
     */
    private Integer sensor_id;

    /**
     * 温度值
     */
    private Double humidity;

    /**
     * 湿度单位
     */
    private String unit;

    /**
     * 测量时间
     */
    private Date measurement_time;

    /**
     * 是否删除
     */
    private Integer isDelete;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}