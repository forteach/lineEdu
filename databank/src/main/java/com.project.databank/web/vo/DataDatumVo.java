package com.project.databank.web.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Auther: zhangyy
 * @Email: zhang10092009@hotmail.com
 * @Date: 18-11-28 15:24
 * @Version: 1.0
 * @Description:
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DataDatumVo implements Serializable {

    private String fileId;

    private String fileName;

    private String fileUrl;

    private String mount;

    private int indexNum;

    private long videoDuration;
}
