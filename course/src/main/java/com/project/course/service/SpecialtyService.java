//package com.project.course.service;
//
//import com.project.course.domain.Specialty;
//import com.project.course.repository.dto.ISpecialtyDto;
//import org.springframework.data.domain.Page;
//
//import java.util.List;
//
///**
// * @Auther: zhangyy
// * @Email: zhang10092009@hotmail.com
// * @Date: 2019/7/8 22:04
// * @Version: 1.0
// * @Description:
// */
//public interface SpecialtyService {
//    /**
//     * 保存修改专业信息
//     *
//     * @param specialtyId
//     * @param specialtyName
//     */
//    public Specialty saveUpdate(String specialtyId, String specialtyName, String centerAreaId);
//
//    /**
//     * 查询对应专业信息(全部)
//     *
//     * @return
//     */
//    public List<ISpecialtyDto> findAllSpecialty();
//
//    /**
//     * 删除对应的专业信息通过专业id
//     *
//     * @param specialtyId
//     */
//    public void deleteById(String specialtyId);
//
//    public Specialty findBySpecialtyName(String specialtyName);
//
//    public Page<Specialty> findAllPage(int page, int size);
//
//
//    Specialty getSpecialtyById(String specialtyId);
//
//
//    /**
//     * 逻辑删除
//     *
//     * @param specialtyId
//     */
//    void removeById(String specialtyId);
//}
