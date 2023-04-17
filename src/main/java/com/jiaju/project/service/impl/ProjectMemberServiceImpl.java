package com.jiaju.project.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jiaju.project.common.ErrorCode;
import com.jiaju.project.exception.BusinessException;
import com.jiaju.project.model.entity.ProjectMember;
import com.jiaju.project.service.ProjectMemberService;
import com.jiaju.project.mapper.ProjectMemberMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
* @author jiaju
* @description 针对表【project_member(项目成员表)】的数据库操作Service实现
* @createDate 2023-04-15 15:03:12
*/
@Service
public class ProjectMemberServiceImpl extends ServiceImpl<ProjectMemberMapper, ProjectMember>
    implements ProjectMemberService{

    @Override
    public void validProjectMember(ProjectMember projectMember, boolean add) {
        if (projectMember == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        String name = projectMember.getUser_name();
        Integer projectId = projectMember.getProject_id();
        String avatar = projectMember.getAvatar();
        // 创建时，所有参数必须非空
        if (add) {
            if (StringUtils.isAnyBlank(name, avatar) && projectId == null) {
                throw new BusinessException(ErrorCode.PARAMS_ERROR);
            }
        }
        if (StringUtils.isNotBlank(name) && name.length() > 50) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "名称过长");
        }
    }
}




