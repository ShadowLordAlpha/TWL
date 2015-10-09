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
package de.matthiasmann.twl.renderer.lwjgl;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWImage;
import org.lwjgl.system.MemoryUtil;

import de.matthiasmann.twl.renderer.MouseCursor;

/**
 *
 * @author Matthias Mann
 */
class LWJGLCursor implements MouseCursor {

	long cursor = MemoryUtil.NULL;

	LWJGLCursor(ByteBuffer src, LWJGLTexture.Format srcFmt, int srcStride,
			int x, int y, int width, int height, int hotSpotX, int hotSpotY) {
		int dstSize = Math.max(width, height);

		ByteBuffer buffer = BufferUtils.createByteBuffer(dstSize * dstSize * 4);
		IntBuffer buf = buffer.asIntBuffer();
		for (int row = height, dstPos = 0; row-- > 0; dstPos += dstSize) {
			int offset = srcStride * (y + row) + x * srcFmt.getPixelSize();
			buf.position(dstPos);

			switch (srcFmt) {
			case RGB:
				for (int col = 0; col < width; col++) {
					int r = src.get(offset + col * 3 + 0) & 255;
					int g = src.get(offset + col * 3 + 1) & 255;
					int b = src.get(offset + col * 3 + 2) & 255;
					buf.put(makeColor(r, g, b, 0xFF));
				}
				break;
			case RGBA:
				for (int col = 0; col < width; col++) {
					int r = src.get(offset + col * 4 + 0) & 255;
					int g = src.get(offset + col * 4 + 1) & 255;
					int b = src.get(offset + col * 4 + 2) & 255;
					int a = src.get(offset + col * 4 + 3) & 255;
					buf.put(makeColor(r, g, b, a));
				}
				break;
			case ABGR:
				for (int col = 0; col < width; col++) {
					int r = src.get(offset + col * 4 + 3) & 255;
					int g = src.get(offset + col * 4 + 2) & 255;
					int b = src.get(offset + col * 4 + 1) & 255;
					int a = src.get(offset + col * 4 + 0) & 255;
					buf.put(makeColor(r, g, b, a));
				}
				break;
			default:
				throw new IllegalStateException("Unsupported color format");
			}
		}
		// TODO Maybe use flip here instead maybe not needed at all
		buf.clear();

		GLFWImage image = new GLFWImage(buffer);
		cursor = GLFW.glfwCreateCursor(image, 0, 0);
		// TODO probably need to set it to the window as well
	}

	private static int makeColor(int r, int g, int b, int a) {
		a = (a > 222) ? 255 : 0;
		return (a << 24) | (r << 16) | (g << 8) | b;
	}

	void destroy() {
		if (cursor != MemoryUtil.NULL) {
			GLFW.glfwDestroyCursor(cursor);
			cursor = MemoryUtil.NULL;
		}
	}

}
