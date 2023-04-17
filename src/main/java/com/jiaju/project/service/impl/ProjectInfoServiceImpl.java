package com.jiaju.project.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jiaju.project.common.ErrorCode;
import com.jiaju.project.exception.BusinessException;
import com.jiaju.project.mapper.ProjectInfoMapper;
import com.jiaju.project.model.entity.ProjectInfo;
import com.jiaju.project.model.vo.ProjectVO;
import com.jiaju.project.service.ProjectInfoService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

/**
* @author jiaju
* @description 针对表【project_info(项目信息表)】的数据库操作Service实现
* @createDate 2023-04-15 15:03:12
*/
@Service
public class ProjectInfoServiceImpl extends ServiceImpl<ProjectInfoMapper, ProjectInfo>
    implements ProjectInfoService{

    @Resource
    private ProjectInfoMapper projectInfoMapper;
    @Override
    public void validProjectInfo(ProjectInfo projectInfo, boolean add) {
        if (projectInfo == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        String name = projectInfo.getName();
        String principal = projectInfo.getPrincipal();
        String description = projectInfo.getDescription();
        String cover = projectInfo.getCover();
        // 创建时，所有参数必须非空
        if (add) {
            if (StringUtils.isAnyBlank(name, principal, description, cover)) {
                throw new BusinessException(ErrorCode.PARAMS_ERROR);
            }
        }
        if (StringUtils.isNotBlank(name) && name.length() > 50) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "名称过长");
        }
    }
}




