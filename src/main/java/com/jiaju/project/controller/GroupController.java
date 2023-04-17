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
import com.jiaju.project.mapper.UserMapper;
import com.jiaju.project.model.dto.group.WorkGroupAddRequest;
import com.jiaju.project.model.dto.group.WorkGroupQueryRequest;
import com.jiaju.project.model.dto.group.WorkGroupUpdateRequest;
import com.jiaju.project.model.entity.User;
import com.jiaju.project.service.UserService;
import com.jiaju.project.model.entity.WorkGroup;
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
@RequestMapping("/group")
@Slf4j
public class GroupController {

    @Resource
    private WorkGroupService groupService;

    @Resource
    private UserService userService;

    @Resource
    private UserMapper userMapper;

    // region 增删改查

    /**
     * 创建
     *
     * @param groupAddRequest
     * @param request
     * @return
     */
    @PostMapping("/add")
    public BaseResponse<Long> addWorkGroup(@RequestBody WorkGroupAddRequest groupAddRequest, HttpServletRequest request) {
        if (groupAddRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        String admin = groupAddRequest.getAdmin();
        String userAccount = groupAddRequest.getAdmin();
        // 使用QueryWrapper根据userAccount查询用户是否存在
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userAccount", userAccount);
        User user = userMapper.selectOne(queryWrapper);
        if (user == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        WorkGroup group = new WorkGroup();
        BeanUtils.copyProperties(groupAddRequest, group);
        // 校验
        groupService.validWorkGroup(group, true);
        // 仅超级管理员可添加
        if (!userService.isRoot(request)) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
        QueryWrapper<WorkGroup> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("admin", groupAddRequest.getAdmin());
        WorkGroup oldWorkGroup = groupService.getOne(queryWrapper1);
        if (oldWorkGroup != null) {
            throw new BusinessException(ErrorCode.RECORD_EXIST_ERROR);
        }
        group.setAdmin(groupAddRequest.getAdmin());
        boolean result = groupService.save(group);
        if (!result) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR);
        }
        long newWorkGroupId = group.getId();
        return ResultUtils.success(newWorkGroupId);
    }

    /**
     * 删除
     *
     * @param deleteRequest
     * @param request
     * @return
     */
    @PostMapping("/delete")
    public BaseResponse<Boolean> deleteWorkGroup(@RequestBody DeleteRequest deleteRequest, HttpServletRequest request) {
        if (deleteRequest == null || deleteRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User user = userService.getLoginUser(request);
        long id = deleteRequest.getId();
        // 判断是否存在
        WorkGroup oldWorkGroup = groupService.getById(id);
        if (oldWorkGroup == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        // 仅超级管理员可删除
        if (!userService.isRoot(request)) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
        boolean b = groupService.removeById(id);
        return ResultUtils.success(b);
    }

    /**
     * 更新
     *
     * @param groupUpdateRequest
     * @param request
     * @return
     */
    @PostMapping("/update")
    public BaseResponse<Boolean> updateWorkGroup(@RequestBody WorkGroupUpdateRequest groupUpdateRequest,
                                                 HttpServletRequest request) {
        if (groupUpdateRequest == null || groupUpdateRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        String userAccount = groupUpdateRequest.getAdmin();
        // 使用QueryWrapper根据userAccount查询用户是否存在
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userAccount", userAccount);
        User admin = userMapper.selectOne(queryWrapper);
        if (admin == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "管理员不存在");
        }
        WorkGroup group = new WorkGroup();
        BeanUtils.copyProperties(groupUpdateRequest, group);
        groupService.validWorkGroup(group, false);
        User user = userService.getLoginUser(request);
        long id = groupUpdateRequest.getId();
        // 判断是否存在
        WorkGroup oldWorkGroup = groupService.getById(id);
        if (oldWorkGroup == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "工作组不存在");
        }
        // 仅本组管理员或超级管理员可修改
        if (!oldWorkGroup.getAdmin().equals(user.getId()) && !userService.isRoot(request)) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
        boolean result = groupService.updateById(group);
        return ResultUtils.success(result);
    }

    /**
     * 根据 id 获取
     *
     * @param id
     * @return
     */
    @GetMapping("/get")
    public BaseResponse<WorkGroup> getWorkGroupById(long id) {
        if (id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        WorkGroup group = groupService.getById(id);
        return ResultUtils.success(group);
    }

    /**
     * 获取列表（仅管理员可使用）
     *
     * @param groupQueryRequest
     * @return
     */
    @AuthCheck(mustRole = "root")
    @GetMapping("/list")
    public BaseResponse<List<WorkGroup>> listWorkGroup(WorkGroupQueryRequest groupQueryRequest) {
        WorkGroup groupQuery = new WorkGroup();
        if (groupQueryRequest != null) {
            BeanUtils.copyProperties(groupQueryRequest, groupQuery);
        }
        QueryWrapper<WorkGroup> queryWrapper = new QueryWrapper<>(groupQuery);
        List<WorkGroup> groupList = groupService.list(queryWrapper);
        return ResultUtils.success(groupList);
    }

    /**
     * 分页获取列表
     *
     * @param groupQueryRequest
     * @param request
     * @return
     */
    @GetMapping("/list/page")
    public BaseResponse<Page<WorkGroup>> listWorkGroupByPage(WorkGroupQueryRequest groupQueryRequest, HttpServletRequest request) {
        if (groupQueryRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        WorkGroup groupQuery = new WorkGroup();
        BeanUtils.copyProperties(groupQueryRequest, groupQuery);
        long current = groupQueryRequest.getCurrent();
        long size = groupQueryRequest.getPageSize();
        String sortField = groupQueryRequest.getSortField();
        String sortOrder = groupQueryRequest.getSortOrder();
        // 限制爬虫
        if (size > 50) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        QueryWrapper<WorkGroup> queryWrapper = new QueryWrapper<>(groupQuery);
        queryWrapper.orderBy(StringUtils.isNotBlank(sortField),
                sortOrder.equals(CommonConstant.SORT_ORDER_ASC), sortField);
        Page<WorkGroup> groupPage = groupService.page(new Page<>(current, size), queryWrapper);
        return ResultUtils.success(groupPage);
    }

    // endregion

}
