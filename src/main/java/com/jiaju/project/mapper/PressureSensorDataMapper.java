package com.jiaju.project.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jiaju.project.model.entity.PressureSensorData;
import com.jiaju.project.model.vo.PressureVO;

import java.util.List;

/**
* @author jiaju
* @description 针对表【pressure_sensor_data(压力传感器信息表)】的数据库操作Mapper
* @createDate 2023-03-27 15:05:19
* @Entity com.jiaju.project.model.entity.PressureSensorData
*/
public interface PressureSensorDataMapper extends BaseMapper<PressureSensorData> {

    List<PressureVO> getPressureHistory();

    List<PressureVO> getPressureRealtime();

}




