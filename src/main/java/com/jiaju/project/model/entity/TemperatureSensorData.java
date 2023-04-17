package com.jiaju.project.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 温度传感器信息表
 * @TableName temperature_sensor_data
 */
@TableName(value ="temperature_sensor_data")
@Data
public class TemperatureSensorData extends SensorInfo implements Serializable {
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
    private Double temperature;

    /**
     * 温度单位
     */
    private String unit;

    /**
     * 测量时间
     */
    private Date measurement_time;

    @TableField(exist = false)
    private String date;
    /**
     * 是否删除
     */
    private Integer isDelete;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}