package com.jiaju.project.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jiaju.project.model.entity.PressureSensorData;
import com.jiaju.project.service.PressureSensorDataService;
import com.jiaju.project.mapper.PressureSensorDataMapper;
import org.springframework.stereotype.Service;

/**
* @author jiaju
* @description 针对表【pressure_sensor_data(压力传感器信息表)】的数据库操作Service实现
* @createDate 2023-03-27 15:05:19
*/
@Service
public class PressureSensorDataServiceImpl extends ServiceImpl<PressureSensorDataMapper, PressureSensorData>
    implements PressureSensorDataService{

}




