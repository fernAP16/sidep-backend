package com.sidep.proyect.backend.service.implementation;

import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sidep.proyect.backend.service.CrudService;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;

@Repository
@Transactional
public class CrudServiceImpl implements CrudService {

    @Autowired
    private EntityManager entityManager;

    @Override
    public EntityManager getEntityManager() {
        return entityManager;
    }

    @Override
    public <T> T find(Class<T> type, Object id) {
        return entityManager.find(type, id);
    }

    @Override
    public <T> T create(T t) {
        entityManager.persist(t);
        entityManager.flush();
        entityManager.refresh(t);
        return t;
    }

    @Override
    public <T> T update(T t) {
        return entityManager.merge(t);
    }

    @Override
    public void delete(Object t) {
        entityManager.remove(t);
        entityManager.flush();
    }

    @Override
    public Query createNativeQuery(String nativeQuery, Map<String, Object> parameters) {
        return createQuery(nativeQuery, parameters, QueryType.NATIVE);
    }

    @Override
    public Query createJPQL(String jpql, Map<String, Object> parameters) {
        return createQuery(jpql, parameters, QueryType.JPQL);
    }

    @Override
    public Query createNamedQuery(String namedQuery, Map<String, Object> parameters) {
        return createQuery(namedQuery, parameters, QueryType.NAMED);
    }

    @Override
    public Query createNativeQuery(String nativeQuery) {
        return createNativeQuery(nativeQuery, null);
    }

    @Override
    public Query createJPQL(String jpql) {
        return createJPQL(jpql, null);
    }

    @Override
    public Query createNamedQuery(String namedQuery) {
        return createNamedQuery(namedQuery, null);
    }

    private Query createQuery(String string, Map<String, Object> parameters, QueryType queryType) {
        Query query = null;
        switch (queryType) {
            case NATIVE:
                query = entityManager.createNativeQuery(string);
                break;
            case JPQL:
                query = entityManager.createQuery(string);
                break;
            case NAMED:
                query = entityManager.createNamedQuery(string);
                break;
        }
        if (parameters != null && query != null) {
            Set<Map.Entry<String, Object>> entrySet = parameters.entrySet();
            for (Map.Entry<String, Object> entry : entrySet) {
                query.setParameter(entry.getKey(), entry.getValue());
            }
        }
        return query;
    }

    private enum QueryType {
        NATIVE, JPQL, NAMED;
    }

}

