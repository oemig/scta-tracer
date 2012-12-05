package net.oemig.scta.tracer.screen;

import java.awt.BorderLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.rmi.RemoteException;

import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import net.oemig.scta.tracer.ITracerColleagueScreenSPI;
import net.oemig.scta.tracer.question.Question;
import net.oemig.scta.tracer.util.SerializableStopWatch;

public class FreezeProbeScreen implements IScreen {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private ITracerColleagueScreenSPI colleagueScreenSPI;
	private JFrame f;
	private JLabel lbQuestion;
	private SerializableStopWatch stopWatch;
	private JRadioButton rbOption1;
	private JRadioButton rbOption2;
	private JRadioButton rbOption3;
	private ButtonGroup g;
	private int questionCounter;
	private int numberOfQuestions;
	private Question[] questions;

	public FreezeProbeScreen(ITracerColleagueScreenSPI newColleagueScreenSPI) {
		this.colleagueScreenSPI = newColleagueScreenSPI;
		this.f = new JFrame("Freeze Probe");
		this.questionCounter = 0;
		this.numberOfQuestions = 0;
		this.lbQuestion = new JLabel();
		f.add(lbQuestion, BorderLayout.PAGE_START);
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		this.stopWatch = new SerializableStopWatch();
		this.rbOption1 = new JRadioButton();
		panel.add(rbOption1, BorderLayout.PAGE_START);
		this.rbOption2 = new JRadioButton();
		panel.add(rbOption2, BorderLayout.CENTER);
		this.rbOption3 = new JRadioButton();
		panel.add(rbOption3, BorderLayout.PAGE_END);
		f.add(panel, BorderLayout.CENTER);
		this.g = new ButtonGroup();
		g.add(rbOption1);
		g.add(rbOption2);
		g.add(rbOption3);
		ItemListener il = new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					stopWatch.stop();
					// System.out.println("item selected: "
					// + ();
					//
					// System.out.println("duration (time in millis): "
					// + stopWatch.getTime());

					try {
						colleagueScreenSPI.getMediator().saveResponseData(
								colleagueScreenSPI.getUserName(),
								Boolean.valueOf(
										((JRadioButton) e.getItem())
												.getActionCommand())
										.booleanValue(),
								(int) stopWatch.getTime());
					} catch (RemoteException e1) {
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
			}
		};

		rbOption1.addItemListener(il);
		rbOption2.addItemListener(il);
		rbOption3.addItemListener(il);

		f.setSize(450, 500);

	}

	public void doFreezeProbe(Question[] questions) {
		this.questionCounter = 0;
		this.numberOfQuestions = questions.length;
		this.questions = questions;
		fillQuestion(0);
	}

	public void fillQuestion(int i) {
		this.f.setTitle("Freeze Probe - Question #" + (i + 1));
		this.g.clearSelection();

		this.lbQuestion.setText("Question #" + (i + 1) + ": "
				+ questions[i].get());
		this.rbOption1.setText("Answer 1: " + questions[i].getAnswer1());
		this.rbOption2.setText("Answer 2: " + questions[i].getAnswer2());
		this.rbOption3.setText("Answer 3: " + questions[i].getAnswer3());

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
