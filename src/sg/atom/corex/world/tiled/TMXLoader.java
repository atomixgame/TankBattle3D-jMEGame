package sg.atom.corex.world.tiled;

import sg.atom.corex.world.tile.TileSetDefinition;
import sg.atom.corex.world.tile.MapStructure;
import sg.atom.corex.world.tile.LayerDefinition;
import com.google.common.io.BaseEncoding;
import com.jme3.asset.AssetInfo;
import com.jme3.asset.AssetLoader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.CharBuffer;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.GZIPInputStream;
import java.util.zip.InflaterInputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


/**
 * Loads a TMX file.
 *
 * @author miguel
 *
 */
public class TMXLoader implements AssetLoader {

    private static Logger logger = Logger.getLogger(TMXLoader.class.getName());
    private MapStructure mapStructure;
    private String xmlPath;

    private static String makeUrl(final String filename) throws MalformedURLException {
        final String url;
        if ((filename.indexOf("://") > -1) || filename.startsWith("file:")) {
            url = filename;
        } else {
            url = (new File(filename)).toURI().toURL().toString();
        }
        return url;
    }

    private static String getAttributeValue(final Node node, final String attribname) {
        final NamedNodeMap attributes = node.getAttributes();
        String att = null;
        if (attributes != null) {
            final Node attribute = attributes.getNamedItem(attribname);
            if (attribute != null) {
                att = attribute.getNodeValue();
            }
        }
        return att;
    }

    private static int getAttribute(final Node node, final String attribname, final int def) {
        final String attr = getAttributeValue(node, attribname);
        if (attr != null) {
            return Integer.parseInt(attr);
        } else {
            return def;
        }
    }

    private TileSetDefinition unmarshalTileset(final Node domNode) throws Exception {
        final String name = getAttributeValue(domNode, "name");
        final int firstGid = getAttribute(domNode, "firstgid", 1);

        final TileSetDefinition set = new TileSetDefinition(name, firstGid);
        set.tileWidth = getAttribute(domNode, "tilewidth", 32);
        set.tileHeight = getAttribute(domNode, "tileheight", 32);
        final boolean hasTilesetImage = false;
        final NodeList children = domNode.getChildNodes();

        for (int i = 0; i < children.getLength(); i++) {
            final Node child = children.item(i);

            if (child.getNodeName().equalsIgnoreCase("image")) {
                if (hasTilesetImage) {
                    continue;
                }

                set.setSource(getAttributeValue(child, "source"));
                set.sourceWidth = getAttribute(child, "width", 100);
                set.sourceHeight = getAttribute(child, "height", 100);
                set.numY = set.sourceHeight / set.tileHeight;
                set.numX = set.sourceWidth / set.tileWidth;
                set.numOfTiles = set.numX * set.numY;

            }
        }

        return set;
    }

    /**
     * Reads properties from amongst the given children. When a "properties"
     * element is encountered, it recursively calls itself with the children of
     * this node. This function ensures backward compatibility with tmx version
     * 0.99a.
     *
     * @param children the children amongst which to find properties
     * @param props the properties object to set the properties of
     */
    @SuppressWarnings("unused")
    private static void readProperties(final NodeList children, final Properties props) {
        for (int i = 0; i < children.getLength(); i++) {
            final Node child = children.item(i);
            if ("property".equalsIgnoreCase(child.getNodeName())) {
                props.setProperty(getAttributeValue(child, "name"),
                        getAttributeValue(child, "value"));
            } else if ("properties".equals(child.getNodeName())) {
                readProperties(child.getChildNodes(), props);
            }
        }
    }

    /**
     * Loads a map layer from a layer node.
     *
     * @param domNode
     * @return the layer definition for the node
     * @throws Exception
     */
    private LayerDefinition readLayer(final Node domNode) throws Exception {
        final int layerWidth = getAttribute(domNode, "width", mapStructure.width);
        final int layerHeight = getAttribute(domNode, "height", mapStructure.height);

        final LayerDefinition layer = new LayerDefinition(layerWidth, layerHeight);

        final int offsetX = getAttribute(domNode, "x", 0);
        final int offsetY = getAttribute(domNode, "y", 0);

        if ((offsetX != 0) || (offsetY != 0)) {
            System.err.println("Severe error: maps has offset displacement");
        }

        layer.setName(getAttributeValue(domNode, "name"));

        for (Node child = domNode.getFirstChild(); child != null; child = child.getNextSibling()) {
            if ("data".equalsIgnoreCase(child.getNodeName())) {
                final String encoding = getAttributeValue(child, "encoding");

                if ((encoding != null) && "base64".equalsIgnoreCase(encoding)) {
                    final Node cdata = child.getFirstChild();
                    if (cdata != null) {
                        final char[] enc = cdata.getNodeValue().trim().toCharArray();
                        final byte[] dec = BaseEncoding.base64().decode(new String(enc));
                        final ByteArrayInputStream bais = new ByteArrayInputStream(
                                dec);
                        InputStream is;

                        final String comp = getAttributeValue(child, "compression");

                        if ("gzip".equalsIgnoreCase(comp)) {
                            is = new GZIPInputStream(bais);
                        } else if ("zlib".equalsIgnoreCase(comp)) {
                            is = new InflaterInputStream(bais);
                        } else {
                            is = bais;
                        }

                        final byte[] raw = layer.exposeRaw();
                        int offset = 0;

                        while (offset != raw.length) {
                            offset += is.read(raw, offset, raw.length - offset);
                        }

                        bais.close();
                    }
                }
            }
        }

        return layer;
    }

    private void buildMap(final Document doc) throws Exception {
        Node mapNode = doc.getDocumentElement();

        if (!"map".equals(mapNode.getNodeName())) {
            throw new Exception("Not a valid tmx map file.");
        }

        // Get the map dimensions and create the map
        final int mapWidth = getAttribute(mapNode, "width", 0);
        final int mapHeight = getAttribute(mapNode, "height", 0);

        if ((mapWidth > 0) && (mapHeight > 0)) {
            mapStructure = new MapStructure(mapWidth, mapHeight);
        }

        if (mapStructure == null) {
            throw new Exception("Couldn't locate map dimensions.");
        }

        // Load the tilesets, properties, layers and objectgroups
        for (Node sibs = mapNode.getFirstChild(); sibs != null; sibs = sibs.getNextSibling()) {
            if ("tileset".equals(sibs.getNodeName())) {
                mapStructure.addTileset(unmarshalTileset(sibs));
            } else if ("layer".equals(sibs.getNodeName())) {
                mapStructure.addLayer(readLayer(sibs));
            }
        }
    }

    private MapStructure unmarshal(final InputStream in) throws Exception {
        final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        Document doc;
        try {
            factory.setIgnoringComments(true);
            factory.setIgnoringElementContentWhitespace(true);
            factory.setExpandEntityReferences(false);

            // Xerces normally tries to retrieve the dtd, even when it's not used - and 
            // dies if it fails. 
            try {
                factory.setAttribute("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
            } catch (final IllegalArgumentException e) {
                //logger.warning(e, e);
            }

            final DocumentBuilder builder = factory.newDocumentBuilder();
            doc = builder.parse(in, xmlPath);
        } catch (final SAXException e) {
            e.printStackTrace();
            throw new Exception("Error while parsing map file: " + e.toString());
        }

        buildMap(doc);
        return mapStructure;
    }

    public MapStructure readMap(final String filename) throws Exception {
        xmlPath = filename.substring(0,
                filename.lastIndexOf(File.separatorChar) + 1);

        InputStream is = getClass().getClassLoader().getResourceAsStream(
                filename);

        if (is == null) {
            final String xmlFile = makeUrl(filename);
            // xmlPath = makeUrl(xmlPath);

            final URL url = new URL(xmlFile);
            is = url.openStream();
        }


        // Wrap with GZIP decoder for .tmx.gz files
        if (filename.endsWith(".gz")) {
            is = new GZIPInputStream(is);
        }

        return readMap(is);
    }

    public MapStructure readMap(InputStream is) throws Exception {
        return unmarshal(is);
    }

    public static MapStructure load(final String filename) throws Exception {
        return new TMXLoader().readMap(filename);
    }

    public Object load(AssetInfo assetInfo) throws IOException {
        try {
            return readMap(assetInfo.openStream());
        } catch (Exception ex) {
            Logger.getLogger(TMXLoader.class.getName()).log(Level.SEVERE, null, ex);
            throw new IOException("Can not load TMX file" + assetInfo.getKey().getName());
        }
    }
}
