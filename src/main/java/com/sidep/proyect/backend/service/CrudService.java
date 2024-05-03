package com.sidep.proyect.backend.service;

import java.util.Map;


import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

public interface CrudService {
EntityManager getEntityManager();

    <T> T find(Class<T> type, Object id);

    <T> T create(T t);

    <T> T update(T t);

    void delete(Object t);

    Query createNativeQuery(String nativeQuery, Map<String, Object> parameters);

    Query createJPQL(String jpql, Map<String, Object> parameters);

    Query createNamedQuery(String namedQuery, Map<String, Object> parameters);

    Query createNativeQuery(String nativeQuery);

    Query createJPQL(String jpql);

    Query createNamedQuery(String namedQuery);
}
