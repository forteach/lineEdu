package com.project.schoolroll.repository.online;


import com.project.schoolroll.domain.online.StudentOnLine;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentOnLineRepository extends JpaRepository<StudentOnLine, String> {


    public List<StudentOnLine> findAllByIsValidatedEqualsAndImportStatus(String isValidated, int importStatus);

    public Page<StudentOnLine> findAllByIsValidatedEqualsAndImportStatus(String isValidated, int importStatus, Pageable pageable);

    public List<StudentOnLine> findAllByIsValidatedEqualsAndImportStatusAndCenterAreaId(String isValidated, int importStatus, String centerAreaId);

    public Page<StudentOnLine> findAllByIsValidatedEqualsAndImportStatusAndCenterAreaId(String isValidated, int importStatus, String centerAreaId, Pageable pageable);
}
