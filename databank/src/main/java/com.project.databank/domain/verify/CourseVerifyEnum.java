package com.project.databank.domain.verify;

/**
 * @author: zhangyy
 * @email: zhang10092009@hotmail.com
 * @date: 19-9-29 17:02
 * @version: 1.0
 * @description:
 */
public enum CourseVerifyEnum {
    //1文档　2图册　3视频　4音频　5链接
    FILE_DATUM("1"),
    PHOTO_DATUM("2"),
    VIEW_DATUM("3"),
    AUDIO_DATUM("4"),
    LINK_DATUM("5"),
    COURSE_DATA("6"),
    CHAPTER_DATE("7"),
    COURSE_IMAGE_DATE("8"),
    COURSE_CHAPTER_QUESTION("9")
    ;
    private String value;

    CourseVerifyEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
