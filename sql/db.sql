-- 结构物表
CREATE TABLE structure_info
(
    id           INT PRIMARY KEY AUTO_INCREMENT,
    name         VARCHAR(256)                                                    NOT NULL,
    location     VARCHAR(256)                                                    NOT NULL,
    created_by   VARCHAR(50)                                                     NOT NULL,
    created_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP                             not null,
    updated_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP not null,
    isDelete     tinyint   default 0                                             not null comment '是否删除'
);

CREATE TABLE work_group
(
    id           INT         NOT NULL AUTO_INCREMENT COMMENT '工作组唯一标识符',
    name         VARCHAR(50) NOT NULL COMMENT '工作组名称',
    admin        VARCHAR(50) NOT NULL COMMENT '工作组管理员',
    created_time TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '工作组信息记录的创建时间',
    updated_time TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '工作组信息记录的更新时间',
    isDelete     tinyint              default 0 not null comment '是否删除',
    PRIMARY KEY (id),
    UNIQUE (name),
    foreign key (admin) references sensors.user (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='工作组信息表';

-- 传感器信息表
CREATE TABLE sensor_info
(
    id            INT PRIMARY KEY AUTO_INCREMENT,
    type          VARCHAR(50)                                                     NOT NULL COMMENT '传感器类型，如温度传感器、倾角传感器等',
    name          VARCHAR(256)                                                    NOT NULL COMMENT '传感器名称',
    status        ENUM ('ACTIVE', 'INACTIVE')                                     NOT NULL DEFAULT 'ACTIVE' COMMENT '传感器状态',
    location      VARCHAR(256)                                                    NOT NULL COMMENT '传感器安装位置',
    structure_id  INT                                                             NOT NULL COMMENT '传感器所属的结构物的ID，外键',
    created_by    VARCHAR(50)                                                     NOT NULL COMMENT '创建该记录的用户',
    group_id      INT                                                             NOT NULL COMMENT '传感器所属的工作组的ID，外键',
    created_time  TIMESTAMP DEFAULT CURRENT_TIMESTAMP                             NOT NULL COMMENT '传感器信息记录的创建时间',
    updated_time  TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP NOT NULL COMMENT '传感器信息记录的更新时间',
    isDelete      tinyint   DEFAULT 0                                             NOT NULL COMMENT '是否删除',
    CONSTRAINT fk_structure_id FOREIGN KEY (structure_id) REFERENCES structure_info (id),
    CONSTRAINT fk_group_id FOREIGN KEY (group_id) REFERENCES work_group (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='传感器信息表';

-- 温度传感器表
CREATE TABLE temperature_sensor_data
(
    id              INT PRIMARY KEY AUTO_INCREMENT,
    sensor_id        INT                                 NOT NULL COMMENT '传感器ID，外键',
    temperature      FLOAT                               NOT NULL COMMENT '温度值',
    unit             VARCHAR(10)                         NOT NULL COMMENT '温度单位',
    measurement_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '测量时间',
    isDelete         tinyint   DEFAULT 0                 NOT NULL COMMENT '是否删除',
    CONSTRAINT fk_temperature_sensor_id FOREIGN KEY (sensor_id) REFERENCES sensor_info (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='温度传感器信息表';


-- 倾角传感器表
CREATE TABLE inclinometer_data
(
    id              INT PRIMARY KEY AUTO_INCREMENT,
    sensor_id          INT                                 NOT NULL COMMENT '倾角传感器的ID，外键',
    x_axis_inclination FLOAT(10, 3)                        NOT NULL COMMENT 'x轴倾角值，单位为度',
    y_axis_inclination FLOAT(10, 3)                        NOT NULL COMMENT 'y轴倾角值，单位为度',
    z_axis_inclination FLOAT(10, 3)                        NOT NULL COMMENT 'z轴倾角值，单位为度',
    measurement_time   TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '测量时间',
    CONSTRAINT fk_sensor_id FOREIGN KEY (sensor_id) REFERENCES sensor_info (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='倾角传感器数据表';


-- 红外传感器表
CREATE TABLE infrared_sensor_data
(
    id              INT PRIMARY KEY AUTO_INCREMENT,
    sensor_id        INT                                 NOT NULL COMMENT '所属传感器ID',
    distance         FLOAT                               NOT NULL COMMENT '红外传感器测量到的距离值',
    measurement_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '测量时间',
    CONSTRAINT fk_infrared_sensor_data_sensor_id FOREIGN KEY (sensor_id) REFERENCES sensor_info (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT = '红外传感器数据表';


-- 压力传感器表
CREATE TABLE pressure_sensor_data
(
    id              INT PRIMARY KEY AUTO_INCREMENT,
    sensor_id INT     NOT NULL COMMENT '所属传感器ID',
    pressure  DECIMAL(5, 2) COMMENT '压力传感器采集到的压力值',
    isDelete  TINYINT NOT NULL DEFAULT 0 COMMENT '是否删除',
    CONSTRAINT fk_pressure_sensor_data_sensor_id FOREIGN KEY (sensor_id) REFERENCES sensor_info (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='压力传感器信息表';

-- 水位传感器表
CREATE TABLE water_level_sensor_data
(
    id              INT PRIMARY KEY AUTO_INCREMENT,
    sensor_id INT     NOT NULL COMMENT '所属传感器ID',
    water_level   DECIMAL(5, 2) COMMENT '水位传感器采集到的水位高度值',
    isDelete      TINYINT                              DEFAULT 0 NOT NULL COMMENT '是否删除',
    CONSTRAINT fk_water_level_sensor_data_sensor_id FOREIGN KEY (sensor_id) REFERENCES sensor_info (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='水位传感器信息表';

-- 项目表
CREATE TABLE project_info
(
    id           INT PRIMARY KEY AUTO_INCREMENT,
    name         VARCHAR(256)                                                    NOT NULL COMMENT '项目名称',
    principal    VARCHAR(50)                                                     NOT NULL COMMENT '项目负责人',
    description  VARCHAR(256)                                                    NOT NULL COMMENT '项目描述',
    cover       VARCHAR(256)                                                    NOT NULL COMMENT '项目封面',
    created_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP                             NOT NULL COMMENT '项目信息记录的创建时间',
    updated_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP NOT NULL COMMENT '项目信息记录的更新时间',
    isDelete     tinyint   DEFAULT 0                                             NOT NULL COMMENT '是否删除'
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='项目信息表';


-- 项目成员表
CREATE TABLE project_member
(
    id           INT PRIMARY KEY AUTO_INCREMENT,
    project_id   INT                                                             NOT NULL COMMENT '项目ID，外键',
    user_name      VARCHAR(50)                                                     NOT NULL COMMENT '项目成员名称',
    avatar       VARCHAR(256)                                                    NOT NULL COMMENT '项目成员头像',
    created_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP                             NOT NULL COMMENT '项目成员信息记录的创建时间',
    updated_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP NOT NULL COMMENT '项目成员信息记录的更新时间',
    isDelete     tinyint   DEFAULT 0                                             NOT NULL COMMENT '是否删除',
    CONSTRAINT fk_project_id FOREIGN KEY (project_id) REFERENCES project_info (id),
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='项目成员表';