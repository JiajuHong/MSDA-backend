package com.jiaju.project.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jiaju.project.model.entity.TemperatureSensorData;
import com.jiaju.project.service.TemperatureSensorDataService;
import com.jiaju.project.mapper.TemperatureSensorDataMapper;
import org.springframework.stereotype.Service;

/**
* @author jiaju
* @description 针对表【temperature_sensor_data(温度传感器信息表)】的数据库操作Service实现
* @createDate 2023-03-27 15:05:19
*/
@Service
public class TemperatureSensorDataServiceImpl extends ServiceImpl<TemperatureSensorDataMapper, TemperatureSensorData>
    implements TemperatureSensorDataService{

}




