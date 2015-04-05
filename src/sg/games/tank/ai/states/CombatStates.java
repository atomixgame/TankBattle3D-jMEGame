package sg.games.tank.ai.states;

import sg.games.tank.entities.Unit;

import com.badlogic.gdx.ai.fsm.State;
import com.badlogic.gdx.ai.msg.Telegram;

public enum CombatStates implements State<Unit> {
	None() {

	},
	Attack() {
		@Override
		public void enter(Unit entity) {
			super.enter(entity);
			entity.letAttack();
			entity.combatFSM.changeState(Guard);
		}

		@Override
		public void update(Unit entity) {
			super.update(entity);
			if (entity.canAttack()) {
				entity.letFindTarget();
			}
		}

		public String toString() {
			return "Attack";
		}
	},
	Guard() {

		@Override
		public void enter(Unit entity) {
			super.enter(entity);

		}

		@Override
		public void update(Unit entity) {
			super.update(entity);
			if (entity.canAttack()) {
				entity.letFindTarget();
				if (entity.combatFSM.getStateTime() > entity.shootInterval) {
					entity.combatFSM.changeState(Attack);
				}
			}
		}

		public String toString() {
			return "Guard";
		}
	},
	Evade() {
	};

	@Override
	public void enter(Unit entity) {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(Unit entity) {

	}

	@Override
	public void exit(Unit entity) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onMessage(Unit entity, Telegram telegram) {
		// TODO Auto-generated method stub
		return false;
	}

}