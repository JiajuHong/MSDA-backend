package com.jiaju.project.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jiaju.project.common.ErrorCode;
import com.jiaju.project.exception.BusinessException;
import com.jiaju.project.model.entity.StructureInfo;
import com.jiaju.project.model.entity.StructureInfo;
import com.jiaju.project.service.StructureInfoService;
import com.jiaju.project.mapper.StructureInfoMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * @author jiaju
 * @description 针对表【structure_info】的数据库操作Service实现
 * @createDate 2023-03-27 15:12:11
 */
@Service
public class StructureInfoServiceImpl extends ServiceImpl<StructureInfoMapper, StructureInfo>
        implements StructureInfoService {

    @Override
    public void validStructureInfo(StructureInfo structureInfo, boolean add) {
        if (structureInfo == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        String name = structureInfo.getName();
        String location = structureInfo.getLocation();
        // 创建时，所有参数必须非空
        if (add) {
            if (StringUtils.isAnyBlank(name, location)) {
                throw new BusinessException(ErrorCode.PARAMS_ERROR);
            }
        }
        if (StringUtils.isNotBlank(name) && name.length() > 50) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "名称过长");
        }
    }
}




