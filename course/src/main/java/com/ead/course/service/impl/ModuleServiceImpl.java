package com.ead.course.service.impl;

import com.ead.course.model.Lesson;
import com.ead.course.model.Module;
import com.ead.course.repository.LessonRepository;
import com.ead.course.repository.ModuleRepository;
import com.ead.course.service.ModuleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

}
