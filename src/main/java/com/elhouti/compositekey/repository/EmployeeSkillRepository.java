package com.elhouti.compositekey.repository;

import com.elhouti.compositekey.domain.EmployeeSkill;
import com.elhouti.compositekey.domain.EmployeeSkillId;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the EmployeeSkill entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EmployeeSkillRepository extends JpaRepository<EmployeeSkill, EmployeeSkillId>, JpaSpecificationExecutor<EmployeeSkill> {

}
