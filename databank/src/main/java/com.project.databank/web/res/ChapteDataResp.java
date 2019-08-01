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
public class ChapteDataResp implements Serializable {

    private String courseId;

    private String chapterId;

    private String datumType;

    private String fileName;

    private String src;

    private String datumAreas;

    private String teachShare;

    private String stuShare;

}
