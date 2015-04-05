/*******************************************************************************
 * Copyright 2011 See AUTHORS file.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/

package sg.atom.corex.stage.actions;

import com.jme3.math.ColorRGBA;
import sg.atom.corex.stage.Actor;

/** Sets the alpha for an actor's color (or a specified color), from the current alpha to the new alpha. Note this action
 * transitions from the alpha at the time the action starts to the specified alpha.
 * @author Nathan Sweet */
public class AlphaAction extends TemporalAction {
	private float start, end;
	private ColorRGBA color;

	protected void begin () {
		if (color == null) color = actor.getColor();
		start = color.a;
	}

	protected void update (float percent) {
		color.a = start + (end - start) * percent;
	}

	public void reset () {
		super.reset();
		color = null;
	}

	public ColorRGBA getColor () {
		return color;
	}

	/** Sets the color to modify. If null (the default), the {@link #getActor() actor's} {@link Actor#getColor() color} will be
	 * used. */
	public void setColor (ColorRGBA color) {
		this.color = color;
	}

	public float getAlpha () {
		return end;
	}

	public void setAlpha (float alpha) {
		this.end = alpha;
	}
}
