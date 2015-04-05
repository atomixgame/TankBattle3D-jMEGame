package sg.games.tank.entities.factories;

import sg.games.tank.entities.Fighter;
import sg.games.tank.entities.Projectile;
import sg.games.tank.entities.Tank;
import sg.games.tank.entities.Unit;

public class UnitFactory {

    public UnitFactory() {
    }

    public void loadAssets() {
    }

    public Unit createUnit(String typeName) {
        Unit newUnit = null;
        if (typeName.equalsIgnoreCase(UnitType.TANK.getTypeName())) {
            newUnit = this.createTank(1);
        } else if (typeName.equalsIgnoreCase(UnitType.FIGHTER.getTypeName())) {
            newUnit = this.createFighter(1);
        } else {
            newUnit = new Unit("");
        }
        return newUnit;
    }

    public Unit createUnit(UnitType type, int subType) {
        Unit newUnit = null;
        switch (type) {

            case FIGHTER:
                newUnit = this.createFighter(subType);
                break;
            case TANK:
                newUnit = this.createTank(subType);
                break;
            default:
                newUnit = new Unit("");
                break;
        }
        return newUnit;
    }

    public Fighter createFighter(int type) {
        Fighter fighter = new Fighter(type);
        fighter.init();
        return fighter;
    }

    public Tank createTank(int type) {
        Tank tank = new Tank(type);
        tank.init();

        return tank;
    }

    public Projectile createProjectile(int type) {
        Projectile bullet = new Projectile(type);
        bullet.init();
        return bullet;
    }
}
