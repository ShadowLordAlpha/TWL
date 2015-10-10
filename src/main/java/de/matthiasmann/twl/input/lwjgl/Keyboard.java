package de.matthiasmann.twl.input.lwjgl;

import java.util.Vector;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWCharCallback;
import org.lwjgl.glfw.GLFWKeyCallback;

public class Keyboard {

	private static Vector<Event> eventStack = new Vector<Event>();
	private static Event proccessing;
	private static long setupWindow;

	public static boolean isCreated() {
		if(setupWindow == GLFW.glfwGetCurrentContext()) {
			System.out.println("Window change!");
		}
		if (ke == null) {
			passKE = GLFW.glfwSetKeyCallback(GLFW.glfwGetCurrentContext(),
					(ke = new KeyEvent()));
		}
		if (ce == null) {
			passCE = GLFW.glfwSetCharCallback(GLFW.glfwGetCurrentContext(),
					(ce = new CharEvent()));
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
	
	private static CharEvent ce;
	private static GLFWCharCallback passCE;
	
	private static class CharEvent extends GLFWCharCallback {

		@Override
		public void invoke(long window, int codepoint) {
			eventStack.addElement(new Event(0, (char)codepoint, true));
			if(passCE != null) {
				passCE.invoke(window, codepoint);
			}
		}
		
	}

	private static KeyEvent ke;
	private static GLFWKeyCallback passKE;

	private static class KeyEvent extends GLFWKeyCallback {

		@Override
		public void invoke(long window, int key, int scancode, int action,
				int mods) {
			int keycode = 0;
			char translated = '\0';
			switch(key) {				
			case GLFW.GLFW_KEY_ESCAPE:
				keycode = de.matthiasmann.twl.Event.KEY_ESCAPE;
				break;
			case GLFW.GLFW_KEY_ENTER:
				keycode = de.matthiasmann.twl.Event.KEY_RETURN;
				translated = '\n';
				break;
			case GLFW.GLFW_KEY_KP_ENTER:
				keycode = de.matthiasmann.twl.Event.KEY_NUMPADENTER;
				translated = '\n';
				break;
			case GLFW.GLFW_KEY_BACKSPACE:
				keycode = de.matthiasmann.twl.Event.KEY_BACK;
				break;
			case GLFW.GLFW_KEY_INSERT:
				keycode = de.matthiasmann.twl.Event.KEY_INSERT;
				break;
			case GLFW.GLFW_KEY_DELETE:
				keycode = de.matthiasmann.twl.Event.KEY_DELETE;
				break;
			case GLFW.GLFW_KEY_RIGHT:
				keycode = de.matthiasmann.twl.Event.KEY_RIGHT;
				break;
			case GLFW.GLFW_KEY_LEFT:
				keycode = de.matthiasmann.twl.Event.KEY_LEFT;
				break;
			case GLFW.GLFW_KEY_DOWN:
				keycode = de.matthiasmann.twl.Event.KEY_DOWN;
				break;
			case GLFW.GLFW_KEY_UP:
				keycode = de.matthiasmann.twl.Event.KEY_UP;
				break;
			case GLFW.GLFW_KEY_PAGE_UP:
				keycode = de.matthiasmann.twl.Event.KEY_PRIOR;
				break;
			case GLFW.GLFW_KEY_PAGE_DOWN:
				keycode = de.matthiasmann.twl.Event.KEY_NEXT;
				break;
			case GLFW.GLFW_KEY_HOME:
				keycode = de.matthiasmann.twl.Event.KEY_HOME;
				break;
			case GLFW.GLFW_KEY_END:
				keycode = de.matthiasmann.twl.Event.KEY_END;
				break;
			case GLFW.GLFW_KEY_CAPS_LOCK:
				keycode = de.matthiasmann.twl.Event.KEY_CAPITAL;
				break;
			case GLFW.GLFW_KEY_SCROLL_LOCK:
				keycode = de.matthiasmann.twl.Event.KEY_SCROLL;
				break;
			case GLFW.GLFW_KEY_NUM_LOCK:
				keycode = de.matthiasmann.twl.Event.KEY_NUMLOCK;
				break;
			case GLFW.GLFW_KEY_PAUSE:
				keycode = de.matthiasmann.twl.Event.KEY_PAUSE;
				break;
			case GLFW.GLFW_KEY_F1:
				keycode = de.matthiasmann.twl.Event.KEY_F1;
				break;
			case GLFW.GLFW_KEY_F2:
				keycode = de.matthiasmann.twl.Event.KEY_F2;
				break;
			case GLFW.GLFW_KEY_F3:
				keycode = de.matthiasmann.twl.Event.KEY_F3;
				break;
			case GLFW.GLFW_KEY_F4:
				keycode = de.matthiasmann.twl.Event.KEY_F4;
				break;
			case GLFW.GLFW_KEY_F5:
				keycode = de.matthiasmann.twl.Event.KEY_F5;
				break;
			case GLFW.GLFW_KEY_F6:
				keycode = de.matthiasmann.twl.Event.KEY_F6;
				break;
			case GLFW.GLFW_KEY_F7:
				keycode = de.matthiasmann.twl.Event.KEY_F7;
				break;
			case GLFW.GLFW_KEY_F8:
				keycode = de.matthiasmann.twl.Event.KEY_F8;
				break;
			case GLFW.GLFW_KEY_F9:
				keycode = de.matthiasmann.twl.Event.KEY_F9;
				break;
			case GLFW.GLFW_KEY_F10:
				keycode = de.matthiasmann.twl.Event.KEY_F10;
				break;
			case GLFW.GLFW_KEY_F11:
				keycode = de.matthiasmann.twl.Event.KEY_F11;
				break;
			case GLFW.GLFW_KEY_F12:
				keycode = de.matthiasmann.twl.Event.KEY_F12;
				break;
			case GLFW.GLFW_KEY_F13:
				keycode = de.matthiasmann.twl.Event.KEY_F13;
				break;
			case GLFW.GLFW_KEY_F14:
				keycode = de.matthiasmann.twl.Event.KEY_F14;
				break;
			case GLFW.GLFW_KEY_F15:
				keycode = de.matthiasmann.twl.Event.KEY_F15;
				break;
			// These don't have a matching key
			case GLFW.GLFW_KEY_F16:
				break;
			case GLFW.GLFW_KEY_F17:
				break;
			case GLFW.GLFW_KEY_F18:
				break;
			case GLFW.GLFW_KEY_F19:
				break;
			case GLFW.GLFW_KEY_F20:
				break;
			case GLFW.GLFW_KEY_F21:
				break;
			case GLFW.GLFW_KEY_F22:
				break;
			case GLFW.GLFW_KEY_F23:
				break;
			case GLFW.GLFW_KEY_F24:
				break;
			case GLFW.GLFW_KEY_F25:
				break;
			case GLFW.GLFW_KEY_LEFT_SHIFT:
				keycode = de.matthiasmann.twl.Event.KEY_LSHIFT;
				break;
			case GLFW.GLFW_KEY_LEFT_CONTROL:
				keycode = de.matthiasmann.twl.Event.KEY_LCONTROL;
				break;
			case GLFW.GLFW_KEY_LEFT_ALT:
				keycode = de.matthiasmann.twl.Event.KEY_LMENU;
				break;
			case GLFW.GLFW_KEY_LEFT_SUPER:
				keycode = de.matthiasmann.twl.Event.KEY_LMETA;
				break;
			case GLFW.GLFW_KEY_RIGHT_SHIFT:
				keycode = de.matthiasmann.twl.Event.KEY_RSHIFT;
				break;
			case GLFW.GLFW_KEY_RIGHT_CONTROL:
				keycode = de.matthiasmann.twl.Event.KEY_RCONTROL;
				break;
			case GLFW.GLFW_KEY_RIGHT_ALT:
				keycode = de.matthiasmann.twl.Event.KEY_RMENU;
				break;
			case GLFW.GLFW_KEY_RIGHT_SUPER:
				keycode = de.matthiasmann.twl.Event.KEY_RMETA;
				break;
			// Unknown what to map these to
			//GLFW_KEY_MENU          = 0x15C,
			//GLFW_KEY_LAST          = GLFW_KEY_MENU;
			default:
				//translated = ((char) key);
				break;
			}

			if (action == GLFW.GLFW_PRESS || action == GLFW.GLFW_REPEAT) {
				eventStack.addElement(new Event(keycode, translated, true));
			} else {
				eventStack.addElement(new Event(keycode, translated, false));
			}

			if (passKE != null) {
				passKE.invoke(window, key, scancode, action, mods);
			}
		}
	}
}
