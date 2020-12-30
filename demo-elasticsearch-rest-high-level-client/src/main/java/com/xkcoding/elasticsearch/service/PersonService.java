package com.xkcoding.elasticsearch.service;

import com.xkcoding.elasticsearch.model.Person;
import org.springframework.lang.Nullable;

import java.util.List;

/**
 * PersonService
 *
 * @author fxbin
 * @version v1.0
 * @since 2019-09-15 23:07
 */
public interface PersonService {

    /**
     * create Index
     *
     * @param index elasticsearch index name
     * @author fxbin
     */
    void createIndex(String index);

    /**
     * delete Index
     *
     * @param index elasticsearch index name
     * @author fxbin
     */
    void deleteIndex(String index);

    /**
     * insert document source
     *
     * @param index elasticsearch index name
     * @param list  data source
     * @author fxbin
     */
    void insert(String index, List<Person> list);

    /**
     * update document source
     *
     * @param index elasticsearch index name
     * @param list  data source
     * @author fxbin
     */
    void update(String index, List<Person> list);

    /**
     * delete document source
     *
     * @param person delete data source and allow null object
     * @author fxbin
     */
    void delete(String index, @Nullable Person person);

    /**
     * search all doc records
     *
     * @param index elasticsearch index name
     * @return person list
     * @author fxbin
     */
    List<Person> searchList(String index);

}
