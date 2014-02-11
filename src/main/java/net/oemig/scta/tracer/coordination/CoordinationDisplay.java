package net.oemig.scta.tracer.coordination;

import javax.swing.JFrame;
import javax.swing.JLabel;

import net.oemig.scta.tracer.screen.IScreen;

public class CoordinationDisplay implements IScreen {

	private static final long serialVersionUID = -2239092771193421259L;
	private final JFrame f;
	private final JLabel l;

	//constructor
	public CoordinationDisplay(){
		this.f = new JFrame("Coordination Display");
		// f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		l = new JLabel("Loading");
		f.getContentPane().add(l);
		f.setSize(450, 250);
	}
	
	@Override
	public void show() {
		f.setVisible(true);
	}

	@Override
	public void hide() {
		f.setVisible(false);
	}

	public void update(String someContents) {
		l.setText(someContents);
	}
}
