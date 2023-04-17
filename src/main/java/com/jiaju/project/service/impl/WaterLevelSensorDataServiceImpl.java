package com.jiaju.project.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jiaju.project.model.entity.WaterLevelSensorData;
import com.jiaju.project.service.WaterLevelSensorDataService;
import com.jiaju.project.mapper.WaterLevelSensorDataMapper;
import org.springframework.stereotype.Service;

/**
* @author jiaju
* @description 针对表【water_level_sensor_data(水位传感器信息表)】的数据库操作Service实现
* @createDate 2023-03-27 15:05:19
*/
@Service
public class WaterLevelSensorDataServiceImpl extends ServiceImpl<WaterLevelSensorDataMapper, WaterLevelSensorData>
    implements WaterLevelSensorDataService{

}




