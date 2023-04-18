package com.jiaju.project.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jiaju.project.model.entity.HumiditySensorData;
import com.jiaju.project.model.vo.HumidityVO;

import java.util.List;

/**
* @author jiaju
* @description 针对表【humidity_sensor_data(湿度传感器数据表)】的数据库操作Mapper
* @createDate 2023-04-17 21:16:54
* @Entity com.jiaju.project.model.entity.HumiditySensorData
*/
public interface HumiditySensorDataMapper extends BaseMapper<HumiditySensorData> {
    List<HumidityVO> getHumidityRealtime();

    List<HumidityVO> getHumidityHistory();

}




