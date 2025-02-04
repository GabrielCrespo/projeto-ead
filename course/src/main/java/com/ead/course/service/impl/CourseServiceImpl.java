package com.ead.course.service.impl;

import com.ead.course.dto.CourseRecordDto;
import com.ead.course.exception.NotFoundExcepetion;
import com.ead.course.model.Course;
import com.ead.course.model.CourseUser;
import com.ead.course.model.Lesson;
import com.ead.course.model.Module;
import com.ead.course.repository.CourseRepository;
import com.ead.course.repository.CourseUserRepository;
import com.ead.course.repository.LessonRepository;
import com.ead.course.repository.ModuleRepository;
import com.ead.course.service.CourseService;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;

    private final ModuleRepository moduleRepository;

    private final LessonRepository lessonRepository;

    private final CourseUserRepository courseUserRepository;

    public CourseServiceImpl(
            CourseRepository courseRepository,
            ModuleRepository moduleRepository,
            LessonRepository lessonRepository,
            CourseUserRepository courseUserRepository) {
        this.courseRepository = courseRepository;
        this.moduleRepository = moduleRepository;
        this.lessonRepository = lessonRepository;
        this.courseUserRepository = courseUserRepository;
    }

    @Transactional
    @Override
    public void delete(Course course) {

        List<Module> modules = moduleRepository.findAllModulesIntoCourse(course.getCourseId());

        if (!modules.isEmpty()) {
            for (Module module : modules) {
                List<Lesson> lessons = lessonRepository.findAllLessonsIntoModule(module.getModuleId());
                if (!lessons.isEmpty()) {
                    lessonRepository.deleteAll();
                }
            }
            moduleRepository.deleteAll(modules);
        }

        List<CourseUser> courseUsers = courseUserRepository.findAllCourseUserIntoCourse(course.getCourseId());

        if (!courseUsers.isEmpty()) {
            courseUserRepository.deleteAll(courseUsers);
        }

        courseRepository.delete(course);
    }

    @Override
    public Object save(CourseRecordDto courseRecordDto) {
        var course = new Course();
        BeanUtils.copyProperties(courseRecordDto, course);
        course.setCreationDate(LocalDateTime.now(ZoneId.of("UTC")));
        course.setLastUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));
        return courseRepository.save(course);
    }

    @Override
    public boolean existsByName(String name) {
        return courseRepository.existsByName(name);
    }

    @Override
    public Page<Course> findAll(Specification<Course> specification, Pageable pageable) {
        return courseRepository.findAll(specification, pageable);
    }

    @Override
    public Optional<Course> findById(UUID courseId) {
        Optional<Course> courseOptional = courseRepository.findById(courseId);
        if (courseOptional.isEmpty()) throw new NotFoundExcepetion("Error: Course not found");
        return courseRepository.findById(courseId);
    }

    @Override
    public Course update(CourseRecordDto courseRecordDto, Course course) {
        BeanUtils.copyProperties(courseRecordDto, course);
        course.setLastUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));
        return courseRepository.save(course);
    }
}
