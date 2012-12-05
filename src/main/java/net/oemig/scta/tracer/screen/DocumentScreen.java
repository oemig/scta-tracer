package net.oemig.scta.tracer.screen;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import net.oemig.scta.tracer.ITracerColleagueScreenSPI;
import net.oemig.scta.tracer.run.Document;

public class DocumentScreen implements IScreen {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JFrame f;
	private JTextArea text;
	private ITracerColleagueScreenSPI colleagueScreenSPI;

	public DocumentScreen(ITracerColleagueScreenSPI newColleagueScreenSPI) {
		this.colleagueScreenSPI = newColleagueScreenSPI;

		this.f = new JFrame("Document Screen");
		// f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setSize(450, 800);
		f.setLayout(new BorderLayout());

		this.text = new JTextArea("No document available");
		text.setEditable(false);
		f.add(text, BorderLayout.CENTER);
		JPanel panel = new JPanel();

		panel.add(new JLabel("Letter:"));
		final JTextField txtLetter = new JTextField(2);
		panel.add(txtLetter);

		panel.add(new JLabel("Count:"));
		final JTextField txtCount = new JTextField(6);
		panel.add(txtCount);

		JButton btnSubmit = new JButton("Submit");
		panel.add(btnSubmit);

		ActionListener al = new ActionListener() {
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
							txtLetter.getText(),
							Integer.parseInt(txtCount.getText()));

					// clear text fields
					txtLetter.setText("");
					txtCount.setText("");
				} catch (NumberFormatException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};

		btnSubmit.addActionListener(al);

		f.add(panel, BorderLayout.PAGE_END);

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
