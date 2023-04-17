package com.jiaju.project.service;

import com.jiaju.project.model.entity.ProjectInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jiaju.project.model.entity.SensorInfo;

import java.util.HashMap;

/**
* @author jiaju
* @description 针对表【project_info(项目信息表)】的数据库操作Service
* @createDate 2023-04-15 15:03:12
*/
public interface ProjectInfoService extends IService<ProjectInfo> {

    void validProjectInfo(ProjectInfo projectInfo, boolean add);
}
