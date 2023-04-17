package com.jiaju.project.model.vo;

import com.jiaju.project.model.entity.WorkGroup;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 帖子视图
 *
 * @author jiaju
 * @TableName product
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class GroupVO extends WorkGroup {
    private String name;

    private String admin;

    private static final long serialVersionUID = 1L;
}