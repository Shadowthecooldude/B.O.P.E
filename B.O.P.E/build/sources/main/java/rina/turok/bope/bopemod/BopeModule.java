package rina.turok.bope.bopemod;

import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.common.MinecraftForge;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.Minecraft;

import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Retention;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Keyboard;

import rina.turok.bope.bopemod.events.BopeEventRender;
import rina.turok.bope.bopemod.hacks.BopeCategory;
import rina.turok.bope.framework.TurokBoolean;
import rina.turok.bope.framework.TurokString;
import rina.turok.bope.framework.TurokBind;
import rina.turok.bope.framework.TurokEnum;
import rina.turok.bope.framework.TurokInt;
import rina.turok.bope.Bope;

// External:
import rina.turok.bope.external.BopeEventBus;

//
// Rina.
// Coded by Rina.
// 31/03/2020.
//
public class BopeModule {
	public TurokString name;
	public TurokString description;

	public BopeCategory.Category category;

	public TurokBoolean state_module = new TurokBoolean(false);

	public TurokBind bind;

	public final Minecraft mc = Minecraft.getMinecraft();

	public BopeModule(String name_module, String description_module, int key, BopeCategory.Category category_module) {
		name        = new TurokString(name_module);
		description = new TurokString(description_module);
		bind        = new TurokBind(key);
		category    = category_module;
	}

	public void onWorldRender(BopeEventRender event) {} // Render event into module.

	public void onUpdate() {} // While module.

	public void onRender() {} // While render.

	public void onDisable() {} // Disable effect.

	public void onEnable() {} // While actived.

	public void set_active(boolean value) {
		state_module.set_value(value);

		if (state_module.get_value() != value) {
			if (value) {
				enable();
			} else {
				disable();
			}
		}
	}

	public void toggle() {
		set_active(!is_active());
	}

	public void disable() {
		state_module.set_value(false);

		onDisable();

		BopeEventBus.ZERO_ALPINE_EVENT_BUS.unsubscribe(this);
	}

	public void enable() {
		state_module.set_value(true);

		onEnable();

		BopeEventBus.ZERO_ALPINE_EVENT_BUS.subscribe(this);
	}

	public boolean is_active() {
		return state_module.get_value();
	}

	public String get_name() {
		return name.get_value();
	}

	public String get_description() {
		return description.get_value();
	}

	public void set_bind(int key) {
		bind.set_key(key);
	}

	public TurokBind get_bind() {
		return bind;
	}

	public int get_key_bind() {
		return bind.get_key();
	}

	public BopeCategory.Category get_category() {
		return category;
	}
}