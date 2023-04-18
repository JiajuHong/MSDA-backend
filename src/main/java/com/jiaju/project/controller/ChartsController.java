package com.jiaju.project.controller;

import com.jiaju.project.mapper.HumiditySensorDataMapper;
import com.jiaju.project.mapper.PressureSensorDataMapper;
import com.jiaju.project.mapper.TemperatureSensorDataMapper;
import com.jiaju.project.model.vo.HumidityVO;
import com.jiaju.project.model.vo.PressureVO;
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

    @Resource
    private HumiditySensorDataMapper humiditySensorDataMapper;

    @Resource
    private PressureSensorDataMapper pressureSensorDataMapper;


    @GetMapping("/history/temperature")
    public List<TemperatureVO> temperatureHistory() {
        return temperatureSensorDataMapper.getTemperatureHistory();
    }

    @GetMapping("/realtime/temperature")
    public List<TemperatureVO> temperatureRealtime() {
        return temperatureSensorDataMapper.getTemperatureRealtime();
    }

    @GetMapping("/realtime/humidity")
    public List<HumidityVO> humidityRealtime() {
        return humiditySensorDataMapper.getHumidityRealtime();
    }

    @GetMapping("/history/humidity")
    public List<HumidityVO> humidityHistory() {
        return humiditySensorDataMapper.getHumidityHistory();
    }

    @GetMapping("/history/pressure")
    public List<PressureVO> pressureHistory() {
        return pressureSensorDataMapper.getPressureHistory();
    }

    @GetMapping("/realtime/pressure")
    public List<PressureVO> pressureRealtime() {
        return pressureSensorDataMapper.getPressureRealtime();
    }
    // endregion
}
