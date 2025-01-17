package com.ead.course.service.impl;

import com.ead.course.exception.NotFoundExcepetion;
import com.ead.course.dto.ModuleRecordDto;
import com.ead.course.model.Course;
import com.ead.course.model.Lesson;
import com.ead.course.model.Module;
import com.ead.course.repository.LessonRepository;
import com.ead.course.repository.ModuleRepository;
import com.ead.course.service.ModuleService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ModuleServiceImpl implements ModuleService {

    private final ModuleRepository moduleRepository;

    private final LessonRepository lessonRepository;

    public ModuleServiceImpl(ModuleRepository moduleRepository, LessonRepository lessonRepository) {
        this.moduleRepository = moduleRepository;
        this.lessonRepository = lessonRepository;
    }

    @Transactional
    @Override
    public void delete(Module module) {

        List<Lesson> lessons = lessonRepository.findAllLessonsIntoModule(module.getModuleId());

        if (!lessons.isEmpty()) {
            lessonRepository.deleteAll();
        }

        moduleRepository.delete(module);
    }

    @Override
    public Module save(ModuleRecordDto moduleRecordDto, Course course) {
        Module module = new Module();
        BeanUtils.copyProperties(moduleRecordDto, module);
        module.setCreationDate(LocalDateTime.now(ZoneId.of("UTC")));
        module.setCourse(course);

        return moduleRepository.save(module);
    }

    @Override
    public List<Module> findAllModulesIntoCourse(UUID courseId) {
        return moduleRepository.findAllModulesIntoCourse(courseId);
    }

    @Override
    public Optional<Module> findModuleIntoCourse(UUID courseId, UUID moduleId) {
        Optional<Module> moduleOptional = moduleRepository.findModuleIntoCourse(courseId, moduleId);
        if (moduleOptional.isEmpty()) throw new NotFoundExcepetion("Error: Module not found");
        return moduleOptional;
    }

    @Override
    public Module update(ModuleRecordDto moduleRecordDto, Module module) {
        BeanUtils.copyProperties(moduleRecordDto, module);
        return moduleRepository.save(module);
    }

    @Override
    public Optional<Module> findById(UUID moduleId) {
        Optional<Module> moduleOptional = moduleRepository.findById(moduleId);
        if (moduleOptional.isEmpty()) throw new NotFoundExcepetion("Error: Module not found");
        return moduleOptional;
    }
}
