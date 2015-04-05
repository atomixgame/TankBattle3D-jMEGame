package sg.games.tank.ai.states;


import sg.games.tank.entities.Unit;

import com.badlogic.gdx.ai.fsm.State;
import com.badlogic.gdx.ai.msg.Telegram;

public enum ActionStates implements State<Unit> {
	None(){
		
	}, Idle(){
		
	}, Move() {
	
		@Override
		public void enter(Unit entity) {
			entity.combatFSM.changeState(CombatStates.None);
			entity.letMoveToTarget();
		}

		@Override
		public void update(Unit entity) {
			entity.letFindTarget();
			if (entity.getTarget() != null) {
				if (entity.getTarget().getPosition()
						.dst(entity.getPosition()) < entity.attackRange) {
					entity.actionFSM.changeState(ActionStates.Combat);
				} else {
					// Continue moving
				}
			} else {
				entity.actionFSM.changeState(ActionStates.Combat);
			}
		}

		@Override
		public void exit(Unit entity) {
			super.exit(entity);
			entity.letStopMoving();
		}

		public String toString() {
			return "Move";
		}
	},
	Combat() {
		@Override
		public void enter(Unit entity) {
			entity.combatFSM.changeState(CombatStates.Guard);
		}

		@Override
		public void update(Unit entity) {
			// Find target
			entity.letFindTarget();
			if (entity.getTarget() != null) {
				if (entity.getTarget().getPosition()
						.dst(entity.getPosition()) < entity.attackRange) {
					// If in range, attack
					entity.combatFSM.update(entity.deltaTime);
				} else {
					// If its too far. Move to it
					if (entity.isMovable()) {
						entity.actionFSM.changeState(ActionStates.Move);
					} else {
						entity.actionFSM.changeState(ActionStates.None);
					}
				}
			} else {
				// Wander to find target?
				entity.letFindTarget();
				entity.combatFSM.changeState(CombatStates.Guard);
			}
		}

		public String toString() {
			return "Combat";
		}
	};

	@Override
	public void enter(Unit entity) {
		// TODO Auto-generated method stub
	}

	@Override
	public void update(Unit entity) {
		// TODO Auto-generated method stub

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