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
import com.jiaju.project.mapper.SensorInfoMapper;
import com.jiaju.project.model.dto.senser.SensorAddRequest;
import com.jiaju.project.model.dto.senser.SensorQueryRequest;
import com.jiaju.project.model.dto.senser.SensorUpdateRequest;
import com.jiaju.project.model.entity.*;
import com.jiaju.project.model.vo.SensorVO;
import com.jiaju.project.service.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 帖子接口
 *
 * @author jiaju
 */
@RestController
@RequestMapping("/sensor")
@Slf4j
public class SensorController {

    @Resource
    private SensorInfoService sensorInfoService;

    @Resource
    private SensorInfoMapper sensorInfoMapper;

    @Resource
    private WorkGroupService workGroupService;

    @Resource
    private UserService userService;

    @Resource
    private StructureInfoService structureInfoService;

    @Resource
    private ProjectInfoMapper projectInfoMapper;

    @Resource
    private ProjectInfoService projectInfoService;

    // region 增删改查

    /**
     * 创建
     *
     * @param sensorAddRequest
     * @param request
     * @return
     */
    @PostMapping("/add")
    public BaseResponse<Long> addSensor(@RequestBody SensorAddRequest sensorAddRequest, HttpServletRequest request) {
        if (sensorAddRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        SensorInfo sensor = new SensorInfo();
        BeanUtils.copyProperties(sensorAddRequest, sensor);
        // 校验
        sensorInfoService.validSensorInfo(sensor, true);
        User loginUser = userService.getLoginUser(request);
        String structureName = sensorAddRequest.getStructure_name();

        sensor.setCreated_by(loginUser.getUserAccount());

        QueryWrapper<StructureInfo> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("name", structureName);
        StructureInfo structure = structureInfoService.getOne(queryWrapper1);
        if (structure == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "未找到该结构物");
        }
        sensor.setStructure_id(structure.getId());

        String groupName = sensorAddRequest.getGroup_name();
        QueryWrapper<WorkGroup> queryWrapper2 = new QueryWrapper<>();
        queryWrapper2.eq("name", groupName);
        WorkGroup workGroup = workGroupService.getOne(queryWrapper2);

        if (workGroup == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "未找到该工作组");
        }
        sensor.setGroup_id(workGroup.getId());

        boolean result = sensorInfoService.save(sensor);
        if (!result) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR);
        }
        long newSensorId = sensor.getId();
        return ResultUtils.success(newSensorId);
    }

    /**
     * 删除
     *
     * @param deleteRequest
     * @param request
     * @return
     */
    @PostMapping("/delete")
    public BaseResponse<Boolean> deleteSensor(@RequestBody DeleteRequest deleteRequest, HttpServletRequest request) {
        if (deleteRequest == null || deleteRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User user = userService.getLoginUser(request);
        long id = deleteRequest.getId();
        // 判断是否存在
        SensorInfo oldSensor = sensorInfoService.getById(id);
        if (oldSensor == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        // 仅本人或管理员可删除
        if (!oldSensor.getCreated_by().equals(user.getUserAccount()) && !userService.isAdmin(request) && !userService.isRoot(request)) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
        boolean b = sensorInfoService.removeById(id);
        return ResultUtils.success(b);
    }

    /**
     * 更新
     *
     * @param sensorUpdateRequest
     * @param request
     * @return
     */
    @PostMapping("/update")
    public BaseResponse<Boolean> updateSensor(@RequestBody SensorUpdateRequest sensorUpdateRequest,
                                            HttpServletRequest request) {
        if (sensorUpdateRequest == null || sensorUpdateRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        SensorInfo sensor = new SensorInfo();
        BeanUtils.copyProperties(sensorUpdateRequest, sensor);
        // 参数校验
        sensorInfoService.validSensorInfo(sensor, false);
        User user = userService.getLoginUser(request);
        long id = sensorUpdateRequest.getId();
        // 判断是否存在
        SensorInfo oldSensor = sensorInfoService.getById(id);
        if (oldSensor == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }

        String structureName = sensorUpdateRequest.getStructure_name();
        QueryWrapper<StructureInfo> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("name", structureName);
        StructureInfo structure = structureInfoService.getOne(queryWrapper1);

        if (structure == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        sensor.setStructure_id(structure.getId());

        String groupName = sensorUpdateRequest.getGroup_name();
        QueryWrapper<WorkGroup> queryWrapper2 = new QueryWrapper<>();
        queryWrapper2.eq("name", groupName);
        WorkGroup workGroup = workGroupService.getOne(queryWrapper2);

        if (workGroup == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        sensor.setGroup_id(workGroup.getId());

        // 仅本人或管理员可修改
        if (!oldSensor.getCreated_by().equals(user.getUserAccount()) && !userService.isAdmin(request) && !userService.isRoot(request)) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
        boolean result = sensorInfoService.updateById(sensor);
        return ResultUtils.success(result);
    }

    /**
     * 根据 id 获取
     *
     * @param id
     * @return
     */
    @GetMapping("/get")
    public BaseResponse<SensorInfo> getSensorById(long id) {
        if (id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        SensorInfo sensor = sensorInfoService.getById(id);
        return ResultUtils.success(sensor);
    }

    /**
     * 获取列表（仅管理员可使用）
     * @param sensorQueryRequest
     * @return
     */
    @AuthCheck(anyRole = {"root", "admin"})
    @GetMapping("/list")
    public BaseResponse<List<SensorVO>> listSensor(SensorQueryRequest sensorQueryRequest) {
        SensorInfo sensorInfo = new SensorInfo();
        if (sensorQueryRequest != null) {
            BeanUtils.copyProperties(sensorQueryRequest, sensorInfo);
        }
        QueryWrapper<SensorInfo> queryWrapper = new QueryWrapper<>(sensorInfo);
        // 如果查询条件中包含结构物名称，则需要先查询结构物信息，再根据结构物 id 查询传感器信息
        if (StringUtils.isNotBlank(sensorInfo.getStructure_name())) {
            QueryWrapper<StructureInfo> wrapper = new QueryWrapper<>();
            wrapper.eq("name", sensorInfo.getStructure_name());
            StructureInfo structureInfo = structureInfoService.getOne(wrapper);
            if (structureInfo == null) {
                throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "未找到该结构物");
            }
            queryWrapper.eq("structure_id", structureInfo.getId());
        }
        // 如果查询条件中包含工作组名称，则需要先查询工作组信息，再根据工作组 id 查询传感器信息
        if (StringUtils.isNotBlank(sensorInfo.getGroup_name())) {
            QueryWrapper<WorkGroup> wrapper = new QueryWrapper<>();
            wrapper.eq("name", sensorInfo.getGroup_name());
            WorkGroup workGroup = workGroupService.getOne(wrapper);
            if (workGroup == null) {
                throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "未找到该工作组");
            }
            queryWrapper.eq("group_id", workGroup.getId());
        }
        List<SensorInfo> sensorList = sensorInfoService.list(queryWrapper);
        List<SensorVO> sensorVOList = sensorList.stream().map(sensor -> {
            SensorVO sensorVO = new SensorVO();
            QueryWrapper<StructureInfo> wrapper = new QueryWrapper<>();
            wrapper.eq("id", sensor.getStructure_id());
            StructureInfo structureInfo = structureInfoService.getOne(wrapper);
            sensor.setStructure_name(structureInfo.getName());

            QueryWrapper<WorkGroup> wrapper1 = new QueryWrapper<>();
            wrapper1.eq("id", sensor.getGroup_id());
            WorkGroup workGroup = workGroupService.getOne(wrapper1);
            sensor.setGroup_name(workGroup.getName());
            BeanUtils.copyProperties(sensor, sensorVO);
            return sensorVO;
        }).collect(Collectors.toList());
        return ResultUtils.success(sensorVOList);
    }

    /**
     * 分页获取列表
     *
     * @param sensorQueryRequest
     * @param request
     * @return
     */
    @GetMapping("/list/page")
    public BaseResponse<Page<SensorInfo>> listSensorByPage(SensorQueryRequest sensorQueryRequest, HttpServletRequest request) {
        if (sensorQueryRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        SensorInfo sensorQuery = new SensorInfo();
        BeanUtils.copyProperties(sensorQueryRequest, sensorQuery);
        long current = sensorQueryRequest.getCurrent();
        long size = sensorQueryRequest.getPageSize();
        String sortField = sensorQueryRequest.getSortField();
        String sortOrder = sensorQueryRequest.getSortOrder();
        // 限制爬虫
        if (size > 50) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        QueryWrapper<SensorInfo> queryWrapper = new QueryWrapper<>(sensorQuery);
        queryWrapper.orderBy(StringUtils.isNotBlank(sortField),
                sortOrder.equals(CommonConstant.SORT_ORDER_ASC), sortField);
        Page<SensorInfo> sensorPage = sensorInfoService.page(new Page<>(current, size), queryWrapper);
        return ResultUtils.success(sensorPage);
    }

    @GetMapping("/list/type")
    public BaseResponse<Map<String, Object>> listSensorByLocationType() {
        QueryWrapper<SensorInfo> wrapper = new QueryWrapper<>();
        wrapper.groupBy( "type");
        wrapper.select("type, count(*) as count");
        List<SensorInfo> list = sensorInfoMapper.selectList(wrapper);
        List<Map<String, Object>> resultList = new ArrayList<>();
        for(SensorInfo sensorInfo : list) {
            // 写一个map，有三个key，location，type，count
            Map<String, Object> map = new HashMap<>();
            map.put("type", sensorInfo.getType());
            map.put("count", sensorInfo.getCount());
            resultList.add(map);
        }
        Map<String, Object> map = new HashMap<>();
        map.put("pie", resultList);
        return ResultUtils.success(map);
    }

    @GetMapping("/data-overview")
    public HashMap<String, Object> welcome() {
        HashMap<String, Object> map = new HashMap<>();
        QueryWrapper<SensorInfo> wrapper = new QueryWrapper<>();
        wrapper.eq("status", "inactive");
        List<SensorInfo> errorSensorInfos = sensorInfoMapper.selectList(wrapper);
        long count = sensorInfoService.count();
        map.put("total", count);
        map.put("error", (long) errorSensorInfos.size());
        QueryWrapper<ProjectInfo> wrapper1 = new QueryWrapper<>();
        wrapper1.eq("status", "ongoing");
        List<ProjectInfo> ongoingProject = projectInfoMapper.selectList(wrapper1);
        long projectCount = projectInfoService.count();
        map.put("processing", (long) ongoingProject.size());
        map.put("project_total", projectCount);
        long finished = projectCount - ongoingProject.size();
        DecimalFormat df = new DecimalFormat("0.00");
        double s =  (double) finished / (double) projectCount;
        String percent = df.format(s);
        map.put("percent", percent);
        return map;
    }

    @GetMapping("/list/LocationTypeBar")
    public List<HashMap<String, Object>> listLocationTypeBar() {
        HashMap<String, Object> map = new HashMap<>();
        QueryWrapper<SensorInfo> wrapper = new QueryWrapper<>();
        wrapper.groupBy( "location, type");
        wrapper.select( "location, type, count(*) as count");
        List<SensorInfo> list = sensorInfoMapper.selectList(wrapper);
        List<HashMap<String, Object>> resultList = new ArrayList<>();
        for(SensorInfo sensorInfo : list) {
            // 写一个map，有三个key，location，type，count
            HashMap<String, Object> map1 = new HashMap<>();
            map1.put("location", sensorInfo.getLocation());
            map1.put("type", sensorInfo.getType());
            map1.put("count", sensorInfo.getCount());
            resultList.add(map1);
        }
        return resultList;
    }

    @GetMapping("/list/MyGroupSensor")
    public BaseResponse<List<SensorVO>> listMyGroupSensor(SensorQueryRequest sensorQueryRequest, HttpServletRequest request) {
        SensorInfo sensorInfo = new SensorInfo();
        if (sensorQueryRequest != null) {
            BeanUtils.copyProperties(sensorQueryRequest, sensorInfo);
        }
        User user = userService.getLoginUser(request);
        String groupName = user.getWorkGroup();
        QueryWrapper<WorkGroup> wrapper2 = new QueryWrapper<>();
        wrapper2.eq("name", groupName);
        WorkGroup group = workGroupService.getOne(wrapper2);
        if ( group == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "未找到该组");
        }
        sensorInfo.setGroup_id(group.getId());
        QueryWrapper<SensorInfo> queryWrapper = new QueryWrapper<>(sensorInfo);
        // 如果查询条件中包含结构物名称，则需要先查询结构物信息，再根据结构物 id 查询传感器信息
        if (StringUtils.isNotBlank(sensorInfo.getStructure_name())) {
            QueryWrapper<StructureInfo> wrapper = new QueryWrapper<>();
            wrapper.eq("name", sensorInfo.getStructure_name());
            StructureInfo structureInfo = structureInfoService.getOne(wrapper);
            if (structureInfo == null) {
                throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "未找到该结构物");
            }
            queryWrapper.eq("structure_id", structureInfo.getId());
        }
        List<SensorInfo> sensorList = sensorInfoService.list(queryWrapper);
        List<SensorVO> sensorVOList = sensorList.stream().map(sensor -> {
            SensorVO sensorVO = new SensorVO();
            QueryWrapper<StructureInfo> wrapper = new QueryWrapper<>();
            wrapper.eq("id", sensor.getStructure_id());
            StructureInfo structureInfo = structureInfoService.getOne(wrapper);
            sensor.setStructure_name(structureInfo.getName());

            QueryWrapper<WorkGroup> wrapper1 = new QueryWrapper<>();
            wrapper1.eq("id", sensor.getGroup_id());
            WorkGroup workGroup = workGroupService.getOne(wrapper1);
            sensor.setGroup_name(workGroup.getName());
            BeanUtils.copyProperties(sensor, sensorVO);
            return sensorVO;
        }).collect(Collectors.toList());
        return ResultUtils.success(sensorVOList);
    }

    // endregion
}
