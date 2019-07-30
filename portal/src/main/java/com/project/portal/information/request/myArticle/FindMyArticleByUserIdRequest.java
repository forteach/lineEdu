package com.project.portal.information.request.myArticle;




import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

//查询我收藏的知识库文章
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FindMyArticleByUserIdRequest implements Serializable {
private String userId;
private int pageNo;

}
