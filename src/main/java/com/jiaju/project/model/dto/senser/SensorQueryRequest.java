package com.jiaju.project.model.dto.senser;

import com.jiaju.project.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * 查询请求
 *
 * @author jiaju
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SensorQueryRequest extends PageRequest implements Serializable {
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

    /**
     * 传感器信息记录的创建时间
     */
    private Date created_time;

    /**
     * 传感器信息记录的更新时间
     */
    private Date updated_time;

    private static final long serialVersionUID = 1L;
}