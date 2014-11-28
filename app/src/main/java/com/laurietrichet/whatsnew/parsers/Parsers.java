package com.laurietrichet.whatsnew.parsers;

import java.util.EnumMap;
import java.util.Map;

/**
 * Factory to get a parser to parse rss stream
 */
public final class Parsers {

    public enum PARSER_TYPE {RSS};

    private static final Map <PARSER_TYPE, IParser> parserMap =
            new EnumMap<PARSER_TYPE, IParser>(PARSER_TYPE.class);

    static {
        try {
            parserMap.put(PARSER_TYPE.RSS,new RssParser());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Return the parser according to the argument
     * @param parserType {@link com.laurietrichet.whatsnew.parsers.Parsers.PARSER_TYPE}s
     * @return IParser
     * @throws NullPointerException when the parser is not properly set
     */

    public static IParser getParser (PARSER_TYPE parserType) throws NullPointerException{
        IParser parser = parserMap.get(parserType);
        if (parser == null){
            throw new NullPointerException("Parser is not set, see your IParser implementation");
        }
        return parser;
    }
}
