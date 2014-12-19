package com.laurietrichet.whatsnew.data;

/**
 * Created by laurie on 30/10/2014.
 * Describes the accessor to access model object
 */
public interface IDataAccessor {

    /**
     * Defines signature methods that are called when the data request is finished
     * @param <T>
     */
    public interface DataAccessorListener <T>{

        /**
         * give the obj result to process
         * @param obj The object that was requested
         */
        public void onSuccess(T obj);


        /**
         * get the error that has occur during data retrieving
         * @param error The error that has occurred during the request
         */
        public void onError(Error error);
    }

    /**
     * implement here code to retrieve all available objects
     * @param listener listener to pass to get the result
     */
    public void getAll(DataAccessorListener listener);


    /**
     * implement here code to retrieve a specific object
     * @param id id of the object to be retreived
     * @param listener listener to pass to get the result
     */
    public void get (String id, DataAccessorListener listener);
}
