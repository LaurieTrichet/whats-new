package com.laurietrichet.whatsnew.core.data.accessors;

/**
 * Created by laurie on 30/10/2014.
 * Describes the accessor to access model object
 */
public interface IDataAccessor {

    /**
     * implement here code to retrieve all available objects
     */
    void getAll();


    /**
     * implement here code to retrieve a specific object
     * @param id id of the object to be retrieved
     */
    void get (String id);
}
