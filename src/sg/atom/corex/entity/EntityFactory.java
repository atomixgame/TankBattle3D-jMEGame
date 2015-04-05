package sg.atom.corex.entity;

import com.google.common.collect.ImmutableSet;
import com.google.common.reflect.ClassPath;
import com.jme3.scene.Spatial;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.lang3.reflect.ConstructorUtils;
import sg.atom.core.AtomMain;

/**
 * EntityFactory to procedure Entity.
 *
 * (CommonImplementation) Consider as Suggestion to use the Factory pattern
 * along with EntitySystem.
 * <ul>
 * <li>It has a Cache implementation of original entities beside of one in
 * AssetManager.
 *
 * <li>
 *
 * </ul>
 *
 * @author atomix
 */
public class EntityFactory {

    protected EntityManager entityManager;
    public static final String entityPackageName = "sg.games.dragon.entities";
    protected AtomMain app;

    public EntityFactory(AtomMain app) {
        this.app = app;
        this.entityManager = app.getEntityManager();
    }

    public Entity create(String type) {
        return null;
    }

    public Entity create(Class<? extends SpatialEntity> clazz) {
        Entity newEntity = createSpatialEntity(clazz, "");
        return newEntity;
    }

    public Class<? extends SpatialEntity> getEntityClass(String type) {
        try {
            Class<? extends SpatialEntity> clazz = null;
            ImmutableSet<ClassPath.ClassInfo> packages = ClassPath.from(this.getClass().getClassLoader()).getTopLevelClasses(entityPackageName);
            for (ClassPath.ClassInfo clazzInfo : packages) {
                if (clazzInfo.getSimpleName().equalsIgnoreCase(type)) {
                    clazz = (Class<? extends SpatialEntity>) clazzInfo.load();
                }
            }
            if (clazz != null) {
                return clazz;
            }
        } catch (IOException ex) {
            Logger.getLogger(EntityFactory.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public SpatialEntity createSpatialEntity(String type, String path) {
        Class<? extends SpatialEntity> clazz = getEntityClass(type);
        if (clazz != null) {
            return createSpatialEntity(clazz, path);
        }

        return null;
    }

    public SpatialEntity createSpatialEntity(String type, Spatial model) {
        Class<? extends SpatialEntity> clazz = getEntityClass(type);
        if (clazz != null) {
            return createSpatialEntity(clazz, model);
        }

        return null;
    }

    public SpatialEntity createSpatialEntity(Class<? extends SpatialEntity> clazz, Spatial model, Class<? extends SpatialEntityControl>... controls) {
        SpatialEntity newEntity = null;
        String type = clazz.getSimpleName();
        try {
            newEntity = ConstructorUtils.invokeConstructor(clazz, new Object[]{entityManager.getNewEntityId(), type, model});
//            entityManager.addEntity(newEntity);
            for (Class<? extends SpatialEntityControl> c : controls) {
                SpatialEntityControl controlInstance = ConstructorUtils.invokeConstructor(c, new Object[]{entityManager, newEntity});
                newEntity.getSpatial().addControl(controlInstance);
            }

        } catch (NoSuchMethodException ex) {
            Logger.getLogger(EntityFactory.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(EntityFactory.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvocationTargetException ex) {
            Logger.getLogger(EntityFactory.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(EntityFactory.class.getName()).log(Level.SEVERE, null, ex);
        }
        return newEntity;
    }

    public SpatialEntity createSpatialEntity(Class<? extends SpatialEntity> clazz, String path) {
        Spatial model;
        String type = clazz.getSimpleName();
        if (path == null || path.isEmpty()) {
            model = entityManager.getApp().getWorldManager().createPlaceHolder(type);
        } else {
            model = app.getAssetManager().loadModel(path);
        }

        return createSpatialEntity(clazz, model);
    }

    public SpatialEntity createSpatialEntity(String type, Class<? extends SpatialEntityControl>... controls) {
        Spatial model = app.getWorldManager().getModel(type).clone();
        Class<? extends SpatialEntity> clazz = getEntityClass(type);
        SpatialEntity newEntity = createSpatialEntity(clazz, model, controls);
        return newEntity;
    }

    public Entity create(String type, Object... params) {
        return null;
    }

    public Entity cloneObject(Entity orginal) {
        return null;
    }

    public Entity get() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Entity createFromComponents(Object... components) {
        SpatialEntity newEntity = new SpatialEntity(entityManager.getNewEntityId());
        newEntity.compose(components);
        return newEntity;
    }

    public static void decorate(Entity entity, Object... components) {
        entity.compose(components);
    }

    public Object cloneComponent(Object component) {
        return null;
    }
}
