package com.civicflow.specification;

import com.civicflow.core.model.IssuePriority;
import com.civicflow.core.model.IssueStatus;
import com.civicflow.entity.DepartmentEntity;
import com.civicflow.entity.IssueEntity;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

public final class IssueSpecifications {

    private IssueSpecifications() {
    }

    public static Specification<IssueEntity> hasStatus(
            IssueStatus status
    ) {
        return (root, query, cb) ->
                status == null
                        ? null
                        : cb.equal(
                        root.get("status"),
                        status
                );
    }

    public static Specification<IssueEntity> hasPriority(
            IssuePriority priority
    ) {
        return (root, query, cb) ->
                priority == null
                        ? null
                        : cb.equal(
                        root.get("priority"),
                        priority
                );
    }

    public static Specification<IssueEntity> belongsToDepartment(
            Long departmentId
    ) {
        return (root, query, cb) -> {

            if (departmentId == null) {
                return null;
            }

            Join<IssueEntity, DepartmentEntity> department =
                    root.join(
                            "department",
                            JoinType.INNER
                    );

            return cb.equal(
                    department.get("id"),
                    departmentId
            );
        };
    }

    public static Specification<IssueEntity> titleOrDescriptionContains(
            String search
    ) {
        return (root, query, cb) -> {

            if (search == null || search.isBlank()) {
                return null;
            }

            String pattern =
                    "%" + search.trim().toLowerCase() + "%";

            Predicate title =
                    cb.like(
                            cb.lower(root.get("title")),
                            pattern
                    );

            Predicate description =
                    cb.like(
                            cb.lower(root.get("description")),
                            pattern
                    );

            return cb.or(title, description);
        };
    }
}