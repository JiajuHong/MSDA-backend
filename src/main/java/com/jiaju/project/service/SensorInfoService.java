package com.jiaju.project.service;

import com.jiaju.project.model.entity.SensorInfo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.HashMap;
import java.util.List;

/**
* @author jiaju
* @description 针对表【sensor_info(传感器信息表)】的数据库操作Service
* @createDate 2023-04-02 15:44:00
*/
public interface SensorInfoService extends IService<SensorInfo> {
    void validSensorInfo(SensorInfo sensorInfo, boolean add);

//    HashMap getSensorInfoSubtotals(); // 获取传感器信息表的统计数据

}
