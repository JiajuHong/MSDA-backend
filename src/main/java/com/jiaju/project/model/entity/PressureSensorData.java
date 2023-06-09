package com.jiaju.project.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 压力传感器信息表
 * @TableName pressure_sensor_data
 */
@TableName(value ="pressure_sensor_data")
@Data
public class PressureSensorData extends SensorInfo implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 所属传感器ID
     */
    private Integer sensor_id;

    /**
     * 压力传感器采集到的压力值
     */
    private BigDecimal pressure;


    private String unit;

    private Date measurement_time;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}