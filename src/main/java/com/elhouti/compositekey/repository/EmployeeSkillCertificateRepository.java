package com.elhouti.compositekey.repository;

import com.elhouti.compositekey.domain.EmployeeSkillCertificate;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the EmployeeSkillCertificate entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EmployeeSkillCertificateRepository extends JpaRepository<EmployeeSkillCertificate, Long>, JpaSpecificationExecutor<EmployeeSkillCertificate> {

}
