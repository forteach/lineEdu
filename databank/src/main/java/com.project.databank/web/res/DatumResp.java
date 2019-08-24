package com.project.databank.web.res;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Auther: zhangyy
 * @Email: zhang10092009@hotmail.com
 * @Date: 18-12-3 17:09
 * @Version: 1.0
 * @Description:
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DatumResp implements Serializable {

    /**
     * 文件编号
     */
    public String fileId;
    /**
     * 章节编号
     */
    public String chapterId;
    /**
     * 文件名称
     */
    public String fileName;
    /**
     * 文件扩展名
     */
    public String fileType;
    /**
     * 文件URl
     */
    public String fileUrl;
    /**
     * 文件类型（音频、视频....）
     */
    private String datumType;
}