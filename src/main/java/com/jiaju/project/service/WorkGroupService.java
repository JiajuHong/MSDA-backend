package com.jiaju.project.service;

import com.jiaju.project.model.entity.Post;
import com.jiaju.project.model.entity.WorkGroup;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author jiaju
* @description 针对表【work_group(工作组信息表)】的数据库操作Service
* @createDate 2023-03-27 16:17:28
*/
public interface WorkGroupService extends IService<WorkGroup> {

    void validWorkGroup(WorkGroup workGroup, boolean add);

}
