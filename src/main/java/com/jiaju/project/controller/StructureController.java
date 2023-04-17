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
import com.jiaju.project.model.dto.structure.StructureAddRequest;
import com.jiaju.project.model.dto.structure.StructureQueryRequest;
import com.jiaju.project.model.dto.structure.StructureUpdateRequest;
import com.jiaju.project.model.entity.StructureInfo;
import com.jiaju.project.model.entity.User;
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
@RequestMapping("/structure")
@Slf4j
public class StructureController {

    @Resource
    private StructureInfoService structureInfoService;

    @Resource
    private UserService userService;


    // region 增删改查

    /**
     * 创建
     *
     * @param structureAddRequest
     * @param request
     * @return
     */
    @PostMapping("/add")
    public BaseResponse<Long> addStructure(@RequestBody StructureAddRequest structureAddRequest, HttpServletRequest request) {
        if (structureAddRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        StructureInfo structure = new StructureInfo();
        BeanUtils.copyProperties(structureAddRequest, structure);
        // 校验
        structureInfoService.validStructureInfo(structure, true);
        User loginUser = userService.getLoginUser(request);
        structure.setCreated_by(loginUser.getUserAccount());
        boolean result = structureInfoService.save(structure);
        if (!result) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR);
        }
        long newStructureId = structure.getId();
        return ResultUtils.success(newStructureId);
    }

    /**
     * 删除
     *
     * @param deleteRequest
     * @param request
     * @return
     */
    @PostMapping("/delete")
    public BaseResponse<Boolean> deleteStructure(@RequestBody DeleteRequest deleteRequest, HttpServletRequest request) {
        if (deleteRequest == null || deleteRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User user = userService.getLoginUser(request);
        long id = deleteRequest.getId();
        // 判断是否存在
        StructureInfo oldStructure = structureInfoService.getById(id);
        if (oldStructure == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        // 仅本人或管理员可删除
        if (!oldStructure.getCreated_by().equals(user.getUserAccount()) && !userService.isRoot(request) && !userService.isAdmin(request)) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
        boolean b = structureInfoService.removeById(id);
        return ResultUtils.success(b);
    }

    /**
     * 更新
     *
     * @param structureUpdateRequest
     * @param request
     * @return
     */
    @PostMapping("/update")
    public BaseResponse<Boolean> updateStructure(@RequestBody StructureUpdateRequest structureUpdateRequest,
                                            HttpServletRequest request) {
        if (structureUpdateRequest == null || structureUpdateRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        StructureInfo structure = new StructureInfo();
        BeanUtils.copyProperties(structureUpdateRequest, structure);
        // 参数校验
        structureInfoService.validStructureInfo(structure, false);
        User user = userService.getLoginUser(request);
        int id = structureUpdateRequest.getId();
        // 判断是否存在
        StructureInfo oldStructure = structureInfoService.getById(id);
        if (oldStructure == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        // 仅本人或管理员可修改
        if (!oldStructure.getCreated_by().equals(user.getUserAccount()) && !userService.isRoot(request) && !userService.isAdmin(request)) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
        if (StringUtils.isNotBlank(structure.getCreated_by()) && StringUtils.isNotBlank(structure.getName()) && StringUtils.isNotBlank(structure.getLocation())) {

        }
        boolean result = structureInfoService.updateById(structure);
        return ResultUtils.success(result);
    }

    /**
     * 根据 id 获取
     *
     * @param id
     * @return
     */
    @GetMapping("/get")
    public BaseResponse<StructureInfo> getStructureById(long id) {
        if (id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        StructureInfo structure = structureInfoService.getById(id);
        return ResultUtils.success(structure);
    }

    /**
     * 获取列表（仅管理员可使用）
     * @param structureQueryRequest
     * @return
     */
    @AuthCheck(mustRole = "root")
    @GetMapping("/list")
    public BaseResponse<List<StructureInfo>> listStructure(StructureQueryRequest structureQueryRequest) {
        StructureInfo structureQuery = new StructureInfo();
        if (structureQueryRequest != null) {
            BeanUtils.copyProperties(structureQueryRequest, structureQuery);
        }
        QueryWrapper<StructureInfo> queryWrapper = new QueryWrapper<>(structureQuery);
        List<StructureInfo> structureList = structureInfoService.list(queryWrapper);
        return ResultUtils.success(structureList);
    }

    /**
     * 分页获取列表
     *
     * @param structureQueryRequest
     * @param request
     * @return
     */
    @GetMapping("/list/page")
    public BaseResponse<Page<StructureInfo>> listStructureByPage(StructureQueryRequest structureQueryRequest, HttpServletRequest request) {
        if (structureQueryRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        StructureInfo structureQuery = new StructureInfo();
        BeanUtils.copyProperties(structureQueryRequest, structureQuery);
        long current = structureQueryRequest.getCurrent();
        long size = structureQueryRequest.getPageSize();
        String sortField = structureQueryRequest.getSortField();
        String sortOrder = structureQueryRequest.getSortOrder();
        // 限制爬虫
        if (size > 50) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        QueryWrapper<StructureInfo> queryWrapper = new QueryWrapper<>(structureQuery);
        queryWrapper.orderBy(StringUtils.isNotBlank(sortField),
                sortOrder.equals(CommonConstant.SORT_ORDER_ASC), sortField);
        Page<StructureInfo> structurePage = structureInfoService.page(new Page<>(current, size), queryWrapper);
        return ResultUtils.success(structurePage);
    }

    // endregion

}
