package com.laurietrichet.whatsnew.parsers;

/**
 * Created by laurie on 22/11/14.
 * Interface to specify parsing contract
 */
public interface IParser <T> {
    /**
     * Parse the input and return an object
     *
     * @param input
     * @return T object resulting of the parsing
     * @throws Exception when an issue occured during parsing
     */
    T parse (String input) throws Exception;

}
