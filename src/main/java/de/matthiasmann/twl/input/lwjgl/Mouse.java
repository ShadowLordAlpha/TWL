package de.matthiasmann.twl.input.lwjgl;

import java.nio.ByteBuffer;
import java.util.Vector;

import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWCursorPosCallback;
import org.lwjgl.glfw.GLFWMouseButtonCallback;
import org.lwjgl.glfw.GLFWScrollCallback;

public class Mouse {

	// GLFW callbacks to emulate old functionality

	// values
	private static Vector<Event> eventStack = new Vector<Event>();
	private static Event proccessing;
	private static long setupWin;
	
	public static boolean getEventButtonState() {
		return proccessing.action;
	}

	public static int getEventY() {
		return proccessing.y;
	}

	public static int getEventX() {
		return proccessing.x;
	}

	public static int getEventButton() {
		return proccessing.button;
	}

	public static int getEventDWheel() {
		return proccessing.dw;
	}

	// TODO make better in general
	public static boolean isCreated() {
		if (mbe == null) {
			passMBE = GLFW.glfwSetMouseButtonCallback(
					GLFW.glfwGetCurrentContext(),
					(mbe = new MouseButtenEvent()));
		}

		if (mse == null) {
			passMSE = GLFW.glfwSetScrollCallback(GLFW.glfwGetCurrentContext(),
					(mse = new MouseScrollEvent()));
		}

		if (mme == null) {
			passMME = GLFW.glfwSetCursorPosCallback(
					GLFW.glfwGetCurrentContext(), (mme = new MouseMoveEvent()));
		}

		return true;
	}

	public static boolean next() {
		try {
			proccessing = eventStack.remove(0);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	private static class Event {
		public int x;
		public int y;
		public int dw;
		public int button;
		public boolean action;

		Event(int x, int y, int dw, int button, boolean action) {
			this.x = x;
			this.y = y;
			this.dw = dw;
			this.button = button;
			this.action = action;
		}
	}

	private static MouseButtenEvent mbe;
	private static GLFWMouseButtonCallback passMBE;

	private static class MouseButtenEvent extends GLFWMouseButtonCallback {

		ByteBuffer xpos = BufferUtils.createByteBuffer(8);
		ByteBuffer ypos = BufferUtils.createByteBuffer(8);

		@Override
		public void invoke(long window, int button, int action, int mods) {
			GLFW.glfwGetCursorPos(window, xpos, ypos);
			if (action == GLFW.GLFW_PRESS || action == GLFW.GLFW_REPEAT) {
				eventStack.addElement(new Event((int) Math.round(xpos
						.getDouble(0)), (int) Math.round(ypos.getDouble(0)), 0,
						button, true));
			} else {
				eventStack.addElement(new Event((int) Math.round(xpos
						.getDouble(0)), (int) Math.round(ypos.getDouble(0)), 0,
						button, false));
			}

			if (passMBE != null) {
				passMBE.invoke(window, button, action, mods);
			}
		}
	}

	private static MouseScrollEvent mse;
	private static GLFWScrollCallback passMSE;

	private static class MouseScrollEvent extends GLFWScrollCallback {

		ByteBuffer xpos = BufferUtils.createByteBuffer(8);
		ByteBuffer ypos = BufferUtils.createByteBuffer(8);

		@Override
		public void invoke(long window, double xoffset, double yoffset) {
			GLFW.glfwGetCursorPos(window, xpos, ypos);
			eventStack.addElement(new Event((int) Math.round(xpos.getDouble(0)), (int) Math.round(ypos.getDouble(0)), (int) Math.round(xoffset), -1, false));

			if (passMSE != null) {
				passMSE.invoke(window, xoffset, yoffset);
			}
		}
	}

	private static MouseMoveEvent mme;
	private static GLFWCursorPosCallback passMME;

	private static class MouseMoveEvent extends GLFWCursorPosCallback {

		@Override
		public void invoke(long window, double xpos, double ypos) {

			eventStack.addElement(new Event((int) Math.round(xpos), (int) Math.round(ypos), 0, -1, false));

			if (passMME != null) {
				passMME.invoke(window, xpos, ypos);
			}
		}

	}
}
