package net.oemig.test;

import java.awt.BorderLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.UIManager;

import org.apache.commons.lang.time.StopWatch;

public class FreezeProbeScreen {

	public static void main(String[] args)

	{

		try {

			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

		} catch (Exception e) {
			e.printStackTrace();
		}

		new FreezeProbeScreen().doFreezeProbe();

	}

	private int questionCounter;

	private int numberOfQuestions;

	private JFrame f;

	private JLabel lbQuestion;

	private JRadioButton rbOption1;

	private JRadioButton rbOption2;

	private JRadioButton rbOption3;

	private StopWatch stopWatch;

	private ButtonGroup g;

	public FreezeProbeScreen() {
		this.questionCounter = 0;
		this.numberOfQuestions = 5;
		this.f = new JFrame("Freeze Probe Screen");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setLocationRelativeTo(null);
		f.setSize(400, 400);
		f.setLayout(new BorderLayout());
		this.lbQuestion = new JLabel();
		f.add(lbQuestion, BorderLayout.PAGE_START);
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		this.stopWatch = new StopWatch();
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
					System.out.println("item selected: "
							+ ((JRadioButton) e.getItem()).getText());

					System.out.println("duration (time in millis): "
							+ stopWatch.getTime());

					// continue with next question
					questionCounter++;
					if (questionCounter < numberOfQuestions) {
						stopWatch.reset();
						fillQuestion(questionCounter);
					} else {
						System.out.println("Finished");
						f.dispose();
					}
				}
			}
		};

		rbOption1.addItemListener(il);
		rbOption2.addItemListener(il);
		rbOption3.addItemListener(il);

		f.setVisible(true);

	}

	public void doFreezeProbe() {
		fillQuestion(questionCounter);

	}

	public void fillQuestion(int i) {
		this.g.clearSelection();
		this.lbQuestion.setText("Question " + i);
		this.rbOption1.setText("Option 1:" + i);
		this.rbOption2.setText("Option 2:" + i);
		this.rbOption3.setText("Option 3:" + i);
		stopWatch.start();

	}

}
