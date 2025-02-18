package com.ead.course.specification;

import com.ead.course.model.Course;
import com.ead.course.model.Lesson;
import com.ead.course.model.Module;
import com.ead.course.model.User;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Join;
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

    @And({
            @Spec(path = "email", spec = Equal.class),
            @Spec(path = "fullname", spec = LikeIgnoreCase.class),
            @Spec(path = "userStatus", spec = Equal.class),
            @Spec(path = "userType", spec = Equal.class)
    })
    public interface UserSpec extends Specification<User> {
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

    public static Specification<Course> courseUserId(final UUID userId) {
        return ((root, query, criteriaBuilder) -> {
            query.distinct(true);
            Root<User> user = query.from(User.class);
            Expression<Collection<Course>> courseUsers = user.get("courses");
            return criteriaBuilder.and(criteriaBuilder.equal(user.get("userId"), userId),
                    criteriaBuilder.isMember(root, courseUsers));
        });
    }

    public static Specification<User> userCourseId(final UUID courseId) {
        return ((root, query, criteriaBuilder) -> {
            query.distinct(true);
            Root<Course> course = query.from(Course.class);
            Expression<Collection<User>> courseUsers = course.get("users");
            return criteriaBuilder.and(criteriaBuilder.equal(course.get("courseId"), courseId),
                    criteriaBuilder.isMember(root, courseUsers));
        });
    }

}
