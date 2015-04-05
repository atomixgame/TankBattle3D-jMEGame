package sg.games.tank.ai.states;

import sg.games.tank.entities.Unit;

import com.badlogic.gdx.ai.fsm.State;
import com.badlogic.gdx.ai.msg.Telegram;

public enum MoveStates implements State<Unit> {
	Walk, Run, Stop, Frezze, Follow, Chase;

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
