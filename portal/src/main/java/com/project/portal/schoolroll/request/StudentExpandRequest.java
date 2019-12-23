//package com.project.portal.schoolroll.request;
//
//import io.swagger.annotations.ApiModel;
//import io.swagger.annotations.ApiModelProperty;
//import lombok.Data;
//
//import java.io.Serializable;
//import java.util.List;
//
///**
// * @author: zhangyy
// * @email: zhang10092009@hotmail.com
// * @date: 19-7-8 16:24
// * @version: 1.0
// * @description:
// */
//@Data
//@ApiModel(value = "保存修改学生扩展信息")
//public class StudentExpandRequest implements Serializable {
//    /**
//     * 修改的扩展学生id
//     */
//    @ApiModelProperty(name = "studentId", value = "学生id", dataType = "string")
//    private String studentId;
//
//    @ApiModelProperty(name = "expandValues", value = "扩展字段需要修改添加的值", required = true)
//    private List<StudentExpandValueRequest> expandValues;
//}
