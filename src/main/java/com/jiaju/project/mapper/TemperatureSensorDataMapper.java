package com.jiaju.project.mapper;

import com.jiaju.project.model.entity.TemperatureSensorData;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jiaju.project.model.vo.TemperatureVO;

import java.util.List;

/**
* @author jiaju
* @description 针对表【temperature_sensor_data(温度传感器信息表)】的数据库操作Mapper
* @createDate 2023-03-27 15:05:19
* @Entity com.jiaju.project.model.entity.TemperatureSensorData
*/
public interface TemperatureSensorDataMapper extends BaseMapper<TemperatureSensorData> {

    List<TemperatureVO> getTemperatureHistory();

    List<TemperatureVO> getTemperatureRealtime();
}




