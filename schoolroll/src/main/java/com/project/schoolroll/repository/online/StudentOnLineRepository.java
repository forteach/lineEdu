package com.project.schoolroll.repository.online;


import com.project.schoolroll.domain.online.StudentOnLine;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface StudentOnLineRepository extends JpaRepository<StudentOnLine, String> {


    List<StudentOnLine> findAllByIsValidatedEqualsAndImportStatus(String isValidated, int importStatus);

    Page<StudentOnLine> findAllByIsValidatedEqualsAndImportStatus(String isValidated, int importStatus, Pageable pageable);

    List<StudentOnLine> findAllByIsValidatedEqualsAndImportStatusAndCenterAreaId(String isValidated, int importStatus, String centerAreaId);

    Page<StudentOnLine> findAllByIsValidatedEqualsAndImportStatusAndCenterAreaId(String isValidated, int importStatus, String centerAreaId, Pageable pageable);

    int countAllByIsValidatedEqualsAndClassId(String isValidated, String classId);

    @Transactional(readOnly = true)
    Page<StudentOnLine> findAllByIsValidatedEqualsOrderByCreateTimeDesc(String isValidated, Pageable pageable);

    @Transactional(readOnly = true)
    Page<StudentOnLine> findAllByIsValidatedEqualsAndCenterAreaIdOrderByCreateTimeDesc(String isValidated, String centerAreaId, Pageable pageable);

    List<StudentOnLine> findAllByIsValidatedEqualsAndStuIDCardAndStudentNameOrderByCreateTimeDesc(String isValidated, String stuIDCard, String studentName);
}