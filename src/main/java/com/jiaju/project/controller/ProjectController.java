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
import com.jiaju.project.model.dto.project.ProjectAddRequest;
import com.jiaju.project.model.dto.project.ProjectQueryRequest;
import com.jiaju.project.model.dto.project.ProjectUpdateRequest;
import com.jiaju.project.model.entity.ProjectInfo;
import com.jiaju.project.model.entity.User;
import com.jiaju.project.model.vo.LeaderVO;
import com.jiaju.project.service.ProjectInfoService;
import com.jiaju.project.service.StructureInfoService;
import com.jiaju.project.service.UserService;
import com.jiaju.project.service.WorkGroupService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 帖子接口
 *
 * @author jiaju
 */
@RestController
@RequestMapping("/project")
@Slf4j
public class ProjectController {

    @Resource
    private ProjectInfoService projectInfoService;

    @Resource
    private ProjectInfoMapper projectInfoMapper;

    @Resource
    private WorkGroupService workGroupService;

    @Resource
    private UserService userService;

    @Resource
    private StructureInfoService structureInfoService;

    // region 增删改查

    /**
     * 创建
     *
     * @param projectAddRequest
     * @param request
     * @return
     */
    @PostMapping("/add")
    public BaseResponse<Long> addProject(@RequestBody ProjectAddRequest projectAddRequest, HttpServletRequest request) {
        if (projectAddRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        ProjectInfo project = new ProjectInfo();
        BeanUtils.copyProperties(projectAddRequest, project);
        // 校验
        projectInfoService.validProjectInfo(project, true);
        boolean result = projectInfoService.save(project);
        if (!result) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR);
        }
        long newProjectId = project.getId();
        return ResultUtils.success(newProjectId);
    }

    /**
     * 删除
     *
     * @param deleteRequest
     * @param request
     * @return
     */
    @PostMapping("/delete")
    public BaseResponse<Boolean> deleteProject(@RequestBody DeleteRequest deleteRequest, HttpServletRequest request) {
        if (deleteRequest == null || deleteRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User user = userService.getLoginUser(request);
        long id = deleteRequest.getId();
        // 判断是否存在
        ProjectInfo oldProject = projectInfoService.getById(id);
        if (oldProject == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        // 仅管理员可删除
        if (!userService.isRoot(request)) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
        boolean b = projectInfoService.removeById(id);
        return ResultUtils.success(b);
    }

    /**
     * 更新
     *
     * @param projectUpdateRequest
     * @param request
     * @return
     */
    @PostMapping("/update")
    public BaseResponse<Boolean> updateProject(@RequestBody ProjectUpdateRequest projectUpdateRequest,
                                            HttpServletRequest request) {
        if (projectUpdateRequest == null || projectUpdateRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        ProjectInfo project = new ProjectInfo();
        BeanUtils.copyProperties(projectUpdateRequest, project);
        // 参数校验
        projectInfoService.validProjectInfo(project, false);
        User user = userService.getLoginUser(request);
        long id = projectUpdateRequest.getId();
        // 判断是否存在
        ProjectInfo oldProject = projectInfoService.getById(id);
        if (oldProject == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        // 仅管理员可修改
        if (!userService.isRoot(request)) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
        boolean result = projectInfoService.updateById(project);
        return ResultUtils.success(result);
    }

    /**
     * 根据 id 获取
     *
     * @param id
     * @return
     */
    @GetMapping("/get")
    public BaseResponse<ProjectInfo> getProjectById(long id) {
        if (id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        ProjectInfo project = projectInfoService.getById(id);
        return ResultUtils.success(project);
    }

    /**
     * 获取列表（仅管理员可使用）
     * @param projectQueryRequest
     * @return
     */
    @AuthCheck(anyRole = {"root", "admin"})
    @GetMapping("/list")
    public BaseResponse<List<ProjectInfo>> listProject(ProjectQueryRequest projectQueryRequest) {
        ProjectInfo projectQuery = new ProjectInfo();
        if (projectQueryRequest != null) {
            BeanUtils.copyProperties(projectQueryRequest, projectQuery);
        }
        QueryWrapper<ProjectInfo> queryWrapper = new QueryWrapper<>(projectQuery);
        List<ProjectInfo> projectList = projectInfoService.list(queryWrapper);
        return ResultUtils.success(projectList);
    }

    /**
     * 分页获取列表
     *
     * @param projectQueryRequest
     * @param request
     * @return
     */
    @GetMapping("/list/page")
    public BaseResponse<Page<ProjectInfo>> listProjectByPage(ProjectQueryRequest projectQueryRequest, HttpServletRequest request) {
        if (projectQueryRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        ProjectInfo projectQuery = new ProjectInfo();
        BeanUtils.copyProperties(projectQueryRequest, projectQuery);
        long current = projectQueryRequest.getCurrent();
        long size = projectQueryRequest.getPageSize();
        String sortField = projectQueryRequest.getSortField();
        String sortOrder = projectQueryRequest.getSortOrder();
        // 限制爬虫
        if (size > 50) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        QueryWrapper<ProjectInfo> queryWrapper = new QueryWrapper<>(projectQuery);
        queryWrapper.orderBy(StringUtils.isNotBlank(sortField),
                sortOrder.equals(CommonConstant.SORT_ORDER_ASC), sortField);
        Page<ProjectInfo> projectPage = projectInfoService.page(new Page<>(current, size), queryWrapper);
        return ResultUtils.success(projectPage);
    }

    // endregion
}
