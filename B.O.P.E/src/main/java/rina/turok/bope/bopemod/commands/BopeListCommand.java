package rina.turok.bope.bopemod.commands;

import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.Style;

import java.util.Comparator;
import java.util.*;

// Commands.
import rina.turok.bope.bopemod.commands.*;

// Data.
import rina.turok.bope.bopemod.BopeCommand;

// Turok.
import rina.turok.turok.values.TurokString;

/**
* @author Rina
*
* Created by Rina.
* 08/04/20.
*
*/
public class BopeListCommand {
	public static ArrayList<BopeCommand> command_list = new ArrayList<>();
	static HashMap<String, BopeCommand> list_command  = new HashMap<>();

	public static final TurokString prefix = new TurokString("Prefix", "Prefix", ".");

	public final Style style;

	public BopeListCommand(Style style_) {
		style = style_;

		add_command(new BopeToggleMessage());
		add_command(new BopeSettings());
		add_command(new BopeFriends());
		add_command(new BopeFriendC());
		add_command(new BopePrefix());
		add_command(new BopeToggle());
		add_command(new BopeBind());
		add_command(new BopeFont());
		add_command(new BopeHelp());
		add_command(new BopeFOV());

		command_list.sort(Comparator.comparing(BopeCommand::get_name));
	}

	public static void add_command(BopeCommand command) {
		command_list.add(command);

		list_command.put(command.get_name().toLowerCase(), command);
	}

	public String[] get_message(String message) {
		String[] arguments = {};

		if (has_prefix(message)) {
			arguments = message.replaceFirst(prefix.get_value(), "").split(" ");
		}

		return arguments;
	}

	public boolean has_prefix(String message) {
		return message.startsWith(prefix.get_value());
	}

	public void set_prefix(String new_prefix) {
		prefix.set_value(new_prefix);
	}

	public String get_prefix() {
		return prefix.get_value();
	}

	public static ArrayList<BopeCommand> get_pure_command_list() {
		return command_list;
	}

	public static BopeCommand get_command_with_name(String name) {
		return list_command.get(name.toLowerCase());
	}
}