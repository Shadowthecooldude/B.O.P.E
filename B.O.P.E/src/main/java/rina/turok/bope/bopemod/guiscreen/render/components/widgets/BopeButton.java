package rina.turok.bope.bopemod.guiscreen.render.components.widgets;

// Guiscreen.
import rina.turok.bope.bopemod.guiscreen.render.components.BopeAbstractWidget;
import rina.turok.bope.bopemod.guiscreen.render.components.BopeModuleButton;
import rina.turok.bope.bopemod.guiscreen.render.components.BopeFrame;
import rina.turok.bope.bopemod.guiscreen.render.BopeDraw;

// Settings.
import rina.turok.bope.bopemod.guiscreen.settings.BopeSetting;

// Core.
import rina.turok.bope.Bope;

/**
* @author Rina
*
* Created by Rina.
* 01/05/20.
*
*/
public class BopeButton extends BopeAbstractWidget {
	private BopeFrame        frame;
	private BopeModuleButton master;
	private BopeSetting      setting;

	private String button_name;

	private int x;
	private int y;

	private int width;
	private int height;

	private int save_y;

	private boolean can;

	private BopeDraw font = new BopeDraw(1);

	private int bg_r = 0;
	private int bg_g = 0;
	private int bg_b = 100;
	private int bg_a = 255;

	private int nb_r = 255;
	private int nb_g = 255;
	private int nb_b = 255;
	private int nb_a = 255;

	private int bd_r = 0;
	private int bd_g = 0;
	private int bd_b = 42;
	private int bd_a = 150;

	private int border_size = 0;

	public BopeButton(BopeFrame frame, BopeModuleButton master, String tag, int update_postion) {
		this.frame   = frame;
		this.master  = master;
		this.setting = Bope.get_setting_manager().get_setting_with_tag(master.get_module(), tag);

		this.x = master.get_x();
		this.y = update_postion;

		this.save_y = this.y;

		this.width  = master.get_width();
		this.height = font.get_string_height(this.setting.get_name());

		this.button_name = this.setting.get_name();

		this.can = true;
	}

	public BopeSetting get_setting() {
		return this.setting;
	}

	@Override
	public void does_can(boolean value) {
		this.can = value;
	}

	@Override
	public void set_x(int x) {
		this.x = x;
	}

	@Override
	public void set_y(int y) {
		this.y = y;
	}

	@Override
	public void set_width(int width) {
		this.width = width;
	}

	@Override
	public void set_height(int height) {
		this.height = height;
	}

	@Override
	public int get_x() {
		return this.x;
	}

	@Override
	public int get_y() {
		return this.y;
	}

	@Override
	public int get_width() {
		return this.width;
	}

	@Override
	public int get_height() {
		return this.height;
	}

	public int get_save_y() {
		return this.save_y;
	}

	@Override
	public boolean motion_pass(int mx, int my) {
		return motion(mx, my);
	}

	public boolean motion(int mx, int my) {
		if (mx >= get_x() && my >= get_save_y() && mx <= get_x() + get_width() && my <= get_save_y() + get_height()) {
			return true;
		}

		return false;
	}

	public boolean can() {
		return this.can;
	}

	@Override
	public void mouse(int mx, int my, int mouse) {
		if (mouse == 0) {
			if (motion(mx, my) && this.master.is_open() && can()) {
				this.frame.does_can(false);

				this.setting.set_value(!(this.setting.get_value(true)));
			}
		}
	}

	@Override
	public void render(int master_y, int separe, int absolute_x, int absolute_y) {
		set_width(this.master.get_width() - separe);

		boolean zbob = true;

		this.save_y = this.y + master_y;

		if (this.setting.get_value(zbob)) {
			BopeDraw.draw_rect(get_x(), this.save_y, get_x() + this.width, this.save_y + this.height, this.bg_r, this.bg_g, this.bg_b, this.bg_a);

			BopeDraw.draw_string(this.button_name, this.x + 2, this.save_y, this.nb_r, this.nb_g, this.nb_b);
		} else {
			BopeDraw.draw_string(this.button_name, this.x + 2, this.save_y, this.nb_r, this.nb_g, this.nb_b);
		}
	}
}