package net.oemig.scta.tracer.awareness;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JTextArea;

import net.oemig.scta.tracer.screen.IScreen;

import com.google.common.collect.Lists;

public class AwarenessDisplay implements IScreen {
	

	private static final long serialVersionUID = -5208363223767538457L;
	private final JFrame f;
	private final JTextArea l;

	//constructor
	public AwarenessDisplay(){
		this.f = new JFrame("Awareness Display");
		// f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		l = new JTextArea("Loading");
		l.setEditable(false);
		f.getContentPane().add(l);
		f.setSize(450, 500);
	}
	
	@Override
	public void show() {
		f.setVisible(true);
	}

	@Override
	public void hide() {
		f.setVisible(false);
	}

	//awareness support listener interface
	public void update(Map<Date, AwarenessEvent> someEvents) {
		List<Date> dates=Lists.newArrayList(someEvents.keySet());
		Collections.sort(dates); //sorts asc by default
		Collections.reverse(dates); //desc
		StringBuffer sb=new StringBuffer();
		SimpleDateFormat df=new SimpleDateFormat();
		
		for(Date d:dates){
			AwarenessEvent e=someEvents.get(d);
			sb.append(df.format(d));
			sb.append(": ");
			sb.append(e.getUserName().toString());
			sb.append(" ");
			sb.append(e.getLetter());
			sb.append("-->");
			sb.append(e.getCountResult());
			sb.append("\n");
			
		}
		
		l.setText(sb.toString());
		
	}

}
