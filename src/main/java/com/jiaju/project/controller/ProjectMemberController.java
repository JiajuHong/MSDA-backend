package com.jiaju.project.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jiaju.project.annotation.AuthCheck;
import com.jiaju.project.common.BaseResponse;
import com.jiaju.project.common.DeleteRequest;
import com.jiaju.project.common.ErrorCode;
import com.jiaju.project.common.ResultUtils;
import com.jiaju.project.constant.CommonConstant;
import com.jiaju.project.exception.BusinessException;
import com.jiaju.project.mapper.ProjectInfoMapper;
import com.jiaju.project.mapper.ProjectMemberMapper;
import com.jiaju.project.model.dto.projectMember.ProjectMemberAddRequest;
import com.jiaju.project.model.dto.projectMember.ProjectMemberQueryRequest;
import com.jiaju.project.model.dto.projectMember.ProjectMemberUpdateRequest;
import com.jiaju.project.model.entity.ProjectInfo;
import com.jiaju.project.model.entity.ProjectMember;
import com.jiaju.project.model.entity.User;
import com.jiaju.project.model.vo.ProjectMemberVO;
import com.jiaju.project.service.ProjectInfoService;
import com.jiaju.project.service.ProjectMemberService;
import com.jiaju.project.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 帖子接口
 *
 * @author jiaju
 */
@RestController
@RequestMapping("/projectMember")
@Slf4j
public class ProjectMemberController {
    @Resource
    private ProjectInfoMapper projectInfoMapper;

    @Resource
    private UserService userService;


    @Resource
    private ProjectMemberService projectMemberService;

    @Resource
    private ProjectMemberMapper projectMemberMapper;

    @Resource
    private ProjectInfoService projectInfoService;
    // region 增删改查

    /**
     * 创建
     *
     * @param projectMemberAddRequest
     * @param request
     * @return
     */
    @PostMapping("/add")
    public BaseResponse<Long> addProjectMember(@RequestBody ProjectMemberAddRequest projectMemberAddRequest, HttpServletRequest request) {
        if (projectMemberAddRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        ProjectMember projectMember = new ProjectMember();
        BeanUtils.copyProperties(projectMemberAddRequest, projectMember);
        String projectName = projectMemberAddRequest.getProject_name();
        QueryWrapper<ProjectInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", projectName);
        ProjectInfo projectInfo = projectInfoMapper.selectOne(queryWrapper);
        if (projectInfo == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        projectMember.setProject_id(projectInfo.getId());
        // 校验
        projectMemberService.validProjectMember(projectMember, true);
        boolean result = projectMemberService.save(projectMember);
        if (!result) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR);
        }
        long newProjectMemberId = projectMember.getId();
        return ResultUtils.success(newProjectMemberId);
    }

    /**
     * 删除
     *
     * @param deleteRequest
     * @param request
     * @return
     */
    @PostMapping("/delete")
    public BaseResponse<Boolean> deleteProjectMember(@RequestBody DeleteRequest deleteRequest, HttpServletRequest request) {
        if (deleteRequest == null || deleteRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User user = userService.getLoginUser(request);
        long id = deleteRequest.getId();
        // 判断是否存在
        ProjectMember oldProjectMember = projectMemberService.getById(id);
        if (oldProjectMember == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        // 仅管理员可删除
        if (!userService.isRoot(request)) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
        boolean b = projectMemberService.removeById(id);
        return ResultUtils.success(b);
    }

    /**
     * 更新
     *
     * @param projectMemberUpdateRequest
     * @param request
     * @return
     */
    @PostMapping("/update")
    public BaseResponse<Boolean> updateProjectMember(@RequestBody ProjectMemberUpdateRequest projectMemberUpdateRequest,
                                            HttpServletRequest request) {
        if (projectMemberUpdateRequest == null || projectMemberUpdateRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        ProjectMember projectMember = new ProjectMember();
        BeanUtils.copyProperties(projectMemberUpdateRequest, projectMember);

        String projectName = projectMemberUpdateRequest.getProject_name();
        QueryWrapper<ProjectInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", projectName);
        ProjectInfo projectInfo = projectInfoMapper.selectOne(queryWrapper);
        if (projectInfo == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        projectMember.setProject_id(projectInfo.getId());
        // 参数校验
        projectMemberService.validProjectMember(projectMember, false);
        long id = projectMemberUpdateRequest.getId();
        // 判断是否存在
        ProjectMember oldProjectMember = projectMemberService.getById(id);
        if (oldProjectMember == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        // 仅管理员可修改
        if (!userService.isRoot(request)) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
        boolean result = projectMemberService.updateById(projectMember);
        return ResultUtils.success(result);
    }

    /**
     * 根据 id 获取
     *
     * @param id
     * @return
     */
    @GetMapping("/get")
    public BaseResponse<ProjectMember> getProjectMemberById(long id) {
        if (id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        ProjectMember projectMember = projectMemberService.getById(id);
        return ResultUtils.success(projectMember);
    }

    /**
     * 获取列表（仅管理员可使用）
     * @param projectMemberQueryRequest
     * @return
     */
    @AuthCheck(anyRole = {"root", "admin"})
    @GetMapping("/list")
    public BaseResponse<List<ProjectMemberVO>> listProjectMember(ProjectMemberQueryRequest projectMemberQueryRequest) {
        ProjectMember projectMemberQuery = new ProjectMember();
        if (projectMemberQueryRequest != null) {
            BeanUtils.copyProperties(projectMemberQueryRequest, projectMemberQuery);
        }
        QueryWrapper<ProjectMember> wrapper = new QueryWrapper<>(projectMemberQuery);
        List<ProjectMember> memberList = projectMemberService.list(wrapper);
        List<ProjectMemberVO> memberVOS = memberList.stream().map(projectMember -> {
            ProjectMemberVO projectMemberVO = new ProjectMemberVO();
            QueryWrapper<ProjectInfo> wrapper1 = new QueryWrapper<>();
            wrapper1.eq("id", projectMember.getProject_id());
            ProjectInfo projectInfo = projectInfoMapper.selectOne(wrapper1);
            projectMember.setProject_name(projectInfo.getName());
            BeanUtils.copyProperties(projectMember, projectMemberVO);
            return projectMemberVO;
        }).collect(Collectors.toList());
        return ResultUtils.success(memberVOS);
    }

    /**
     * 分页获取列表
     *
     * @param projectMemberQueryRequest
     * @param request
     * @return
     */
    @GetMapping("/list/page")
    public BaseResponse<Page<ProjectMember>> listProjectMemberByPage(ProjectMemberQueryRequest projectMemberQueryRequest, HttpServletRequest request) {
        if (projectMemberQueryRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        ProjectMember projectMemberQuery = new ProjectMember();
        BeanUtils.copyProperties(projectMemberQueryRequest, projectMemberQuery);
        long current = projectMemberQueryRequest.getCurrent();
        long size = projectMemberQueryRequest.getPageSize();
        String sortField = projectMemberQueryRequest.getSortField();
        String sortOrder = projectMemberQueryRequest.getSortOrder();
        // 限制爬虫
        if (size > 50) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        QueryWrapper<ProjectMember> queryWrapper = new QueryWrapper<>(projectMemberQuery);
        queryWrapper.orderBy(StringUtils.isNotBlank(sortField),
                sortOrder.equals(CommonConstant.SORT_ORDER_ASC), sortField);
        Page<ProjectMember> projectMemberPage = projectMemberService.page(new Page<>(current, size), queryWrapper);
        return ResultUtils.success(projectMemberPage);
    }

    // endregion
}
