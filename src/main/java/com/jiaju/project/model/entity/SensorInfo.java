package com.jiaju.project.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 传感器信息表
 * @TableName sensor_info
 */
@TableName(value ="sensor_info")
@Data
public class SensorInfo implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

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
     * 传感器所属的结构物的ID，外键
     */
    private Integer structure_id;

    /**
     * 创建该记录的用户
     */
    private String created_by;

    /**
     * 传感器所属的工作组的ID，外键
     */
    private Integer group_id;

    /**
     * 传感器信息记录的创建时间
     */
    private Date created_time;

    /**
     * 传感器信息记录的更新时间
     */
    private Date updated_time;

    /**
     * 是否删除
     */
    private Integer isDelete;

    @TableField(exist = false)
    private int count;

    @TableField(exist = false)
    private int error;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}