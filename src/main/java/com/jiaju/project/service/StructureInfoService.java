package com.jiaju.project.service;

import com.jiaju.project.model.entity.StructureInfo;
import com.jiaju.project.model.entity.StructureInfo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author jiaju
* @description 针对表【structure_info】的数据库操作Service
* @createDate 2023-03-27 15:12:11
*/
public interface StructureInfoService extends IService<StructureInfo> {
    void validStructureInfo(StructureInfo structureInfo, boolean add);

}
