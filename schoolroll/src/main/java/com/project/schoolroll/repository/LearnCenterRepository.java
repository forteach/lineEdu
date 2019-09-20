package com.project.schoolroll.repository;

import com.project.schoolroll.domain.LearnCenter;
import com.project.schoolroll.repository.dto.LearnCenterDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author: zhangyy
 * @email: zhang10092009@hotmail.com
 * @date: 19-7-2 18:28
 * @version: 1.0
 * @description:
 */
public interface LearnCenterRepository extends JpaRepository<LearnCenter, String> {

    @Transactional(readOnly = true)
    public Page<LearnCenter> findAllByIsValidatedEquals(String isValidated, Pageable pageable);

    @Transactional(readOnly = true)
    @Query(value = "SELECT centerId AS centerId, centerName AS centerName, address as address, principal as principal, " +
            " phone as phone, bankingAccount as bankingAccount, accountHolder as accountHolder, accountHolderPhone as accountHolderPhone, " +
            " bankingAccountAddress as bankingAccountAddress, companyAddress as companyAddress, companyName as companyName " +
            " FROM LearnCenter WHERE isValidated = '0' order by createTime desc ")
    public List<LearnCenterDto> findAllByIsValidatedEquals();

    @Transactional(readOnly = true)
    public List<LearnCenter> findByCenterName(String centerName);
}
