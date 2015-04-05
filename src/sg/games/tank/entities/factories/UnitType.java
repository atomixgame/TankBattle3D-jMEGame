package sg.games.tank.entities.factories;

/**
 * UnitTypes enum.
 * 
 * FIXME: Should use Json data instead?
 * 
 * @author cuong.nguyen
 *
 */
public enum UnitType {
	TANK("tank"), FIGHTER("fighter");
	private String typeName;

	UnitType(String typeName) {
		this.typeName = typeName;
	}

	@Override
	public String toString() {
		return typeName;
	}
	
	public String getTypeName() {
		return typeName;
	}
}