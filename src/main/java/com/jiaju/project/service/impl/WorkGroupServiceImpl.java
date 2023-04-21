package com.jiaju.project.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jiaju.project.common.ErrorCode;
import com.jiaju.project.exception.BusinessException;
import com.jiaju.project.mapper.WorkGroupMapper;
import com.jiaju.project.model.entity.WorkGroup;
import com.jiaju.project.service.WorkGroupService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
* @author jiaju
* @description 针对表【work_group(工作组信息表)】的数据库操作Service实现
* @createDate 2023-03-27 16:17:28
*/
@Service
public class WorkGroupServiceImpl extends ServiceImpl<WorkGroupMapper, WorkGroup>
    implements WorkGroupService{

    @Override
    public void validWorkGroup(WorkGroup workGroup, boolean add) {
        if (workGroup == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        String name = workGroup.getName();
        // 创建时，所有参数必须非空
        if (add) {
            if (StringUtils.isAnyBlank(name
            )) {
                throw new BusinessException(ErrorCode.PARAMS_ERROR);
            }
        }
    }

}




