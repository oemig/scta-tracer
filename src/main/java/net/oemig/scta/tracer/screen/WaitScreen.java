package net.oemig.scta.tracer.screen;

import java.awt.GraphicsEnvironment;
import java.awt.Point;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class WaitScreen implements IScreen {

	private static final long serialVersionUID = 5199791874732847255L;
	private final JFrame f;
	private final JLabel l;
	private final int width=450;
	private final int height=500;

	public WaitScreen(String message) {
		this.f = new JFrame("Wait Screen");
		// f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Point p=GraphicsEnvironment.getLocalGraphicsEnvironment().getCenterPoint();
		Point leftCorner=new Point(p.x-Math.round(width/2), p.y-Math.round(height/2));
		f.setLocation(leftCorner);
		
		l = new JLabel("Please wait: " + message);
		f.getContentPane().add(l);
		f.setSize(width, height);

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
