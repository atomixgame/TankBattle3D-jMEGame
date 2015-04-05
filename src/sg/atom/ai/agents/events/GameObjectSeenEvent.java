package sg.atom.ai.agents.events;

import sg.atom.ai.agents.control.GameObjectControl;

/**
 * Event for seen PhysicalObjects.
 *
 * @author Tihomir Radosavljević
 * @version 1.0
 */
public class GameObjectSeenEvent extends GameObjectEvent {

    /**
     * GameObject that have been seen.
     */
    private GameObjectControl gameObjectSeen;

    /**
     *
     * @param source object that produce this event (it is usually agent)
     * @param gameObject GameObject that have been seen
     */
    public GameObjectSeenEvent(Object source, GameObjectControl gameObject) {
        super(source);
        this.gameObjectSeen = gameObject;
    }

    /**
     *
     * @return seen GameObject
     */
    public GameObjectControl getGameObjectSeen() {
        return gameObjectSeen;
    }

    /**
     *
     * @param gameObjectSeen seen GameObject
     */
    public void setGameObjectSeen(GameObjectControl gameObjectSeen) {
        this.gameObjectSeen = gameObjectSeen;
    }
}
