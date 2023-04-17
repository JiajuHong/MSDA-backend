package com.jiaju.project.controller;

import com.jiaju.project.mapper.TemperatureSensorDataMapper;
import com.jiaju.project.model.vo.TemperatureVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 帖子接口
 *
 * @author jiaju
 */
@RestController
@RequestMapping("/charts")
@Slf4j
public class ChartsController {

    @Resource
    private TemperatureSensorDataMapper temperatureSensorDataMapper;


    @GetMapping("/history/temperature")
    public List<TemperatureVO> temperatureHistory() {
        return temperatureSensorDataMapper.getTemperatureHistory();
    }

    @GetMapping("/realtime/temperature")
    public List<TemperatureVO> temperatureRealtime() {
        return temperatureSensorDataMapper.getTemperatureRealtime();
    }

    // endregion
}
