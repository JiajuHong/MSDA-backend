package com.jiaju.project.model.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class LeaderVO implements Serializable {
    private String source;
    private String target;
    private Integer value;
    private static final long serialVersionUID = 1L;
}
