package de.matthiasmann.twl.input.lwjgl;

import java.util.Vector;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWKeyCallback;

public class Keyboard {

	private static Vector<Event> eventStack = new Vector<Event>();
	private static Event proccessing;
	private static long setupWindow;

	public static boolean isCreated() {
		if(setupWindow == GLFW.glfwGetCurrentContext()) {
			System.out.println("Window change!");
		}
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
			
			// easy and bad method to convert from GLFW keys to the Keys that TWL currently understands
			int keycode = de.matthiasmann.twl.Event.KEY_NONE;
			char translated = '\0';
			switch(key) {
			/** The unknown key. */
			case GLFW.GLFW_KEY_UNKNOWN:
				translated = ((char) key);
				break;
			case GLFW.GLFW_KEY_SPACE:
				keycode = de.matthiasmann.twl.Event.KEY_SPACE;
				translated = ' ';
				break;
			case GLFW.GLFW_KEY_APOSTROPHE:
				keycode = de.matthiasmann.twl.Event.KEY_APOSTROPHE;
				translated = '\'';
				break;
			case GLFW.GLFW_KEY_COMMA:
				keycode = de.matthiasmann.twl.Event.KEY_COMMA;
				translated = ',';
				break;
			case GLFW.GLFW_KEY_MINUS:
				keycode = de.matthiasmann.twl.Event.KEY_MINUS;
				translated = '-';
				break;
			case GLFW.GLFW_KEY_PERIOD:
				keycode = de.matthiasmann.twl.Event.KEY_PERIOD;
				translated = '.';
				break;
			case GLFW.GLFW_KEY_SLASH:
				keycode = de.matthiasmann.twl.Event.KEY_SLASH;
				translated = '/';
				break;
			case GLFW.GLFW_KEY_0:
				keycode = de.matthiasmann.twl.Event.KEY_0;
				translated = '0';
				break;
			case GLFW.GLFW_KEY_1:
				keycode = de.matthiasmann.twl.Event.KEY_1;
				translated = '1';
				break;
			case GLFW.GLFW_KEY_2:
				keycode = de.matthiasmann.twl.Event.KEY_2;
				translated = '2';
				break;
			case GLFW.GLFW_KEY_3:
				keycode = de.matthiasmann.twl.Event.KEY_3;
				translated = '3';
				break;
			case GLFW.GLFW_KEY_4:
				keycode = de.matthiasmann.twl.Event.KEY_4;
				translated = '4';
				break;
			case GLFW.GLFW_KEY_5:
				keycode = de.matthiasmann.twl.Event.KEY_5;
				translated = '5';
				break;
			case GLFW.GLFW_KEY_6:
				keycode = de.matthiasmann.twl.Event.KEY_6;
				translated = '6';
				break;
			case GLFW.GLFW_KEY_7:
				keycode = de.matthiasmann.twl.Event.KEY_7;
				translated = '7';
				break;
			case GLFW.GLFW_KEY_8:
				keycode = de.matthiasmann.twl.Event.KEY_8;
				translated = '8';
				break;
			case GLFW.GLFW_KEY_9:
				keycode = de.matthiasmann.twl.Event.KEY_9;
				translated = '9';
				break;
			case GLFW.GLFW_KEY_SEMICOLON:
				keycode = de.matthiasmann.twl.Event.KEY_SEMICOLON;
				translated = ';';
				break;
			case GLFW.GLFW_KEY_EQUAL:
				//keycode = de.matthiasmann.twl.Event.KEY_EQUAL;
				translated = '=';
				break;
			case GLFW.GLFW_KEY_A:
				keycode = de.matthiasmann.twl.Event.KEY_A;
				translated = 'a';
				break;
			case GLFW.GLFW_KEY_B:
				keycode = de.matthiasmann.twl.Event.KEY_B;
				translated = 'b';
				break;
			case GLFW.GLFW_KEY_C:
				keycode = de.matthiasmann.twl.Event.KEY_C;
				translated = 'c';
				break;
			case GLFW.GLFW_KEY_D:
				keycode = de.matthiasmann.twl.Event.KEY_D;
				translated = 'd';
				break;
			case GLFW.GLFW_KEY_E:
				keycode = de.matthiasmann.twl.Event.KEY_E;
				translated = 'e';
				break;
			case GLFW.GLFW_KEY_F:
				keycode = de.matthiasmann.twl.Event.KEY_F;
				translated = 'f';
				break;
			case GLFW.GLFW_KEY_G:
				keycode = de.matthiasmann.twl.Event.KEY_G;
				translated = 'g';
				break;
			case GLFW.GLFW_KEY_H:
				keycode = de.matthiasmann.twl.Event.KEY_H;
				translated = 'h';
				break;
			case GLFW.GLFW_KEY_I:
				keycode = de.matthiasmann.twl.Event.KEY_I;
				translated = 'i';
				break;
			case GLFW.GLFW_KEY_J:
				keycode = de.matthiasmann.twl.Event.KEY_J;
				translated = 'j';
				break;
			case GLFW.GLFW_KEY_K:
				keycode = de.matthiasmann.twl.Event.KEY_K;
				translated = 'k';
				break;
			case GLFW.GLFW_KEY_L:
				keycode = de.matthiasmann.twl.Event.KEY_L;
				translated = 'l';
				break;
			case GLFW.GLFW_KEY_M:
				keycode = de.matthiasmann.twl.Event.KEY_M;
				translated = 'm';
				break;
			case GLFW.GLFW_KEY_N:
				keycode = de.matthiasmann.twl.Event.KEY_N;
				translated = 'n';
				break;
			case GLFW.GLFW_KEY_O:
				keycode = de.matthiasmann.twl.Event.KEY_O;
				translated = 'o';
				break;
			case GLFW.GLFW_KEY_P:
				keycode = de.matthiasmann.twl.Event.KEY_P;
				translated = 'p';
				break;
			case GLFW.GLFW_KEY_Q:
				keycode = de.matthiasmann.twl.Event.KEY_Q;
				translated = 'q';
				break;
			case GLFW.GLFW_KEY_R:
				keycode = de.matthiasmann.twl.Event.KEY_R;
				translated = 'r';
				break;
			case GLFW.GLFW_KEY_S:
				keycode = de.matthiasmann.twl.Event.KEY_S;
				translated = 's';
				break;
			case GLFW.GLFW_KEY_T:
				keycode = de.matthiasmann.twl.Event.KEY_T;
				translated = 't';
				break;
			case GLFW.GLFW_KEY_U:
				keycode = de.matthiasmann.twl.Event.KEY_U;
				translated = 'u';
				break;
			case GLFW.GLFW_KEY_V:
				keycode = de.matthiasmann.twl.Event.KEY_V;
				translated = 'v';
				break;
			case GLFW.GLFW_KEY_W:
				keycode = de.matthiasmann.twl.Event.KEY_W;
				translated = 'w';
				break;
			case GLFW.GLFW_KEY_X:
				keycode = de.matthiasmann.twl.Event.KEY_X;
				translated = 'x';
				break;
			case GLFW.GLFW_KEY_Y:
				keycode = de.matthiasmann.twl.Event.KEY_Y;
				translated = 'y';
				break;
			case GLFW.GLFW_KEY_Z:
				keycode = de.matthiasmann.twl.Event.KEY_Z;
				translated = 'z';
				break;
			case GLFW.GLFW_KEY_LEFT_BRACKET:
				keycode = de.matthiasmann.twl.Event.KEY_LBRACKET;
				translated = '[';
				break;
			case GLFW.GLFW_KEY_BACKSLASH:
				keycode = de.matthiasmann.twl.Event.KEY_BACKSLASH;
				translated = '\\';
				break;
			case GLFW.GLFW_KEY_RIGHT_BRACKET:
				keycode = de.matthiasmann.twl.Event.KEY_RBRACKET;
				translated = ']';
				break;
			case GLFW.GLFW_KEY_GRAVE_ACCENT:
				keycode = de.matthiasmann.twl.Event.KEY_GRAVE;
				translated = '`';
				break;
			// Unknown what to map these to
			//GLFW_KEY_WORLD_1       = 0xA1,
			//GLFW_KEY_WORLD_2       = 0xA2;
			case GLFW.GLFW_KEY_KP_0:
				keycode = de.matthiasmann.twl.Event.KEY_NUMPAD0;
				translated = '0';
				break;
			case GLFW.GLFW_KEY_KP_1:
				keycode = de.matthiasmann.twl.Event.KEY_NUMPAD1;
				translated = '1';
				break;
			case GLFW.GLFW_KEY_KP_2:
				keycode = de.matthiasmann.twl.Event.KEY_NUMPAD2;
				translated = '2';
				break;
			case GLFW.GLFW_KEY_KP_3:
				keycode = de.matthiasmann.twl.Event.KEY_NUMPAD3;
				translated = '3';
				break;
			case GLFW.GLFW_KEY_KP_4:
				keycode = de.matthiasmann.twl.Event.KEY_NUMPAD4;
				translated = '4';
				break;
			case GLFW.GLFW_KEY_KP_5:
				keycode = de.matthiasmann.twl.Event.KEY_NUMPAD5;
				translated = '5';
				break;
			case GLFW.GLFW_KEY_KP_6:
				keycode = de.matthiasmann.twl.Event.KEY_NUMPAD6;
				translated = '6';
				break;
			case GLFW.GLFW_KEY_KP_7:
				keycode = de.matthiasmann.twl.Event.KEY_NUMPAD7;
				translated = '7';
				break;
			case GLFW.GLFW_KEY_KP_8:
				keycode = de.matthiasmann.twl.Event.KEY_NUMPAD8;
				translated = '8';
				break;
			case GLFW.GLFW_KEY_KP_9:
				keycode = de.matthiasmann.twl.Event.KEY_NUMPAD9;
				translated = '9';
				break;
			case GLFW.GLFW_KEY_KP_DECIMAL:
				keycode = de.matthiasmann.twl.Event.KEY_DECIMAL;
				translated = '.';
				break;
			case GLFW.GLFW_KEY_KP_DIVIDE:
				keycode = de.matthiasmann.twl.Event.KEY_DIVIDE;
				translated = '/';
				break;
			case GLFW.GLFW_KEY_KP_MULTIPLY:
				keycode = de.matthiasmann.twl.Event.KEY_MULTIPLY;
				translated = '*';
				break;
			case GLFW.GLFW_KEY_KP_SUBTRACT:
				keycode = de.matthiasmann.twl.Event.KEY_SUBTRACT;
				translated = '-';
				break;
			case GLFW.GLFW_KEY_KP_ADD:
				keycode = de.matthiasmann.twl.Event.KEY_ADD;
				translated = '+';
				break;
			case GLFW.GLFW_KEY_KP_ENTER:
				keycode = de.matthiasmann.twl.Event.KEY_NUMPADENTER;
				translated = '\n';
				break;
			case GLFW.GLFW_KEY_KP_EQUAL:
				keycode = de.matthiasmann.twl.Event.KEY_NUMPADEQUALS;
				translated = '=';
				break;
				
			case GLFW.GLFW_KEY_ESCAPE:
				keycode = de.matthiasmann.twl.Event.KEY_ESCAPE;
				break;
			case GLFW.GLFW_KEY_ENTER:
				keycode = de.matthiasmann.twl.Event.KEY_RETURN;
				translated = '\n';
				break;
			case GLFW.GLFW_KEY_TAB:
				keycode = de.matthiasmann.twl.Event.KEY_TAB;
				translated = '\t';
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
				translated = ((char) key);
				break;
			}
			
			// TODO make better as only shift key value is checked and only letters changed
			if(!((mods & GLFW.GLFW_MOD_SHIFT) == 0)) {
				translated = Character.toUpperCase(translated);
			}

			if (action == GLFW.GLFW_PRESS || action == GLFW.GLFW_REPEAT) {
				eventStack.addElement(new Event(keycode, translated, true));
			} else {
				eventStack.addElement(new Event(keycode, translated, false));
			}

			if (passKCE != null) {
				passKCE.invoke(window, key, scancode, action, mods);
			}
		}
	}
}
