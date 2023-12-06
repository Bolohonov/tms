package dev.bolohonov.tms.server.repo.user;

import dev.bolohonov.tms.server.model.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class UserRepositoryCustomImpl implements UserRepositoryCustom {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Page<User> getUsersByIds(Set<Long> ids, Pageable pageable) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> query = cb.createQuery(User.class);
        Root<User> user = query.from(User.class);
        List<Predicate> predicates = new ArrayList<>();
        for (Long id : ids) {
            predicates.add(cb.equal(user.get("id"), id));
        }
        query.select(user)
                .where(cb.or(predicates.toArray(new Predicate[predicates.size()])));
        List<User> users = entityManager.createQuery(query)
                .getResultList();
        return new PageImpl<>(users, pageable, users.size());
    }
}
