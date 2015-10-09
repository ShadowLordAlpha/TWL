package test;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.system.MemoryUtil;

import de.matthiasmann.twl.Button;
import de.matthiasmann.twl.DialogLayout;
import de.matthiasmann.twl.DialogLayout.Group;
import de.matthiasmann.twl.GUI;
import de.matthiasmann.twl.ResizableFrame;
import de.matthiasmann.twl.ScrollPane;
import de.matthiasmann.twl.TextArea;
import de.matthiasmann.twl.Widget;
import de.matthiasmann.twl.renderer.lwjgl.LWJGLRenderer;
import de.matthiasmann.twl.textarea.HTMLTextAreaModel;
import de.matthiasmann.twl.theme.ThemeManager;

/**
 * 
 * @author NateS
 */
public class TwlTest {

	private LWJGLRenderer renderer;
	private ThemeManager theme;
	private GUI gui;
	private Widget root;

	public TwlTest() throws Exception {
		renderer = new LWJGLRenderer();

		theme = ThemeManager.createThemeManager(
				SimpleTest.class.getResource("simple_demo.xml"), renderer);

		root = new Widget();
		root.setTheme("");

		gui = new GUI(root, renderer);
		gui.setSize();
		gui.applyTheme(theme);

		addTestAlert(10, 10, "&lt;minwidth");

		addTestAlert(10, 100, "Between min and max width");

		addTestAlert(
				10,
				180,
				"Past max width but less than max height. "
						+ "This is a lot of text. This is a lot of text. This is a lot of text. This is a lot of text. "
						+ "This is a lot of text. This is a lot of text. This is a lot of text. This is a lot of text. "
						+ "This is a lot of text. This is a lot of text. This is a lot of text. This is a lot of text. "
						+ "This is a lot of text. This is a lot of text. This is a lot of text. This is a lot of text. "
						+ "This is a lot of text. This is a lot of text. This is a lot of text. This is a lot of text. ");

		addTestAlert(
				10,
				350,
				"Past max width and past max height. "
						+ "This is a lot of text. This is a lot of text. This is a lot of text. This is a lot of text. "
						+ "This is a lot of text. This is a lot of text. This is a lot of text. This is a lot of text. "
						+ "This is a lot of text. This is a lot of text. This is a lot of text. This is a lot of text. "
						+ "This is a lot of text. This is a lot of text. This is a lot of text. This is a lot of text. "
						+ "This is a lot of text. This is a lot of text. This is a lot of text. This is a lot of text. "
						+ "This is a lot of text. This is a lot of text. This is a lot of text. This is a lot of text. "
						+ "This is a lot of text. This is a lot of text. This is a lot of text. This is a lot of text. "
						+ "This is a lot of text. This is a lot of text. This is a lot of text. This is a lot of text. "
						+ "This is a lot of text. This is a lot of text. This is a lot of text. This is a lot of text. "
						+ "This is a lot of text. This is a lot of text. This is a lot of text. This is a lot of text. ");
	}

	public void run() {
		while (!(GLFW.glfwWindowShouldClose(window) == GL11.GL_TRUE)) {
			GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
			gui.update();
			GLFW.glfwPollEvents();
			GLFW.glfwSwapBuffers(window);
			TestUtils.reduceInputLag();
		}
	}

	private void addTestAlert(int x, int y, String text) {
		Alert alert = new Alert(text);
		alert.addButton("OK");
		alert.addButton("Cancel");
		alert.setPosition(x, y);
		root.add(alert);
		alert.adjustSize();
	}

	public class Alert extends ResizableFrame {

		private Group buttonGroupH, buttonGroupV;
		private TextArea textArea;
		private ScrollPane scrollPane;

		public Alert(String text) {
			setTheme("/resizableframe");

			final HTMLTextAreaModel textAreaModel = new HTMLTextAreaModel(text);
			textArea = new TextArea(textAreaModel);

			scrollPane = new ScrollPane(textArea);
			scrollPane.setFixed(ScrollPane.Fixed.HORIZONTAL);

			DialogLayout layout = new DialogLayout();

			buttonGroupH = layout.createSequentialGroup();
			buttonGroupH.addGap();
			buttonGroupV = layout.createParallelGroup();

			layout.setTheme("/alertbox");
			layout.setHorizontalGroup(layout.createParallelGroup()
					.addWidget(scrollPane).addGroup(buttonGroupH));
			layout.setVerticalGroup(layout.createSequentialGroup()
					.addWidget(scrollPane).addGroup(buttonGroupV));
			add(layout);
		}

		public void addButton(String text) {
			Button button = new Button(text);
			buttonGroupH.addWidget(button);
			buttonGroupV.addWidget(button);
		}
	}

	private static long window;
	
	public static void main(String[] args) throws Exception {
		if (GLFW.glfwInit() != GL11.GL_TRUE) {
			System.err.println("Failed To Initilize GLFW!");
			System.exit(-1);
		}
		window = GLFW.glfwCreateWindow(800, 600, "TWL Examples",
				MemoryUtil.NULL, MemoryUtil.NULL);

		if (window == MemoryUtil.NULL) {
			System.err.println("Failed To Create Window!");
			System.exit(-1);
		}
		GLFW.glfwMakeContextCurrent(window);
		GL.createCapabilities();

		GLFW.glfwSwapInterval(1); // vsync
		
		TwlTest twlTest = new TwlTest();
		twlTest.run();
		GLFW.glfwDestroyWindow(window);
	}
}
