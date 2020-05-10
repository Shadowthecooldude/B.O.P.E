package rina.turok.bope.bopemod.hacks;

// Guiscreen.
import rina.turok.bope.bopemod.guiscreen.settings.BopeSetting;

// Hacks.
import rina.turok.bope.bopemod.hacks.BopeCategory;

// Data.
import rina.turok.bope.bopemod.BopeModule;

// Core.
import rina.turok.bope.Bope;

/**
 * @author Rina
 *
 * Created by Rina.
 * 05/05/20.
 *
 */
public class BopeClickGUI extends BopeModule {
	BopeSetting label_frame = create("info", "ClickGUIInfoFrame", "frame");

	BopeSetting name_frame_r = create("Name Color R", "ClickGUINameFrameR", 255, 0, 255);
	BopeSetting name_frame_g = create("Name Color G", "ClickGUINameFrameG", 255, 0, 255);
	BopeSetting name_frame_b = create("Name Color B", "ClickGUINameFrameB", 255, 0, 255);

	BopeSetting background_frame_r = create("Background Color R", "ClickGUIBackgroundFrameR", 0, 0, 255);
	BopeSetting background_frame_g = create("Background Color G", "ClickGUIBackgroundFrameG", 0, 0, 255);
	BopeSetting background_frame_b = create("Background Color B", "ClickGUIBackgroundFrameB", 0, 0, 255);
	BopeSetting background_frame_a = create("Background Color A", "ClickGUIBackgroundFrameA", 242, 0, 255);

	BopeSetting border_frame_r = create("Border Color R", "ClickGUIBackgroundFrameR", 255, 0, 255);
	BopeSetting border_frame_g = create("Border Color G", "ClickGUIBackgroundFrameG", 255, 0, 255);
	BopeSetting border_frame_b = create("Border Color B", "ClickGUIBackgroundFrameB", 255, 0, 255);

	BopeSetting label_widget = create("info", "ClickGUIInfoWidget", "widget");

	BopeSetting name_widget_r = create("Name Color R", "ClickGUINameWidgetR", 0, 0, 255);
	BopeSetting name_widget_g = create("Name Color G", "ClickGUINameWidgetG", 0, 0, 255);
	BopeSetting name_widget_b = create("Name Color B", "ClickGUINameWidgetB", 242, 0, 255);

	BopeSetting background_widget_r = create("Background Color R", "ClickGUIBackgroundWidgetR", 255, 0, 255);
	BopeSetting background_widget_g = create("Background Color G", "ClickGUIBackgroundWidgetG", 255, 0, 255);
	BopeSetting background_widget_b = create("Background Color B", "ClickGUIBackgroundWidgetB", 255, 0, 255);
	BopeSetting background_widget_a = create("Background Color A", "ClickGUIBackgroundWidgetA", 100, 0, 200);

	BopeSetting border_widget_r = create("Border Color R", "ClickGUIBackgroundWidgetR", 255, 0, 255);
	BopeSetting border_widget_g = create("Border Color G", "ClickGUIBackgroundWidgetG", 255, 0, 255);
	BopeSetting border_widget_b = create("Border Color B", "ClickGUIBackgroundWidgetB", 255, 0, 255);

	public BopeClickGUI() {
		super(BopeCategory.BOPE_GUI);

		// Info.
		this.name        = "B.O.P.E GUI";
		this.tag         = "GUI";
		this.description = "B.O.P.E GUI for enbable or disable modules.";

		release("B.O.P.E");

		set_bind(Bope.BOPE_KEY_GUI);
	}

	@Override
	public void update() {
		// Update frame colors.
		Bope.click_gui.theme_frame_name_r = name_frame_r.get_value(1);
		Bope.click_gui.theme_frame_name_g = name_frame_g.get_value(1);
		Bope.click_gui.theme_frame_name_b = name_frame_b.get_value(1);

		Bope.click_gui.theme_frame_background_r = background_frame_r.get_value(1);
		Bope.click_gui.theme_frame_background_g = background_frame_g.get_value(1);
		Bope.click_gui.theme_frame_background_b = background_frame_b.get_value(1);
		Bope.click_gui.theme_frame_background_a = background_frame_a.get_value(1);

		Bope.click_gui.theme_frame_border_r = border_frame_r.get_value(1);
		Bope.click_gui.theme_frame_border_g = border_frame_g.get_value(1);
		Bope.click_gui.theme_frame_border_b = border_frame_b.get_value(1);

		// Update widget colors.
		Bope.click_gui.theme_widget_name_r = name_widget_r.get_value(1);
		Bope.click_gui.theme_widget_name_g = name_widget_g.get_value(1);
		Bope.click_gui.theme_widget_name_b = name_widget_b.get_value(1);

		Bope.click_gui.theme_widget_background_r = background_widget_r.get_value(1);
		Bope.click_gui.theme_widget_background_g = background_widget_g.get_value(1);
		Bope.click_gui.theme_widget_background_b = background_widget_b.get_value(1);
		Bope.click_gui.theme_widget_background_a = background_widget_a.get_value(1);

		Bope.click_gui.theme_widget_border_r = border_widget_r.get_value(1);
		Bope.click_gui.theme_widget_border_g = border_widget_g.get_value(1);
		Bope.click_gui.theme_widget_border_b = border_widget_b.get_value(1);
	}

	@Override
	public void enable() {
		if (mc.world != null && mc.player != null) {
			mc.displayGuiScreen(Bope.click_gui);
		}
	}
}