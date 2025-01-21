package com.ead.course.service;

import com.ead.course.dto.ModuleRecordDto;
import com.ead.course.model.Course;
import com.ead.course.model.Module;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ModuleService {

    void delete(Module module);

    Module save(ModuleRecordDto moduleRecordDto, Course course);

    List<Module> findAllModulesIntoCourse(UUID courseId);

    Optional<Module> findModuleIntoCourse(UUID courseId, UUID moduleId);

    Module update(ModuleRecordDto moduleRecordDto, Module module);

    Optional<Module> findById(UUID moduleId);

    Page<Module> findAllModulesIntoCourse(Specification<Module> specification, Pageable pageable);
}
