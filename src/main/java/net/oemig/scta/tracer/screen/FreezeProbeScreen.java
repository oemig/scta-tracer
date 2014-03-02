package net.oemig.scta.tracer.screen;

import java.awt.BorderLayout;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import net.oemig.scta.model.data.Millisecond;
import net.oemig.scta.model.exception.NoCurrentRunSelectedException;
import net.oemig.scta.tracer.ITracerColleagueScreenSPI;
import net.oemig.scta.tracer.question.Question;
import net.oemig.scta.tracer.util.SerializableStopWatch;

public class FreezeProbeScreen implements IScreen {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private final ITracerColleagueScreenSPI colleagueScreenSPI;
	private final JFrame f;
	private final JLabel lbQuestion;
	private final SerializableStopWatch stopWatch;
	private final JButton rbOption1;
	private final JButton rbOption2;
	private final JButton rbOption3;
//	private final ButtonGroup g;
	private int questionCounter;
	private int numberOfQuestions;
	private Question[] questions;
	private final int width=450;
	private final int height=500;


	/**
	 * Constructor 
	 * @param newColleagueScreenSPI
	 * @throws RemoteException
	 */
	public FreezeProbeScreen(ITracerColleagueScreenSPI newColleagueScreenSPI) throws RemoteException {
		this.colleagueScreenSPI = newColleagueScreenSPI;
		this.f = new JFrame(colleagueScreenSPI.getResourceBundle().getString("fps.title"));
		Point p=GraphicsEnvironment.getLocalGraphicsEnvironment().getCenterPoint();
		Point leftCorner=new Point(p.x-Math.round(width/2), p.y-Math.round(height/2));
		f.setLocation(leftCorner);
		f.setSize(width, height);
		
		this.questionCounter = 0;
		this.numberOfQuestions = 0;
		this.lbQuestion = new JLabel();
		f.add(lbQuestion, BorderLayout.PAGE_START);
		//the button panel
		JPanel pButtons = new JPanel();
		pButtons.setLayout(new GridLayout(3,3));
		this.stopWatch = new SerializableStopWatch();
		this.rbOption1 = new JButton();
		pButtons.add(rbOption1);
		this.rbOption2 = new JButton();
		pButtons.add(rbOption2);
		this.rbOption3 = new JButton();
		pButtons.add(rbOption3);
		f.add(pButtons, BorderLayout.CENTER);
//		this.g = new ButtonGroup();
//		g.add(rbOption1);
//		g.add(rbOption2);
//		g.add(rbOption3);
		
		//an item listener waits for the selection of an
		//option and forwards the answer to the mediator
		ActionListener il = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				stopWatch.stop();

				try {
					colleagueScreenSPI.getMediator().saveResponseData(
							colleagueScreenSPI.getUserName(),
							Boolean.valueOf(
									arg0.getActionCommand())
									.booleanValue(),
							Millisecond.of(stopWatch.getTime()),
							questions[questionCounter].getType()
							);
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (NoCurrentRunSelectedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				// continue with next question
				questionCounter++;
				stopWatch.reset();
				if (questionCounter < numberOfQuestions) {
					fillQuestion(questionCounter);
				} else {
					System.out.println("Finished");

					colleagueScreenSPI.finishedFreezeProbe();
				}
				
			}
		};
		//add item listener to each option
		rbOption1.addActionListener(il);
		rbOption2.addActionListener(il);
		rbOption3.addActionListener(il);

		

	}

	public void doFreezeProbe(Question[] questions) {
		this.questionCounter = 0;
		this.numberOfQuestions = questions.length;
		this.questions = questions;
		fillQuestion(0);
	}

	private void fillQuestion(int i) {
		this.f.setTitle(colleagueScreenSPI.getResourceBundle().getString("fps.title"));
//		this.g.clearSelection();
		
		this.lbQuestion.setText("<html>"+colleagueScreenSPI.getResourceBundle().getString("fps.question") + (i + 1) + ": <br><font size=+2>"
				+ questions[i].get()+"</font></html>");
		this.rbOption1.setText("<html><font size=+1>"+colleagueScreenSPI.getResourceBundle().getString("fps.answer")+" 1: " + questions[i].getAnswer1()+"</font></html>");
		this.rbOption2.setText("<html><font size=+1>"+colleagueScreenSPI.getResourceBundle().getString("fps.answer")+" 2: " + questions[i].getAnswer2()+"</font></html>");
		this.rbOption3.setText("<html><font size=+1>"+colleagueScreenSPI.getResourceBundle().getString("fps.answer")+" 3: " + questions[i].getAnswer3()+"</font></html>");

		this.rbOption1.setActionCommand(Boolean.valueOf(
				questions[i].getAnswer1().equals(
						questions[i].getCorrectAnswer())).toString());
		this.rbOption2.setActionCommand(Boolean.valueOf(
				questions[i].getAnswer2().equals(
						questions[i].getCorrectAnswer())).toString());
		this.rbOption3.setActionCommand(Boolean.valueOf(
				questions[i].getAnswer3().equals(
						questions[i].getCorrectAnswer())).toString());
		stopWatch.start();

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
