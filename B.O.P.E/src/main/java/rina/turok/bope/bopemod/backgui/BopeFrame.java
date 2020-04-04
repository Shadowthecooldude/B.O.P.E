package rina.turok.bope.bopemod.backgui;

import rina.turok.bope.bopemod.backgui.BopeButton;

import java.util.Comparator;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class BopeFrame {
	public static ArrayList<BopeButton> buttons_list = new ArrayList<>();
	static HashMap<String, BopeButton>  list_buttons = new HashMap<>();

	public BopeFrame(String tag) {}

	public static void update_components() {
		list_buttons.clear();

		for (BopeButton buttons : BopeButton.get_buttons()) {
			list_buttons.put(buttons.get_tag().toLowerCase(), buttons);
		}
	}

	public static void add_button(BopeButton button) {
		buttons_list.add(button);

		update_componets();
	}

	public static BopeButton get_button(String name) {
		return list_buttons.get(name.toLowerCase());
	}
}