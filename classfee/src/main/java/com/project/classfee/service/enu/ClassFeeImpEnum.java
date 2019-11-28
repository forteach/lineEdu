package com.project.classfee.service.enu;

public enum ClassFeeImpEnum {

    /** 姓名*/
    fullName("姓名"),
    /** 学年*/
    createYear("学年"),
    /** 月份*/
    createMonth("月份"),
    /** 专业*/
    specialtyIds("专业"),
    /** 课时标准*/
    classFee("课时费标准"),
    /** 课时数量*/
    classCount("课时");


    private String name;


    public String getName() {
        return name;
    }

    ClassFeeImpEnum(String name) {
        this.name = name;
    }

    ClassFeeImpEnum() {
    }
}
