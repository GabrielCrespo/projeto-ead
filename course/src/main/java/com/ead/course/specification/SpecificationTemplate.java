package com.ead.course.specification;

import com.ead.course.model.Course;
import com.ead.course.model.Lesson;
import com.ead.course.model.Module;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Root;
import net.kaczmarzyk.spring.data.jpa.domain.Equal;
import net.kaczmarzyk.spring.data.jpa.domain.LikeIgnoreCase;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.data.jpa.domain.Specification;

import java.util.Collection;
import java.util.UUID;

public class SpecificationTemplate {

    @And({
            @Spec(path = "courseLevel", spec = Equal.class),
            @Spec(path = "courseStatus", spec = Equal.class),
            @Spec(path = "name", spec = LikeIgnoreCase.class),
            @Spec(path = "userInstructor", spec = Equal.class)
    })
    public interface CourseSpec extends Specification<Course> {
    }

    @Spec(path = "title", spec = LikeIgnoreCase.class)
    public interface ModuleSpec extends Specification<Module> {
    }

    @Spec(path = "title", spec = LikeIgnoreCase.class)
    public interface LessonSpec extends Specification<Lesson> {
    }

    public static Specification<Module> moduleCourseId(final UUID courseId) {
        return ((root, query, criteriaBuilder) -> {
            query.distinct(true);
            Root<Course> course = query.from(Course.class);
            Expression<Collection<Module>> courseModules = course.get("modules");
            return criteriaBuilder.and(criteriaBuilder.equal(course.get("courseId"), courseId),
                    criteriaBuilder.isMember(root, courseModules));
        });
    }

    public static Specification<Lesson> lessonModuleId(final UUID lessonId) {
        return ((root, query, criteriaBuilder) -> {
            query.distinct(true);
            Root<Module> module = query.from(Module.class);
            Expression<Collection<Lesson>> modulesLessons = module.get("lessons");
            return criteriaBuilder.and(criteriaBuilder.equal(module.get("moduleId"), lessonId),
                    criteriaBuilder.isMember(root, modulesLessons));
        });
    }

}
