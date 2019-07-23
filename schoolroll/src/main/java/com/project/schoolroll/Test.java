package com.project.schoolroll;

import static com.project.schoolroll.domain.excel.StudentExpandEnum.FAMILY_ADDRESS;
import static com.project.schoolroll.domain.excel.StudentExpandEnum.STU_EMAIL;

/**
 * @author: zhangyy
 * @email: zhang10092009@hotmail.com
 * @date: 19-7-23 17:02
 * @version: 1.0
 * @description:
 */
public class Test {
    public static void main(String[] args) {
        System.out.println("get "+ STU_EMAIL.getExpandName());
        System.out.println("name"+ STU_EMAIL.name());
        System.out.println("address "+FAMILY_ADDRESS.getExpandName());
    }
}
