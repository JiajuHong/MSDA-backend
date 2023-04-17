package com.jiaju.project.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 倾角传感器数据表
 * @TableName inclinometer_data
 */
@TableName(value ="inclinometer_data")
@Data
public class InclinometerData extends SensorInfo implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 倾角传感器的ID，外键
     */
    private Integer sensor_id;

    /**
     * x轴倾角值，单位为度
     */
    private Double x_axis_inclination;

    /**
     * y轴倾角值，单位为度
     */
    private Double y_axis_inclination;

    /**
     * z轴倾角值，单位为度
     */
    private Double z_axis_inclination;

    /**
     * 测量时间
     */
    private Date measurement_time;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}