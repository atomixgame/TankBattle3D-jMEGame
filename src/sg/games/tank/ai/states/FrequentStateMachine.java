package sg.games.tank.ai.states;

import com.badlogic.gdx.ai.fsm.DefaultStateMachine;
import com.badlogic.gdx.ai.fsm.State;

public class FrequentStateMachine<E> extends DefaultStateMachine<E> {

	private float stateTime;

	public FrequentStateMachine(E owner) {
		super(owner);
	}

	public FrequentStateMachine(E owner, State<E> initialState) {
		super(owner, initialState);
	}

	public FrequentStateMachine(E owner, State<E> initialState,
			State<E> globalState) {
		super(owner, initialState, globalState);
	}

	public void update(float delta) {
		this.stateTime += delta;

		super.update();
	}

	@Override
	public void changeState(State<E> newState) {
		super.changeState(newState);
		stateTime = 0;
	}

	public float getStateTime() {
		return stateTime;
	}
}
