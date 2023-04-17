package com.jiaju.project.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jiaju.project.common.ErrorCode;
import com.jiaju.project.exception.BusinessException;
import com.jiaju.project.model.entity.SensorInfo;
import com.jiaju.project.model.entity.WorkGroup;
import com.jiaju.project.service.SensorInfoService;
import com.jiaju.project.mapper.SensorInfoMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

/**
* @author jiaju
* @description 针对表【sensor_info(传感器信息表)】的数据库操作Service实现
* @createDate 2023-04-02 15:44:00
*/
@Service
public class SensorInfoServiceImpl extends ServiceImpl<SensorInfoMapper, SensorInfo>
    implements SensorInfoService{

    @Resource
    private SensorInfoMapper sensorInfoMapper;
    @Override
    public void validSensorInfo(SensorInfo sensorInfo, boolean add) {
        if (sensorInfo == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        String name = sensorInfo.getName();
        // 创建时，所有参数必须非空
        if (add) {
            if (StringUtils.isAnyBlank(name)) {
                throw new BusinessException(ErrorCode.PARAMS_ERROR);
            }
        }
        if (StringUtils.isNotBlank(name) && name.length() > 50) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "名称过长");
        }
    }

/*    @Override
    public HashMap getSensorInfoSubtotals() {
        return sensorInfoMapper.getSensorInfoSubtotals();
    }*/
}




