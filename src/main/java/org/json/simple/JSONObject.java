/*
 * $Id: JSONObject.java,v 1.1 2006/04/15 14:10:48 platform Exp $
 * Created on 2006-4-10
 */
package org.json.simple;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * A JSON object. Key value pairs are unordered. JSONObject supports java.util.Map interface.
 *
 * @author FangYidong<fangyidong@yahoo.com.cn>
 */
@SuppressWarnings("ALL")
public class JSONObject extends HashMap<String, Object> implements Map<String, Object>, JSONAware, JSONStreamAware
{

    private static final long serialVersionUID = -503443796854799292L;


    public JSONObject()
    {
        super();
    }

    /**
     * Allows creation of a JSONObject from a Map. After that, both the
     * generated JSONObject and the Map can be modified independently.
     *
     * @param map
     */
    public JSONObject(final Map<String, Object> map)
    {
        super( map );
    }


    /**
     * Encode a map into JSON text and write it to out.
     * If this map is also a JSONAware or JSONStreamAware, JSONAware or JSONStreamAware specific behaviours will be ignored at this top level.
     *
     * @param map
     * @param out
     * @see org.json.simple.JSONValue#writeJSONString(Object, Writer)
     */
    public static void writeJSONString(final Map<String, Object> map, final Writer out) throws IOException
    {
        if ( map == null )
        {
            out.write( "null" );
            return;
        }

        boolean first = true;
        Iterator<Map.Entry<String, Object>> iter = map.entrySet().iterator();

        out.write( '{' );
        while ( iter.hasNext() )
        {
            if ( first )
                first = false;
            else
                out.write( ',' );
            Map.Entry entry = (Map.Entry) iter.next();
            out.write( '\"' );
            out.write( escape( String.valueOf( entry.getKey() ) ) );
            out.write( '\"' );
            out.write( ':' );
            JSONValue.writeJSONString( entry.getValue(), out );
        }
        out.write( '}' );
    }

    /**
     * Convert a map to JSON text. The result is a JSON object.
     * If this map is also a JSONAware, JSONAware specific behaviours will be omitted at this top level.
     *
     * @param map
     * @return JSON text, or "null" if map is null.
     * @see org.json.simple.JSONValue#toJSONString(Object)
     */
    public static String toJSONString(final Map<String, Object> map)
    {
        final StringWriter writer = new StringWriter();

        try
        {
            writeJSONString( map, writer );
            return writer.toString();
        } catch ( IOException e )
        {
            // This should never happen with a StringWriter
            throw new RuntimeException( e );
        }
    }

    public static String toString(final String key, final Object value)
    {
        StringBuilder sb = new StringBuilder( "\"" );
        if ( key == null )
            sb.append( "null" );
        else
            JSONValue.escape( key, sb );
        sb.append( '\"' ).append( ':' );

        sb.append( JSONValue.toJSONString( value ) );

        return sb.toString();
    }

    /**
     * Escape quotes, \, /, \r, \n, \b, \f, \t and other control characters (U+0000 through U+001F).
     * It's the same as JSONValue.escape() only for compatibility here.
     *
     * @param s
     * @return
     * @see org.json.simple.JSONValue#escape(String)
     */
    public static String escape(final String s)
    {
        return JSONValue.escape( s );
    }

    public void writeJSONString(final Writer out) throws IOException
    {
        writeJSONString( this, out );
    }

    public String toJSONString()
    {
        return toJSONString( this );
    }

    public String toString()
    {
        return toJSONString();
    }
}
