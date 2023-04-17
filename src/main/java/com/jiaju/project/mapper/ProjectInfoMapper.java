package com.jiaju.project.mapper;

import com.jiaju.project.model.entity.ProjectInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jiaju.project.model.vo.LeaderVO;
import com.jiaju.project.model.vo.ProjectVO;

import java.util.List;

/**
* @author jiaju
* @description 针对表【project_info(项目信息表)】的数据库操作Mapper
* @createDate 2023-04-15 15:03:12
* @Entity com.jiaju.project.model.entity.ProjectInfo
*/
public interface ProjectInfoMapper extends BaseMapper<ProjectInfo> {

    List<ProjectVO> selectProjectList();

    List<LeaderVO> selectLeaderList();

}




