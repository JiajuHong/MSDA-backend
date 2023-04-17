package com.jiaju.project.model.dto.group;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;

/**
 * 更新请求
 *
 * @TableName product
 */
@Data
public class WorkGroupUpdateRequest implements Serializable {

    /**
     * id
     */
    private int id;

    /**
     * 组名称
     */
    private String name;

    /**
     * 组管理员
     */

    private String admin;
    private static final long serialVersionUID = 1L;
}