package net.oemig.scta.tracer.screen;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class WaitScreen implements IScreen {

	private static final long serialVersionUID = 5199791874732847255L;
	private JFrame f;
	private JLabel l;

	public WaitScreen(String message) {
		this.f = new JFrame("Wait Screen");
		// f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		l = new JLabel("Please wait: " + message);
		f.getContentPane().add(l);
		f.setSize(450, 500);

	}

	@Override
	public void show() {
		this.f.setVisible(true);
	}

	@Override
	public void hide() {
		this.f.setVisible(false);
	}

	public void setMessage(String m) {
		l.setText(m);
	}

}
