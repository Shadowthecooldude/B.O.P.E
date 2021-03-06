package rina.turok.bope;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraftforge.fml.common.Mod;
import net.minecraft.client.Minecraft;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.lwjgl.opengl.Display;
import org.lwjgl.input.Keyboard;

import java.text.SimpleDateFormat;
import java.time.*;
import java.util.*;

// Zero Alpine.
import me.zero.alpine.EventManager;
import me.zero.alpine.EventBus;

// Framework.
import rina.turok.turok.Turok;

// Guiscreen;
import rina.turok.bope.bopemod.guiscreen.BopeGUI;
import rina.turok.bope.bopemod.guiscreen.BopeHUD;

// Notify.
import rina.turok.bope.bopemod.guiscreen.hud.BopeNotifyClient;

// Managers.
import rina.turok.bope.bopemod.manager.BopeCommandManager;
import rina.turok.bope.bopemod.manager.BopeSettingManager;
import rina.turok.bope.bopemod.manager.BopeConfigManager;
import rina.turok.bope.bopemod.manager.BopeModuleManager;
import rina.turok.bope.bopemod.manager.BopeFriendManager;
import rina.turok.bope.bopemod.manager.BopeEventManager;
import rina.turok.bope.bopemod.manager.BopeHUDManager;

// Setting.
import rina.turok.bope.bopemod.guiscreen.settings.BopeSetting;

// Data.
import rina.turok.bope.bopemod.BopeDiscordRichPresence;
import rina.turok.bope.bopemod.BopeMessage;
import rina.turok.bope.bopemod.BopeModule;

// External.
import rina.turok.bope.external.BopeEventHandler;

// Core.
import rina.turok.bope.BopeEventRegister;

/** ...
 * @author Rina.
 * 
 * The ZeroAlpine event manager is compatible with license MIT.
 * All Rights for Rina.
 *
 * Created by Rina.
 * 05/04/20.
 *
 */
@Mod(modid = "bope", version = Bope.BOPE_VERSION)
public class Bope {
	// Master instance.
	@Mod.Instance
	private static Bope MASTER;

	public static final EventBus ZERO_ALPINE_EVENT_BUS = new EventManager();

	// Somes arguments like, version, name, space...
	public static final String BOPE_NAME    = "B.O.P.E";
	public static final String BOPE_VERSION = "0.4";
	public static final String BOPE_SPACE   = " ";
	public static final String BOPE_SIGN    = " \u23D0 ";

	// GUI.
	public static final int BOPE_KEY_GUI /****/ = Keyboard.KEY_RSHIFT;
	public static final int BOPE_KEY_DELETE     = Keyboard.KEY_DELETE;
	public static final int BOPE_KEY_GUI_ESCAPE = Keyboard.KEY_ESCAPE;

	// A just log for initializing and if get a error show in log Minecraft.
	public static Logger bope_register_log;

	// Starting managers.
	public static BopeCommandManager command_manager;
	public static BopeSettingManager setting_manager;
	public static BopeConfigManager  config_manager;
	public static BopeModuleManager  module_manager;
	public static BopeFriendManager  friend_manager;
	public static BopeEventManager   event_manager;
	public static BopeHUDManager     hud_manager;

	// RPC.
	public static BopeDiscordRichPresence discord_rpc;

	// Cick GUI and HUD.
	public static BopeGUI click_gui;
	public static BopeHUD click_hud;

	// Font.
	public static String font_name = "Verdana";

	// Framework Turok.
	public static Turok turok;

	// Strings detail.
	public static ChatFormatting r  = ChatFormatting.RESET;
	public static ChatFormatting ba = ChatFormatting.BLACK;
	public static ChatFormatting re = ChatFormatting.RED;
	public static ChatFormatting aq = ChatFormatting.AQUA;
	public static ChatFormatting bl = ChatFormatting.BLUE;
	public static ChatFormatting go = ChatFormatting.GOLD;
	public static ChatFormatting g  = ChatFormatting.GRAY;
	public static ChatFormatting wh = ChatFormatting.WHITE;
	public static ChatFormatting gr = ChatFormatting.GREEN;
	public static ChatFormatting ye = ChatFormatting.YELLOW;
	public static ChatFormatting dr = ChatFormatting.DARK_RED;
	public static ChatFormatting da = ChatFormatting.DARK_AQUA;
	public static ChatFormatting db = ChatFormatting.DARK_BLUE;
	public static ChatFormatting gg = ChatFormatting.DARK_GRAY;
	public static ChatFormatting dg = ChatFormatting.DARK_GREEN;
	public static ChatFormatting dp = ChatFormatting.DARK_PURPLE;
	public static ChatFormatting lp = ChatFormatting.LIGHT_PURPLE;

	public static int client_r = 0;
	public static int client_g = 0;
	public static int client_b = 0;

	@Mod.EventHandler
	public void BopeStarting(FMLInitializationEvent event) {
		init_log(BOPE_NAME);

		send_minecraft_log("Loading packages initializing in main class. [Bope.class]");

		// Init BopeEventHandler.
		BopeEventHandler.INSTANCE = new BopeEventHandler();

		// Init managers. // systen a bit ant-deobf.
		setting_manager = new BopeSettingManager("setting");
		command_manager = new BopeCommandManager("command");
		config_manager  = new BopeConfigManager("config");
		module_manager  = new BopeModuleManager("module");
		friend_manager  = new BopeFriendManager("friend");
		event_manager   = new BopeEventManager("event");
		hud_manager     = new BopeHUDManager("hud");

		try {
			// Load first time.
			config_manager.load_settings();
			config_manager.load_binds();
			config_manager.load_client("stuff");
		} catch (Exception exc) {}

		send_minecraft_log("Managers are initialed.");

		// Fix somethings. :)
		click_hud = new BopeHUD();
		click_gui = new BopeGUI();

		try {
			// Load second time.
			config_manager.load_client();
			config_manager.load_friends();
		} catch (Exception exc) {}

		turok = new Turok("Turok");

		send_minecraft_log("Turok framework initialed.");

		// Register event modules and manager.
		BopeEventRegister.register_command_manager(command_manager);
		BopeEventRegister.register_module_manager(event_manager);

		send_minecraft_log("Events registered.");

		send_minecraft_log("GUI and HUD initialed.");

		try {
			// Oh why you load so many times, yes because I want;
			config_manager.load_settings();
			config_manager.load_binds();
			config_manager.load_client("stuff");
			config_manager.load_client();
			config_manager.load_friends();
		} catch (Exception exc) {}

		// Load rpc.
		discord_rpc = new BopeDiscordRichPresence("RPC");

		// If...
		if (module_manager.get_module_with_tag("RPC").is_active()) {
			discord_rpc.run();
		}

		// For just fix the GUI.
		if (module_manager.get_module_with_tag("GUI").is_active()) {
			module_manager.get_module_with_tag("GUI").set_active(false);
		}

		// And fix the HUD.
		if (module_manager.get_module_with_tag("HUD").is_active()) {
			module_manager.get_module_with_tag("HUD").set_active(false);
		}

		client_r = get_setting_manager().get_setting_with_tag("HUD", "HUDStringsColorR").get_value(1);
		client_g = get_setting_manager().get_setting_with_tag("HUD", "HUDStringsColorG").get_value(1);
		client_b = get_setting_manager().get_setting_with_tag("HUD", "HUDStringsColorB").get_value(1);

		send_minecraft_log("Client started.");
	}

	public void init_log(String name) {
		bope_register_log = LogManager.getLogger(name);

		send_minecraft_log("...");
	}

	public static void dev(String message) {
		BopeMessage.send_client_message(message);
	}

	public static void hud_notify(String value) {
		get_hud_manager().notify_client.notify_hud(value);
	}

	public static Bope get_instance() {
		return MASTER; // A function for get INSTANCE from all client.
	}

	public static void send_minecraft_log(String log) {
		bope_register_log.info(log);
	}

	public static void send_client_log(String log) {
		Date   hora = new Date();
		String data = new SimpleDateFormat("HH:mm:ss:").format(hora);

		get_instance().config_manager.send_log("<" + BOPE_NAME + "><" + data + "> " + log);
	}

	public static String get_name() {
		return BOPE_NAME;
	}

	public static String get_version() {
		return BOPE_VERSION;
	}

	public static String get_actual_user() {
		String player_requested = "NoName";

		if (Minecraft.getMinecraft().player != null) {
			player_requested = Minecraft.getMinecraft().player.getName();
		}

		return player_requested;
	}

	public static BopeCommandManager get_command_manager() {
		return get_instance().command_manager;
	}

	public static BopeConfigManager get_config_manager() {
		return get_instance().config_manager;
	}

	public static BopeModuleManager get_module_manager() {
		return get_instance().module_manager;
	}

	public static BopeFriendManager get_friend_manager() {
		return get_instance().friend_manager;
	}

	public static BopeSettingManager get_setting_manager() {
		return get_instance().setting_manager;
	}

	public static BopeHUDManager get_hud_manager() {
		return get_instance().hud_manager;
	}

	public static BopeDiscordRichPresence get_rpc() {
		return get_instance().discord_rpc;
	}

	public static BopeEventHandler get_event_handler() {
		return BopeEventHandler.INSTANCE;
	}

	public static Turok get_turok() {
		return get_instance().turok;
	}

	public static String smooth(String base) {
		return Bope.get_turok().get_font_manager().smooth(base);
	}

	// Util.
	public static BopeSetting get_setting(String module, String setting) {
		return get_setting_manager().get_setting_with_tag(module, setting);
	}

	public static BopeModule get_module(String module) {
		return get_module_manager().get_module_with_tag(module);
	}

	public static boolean module_is_active(String module) {
		return get_module_manager().get_module_with_tag(module).is_active();
	}
}