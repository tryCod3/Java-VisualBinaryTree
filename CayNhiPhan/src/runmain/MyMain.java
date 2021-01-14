package runmain;

import java.awt.Color;
import java.awt.EventQueue;

import bst.SettingBst;
import system.Event;
import system.Gui;

public class MyMain {
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {

				Gui gui = Gui.getGui();
				gui.createBackGround(Gui.WIDTH, Gui.HEIGHT, new Color(25, 25, 112));
				gui.setVisible(true);

				Event event = Event.getEvent();
				event.available();
				
			}
		});
	}

}
