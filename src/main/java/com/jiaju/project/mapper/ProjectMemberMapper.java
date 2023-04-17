package com.jiaju.project.mapper;

import com.jiaju.project.model.entity.ProjectMember;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jiaju.project.model.vo.ProjectMemberVO;

import java.util.List;

/**
* @author jiaju
* @description 针对表【project_member(项目成员表)】的数据库操作Mapper
* @createDate 2023-04-15 15:03:12
* @Entity com.jiaju.project.model.entity.ProjectMember
*/
public interface ProjectMemberMapper extends BaseMapper<ProjectMember> {

    List<ProjectMemberVO> queryProjectMemberList();

}




