package com.project.schoolroll.domain.excel;

/**
 * @author: zhangyy
 * @email: zhang10092009@hotmail.com
 * @date: 19-7-23 16:34
 * @version: 1.0
 * @description:
 */
public enum StudentExpandEnum {
    //学生邮箱
    STU_EMAIL("学生邮箱"),
    //家庭住址
    FAMILY_ADDRESS("家庭住址"),
    //乘火车区间
    TRAIN_SPACE("乘火车区间"),
    //生源地行政区划码
    STUDENT_FORM_ADMINISTRATIVE_CODE("学生来源地行政区代码"),
    //出生地行政区划码
    BIRTHPLACE_ADMINISTRATIVE_CODE("出生地行政区代码"),
    //家庭邮政编码
    FAMILY_POSTAL_CODE("家庭邮政编码"),
    //户口所在地行政区划码
    HOUSEHOLD_ADMINISTRATIVE_CODE("户口所在地行政代码"),
    //户口所在地县以下详细地址
    HOUSEHOLD_ADDRESS_DETAILS("户口所在地县以下详细地址")
    //所属派出所

    //学生居住地类型
    //健康状况
    //学生来源
    //招生对象
    //是否建档立卡贫困户
    //招生方式
    //联招合作类型
    //考生特长
    //考生既往病史
    //体检结论
    //联招合作办学形式
    //联招合作学校代码
    //校外教学点
    //分阶段培养方式
    //英文姓名
    //其他联系方式
    ;

    private String expandName;

    public String getExpandName() {
        return expandName;
    }

    StudentExpandEnum(String expandName) {
        this.expandName = expandName;
    }

    StudentExpandEnum() {
    }
}
