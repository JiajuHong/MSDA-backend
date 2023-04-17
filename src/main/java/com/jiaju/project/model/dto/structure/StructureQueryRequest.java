package com.jiaju.project.model.dto.structure;

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
public class StructureQueryRequest extends PageRequest implements Serializable {
    private Integer id;

    private String name;

    /**
     *
     */
    private String location;

    /**
     *
     */
    private String created_by;

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