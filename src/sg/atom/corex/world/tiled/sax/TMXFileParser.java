package sg.atom.corex.world.tiled.sax;

import java.io.IOException;
import java.util.Stack;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.*;

/**
 *
 * @author cuong.nguyen
 */
public class TMXFileParser implements DocumentHandler {
    private TMXFileHandler handler;
    private Stack context;
    private StringBuffer buffer;
    private EntityResolver resolver;

    /**
     *
     * Creates a parser instance.
     * @param handler handler interface implementation (never <code>null</code>
     * @param resolver SAX entity resolver implementation or <code>null</code>.
     * It is recommended that it could be able to resolve at least the DTD.
     */
    public TMXFileParser(final TMXFileHandler handler, final EntityResolver resolver) {
        this.handler = handler;
        this.resolver = resolver;
        buffer = new StringBuffer(111);
        context = new java.util.Stack();
    }

    /**
     *
     * This SAX interface method is implemented by the parser.
     */
    public final void setDocumentLocator(Locator locator) {
    }

    /**
     *
     * This SAX interface method is implemented by the parser.
     */
    public final void startDocument() throws SAXException {
    }

    /**
     *
     * This SAX interface method is implemented by the parser.
     */
    public final void endDocument() throws SAXException {
    }

    /**
     *
     * This SAX interface method is implemented by the parser.
     */
    public final void startElement(java.lang.String name, org.xml.sax.AttributeList attrs) throws org.xml.sax.SAXException {
        dispatch(true);
        context.push(new Object[]{name, new org.xml.sax.helpers.AttributeListImpl(attrs)});
        if ("terrain".equals(name)) {
            handler.handle_terrain(attrs);
        } else if ("tileset".equals(name)) {
            handler.start_tileset(attrs);
        } else if ("terraintypes".equals(name)) {
            handler.start_terraintypes(attrs);
        } else if ("tile".equals(name)) {
            handler.handle_tile(attrs);
        } else if ("layer".equals(name)) {
            handler.start_layer(attrs);
        } else if ("map".equals(name)) {
            handler.start_map(attrs);
        } else if ("image".equals(name)) {
            handler.handle_image(attrs);
        }
    }

    /**
     *
     * This SAX interface method is implemented by the parser.
     */
    public final void endElement(java.lang.String name) throws org.xml.sax.SAXException {
        dispatch(false);
        context.pop();
        if ("tileset".equals(name)) {
            handler.end_tileset();
        } else if ("terraintypes".equals(name)) {
            handler.end_terraintypes();
        } else if ("layer".equals(name)) {
            handler.end_layer();
        } else if ("map".equals(name)) {
            handler.end_map();
        }
    }

    /**
     *
     * This SAX interface method is implemented by the parser.
     */
    public final void characters(char[] chars, int start, int len) throws SAXException {
        buffer.append(chars, start, len);
    }

    /**
     *
     * This SAX interface method is implemented by the parser.
     */
    public final void ignorableWhitespace(char[] chars, int start, int len) throws SAXException {
    }

    /**
     *
     * This SAX interface method is implemented by the parser.
     */
    public final void processingInstruction(String target, String data) throws SAXException {
    }

    private void dispatch(final boolean fireOnlyIfMixed) throws SAXException {
        if (fireOnlyIfMixed && buffer.length() == 0) {
            return; //skip it
        }
        Object[] ctx = (Object[]) context.peek();
        String here = (String) ctx[0];
        org.xml.sax.AttributeList attrs = (org.xml.sax.AttributeList) ctx[1];
        if ("data".equals(here)) {
            if (fireOnlyIfMixed) {
                throw new IllegalStateException("Unexpected characters() event! (Missing DTD?)");
            }
            handler.handle_data(buffer.length() == 0 ? null : buffer.toString(), attrs);
        } else {
            //do not care
        }
        buffer.delete(0, buffer.length());
    }

    /**
     *
     * The recognizer entry method taking an InputSource.
     * @param input InputSource to be parsed.
     * @throws java.io.IOException on I/O error
     * @throws org.xml.sax.SAXException propagated exception thrown by a DocumentHandler
     * @throws javax.xml.parsers.ParserConfigurationException a parser satisfying the requested configuration cannot be created
     * @throws javax.xml.parsers.FactoryConfigurationRrror if the implementation cannot be instantiated
     */
    public void parse(final org.xml.sax.InputSource input) throws SAXException, ParserConfigurationException, IOException {
        parse(input, this);
    }

    /**
     *
     * The recognizer entry method taking a URL.
     * @param url URL Source to be parsed.
     * @throws java.io.IOException on I/O error
     * @throws org.xml.sax.SAXException propagated exception thrown by a DocumentHandler
     * @throws javax.xml.parsers.ParserConfigurationException a parser satisfying the requested configuration cannot be created
     * @throws javax.xml.parsers.FactoryConfigurationRrror if the implementation cannot be instantiated
     */
    public void parse(final java.net.URL url) throws SAXException, ParserConfigurationException, IOException {
        parse(new org.xml.sax.InputSource(url.toExternalForm()), this);
    }

    /**
     *
     * The recognizer entry method taking an Inputsource.
     * @param input InputSource to be parsed.
     * @throws java.io.IOException on I/O error
     * @throws org.xml.sax.SAXException propagated exception thrown by a DocumentHandler
     * @throws javax.xml.parsers.ParserConfigurationException a parser satisfying the requested configuration cannot be created
     * @throws javax.xml.parsers.FactoryConfigurationRrror if the implementation cannot be instantiated
     */
    public static void parse(final org.xml.sax.InputSource input, final TMXFileHandler handler) throws SAXException, ParserConfigurationException, IOException {
        parse(input, new TMXFileParser(handler, null));
    }

    /**
     *
     * The recognizer entry method taking a URL.
     * @param url URL source to be parsed.
     * @throws java.io.IOException on I/O error
     * @throws org.xml.sax.SAXException propagated exception thrown by a DocumentHandler
     * @throws javax.xml.parsers.ParserConfigurationException a parser satisfying the requested configuration cannot be created
     * @throws javax.xml.parsers.FactoryConfigurationRrror if the implementation cannot be instantiated
     */
    public static void parse(final java.net.URL url, final TMXFileHandler handler) throws SAXException, ParserConfigurationException, IOException {
        parse(new org.xml.sax.InputSource(url.toExternalForm()), handler);
    }

    private static void parse(final org.xml.sax.InputSource input, final TMXFileParser recognizer) throws SAXException, ParserConfigurationException, IOException {
        javax.xml.parsers.SAXParserFactory factory = javax.xml.parsers.SAXParserFactory.newInstance();
        factory.setValidating(true); //the code was generated according DTD
        factory.setNamespaceAware(false); //the code was generated according DTD
        Parser parser = factory.newSAXParser().getParser();
        parser.setDocumentHandler(recognizer);
        parser.setErrorHandler(recognizer.getDefaultErrorHandler());
        if (recognizer.resolver != null) {
            parser.setEntityResolver(recognizer.resolver);
        }
        parser.parse(input);
    }

    /**
     *
     * Creates default error handler used by this parser.
     * @return org.xml.sax.ErrorHandler implementation
     */
    protected ErrorHandler getDefaultErrorHandler() {
        return new ErrorHandler() {
            public void error(SAXParseException ex) throws SAXException {
                if (context.isEmpty()) {
                    System.err.println("Missing DOCTYPE.");
                }
                throw ex;
            }

            public void fatalError(SAXParseException ex) throws SAXException {
                throw ex;
            }

            public void warning(SAXParseException ex) throws SAXException {
                // ignore
            }
        };
    }
    
}
