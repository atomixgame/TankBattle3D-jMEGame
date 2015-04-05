package sg.atom.corex.entity;

/**
 * Simple Entity interface
 * @author cuong.nguyen
 */
public interface Entity {
    EntityId getId();
    
    long getIid();
    
    void compose(Object... components);
    
    void persist();
}
