package com.jiaju.project.model.vo;

import com.jiaju.project.model.entity.WorkGroup;
import lombok.Data;
import lombok.EqualsAndHashCode;

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

    private static final long serialVersionUID = 1L;
}