package sg.atom.corex.ui.tonegod.layouts;

import com.google.common.base.Function;
import com.google.common.base.Supplier;
import com.google.common.collect.BiMap;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Ordering;
import com.google.common.collect.TreeTraverser;
import com.google.common.eventbus.EventBus;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector4f;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import tonegod.gui.core.Element;
import tonegod.gui.core.Screen;

/**
 * QLayout is Helper for Layout operations in Query style.
 *
 * Concepts: Layout, LayoutData, LayoutBuilder, LayoutEvent
 *
 * Target: Element, Screen.
 *
 * Create common layouts via DSL.
 *
 * Create base and placeholder elements.
 *
 * @author CuongNguyen
 */
public class QLayout {

    protected Ordering order;
    protected TreeTraverser<Element> elementTreeTraverser;
    protected TreeTraverser<LayoutData> layoutTreeTraverser;
    protected EventBus eventBus;
    protected boolean disodered = false;
    protected boolean allowDisordered = false;
    protected Screen screen;
    protected Element topMostElement;
    protected boolean smartActive = true;
    HashMap<Element, LayoutData> layoutDatas = new HashMap<Element, LayoutData>();
//    public static QLayout $;
    protected static ListLayoutBuilder listLayoutBuilder;
    protected static LayoutBuilder layoutBuilder;

    public QLayout(Screen screen) {
        this.screen = screen;
        this.eventBus = new EventBus("LayoutManager");
        this.layoutDatas = new LinkedHashMap<Element, LayoutData>();
    }

    public QLayout(Element topMost) {
        this.topMostElement = topMost;
        this.eventBus = new EventBus("LayoutManager");
    }

    public QLayout(Screen screen, Element topMostElement, EventBus eventBus) {
        this.eventBus = eventBus;
        this.screen = screen;
        this.topMostElement = topMostElement;
    }

    /**
     * LayoutBuilder build LayoutData for single element.
     */
    public static class LayoutBuilder implements Supplier<LayoutData> {

        protected Element element;
        protected Element container;
        protected LayoutData item;
        protected QLayout layout;
        protected static LayoutData global;

        public LayoutBuilder() {
            item = new LayoutData();
        }

        public LayoutBuilder(LayoutData newData) {
            item = newData;
        }

        public LayoutBuilder(Element element, QLayout layout, LayoutData item) {
            this();
            this.element = element;
//            this.item = item;
            //Clone the item
        }

        public LayoutBuilder(Element element, Element container, QLayout layout) {
            this();
            this.element = element;
            this.container = container;
            this.layout = layout;

        }

        public LayoutBuilder(Element element, QLayout layout) {
            this();
            this.element = element;
            this.layout = layout;
        }

        //Pos------------------------------------------------------------------
        public LayoutBuilder pos(Vector2f pos) {
            item.pos.set(pos);
            return this;
        }

        public LayoutBuilder pos(Vector2f pos, int unit) {
            item.pos.set(pos);
            item.posUnit = unit;
            return this;
        }

        public LayoutBuilder pos(float x, float y) {
            item.pos.set(x, y);
            return this;
        }

        public LayoutBuilder pos(float x, float y, int unit) {
            item.pos.set(x, y);
            item.posUnit = unit;
            return this;
        }

        public LayoutBuilder fill() {
            item.pos.set(0, 0);
            item.posUnit = LayoutData.UNIT_PERCENT;
            item.size.set(100, 100);
            return this;
        }

        public LayoutBuilder autoSize() {
            return this;
        }

        public LayoutBuilder alignCenter(float x) {
            item.pos.x = (100 - x) / 2;
            return this;
        }

        public LayoutBuilder vAlignCenter(float y) {
            item.pos.y = (100 - y) / 2;
            return this;
        }
        //Size------------------------------------------------------------------

        public LayoutBuilder size(Vector2f size) {
            item.pos.set(size);
            return this;
        }

        public LayoutBuilder size(Vector2f size, int unit) {
            item.pos.set(size);
            item.sizeUnit = unit;
            return this;
        }

        public LayoutBuilder size(float x, float y) {
            item.size.set(x, y);
            return this;
        }

        public LayoutBuilder size(float x, float y, int unit) {
            item.size.set(x, y);
            item.sizeUnit = unit;
            return this;
        }

        public LayoutBuilder unit(int unit1) {
            item.sizeUnit = unit1;
            item.posUnit = unit1;
            return this;
        }

        public LayoutBuilder convert() {
            if (element != null) {
                item.size.set(element.getDimensions());
                item.pos.set(element.getPosition());
            }
            return this;
        }

        public LayoutBuilder convertSize(int unit1, int unit2) {
            if (element != null) {
                item.size.set(element.getDimensions());
            }
            item.sizeUnit = unit2;
            return this;
        }
//Static methods----------------------------------------------------------------

        public static void pos(LayoutData item, Vector2f pos) {
            item.pos.set(pos);
        }

        public static void pos(LayoutData item, Vector2f pos, int unit) {
            item.pos.set(pos);
            item.posUnit = unit;
        }

        public static void pos(LayoutData item, float x, float y) {
            item.pos.set(x, y);
        }

        public static void pos(LayoutData item, float x, float y, int unit) {
            item.pos.set(x, y);
            item.posUnit = unit;
        }

        public static void size(LayoutData item, Vector2f size) {
            item.pos.set(size);
        }

        public static void size(LayoutData item, Vector2f size, int unit) {
            item.pos.set(size);
            item.sizeUnit = unit;
        }

        public static void size(LayoutData item, float x, float y) {
            item.size.set(x, y);
        }

        public static void size(LayoutData item, float x, float y, int unit) {
            item.size.set(x, y);
            item.sizeUnit = unit;
        }
        // Global switch -------------------------------------------------------
//        public LayoutBuilder left() {
//            return this;
//        }
//
//        public LayoutBuilder right() {
//            return this;
//        }

//        public LayoutBuilder unitPercentage() {
//            return this;
//        }
//
//        public LayoutBuilder unitPixel() {
//            return this;
//        }
        public LayoutData build() {
            if (element != null && layout != null) {
                if (container == null) {
                    layout.$(element, item);
                } else {
                    container.addChild(element);
                    layout.$(element, item, container);
                }
            }
            return item;
        }

        public LayoutData get() {
            return build();
        }

        public void set() {
            build();
            if (layout != null) {
                layout.getEventBus().post(new LayoutEvent(item));
            }
        }

        public void set(Element container) {
            this.container = container;
            set();
        }
        //Hierarchy------------------------------------------------------------------

        public LayoutBuilder addTo(Screen screen) {
            screen.addElement(element);
            container = null;
            return this;
        }

        public LayoutBuilder addTo(Element container) {
            container.addChild(element);
            this.container = container;
            return this;
        }

        public LayoutBuilder link(LayoutData parent, LayoutData child) {
            return this;
        }
        //Additional -----------------------------------------------------------

        public LayoutBuilder text(String string) {
            element.setText(string);
            return this;
        }

        public Element getElement() {
            return element;
        }

        public QLayout getLayout() {
            return layout;
        }

        public Element getContainer() {
            return container;
        }
    }

    /**
     * ListLayoutBuilder build LayoutData for series of Elements.
     */
    public static class ListLayoutBuilder {

        public int _index;
        List<Element> elements;
        List<LayoutData> items;
        BiMap<Element, LayoutBuilder> builderMap;
        QLayout layout;
        static LayoutData global;
//        ListLayoutBuilder $$;
        Element container;

        protected ListLayoutBuilder(Element[] elements, QLayout layout) {
            this.layout = layout;
            this.elements = Arrays.asList(elements);
            this.items = new ArrayList<LayoutData>();
            for (Element element : elements) {
                LayoutData item = new LayoutData();
                items.add(item);
            }
        }

        protected ListLayoutBuilder(LayoutBuilder[] lbs, QLayout layout) {
            this.layout = layout;
            this.elements = new ArrayList<Element>(lbs.length);
            this.items = new ArrayList<LayoutData>(lbs.length);
            for (LayoutBuilder lb : lbs) {
                elements.add(lb.element);
                items.add(lb.item);
            }
        }

        public List<LayoutBuilder> toBuilderList() {
            return Lists.transform(ImmutableList.copyOf(elements), new Function<Element, LayoutBuilder>() {
                public LayoutBuilder apply(Element element) {
                    return new LayoutBuilder(element, layout);
                }
            });
        }

        //Pos------------------------------------------------------------------
        public ListLayoutBuilder cellPos(Vector2f pos) {
            for (LayoutData item : items) {
                item.pos.set(pos);
            }
            return this;
        }

        public ListLayoutBuilder cellPos(Vector2f pos, int unit) {
            for (LayoutData item : items) {
                item.pos.set(pos);
                item.posUnit = unit;
            }
            return this;
        }

        public ListLayoutBuilder cellPos(float x, float y) {
            for (LayoutData item : items) {
                item.pos.set(x, y);
            }
            return this;
        }

        public ListLayoutBuilder cellPos(float x, float y, int unit) {
            for (LayoutData item : items) {

                item.pos.set(x, y);
                item.posUnit = unit;
            }
            return this;
        }

        public ListLayoutBuilder cellPos(int index, float x, float y, int unit) {
            LayoutData item = items.get(index);
            item.pos.set(x, y);
            item.posUnit = unit;

            return this;
        }

        //Pos with padding
        public ListLayoutBuilder posInc(float x, float y) {
            return posInc(x, y, 0, 0, LayoutData.UNIT_PIXEL);
        }

        public ListLayoutBuilder posInc(float x, float y, int unit) {
            return posInc(x, y, 0, 0, unit);
        }

        public ListLayoutBuilder posInc(float x, float y, float padx, float pady) {

            return posInc(x, y, padx, pady, LayoutData.UNIT_PIXEL);
        }

        public ListLayoutBuilder posInc(float x, float y, float padx, float pady, int unit) {
            if (items.isEmpty()) {
                return this;
            }
            LayoutData first = items.get(0);
            for (LayoutData item : items) {
                int index = items.indexOf(item);
                item.pos.set(first.pos.x + (padx + x) * index, first.pos.y + (pady + y) * index);
                item.posUnit = unit;
            }
            return this;
        }
        //Size------------------------------------------------------------------

        public ListLayoutBuilder cellSize(Vector2f size) {
            for (LayoutData item : items) {
                item.pos.set(size);
            }
            return this;
        }

        public ListLayoutBuilder cellSize(Vector2f size, int unit) {
            for (LayoutData item : items) {
                item.pos.set(size);
                item.sizeUnit = unit;
            }
            return this;
        }

        public ListLayoutBuilder cellSize(float x, float y) {
            for (LayoutData item : items) {
                item.size.set(x, y);
            }
            return this;
        }

        public ListLayoutBuilder cellSize(float x, float y, int unit) {
            for (LayoutData item : items) {
                item.size.set(x, y);
                item.sizeUnit = unit;
            }
            return this;
        }
        //Hierachy--------------------------------------------------------------

        public ListLayoutBuilder addTo(Screen screen) {
            for (Element element : elements) {
                screen.addElement(element);
                container = null;
            }
            return this;
        }

        public ListLayoutBuilder addTo(Element container) {
            for (Element element : elements) {
                container.addChild(element);
                this.container = container;
            }
            return this;
        }
        //Build-----------------------------------------------------------------

        public void build() {
            for (Element element : elements) {
                int index = elements.indexOf(element);
                LayoutData item = items.get(index);
                if (element != null && layout != null) {
                    if (container == null) {
                        layout.$(element, item);
                    } else {
                        container.addChild(element);
                        layout.$(element, item, container);
                    }
                }
            }

        }

        public void set() {
            build();
        }
        //Branching-------------------------------------------------------------

        public LayoutBuilder _get(int index) {
            return new LayoutBuilder(items.get(index));
        }

        //Additional -----------------------------------------------------------
        public ListLayoutBuilder text(String string) {
            for (Element element : elements) {
                element.setText(string);
            }
            return this;
        }

        public ListLayoutBuilder text(String... string) {
            for (Element element : elements) {
                int index = elements.indexOf(element);
                element.setText(string[index]);
            }
            return this;
        }
    }

    public static LayoutBuilder create() {
        return new LayoutBuilder();

    }

    public LayoutData $(String style) {
        return new LayoutData();
    }

    public void $(Element element, LayoutData layoutData) {
        this.layoutDatas.put(element, layoutData);
//        layoutData.element = element;
    }

    public LayoutBuilder $(Element element) {
        QLayout.layoutBuilder = new LayoutBuilder(element, this);
        return layoutBuilder;
    }

    public ListLayoutBuilder $(Element... elements) {
        QLayout.listLayoutBuilder = new ListLayoutBuilder(elements, this);
        return listLayoutBuilder;
    }

    public LayoutBuilder $(Element element, Element container) {
        return new LayoutBuilder(element, container, this);
    }

    public void $(Element element, LayoutData layoutData, LayoutData containerLayoutData) {
        $(element, layoutData);
        layoutData.linked = containerLayoutData;

    }

    public void $(Element element, LayoutData layoutData, Element container) {
        //Assume that there is a link
        $(element, layoutData);
        LayoutData containerLayoutData = layoutDatas.get(container);

        if (containerLayoutData == null) {
            containerLayoutData = new LayoutData();
            layoutDatas.put(container, containerLayoutData);

            //Mark as dis-ordered operations?
        } else {
            layoutData.linked = containerLayoutData;
        }

    }

    public LayoutBuilder $() {
        return new LayoutBuilder();
    }

    /**
     * <p>Layout data should be resolve with aligment and containment
     * informations. The order usually are not easy to describle. The resolving
     * progress also not just top-down or bottom up only but mixed. So at first
     * it will try to travel from the root to get fixed attributes (size,
     * aligment) of the container which can be lend to calculate the attributes
     * of its children. If the calculating require some deep travel along the
     * hierarchy to complete, the linkage will be save and result will be
     * propagate through event mechnism.
     *
     * @param element
     * @param layoutData
     */
    public void layout(Element element, LayoutData layoutData) {
        if (layoutData.posUnit == LayoutData.UNIT_PIXEL) {
            element.setPosition(layoutData.getPos());
            element.setDimensions(layoutData.getSize());
        } else if (layoutData.posUnit == LayoutData.UNIT_PERCENT) {
            //Relative unit
            if (layoutData.linked == null) {
                Element elementParent = element.getElementParent();
                Vector2f pSize;
                if (elementParent != null) {
                    pSize = elementParent.getDimensions();

                } else {
                    pSize = new Vector2f(screen.getWidth(), screen.getHeight());
                }
                Vector2f cPos = layoutData.getPos().mult(0.01f).multLocal(pSize);
                Vector2f cSize = layoutData.getSize().mult(0.01f).multLocal(pSize);
                element.setPosition(cPos);
                element.setDimensions(cSize);
                System.out.println(" " + cPos + " " + cSize);
            } else {
            }
        }

    }
    //ordering
    Ordering<Element> naturalOrder = new Ordering<Element>() {
        @Override
        public int compare(Element left, Element right) {
            return 0;
        }
    };

    /**
     * Refresh layout is a progress of arrange element with its layout data in
     * an ordering.
     *
     */
    public void refreshLayout() {
        order = Ordering.natural();
        List<Element> sortedElements = ImmutableList.copyOf(layoutDatas.keySet());

        for (Element element : sortedElements) {
            System.out.println(" " + element.getUID() + " ->" + layoutDatas.get(element).toString());
            layout(element, layoutDatas.get(element));
        }
    }

    public void active(Screen screen) {
        //Find containment info.
        this.screen = screen;
        refreshLayout();
    }

    public void smartActive(Element element) {
    }

    public EventBus getEventBus() {
        return eventBus;
    }
}
