package com.hrms.org;

import com.hrms.org.entity.*;
import com.hrms.org.repository.*;
import com.hrms.org.dto.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class OrgDataAccessTest {

    @Autowired
    private DepartmentRepository departmentRepository;

    @Test
    void shouldSaveAndLoadDepartment() {
        Department department = new Department();
        department.setName("Engineering");

        Department saved = departmentRepository.save(department);

        assertThat(saved.getId()).isNotNull();
        assertThat(departmentRepository.findById(saved.getId())).isPresent();
        assertThat(departmentRepository.findById(saved.getId()).get().getName()).isEqualTo("Engineering");
    }
}
