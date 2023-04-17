package com.jiaju.project.service;

import com.jiaju.project.mapper.ProjectInfoMapper;
import com.jiaju.project.model.entity.User;
import com.jiaju.project.model.vo.ProjectVO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

/**
 * 用户服务测试
 *
 * @author jiaju
 */
@SpringBootTest
class ProjectTest {
    @Resource
    private ProjectInfoMapper projectInfoMapper;


    @Test
    void testProjectInfo() {
        List<ProjectVO> projectVOList = projectInfoMapper.selectProjectList();
        Assertions.assertNotNull(projectVOList);
    }

    @Test
    void test1() {
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH);
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
        LocalDate date = LocalDate.parse("2018-04-10T04:00:00.000Z", inputFormatter);
        String formattedDate = outputFormatter.format(date);
        System.out.println(formattedDate);
    }
}