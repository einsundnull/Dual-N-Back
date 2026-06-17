package screens;

import java.util.Timer;
import java.util.TimerTask;

import javax.swing.SwingUtilities;

public class ScreenSwitchTimer {

	Timer timer;

	public ScreenSwitchTimer(int duration, Runnable run) {
		timer = new Timer(true);
		timer.scheduleAtFixedRate(new TimerTask() {

			int i = 0;

			@Override
			public void run() {
				SwingUtilities.invokeLater(() -> {
					i++;
					if (i >= duration) {
						run.run();
						timer.cancel();
						i = 0;
					}
				});
			}
		}, 0, 10);
	}
}
