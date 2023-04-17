package com.jiaju.project.service;

import com.jiaju.project.model.entity.ProjectInfo;
import com.jiaju.project.model.entity.ProjectMember;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author jiaju
* @description 针对表【project_member(项目成员表)】的数据库操作Service
* @createDate 2023-04-15 15:03:12
*/
public interface ProjectMemberService extends IService<ProjectMember> {

    void validProjectMember(ProjectMember projectMember, boolean add);

}
