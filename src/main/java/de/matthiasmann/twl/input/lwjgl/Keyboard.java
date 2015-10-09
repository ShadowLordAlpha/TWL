package de.matthiasmann.twl.input.lwjgl;

import java.util.Vector;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWKeyCallback;

public class Keyboard {

	private static Vector<Event> eventStack = new Vector<Event>();
	private static Event proccessing;

	public static boolean isCreated() {
		if (kce == null) {
			passKCE = GLFW.glfwSetKeyCallback(GLFW.glfwGetCurrentContext(),
					(kce = new KeyCharEvent()));
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

	public static int getEventKey() {
		return proccessing.key;
	}

	public static char getEventCharacter() {
		return proccessing.charac;
	}

	public static boolean getEventKeyState() {
		return proccessing.state;
	}

	private static class Event {
		public int key;
		public char charac;
		public boolean state;

		Event(int key, char charac, boolean state) {
			this.key = key;
			this.charac = charac;
			this.state = state;
		}
	}

	private static KeyCharEvent kce;
	private static GLFWKeyCallback passKCE;

	private static class KeyCharEvent extends GLFWKeyCallback {

		@Override
		public void invoke(long window, int key, int scancode, int action,
				int mods) {

			if (action == GLFW.GLFW_PRESS || action == GLFW.GLFW_REPEAT) {
				eventStack
						.addElement(new Event(
								scancode,
								((char) (key - ((mods == GLFW.GLFW_MOD_SHIFT) ? 32
										: 0))), true));
			} else {
				eventStack.addElement(new Event(key, (char) (key), false));
			}

			if (passKCE != null) {
				passKCE.invoke(window, key, scancode, action, mods);
			}
		}
	}
}
