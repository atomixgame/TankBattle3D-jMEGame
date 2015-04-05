/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.corex.entity;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.base.Stopwatch;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.MapMaker;
import com.google.common.eventbus.EventBus;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;
import org.apache.commons.configuration.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sg.atom.core.AtomMain;
import sg.atom.core.lifecycle.AbstractManager;
import sg.games.tank.entities.factories.GameEntityFactory;

/**
 * An simple EntityManager implementation which have basic Spatial - Entity
 * relationship management.
 *
 * <ul>
 *
 * <li>It has a Cache implementation of original entities beside of one in
 * AssetManager.</li>
 *
 * @author atomix
 */
@Deprecated
public class EntityManager extends AbstractManager {

    public static final Logger logger = LoggerFactory.getLogger(EntityManager.class.getName());
    protected EntityFactory entityFactory;
    // Entity management 
    protected ConcurrentMap<Long, Entity> entities = new MapMaker()
            .concurrencyLevel(10)
            .weakKeys()
            .weakValues().makeMap();
    protected HashMap<String, Node> nodes = new HashMap<String, Node>();
    private long totalEntityId = -1;
    public static long DEFAULT_NONE_ID = SpatialEntity.DEFAULT_NONE_ID;
    // Services
    private final EventBus eventBus;
    private final Stopwatch stopwatch;

    public EntityManager(AtomMain app) {
        super(app);
        this.eventBus = new EventBus("EntityManager");
        this.stopwatch = new Stopwatch();
        this.customCycle = true;
    }

    public static class EntityEvent {

        long timeStamp;
        Entity entity;
        long entityId;

        public EntityEvent(Entity e) {
            this.entity = e;
            this.entityId = e.getIid();
        }

        public EntityEvent(Entity e, long timeStamp) {
            this.timeStamp = timeStamp;
            this.entity = e;
            this.entityId = e.getIid();
        }

        public Entity getEntity() {
            return entity;
        }

        public long getEntityId() {
            return entityId;
        }

        public void setTimeStamp(long timeStamp) {
            this.timeStamp = timeStamp;
        }
    }

    public static class EntityAddEvent extends EntityEvent {

        public EntityAddEvent(Entity e) {
            super(e);
        }

        public EntityAddEvent(Entity e, long timeStamp) {
            super(e, timeStamp);
        }
    }
//    public static class EntityPersistEvent{
//        
//    }

    public static class EntityRemovalEvent extends EntityEvent {

        public EntityRemovalEvent(Entity e, long timeStamp) {
            super(e, timeStamp);
        }

        public EntityRemovalEvent(Entity e) {
            super(e);
        }
    }
//    public static class EntityRemoteEvent {
//        
//    }
    /* Manage entities's type as primary lookup methods */

    public void registerEntityType() {
    }

    public void registerEntityTypes() {
    }

    public ArrayList<String> getEntityAssets() {
        return new ArrayList<String>();
    }

    public long getNewEntityId() {
        totalEntityId++;
        return totalEntityId;
    }

    public void addEntity(SpatialEntity e) {
        if (e.iid == DEFAULT_NONE_ID) {
            Long newId = getNewEntityId();
            e.iid = newId;
        }
        entities.put(e.iid, e);
        eventBus.post(new EntityAddEvent(e, stopwatch.elapsed(TimeUnit.MICROSECONDS)));
    }

    public Entity removeEntity(Long id) {
        Entity e = entities.remove(id);
        eventBus.post(new EntityRemovalEvent(e, stopwatch.elapsed(TimeUnit.MICROSECONDS)));
        return e;
    }

    public Entity removeEntity(Entity e) {
        return removeEntity(e.getIid());
    }

    public boolean isEntitySpatial(Spatial selectableSpatial) {
        return true;
    }

    public boolean isHasNoId(SpatialEntity entity) {
        return entity.iid == SpatialEntity.DEFAULT_NONE_ID;
    }
    /* Search and filter over entities */

    public ArrayList<SpatialEntity> getAllSpatialEntities() {
        // do filter...
        ArrayList<SpatialEntity> result = new ArrayList<SpatialEntity>();
        for (Entity entity : entities.values()) {
            if (entity instanceof SpatialEntity) {
                result.add((SpatialEntity) entity);
            }
        }
        //
        return result;
    }

    public ArrayList<SpatialEntity> getAllSpatialEntitiesByGroup(String groupName) {
        // do filter...
        ArrayList<SpatialEntity> result = new ArrayList<SpatialEntity>();

        for (Entity entity : entities.values()) {
            //System.out.println(" ByGroup " + entity.id);
//            if (entity instanceof SpatialEntity) {
//                if (entity.getGroup().equals(groupName)) {
//                    result.add((SpatialEntity) entity);
//                }
//            }
        }
        //
        return result;
    }

    public <T extends Entity> ArrayList<T> getEntitiesByClass(Class<T> clazz) {
        // do filter...
        ArrayList<T> result = new ArrayList<T>();
        for (Entity entity : entities.values()) {
            if (clazz.isAssignableFrom(entity.getClass())) {
                result.add((T) entity);
            }
        }
        //
        return result;
    }
    //Cycle--------------------------------------------------------------------

    public void init() {
        this.entityFactory = new GameEntityFactory(app);
        app.getStateManager().attach(this);
        this.setEnabled(true);
    }

    public void load() {
    }

    public void config(Configuration props) {
    }

    @Override
    public void update(float tpf) {
        if (actived) {
            if (!customCycle) {
                //For sometime we will require a consist view.
                Iterator<Entity> iterator = entities.values().iterator();
                while (iterator.hasNext()) {
                    Entity entity = iterator.next();
                    //FIXME: Only deal with spatial entity
                    if (entity instanceof SpatialEntity) {
                        ((SpatialEntity) entity).update(tpf);
                    }
                }
            }
        }
    }

    public void finish() {
    }
    //GETTER & SETTER
    // For Collections of Entities!-----------------------------------------

    public static List<Entity> getBy(List<Entity> characters, Predicate<Entity> predicate) {
        ArrayList<Entity> result = new ArrayList<Entity>();
        for (Entity gc : characters) {
            if (predicate.apply(gc)) {
                result.add(gc);
            }
        }
        return ImmutableList.copyOf(result);
        //        return ImmutableList.copyOf(Iterables.filter(characters, predicate));
    }

    public static List<Entity> getBy(List<Entity> characters, Predicate<Entity>... filters) {
        ArrayList<Entity> result = new ArrayList<Entity>();
        for (Entity gc : characters) {
            Predicate predicate = Predicates.and(filters);
            if (predicate.apply(gc)) {
                result.add(gc);
            }
        }
        return ImmutableList.copyOf(result);
        //        return ImmutableList.copyOf(Iterables.filter(characters, predicate));
    }

    public List<Entity> getBy(Predicate<Entity>... filters) {
        ArrayList<Entity> result = new ArrayList<Entity>();
        for (Entity entity : entities.values()) {
            Predicate predicate = Predicates.and(filters);
            if (predicate.apply(entity)) {
                result.add(entity);
            }
        }
        return ImmutableList.copyOf(result);
        //        return ImmutableList.copyOf(Iterables.filter(characters, predicate));
    }

    public SpatialEntity getEntityFrom(Spatial sp) {
        SpatialEntity result = null;
        Iterator<Entity> iterator = entities.values().iterator();
        while (iterator.hasNext()) {
            Entity entity = iterator.next();
            if (entity instanceof SpatialEntity) {
                Spatial esp = ((SpatialEntity) entity).getSpatial();
                if (esp instanceof Node && sp.hasAncestor((Node) esp)) {
                    result = (SpatialEntity) entity;
                    break;
                } else if (sp == esp) {
                    result = (SpatialEntity) entity;
                    break;
                }
            }
        }
        return result;
    }

    public List<SpatialEntity> getAllEntitiesFrom(Node node) {
        final ArrayList<SpatialEntity> result = new ArrayList<SpatialEntity>();

        return result;
    }

    public Entity getEntityById(long id) {
        return entities.get(id);
    }

    public void setEntityById(long id, Entity newEntity) {
        entities.put(id, newEntity);
    }

    public <T extends EntityFactory> T getEntityFactory(Class<T> clazz) {
        return (T) entityFactory;
    }

    public EntityFactory getEntityFactory() {
        return entityFactory;
    }

    public EventBus getEventBus() {
        return eventBus;
    }
}
