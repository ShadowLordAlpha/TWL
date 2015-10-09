/*
 * Copyright (c) 2008-2009, Matthias Mann
 *
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *     * Redistributions of source code must retain the above copyright notice,
 *       this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the name of Matthias Mann nor the names of its contributors may
 *       be used to endorse or promote products derived from this software
 *       without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package test;

import de.matthiasmann.twl.GUI;
import de.matthiasmann.twl.Graph;
import de.matthiasmann.twl.model.SimpleGraphLineModel;
import de.matthiasmann.twl.model.SimpleGraphModel;

/**
 *
 * @author Matthias Mann
 */
public class GraphDemoDialog1 extends FadeFrame {

	private SimpleGraphLineModel gmMsPerFrame;
	private long lastTime = System.nanoTime();

	public GraphDemoDialog1() {
		gmMsPerFrame = new SimpleGraphLineModel("default", 100, 0, 30);

		Graph graph = new Graph(new SimpleGraphModel(gmMsPerFrame));
		graph.setTheme("/graph");

		setTheme(SimpleTest.WITH_TITLE);
		setTitle("MS per frame");
		add(graph);
	}

	@Override
	protected void paint(GUI gui) {
		long time = System.nanoTime();
		gmMsPerFrame.addPoint((float) (time - lastTime) * 1e-6f);
		lastTime = time;

		super.paint(gui);
	}

}
