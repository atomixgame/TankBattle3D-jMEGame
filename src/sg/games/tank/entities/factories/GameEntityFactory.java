package sg.games.tank.entities.factories;

import com.jme3.asset.AssetManager;
import com.jme3.scene.Spatial;
import sg.atom.core.AtomMain;
import sg.atom.corex.entity.Entity;
import sg.atom.corex.entity.EntityFactory;
import sg.atom.corex.entity.SpatialEntity;
import sg.atom.corex.entity.SpatialEntityControl;
import sg.games.tank.entities.ActorEntity;
import sg.games.tank.entities.Soldier;
import sg.games.tank.entities.Tower;

/**
 *
 * @author cuong.nguyen
 */
public class GameEntityFactory extends EntityFactory {

    public GameEntityFactory(AtomMain app) {
        super(app);
    }

    public Entity create(String type) {
        AssetManager assetManager = app.getAssetManager();
        Entity newEntity;

        //Piority orders: 1- prototype, 2- by method, 3-reflection 4-simple place holder.
        if (type.equalsIgnoreCase("Tree")
                || type.equalsIgnoreCase("Flag")) {
            newEntity = null;
        } else if (type.equalsIgnoreCase("Tower")) {
            newEntity = createTower(assetManager);
        } else if (type.equalsIgnoreCase("Soldier")) {
            newEntity = createSoldier(assetManager);
        } else {
            newEntity = createSpatialEntity(type, SpatialEntityControl.class);
        }

        return newEntity;
    }

    public SpatialEntity createTower(AssetManager assetManager) {
        Spatial model = app.getWorldManager().getModel("Tower").clone();
        String subType = "Tower";
        ActorEntity newUnit = new Tower((int) entityManager.getNewEntityId(), subType, model);
        SpatialEntity newEntity = newUnit.getEntity();
        newEntity.getSpatial().addControl(new SpatialEntityControl(entityManager, newEntity));
        return newEntity;
    }

    public SpatialEntity createSoldier(AssetManager assetManager) {
        //Can use another mapping here?
        Spatial model = app.getWorldManager().getModel("Soldier").clone();
        String subType = "Soldier";
        ActorEntity newUnit = new Soldier((int) entityManager.getNewEntityId(), subType, model);
        SpatialEntity newEntity = newUnit.getEntity();
        newEntity.getSpatial().addControl(new SpatialEntityControl(entityManager, newEntity));
        return newEntity;
    }
}
