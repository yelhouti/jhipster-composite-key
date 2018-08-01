package com.elhouti.compositekey.repository;

import com.elhouti.compositekey.domain.EmployeeSkillCertificate;
import com.elhouti.compositekey.domain.EmployeeSkillCertificateId;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the EmployeeSkillCertificate entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EmployeeSkillCertificateRepository extends JpaRepository<EmployeeSkillCertificate, EmployeeSkillCertificateId>, JpaSpecificationExecutor<EmployeeSkillCertificate> {

}
