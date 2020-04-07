package rina.turok.bope.framework;

import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.math.BlockPos;

import org.lwjgl.opengl.GL11;

import rina.turok.bope.framework.TurokGL;

//
// Rina.
// Is not the same.
//
public class TurokRenderHelp extends Tessellator {
	public static TurokRenderHelp INSTANCE = new TurokRenderHelp();

	public TurokRenderHelp() {
		super(0x200000);
	}

	public static void prepare(int mode) {
		TurokGL.prepare(); // Prepare.

		INSTANCE.getBuffer().begin(mode, DefaultVertexFormats.POSITION_COLOR);
	}

	public static void release() {
		INSTANCE.draw();

		TurokGL.release(); // Buffer //
	}

	public static void prepare_gl() {
		TurokGL.prepare();
	}

	public static void release_gl() {
		TurokGL.release();
	}

	public static void draw_down_face(BlockPos pos, float r, float g, float b, float a) {
		BufferBuilder buffer = INSTANCE.getBuffer();

		float w = 1;
		float h = 1;
		float d = 1;

		float x = pos.x;
		float y = pos.y;
		float z = pos.z;

		buffer.pos(x + w, y, z).color(r, g, b, a).endVertex();
		buffer.pos(x + w, y, z + d).color(r, g, b, a).endVertex();
		buffer.pos(x, y, z + d).color(r, g, b, a).endVertex();
		buffer.pos(x, y, z).color(r, g, b, a).endVertex();
	}

	public static void draw_up_face(BlockPos pos, float r, float g, float b, float a) {
		BufferBuilder buffer = INSTANCE.getBuffer();

		float w = 1;
		float h = 1;
		float d = 1;

		float x = pos.x;
		float y = pos.y;
		float z = pos.z;

		buffer.pos(x + w, y + h, z).color(r, g, b, a).endVertex();
		buffer.pos(x, y + h, z).color(r, g, b, a).endVertex();
		buffer.pos(x, y + h, z + d).color(r, g, b, a).endVertex();
		buffer.pos(x + w, y + h, z + d).color(r, g, b, a).endVertex();
	}

	public static void draw_north_face(BlockPos pos, float r, float g, float b, float a) {
		BufferBuilder buffer = INSTANCE.getBuffer();

		float w = 1;
		float h = 1;
		float d = 1;

		float x = pos.x;
		float y = pos.y;
		float z = pos.z;

		buffer.pos(x + w, y, z).color(r, g, b, a).endVertex();
		buffer.pos(x, y, z).color(r, g, b, a).endVertex();
		buffer.pos(x, y + h, z).color(r, g, b, a).endVertex();
		buffer.pos(x + w, y + h, z).color(r, g, b, a).endVertex();
	}

	public static void draw_south_face(BlockPos pos, float r, float g, float b, float a) {
		BufferBuilder buffer = INSTANCE.getBuffer();

		float w = 1;
		float h = 1;
		float d = 1;

		float x = pos.x;
		float y = pos.y;
		float z = pos.z;

		buffer.pos(x, y, z).color(r, g, b, a).endVertex();
		buffer.pos(x, y, z + d).color(r, g, b, a).endVertex();
		buffer.pos(x, y + h, z + d).color(r, g, b, a).endVertex();
		buffer.pos(x, y + h, z).color(r, g, b, a).endVertex();
	}

	public static void draw_west_face(BlockPos pos, float r, float g, float b, float a) {
		BufferBuilder buffer = INSTANCE.getBuffer();

		float w = 1;
		float h = 1;
		float d = 1;

		float x = pos.x;
		float y = pos.y;
		float z = pos.z;

		buffer.pos(x, y, z).color(r, g, b, a).endVertex();
		buffer.pos(x, y, z + d).color(r, g, b, a).endVertex();
		buffer.pos(x, y + h, z + d).color(r, g, b, a).endVertex();
		buffer.pos(x, y + h, z).color(r, g, b, a).endVertex();
	}

	public static void draw_east_face(BlockPos pos, float r, float g, float b, float a) {
		BufferBuilder buffer = INSTANCE.getBuffer();

		float w = 1;
		float h = 1;
		float d = 1;

		float x = pos.x;
		float y = pos.y;
		float z = pos.z;

		buffer.pos(x + w, y, z + d).color(r, g, b, a).endVertex();
		buffer.pos(x + w, y, z).color(r, g, b, a).endVertex();
		buffer.pos(x + w, y + h, z).color(r, g, b, a).endVertex();
		buffer.pos(x + w, y + h, z + d).color(r, g, b, a).endVertex();
	}

	public static void draw_cube(BlockPos block, float r, float g, float b, float a) {
		draw_down_face(block, r, g, b, a);
		draw_up_face(block, r, g, b, a);

		draw_north_face(block, r, g, b, a);
		draw_south_face(block, r, g, b, a);

		draw_west_face(block, r, g, b, a);
		draw_east_face(block, r, g, b, a);
	}
}