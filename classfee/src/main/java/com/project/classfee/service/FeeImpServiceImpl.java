package com.project.classfee.service;

import cn.hutool.core.date.DateUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import com.project.base.common.keyword.DefineCode;
import com.project.base.exception.MyAssert;
import com.project.base.util.excelImp.AbsExcelImp;
import com.project.classfee.domain.ClassFeeInfo;
import com.project.classfee.service.enu.ClassFeeImpEnum;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import java.io.InputStream;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.project.classfee.service.enu.ClassFeeImpEnum.*;

import static com.project.classfee.key.Dic.IMPORT_CLASSFEE;


/**
 * @Auther: zhangyy
 * @Email: zhang10092009@hotmail.com
 * @Date: 2019/7/9 09:20
 * @Version: 1.0
 * @Description:
 */
@Service
@Slf4j
public class FeeImpServiceImpl extends AbsExcelImp<ClassFeeInfo> {

    private final RedisTemplate redisTemplate;

   public  FeeImpServiceImpl( RedisTemplate redisTemplate){
       this.redisTemplate = redisTemplate;
   }
    public List<ClassFeeInfo> excelReader(InputStream inputStream,Class<ClassFeeInfo> obj) {


        MyAssert.isTrue(redisTemplate.hasKey(IMPORT_CLASSFEE), DefineCode.ERR0013, "有人操作，请稍后再试!");

        //设置导入修改时间 防止失败没有过期时间
        redisTemplate.opsForValue().set(IMPORT_CLASSFEE, DateUtil.now());
        redisTemplate.expire(IMPORT_CLASSFEE, 3, TimeUnit.MINUTES);

        List<ClassFeeInfo> list = ExcelReader(inputStream,obj);

        //导入成功删除对应键值
        delRedisKey();

        return list;
    }

    public void delRedisKey(){
        //导入成功删除对应键值
        redisTemplate.delete(IMPORT_CLASSFEE);

    }

    @Override
    public void setHeaderAlias(@NonNull ExcelReader reader) {
        reader.addHeaderAlias(ClassFeeImpEnum.fullName.getName(), ClassFeeImpEnum.fullName.name());
        reader.addHeaderAlias(ClassFeeImpEnum.createYear.getName(), ClassFeeImpEnum.createYear.name());
        reader.addHeaderAlias(ClassFeeImpEnum.createMonth.getName(), ClassFeeImpEnum.createMonth.name());
        reader.addHeaderAlias(ClassFeeImpEnum.specialtyIds.getName(), ClassFeeImpEnum.specialtyIds.name());
        reader.addHeaderAlias(ClassFeeImpEnum.classFee.getName(), ClassFeeImpEnum.classFee.name());
        reader.addHeaderAlias(ClassFeeImpEnum.classCount.getName(), ClassFeeImpEnum.classCount.name());

    }
}
