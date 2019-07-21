package com.ryt.rabbitmq.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author renyutao
 * @date 2019/7/21 17:09
 */
@Getter
@Setter
public class TestModel implements Serializable {
    /**
     * 姓名
     */
    private String name;
    /**
     * 年龄
     */
    private Integer age;
    /**
     * 性别
     */
    private String gender;
}
