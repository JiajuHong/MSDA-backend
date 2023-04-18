package com.jiaju.project.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jiaju.project.mapper.HumiditySensorDataMapper;
import com.jiaju.project.model.entity.HumiditySensorData;
import com.jiaju.project.service.HumiditySensorDataService;
import org.springframework.stereotype.Service;

/**
* @author jiaju
* @description 针对表【humidity_sensor_data(湿度传感器数据表)】的数据库操作Service实现
* @createDate 2023-04-17 21:16:54
*/
@Service
public class HumiditySensorDataServiceImpl extends ServiceImpl<HumiditySensorDataMapper, HumiditySensorData>
    implements HumiditySensorDataService{

}




