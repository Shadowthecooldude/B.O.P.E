package rina.turok.bope.bopemod.hacks.render;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.Vec3d;
import net.minecraft.item.ItemStack;
import net.minecraft.entity.Entity;

import static org.lwjgl.opengl.GL11.*;
import org.lwjgl.opengl.GL11;

import java.util.*;

// Guiscreen.
import rina.turok.bope.bopemod.guiscreen.settings.BopeSetting;
import rina.turok.bope.bopemod.guiscreen.render.BopeDraw;

// Events.
import rina.turok.bope.bopemod.events.BopeEventRender;

// Modules.
import rina.turok.bope.bopemod.hacks.BopeCategory;

// Data.
import rina.turok.bope.bopemod.BopeModule;

// Core.
import rina.turok.bope.Bope;

/**
* @author Rina
*
* Created by Rina.
* 22/05/2020.
*
* @param render_item;
* - Thanks for this method Jonh.
* - Thanks for help Jonh. :)
*
*/
public class BopeNameTag extends BopeModule {
	BopeSetting name_ = create("Name",      "NameTagName",       true);
	BopeSetting life_ = create("Health",    "NameTagHealth",     true);
	BopeSetting ping_ = create(ChatFormatting.DARK_RED + "Ping",      "NameTagPing",       false); 
	BopeSetting armor = create("Armor",     "NameTagArmor",      true);
	BopeSetting main_ = create("Main Hand", "NameTagMainHand",   true);
	BopeSetting off_h = create("Off Hand",  "NameTagOffHand",    true);
	BopeSetting range = create("Range",     "NameTagRange",      25, 0, 50);
	BopeSetting size  = create("Size",      "NameTagSize",       4, 3, 4);
	BopeSetting smoth = create("Smooth",    "NameTagSmoothFont", false);

	float partial_ticks = 0.0f;

	BopeDraw font = new BopeDraw(1);

	int CLEAR = 256;
	int MASK  = 2929;

	public BopeNameTag() {
		super(BopeCategory.BOPE_RENDER);

		// Info.
		this.name        = "Name Tag";
		this.tag         = "NameTag";
		this.description = "For see the items using of others players.";

		// Release.
		release("B.O.P.E - module - B.O.P.E"); 
	}

	@Override
	public void render(BopeEventRender event) {
		if (mc.player != null && mc.world != null) {
			partial_ticks = event.get_partial_ticks();

			GlStateManager.enableTexture2D();
			GlStateManager.disableLighting();
			GlStateManager.disableDepth();

			mc.world.loadedEntityList.stream()
			/* inaRinaRinaRinaRin */ .filter( entity -> entity instanceof EntityLivingBase)
			/* inaRinaRinaRinaRin */ .filter( entity -> entity != mc.player)
			/* inaRinaRinaRinaRin */ .map(    entity -> (EntityLivingBase) entity)
			/* inaRinaRinaRinaRin */ .filter(_entity -> !_entity.isDead)
			/* inaRinaRinaRinaRin */ .filter( entity -> entity instanceof EntityPlayer)
			/* inaRinaRinaRinaRin */ .filter( entity -> mc.player.getDistance(entity) < range.get_value(1))
			/* inaRinaRinaRinaRin */ .sorted(Comparator.comparing(entity -> -mc.player.getDistance(entity)))
			/* inaRinaRinaRinaRin */ .forEach(this::draw);
		}

		// Release.
		GlStateManager.disableTexture2D();
		RenderHelper.disableStandardItemLighting();
		GlStateManager.enableLighting();
		GlStateManager.enableDepth();
	}

	public void draw(Entity entity) {
		if (mc.getRenderManager().options != null) {
			boolean smooth      = smoth.get_value(true);
			boolean person_view = mc.getRenderManager().options.thirdPersonView == 2;

			float viewer_pitch = mc.getRenderManager().playerViewX;
			float viewer_yaw   = mc.getRenderManager().playerViewY;

			String spac = " ";
			String name = name_.get_value(true) == true ? entity.getName() + spac : "";
			String life = life_.get_value(true) == true ? Math.round(((EntityLivingBase) entity).getHealth() / 2 + (entity instanceof EntityPlayer ? ((EntityPlayer) entity).getAbsorptionAmount() : 0)) + spac : "";
			String ping = ping_.get_value(true) == true ? get_ping(entity) : "";
			String tag  = "[" + ping + "]" + "[" + life + "]" + name;

			GlStateManager.pushMatrix();

			Vec3d pos = get_interpolated_render_pos(entity, partial_ticks);

			double x = pos.x;
			double y = pos.y + (entity.height + 1.0f - (entity.isSneaking() ? 0.25f : 0.0f));
			double z = pos.z;

			GlStateManager.translate(x, y, z);
			GlStateManager.rotate(-viewer_yaw, 0.0f, 1.0f, 0.0f);
			GlStateManager.rotate((float) (person_view ? -1 : 1) * viewer_pitch, 1.0f, 0.0f, 0.0f);

			float distance = mc.player.getDistance(entity);
			float scaling  = distance >= 4.0f ? (distance / 8f) * (float) (Math.pow(1.2589254f, size.get_value(1))) : (float) (Math.pow(1.2589254f, 0.5f));

			GlStateManager.scale(scaling, scaling, scaling);
			GlStateManager.scale(-0.025f, -0.025f, 0.025f);

			GlStateManager.disableLighting();
			GlStateManager.depthMask(false);

			GL11.glDisable(MASK);

			int colapse_x = font.get_string_width(tag, smooth) / 2;

			GlStateManager.enableBlend();
			GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
			GlStateManager.disableTexture2D();

			GlStateManager.enableTexture2D();

			font.draw_string(tag, -colapse_x, 10, 255, 255, 255, smooth == true ? true : false, smooth);

			if (entity instanceof EntityPlayer) {
				EntityPlayer player = (EntityPlayer) entity;

				int off_set_x = 0;

				for (ItemStack armor_slot_item : player.inventory.armorInventory) {
					if (armor_slot_item != null && armor.get_value(true)) {
						off_set_x -= 8;
					}
				}

				if (player.getHeldItemMainhand() != null && main_.get_value(true)) {
					off_set_x -= 8;

					ItemStack main_hand = player.getHeldItemMainhand();

					render_item(main_hand, off_set_x, -5);

					off_set_x += 16;
				}

				for (int i = 0; i < 4; ++i) {
					ItemStack armor_slot_item = player.inventory.armorInventory.get(i);

					if (armor_slot_item != null && armor.get_value(true)) {
						render_item(armor_slot_item, off_set_x, -5);

						off_set_x += 16;
					}
				}

				if (player.getHeldItemOffhand() != null && off_h.get_value(true)) {
					off_set_x -= 0;

					ItemStack off_hand = player.getHeldItemOffhand();

					render_item(off_hand, off_set_x, -5);

					off_set_x += 8;
				}
			}

			GlStateManager.glNormal3f(0.0f, 0.0f, 0.0f);
			glTranslatef(0, 20, 0);

			glEnable(GL11.GL_TEXTURE_2D);
			glEnable(GL_DEPTH_TEST);
			glDepthMask(true);
			glDisable(GL_BLEND);
			glColor4f(1f, 1f, 1f, 1f);

			GlStateManager.scale(-40, -40, 40);
			GlStateManager.enableDepth();
			GlStateManager.popMatrix();
		}
	}

	public void render_item(ItemStack item, int x, int y) {
		glPushMatrix();
		glDepthMask(true);
		
		GlStateManager.clear(CLEAR);

		GlStateManager.disableDepth();
		GlStateManager.enableDepth();

		RenderHelper.enableStandardItemLighting();

		mc.getRenderItem().zLevel = -100.0f;

		GlStateManager.scale(1, 1, 0.01f);

		mc.getRenderItem().renderItemAndEffectIntoGUI(item, x, (y / 2) - 12);
		mc.getRenderItem().renderItemOverlays(mc.fontRenderer, item, x, (y / 2) - 12);
		mc.getRenderItem().zLevel = 0.0f;

		GlStateManager.scale(1, 1, 1);

		RenderHelper.disableStandardItemLighting();

		GlStateManager.enableAlpha();
		GlStateManager.disableBlend();
		GlStateManager.disableLighting();
		GlStateManager.scale(0.5d, 0.5d, 0.5d);
		GlStateManager.disableDepth();
		GlStateManager.enableDepth();
		GlStateManager.scale(2.0f, 2.0f, 2.0f);
		glPopMatrix();
	}

	public String get_ping(Entity entity) {
		String ping = "";

		if (entity instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) entity;

			if (player instanceof EntityPlayerMP) {
				ping = Integer.toString(((EntityPlayerMP) player).ping);
			}
		}

		return ping;
	}

	public Vec3d process_interpolated_pos(Entity entity, float ticks) {
		return new Vec3d(entity.lastTickPosX, entity.lastTickPosY, entity.lastTickPosZ).add(get_interpolated_amout(entity, ticks));
	}

	public Vec3d get_interpolated_render_pos(Entity entity, float ticks) {
		return process_interpolated_pos(entity, ticks).subtract(mc.getRenderManager().renderPosX, mc.getRenderManager().renderPosY, mc.getRenderManager().renderPosZ);
	}

	public Vec3d process_interpolated_amount(Entity entity, double x, double y, double z) {
		return new Vec3d(
			(entity.posX - entity.lastTickPosX) * x,
			(entity.posY - entity.lastTickPosY) * y,
			(entity.posZ - entity.lastTickPosZ) * z
		);
	}

	public Vec3d get_interpolated_amout(Entity entity, float ticks) {
		return process_interpolated_amount(entity, ticks, ticks, ticks);
	}

	public Vec3d get_interpolated_entity(Entity entity, float ticks) {
		return new Vec3d(entity.lastTickPosX, entity.lastTickPosY, entity.lastTickPosZ).add(get_interpolated_amout(entity, ticks));
	}
}