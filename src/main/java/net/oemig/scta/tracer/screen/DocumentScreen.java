package net.oemig.scta.tracer.screen;

import java.awt.BorderLayout;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import net.oemig.scta.model.exception.NoCurrentRunSelectedException;
import net.oemig.scta.tracer.ITracerColleagueScreenSPI;
import net.oemig.scta.tracer.run.Document;

public class DocumentScreen implements IScreen {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private final JFrame f;
	private final JTextArea text;
	private final ITracerColleagueScreenSPI colleagueScreenSPI;
	private final int width=450;
	private final int height=500;

	public DocumentScreen(ITracerColleagueScreenSPI newColleagueScreenSPI) {
		Point p=GraphicsEnvironment.getLocalGraphicsEnvironment().getCenterPoint();
		Point leftCorner=new Point(p.x-Math.round(width/2), p.y-Math.round(height/2));
		
		this.colleagueScreenSPI = newColleagueScreenSPI;

		this.f = new JFrame(colleagueScreenSPI.getResourceBundle().getString("ds.title"));
		// f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setSize(width, height);
		f.setLocation(leftCorner);
		f.setLayout(new BorderLayout());
		
		//the top portion of the document screen
		//
		JPanel pTop=new JPanel();
		pTop.add(new JLabel(colleagueScreenSPI.getResourceBundle().getString("ds.select.letter")+"-->"));
		
		String[] letters=new String[]{"a","A","b","B","c","C","d","D","e","E","f","F","g","G","h","H","i","I","j","J","k","K","l","L","m","M","n","N","o","O","p","P","q","Q","r","R","s","S","t","T","u","U","v","V","w","W","x","X","y","Y","z","Z"};
		final JComboBox cbLetters=new JComboBox(letters);
		cbLetters.setSelectedIndex(-1);//no selection
		pTop.add(cbLetters);
		
		final JButton  btnCount=new JButton(colleagueScreenSPI.getResourceBundle().getString("ds.go"));
		pTop.add(btnCount);
		
		final JButton btnSubmit = new JButton(colleagueScreenSPI.getResourceBundle().getString("ds.submit"));
		btnSubmit.setVisible(false);
		ActionListener alBtnCount=new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				btnCount.setVisible(false);
				btnSubmit.setVisible(true);
				f.repaint();
				try {
					colleagueScreenSPI.getMediator().startCounting(colleagueScreenSPI.getUserName(), (String)cbLetters.getSelectedItem());
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};
		
		btnCount.addActionListener(alBtnCount);
		
		f.add(pTop, BorderLayout.PAGE_START);
		
		//the center of the document screen showing the letters
		//
		this.text = new JTextArea("No document available");
		text.setEditable(false);
		f.add(text, BorderLayout.CENTER);
		
		//now comes the lower portion of the document screen
		//
		JPanel pBottom = new JPanel();

//		pBottom.add(new JLabel(colleagueScreenSPI.getResourceBundle().getString("ds.letter")+"-->"));
//		final JTextField txtLetter = new JTextField(2);
//		pBottom.add(txtLetter);

		pBottom.add(new JLabel(colleagueScreenSPI.getResourceBundle().getString("ds.count")+"-->"));
		final JTextField txtCount = new JTextField(6);
		pBottom.add(txtCount);

		
		pBottom.add(btnSubmit);

		ActionListener alBtnSubmit = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// btn submit was pushed..
				// send response data to mediator
				colleagueScreenSPI.getLog().log(
						getClass().getName()
								+ ": action performed. Saving count data..");
				try {

					colleagueScreenSPI.getMediator().saveCountData(
							colleagueScreenSPI.getUserName(),
							(String)cbLetters.getSelectedItem(),
							Integer.parseInt(txtCount.getText()));

					// clear text fields
					cbLetters.setSelectedIndex(-1);//no selection
					txtCount.setText("");
					
					btnCount.setVisible(true);
					btnSubmit.setVisible(false);
					
					f.repaint();
				} catch (NumberFormatException e) {
					// TODO Auto-generated catch block             
					e.printStackTrace();
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NoCurrentRunSelectedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};

		btnSubmit.addActionListener(alBtnSubmit);

		f.add(pBottom, BorderLayout.PAGE_END);

	}

	public void setDocument(Document document) {
		this.text.setText(document.toString());
	}

	@Override
	public void show() {
		this.f.setVisible(true);
	}

	@Override
	public void hide() {
		this.f.setVisible(false);
	}
}
