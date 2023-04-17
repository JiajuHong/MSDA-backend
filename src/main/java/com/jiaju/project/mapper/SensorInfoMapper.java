package com.jiaju.project.mapper;

import com.jiaju.project.model.entity.SensorInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jiaju.project.model.vo.SensorVO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* @author jiaju
* @description 针对表【sensor_info(传感器信息表)】的数据库操作Mapper
* @createDate 2023-04-02 15:44:00
* @Entity com.jiaju.project.model.entity.SensorInfo
*/
public interface SensorInfoMapper extends BaseMapper<SensorInfo> {

//    HashMap getSensorInfoSubtotals(); // 获取传感器信息表的统计数据

    List<SensorVO> getSensorInfoList(); // 获取传感器信息表的列表数据

}




