package sg.games.tank.ai.states;


import sg.games.tank.entities.Unit;

import com.badlogic.gdx.ai.fsm.State;
import com.badlogic.gdx.ai.msg.Telegram;

/**
 * CommonStates served the main functions of unit
 * 
 * @author cuong.nguyen
 * 
 */
public enum CommonStates implements State<Unit> {
	Preactive() {
		@Override
		public void update(Unit entity) {
			super.update(entity);
			if (entity.commonFSM.getStateTime() > entity.preactiveDuration) {
				entity.commonFSM.changeState(Active);
			}
		}

		public String toString() {
			return "Preactive";
		}
	},
	Active() {
		@Override
		public void enter(Unit entity) {
			super.enter(entity);
			entity.activeUnit();
			entity.actionFSM.changeState(ActionStates.Combat);
		}

		@Override
		public void update(Unit entity) {
			super.update(entity);

			// entity.movementFSM.update(entity.deltaTime);
			entity.actionFSM.update(entity.deltaTime);

			if (entity.health <= 0) {
				// entity.movementFSM.changeState(MovementStates.Stop);
				entity.actionFSM.changeState(ActionStates.None);
				entity.commonFSM.changeState(Inactive);
			}
		}

		@Override
		public void exit(Unit entity) {
			super.exit(entity);
			entity.letDie();
		}

		public String toString() {
			return "Active";
		}
	},
	Inactive() {
		@Override
		public void enter(Unit entity) {
			super.enter(entity);

		}

		public String toString() {
			return "Inactive";
		}
	};

	@Override
	public void enter(Unit entity) {
	}

	@Override
	public void update(Unit entity) {
	}

	@Override
	public void exit(Unit entity) {
	}

	@Override
	public boolean onMessage(Unit entity, Telegram telegram) {
		return false;
	}
}