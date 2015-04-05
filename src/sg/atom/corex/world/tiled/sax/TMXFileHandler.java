package sg.atom.corex.world.tiled.sax;


import org.xml.sax.AttributeList;
import org.xml.sax.SAXException;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author cuong.nguyen
 */
public interface TMXFileHandler {

    /**
     *
     * An empty element event handling method.
     * @param data value or null
     */
    public void handle_terrain(final AttributeList meta) throws SAXException;

    /**
     *
     * A container element start event handling method.
     * @param meta attributes
     */
    public void start_tileset(final AttributeList meta) throws SAXException;

    /**
     *
     * A container element end event handling method.
     */
    public void end_tileset() throws SAXException;

    /**
     *
     * A container element start event handling method.
     * @param meta attributes
     */
    public void start_terraintypes(final AttributeList meta) throws SAXException;

    /**
     *
     * A container element end event handling method.
     */
    public void end_terraintypes() throws SAXException;

    /**
     *
     * An empty element event handling method.
     * @param data value or null
     */
    public void handle_tile(final AttributeList meta) throws SAXException;

    /**
     *
     * A data element event handling method.
     * @param data value or null
     * @param meta attributes
     */
    public void handle_data(final String data, final AttributeList meta) throws SAXException;

    /**
     *
     * A container element start event handling method.
     * @param meta attributes
     */
    public void start_layer(final AttributeList meta) throws SAXException;

    /**
     *
     * A container element end event handling method.
     */
    public void end_layer() throws SAXException;

    /**
     *
     * A container element start event handling method.
     * @param meta attributes
     */
    public void start_map(final AttributeList meta) throws SAXException;

    /**
     *
     * A container element end event handling method.
     */
    public void end_map() throws SAXException;

    /**
     *
     * An empty element event handling method.
     * @param data value or null
     */
    public void handle_image(final AttributeList meta) throws SAXException;
    
}
