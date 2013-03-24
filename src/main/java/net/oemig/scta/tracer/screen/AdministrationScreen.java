package net.oemig.scta.tracer.screen;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.border.TitledBorder;

import net.oemig.scta.tracer.IRegistrationListener;
import net.oemig.scta.tracer.ITracerMediatorScreenSPI;
import net.oemig.scta.tracer.data.UserName;
import net.oemig.scta.tracer.evaluation.exception.ModelMissingException;
import net.oemig.scta.tracer.evaluation.impl.SctaV1EvaluationImpl;
import net.oemig.scta.tracer.exception.TracerException;
import net.oemig.scta.tracer.jfreechart.SCTAItemLabelGenerator;
import net.oemig.scta.tracer.jfreechart.SCTARenderer;
import net.oemig.scta.tracer.jfreechart.data.DefaultSCTADataset;
import net.oemig.scta.tracer.log.ILogListener;
import net.oemig.scta.tracer.model.binding.Trace.Session.Run.Participant;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.Range;
import org.jfree.data.xy.XYDataset;

public class AdministrationScreen implements IScreen {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9169397434346157649L;

	private ITracerMediatorScreenSPI mediatorScreenSPI;
	private JFrame f;

	public AdministrationScreen(ITracerMediatorScreenSPI mediatorImpl) {
		this.mediatorScreenSPI = mediatorImpl;
		this.f = new JFrame("SCTA Tracer");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JTabbedPane tabbedPane = new JTabbedPane();
		tabbedPane.addTab("Session", this.createSessionPanel());
		tabbedPane.addTab("Evaluation", this.createEvaluationPanel());
		tabbedPane.addTab("Configuration", this.createConfigurationPanel());

		f.getContentPane().add(tabbedPane);
		f.setSize(450, 500);
	}

	/**
	 * Session panel
	 */
	private JPanel createSessionPanel() {
		JPanel sessionPanel = new JPanel();

		JPanel logPanel = new javax.swing.JPanel();
		JScrollPane jScrollPane1 = new javax.swing.JScrollPane();
		final JTextArea jTextArea1 = new javax.swing.JTextArea();
		JPanel userPanel = new javax.swing.JPanel();
		JScrollPane jScrollPane3 = new javax.swing.JScrollPane();
		JList userList = new javax.swing.JList();
		final JToggleButton btnStartPauseRun = new javax.swing.JToggleButton();
		JButton btnSave = new javax.swing.JButton();
		JButton btnExport = new javax.swing.JButton();
		JLabel lblTraceName = new javax.swing.JLabel();
		JLabel lblSessionName = new javax.swing.JLabel();

		logPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Log"));

		jTextArea1.setColumns(20);
		jTextArea1.setEditable(false);
		jTextArea1.setLineWrap(true);
		jTextArea1.setRows(5);
		jTextArea1.setWrapStyleWord(true);
		jScrollPane1.setViewportView(jTextArea1);

		ILogListener logListener = new ILogListener() {

			@Override
			public void onEntry(String entry) {
				jTextArea1.setText(jTextArea1.getText() + " --" + entry);
			}
		};

		mediatorScreenSPI.getEnvironment().getLogger().addListener(logListener);

		javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(
				logPanel);
		logPanel.setLayout(jPanel1Layout);
		jPanel1Layout.setHorizontalGroup(jPanel1Layout.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING).addComponent(
				jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 365,
				Short.MAX_VALUE));
		jPanel1Layout.setVerticalGroup(jPanel1Layout.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING).addComponent(
				jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 107,
				Short.MAX_VALUE));

		userPanel.setBorder(javax.swing.BorderFactory
				.createTitledBorder("Registered Users"));
		// --
		final DefaultListModel listModel = new DefaultListModel();
		for (Participant p : this.mediatorScreenSPI.getEnvironment().getTraceModel()
				.getCurrentRun().getParticipant()) {
			listModel.addElement(p.getName());
		}

		IRegistrationListener registrationListener = new IRegistrationListener() {
			@Override
			public void update() {
				listModel.clear();
				for (UserName registeredUser : mediatorScreenSPI
						.getRegisteredUsers()) {
					listModel.addElement(registeredUser.toString());
				}
			}

		};

		mediatorScreenSPI.addRegistrationListener(registrationListener);

		// --
		userList.setModel(listModel);
		jScrollPane3.setViewportView(userList);

		javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(
				userPanel);
		userPanel.setLayout(jPanel2Layout);
		jPanel2Layout.setHorizontalGroup(jPanel2Layout.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING).addComponent(
				jScrollPane3, javax.swing.GroupLayout.Alignment.TRAILING,
				javax.swing.GroupLayout.DEFAULT_SIZE, 207, Short.MAX_VALUE));
		jPanel2Layout.setVerticalGroup(jPanel2Layout.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING).addGroup(
				jPanel2Layout
						.createSequentialGroup()
						.addComponent(jScrollPane3,
								javax.swing.GroupLayout.PREFERRED_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE,
								javax.swing.GroupLayout.PREFERRED_SIZE)
						.addGap(0, 0, Short.MAX_VALUE)));

		btnStartPauseRun.setText("Start Run");

		// --
		ActionListener startPauseListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (btnStartPauseRun.getText().equals("Start Run")) {
					btnStartPauseRun.setText("Stop Run");
					try {
						mediatorScreenSPI.startAssessmentRun(mediatorScreenSPI
								.getUserName() + " started the assement run");
					} catch (TracerException e1) {
						mediatorScreenSPI.getEnvironment()
								.getLogger()
								.log(getClass().getName()
										+ ": Assessment run could not be started: "
										+ e1.getStackTrace());
					}
				} else {
					btnStartPauseRun.setText("Start Run");
					try {
						mediatorScreenSPI.stopAssessmentRun(mediatorScreenSPI
								.getUserName() + " paused the assement run");
					} catch (TracerException e1) {
						mediatorScreenSPI.getEnvironment()
								.getLogger()
								.log(getClass().getName()
										+ ": Assessment run could not be stopped.");
					}
				}
			}
		};
		// --
		btnStartPauseRun.addActionListener(startPauseListener);

		btnSave.setText("Save");
		ActionListener saveListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				mediatorScreenSPI.getEnvironment().getLogger().log("Saving data to file.");
				mediatorScreenSPI.getEnvironment().getTraceModel().save();

			}
		};

		btnSave.addActionListener(saveListener);

		btnExport.setText("Export");
		ActionListener exportListener=new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				mediatorScreenSPI.getEnvironment().getLogger().log("Exporting data to file.");
				mediatorScreenSPI.getEnvironment().getTraceModel().export();
			}
		};
		btnExport.addActionListener(exportListener);

		lblTraceName.setText(mediatorScreenSPI.getEnvironment().getTraceModel()
				.getCurrentTrace().getName());
		lblTraceName.setBorder(javax.swing.BorderFactory
				.createTitledBorder("Trace"));

		lblSessionName.setText(mediatorScreenSPI.getEnvironment().getTraceModel()
				.getCurrentSession().getName());
		lblSessionName.setBorder(javax.swing.BorderFactory
				.createTitledBorder("Session"));

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(
				sessionPanel);
		sessionPanel.setLayout(layout);
		layout.setHorizontalGroup(layout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(
						layout.createSequentialGroup()
								.addContainerGap()
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.LEADING)
												.addGroup(
														layout.createSequentialGroup()
																.addComponent(
																		userPanel,
																		javax.swing.GroupLayout.PREFERRED_SIZE,
																		javax.swing.GroupLayout.DEFAULT_SIZE,
																		javax.swing.GroupLayout.PREFERRED_SIZE)
																.addPreferredGap(
																		javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
																.addGroup(
																		layout.createParallelGroup(
																				javax.swing.GroupLayout.Alignment.LEADING)
																				.addComponent(
																						btnStartPauseRun,
																						javax.swing.GroupLayout.DEFAULT_SIZE,
																						javax.swing.GroupLayout.DEFAULT_SIZE,
																						Short.MAX_VALUE)
																				.addGroup(
																						layout.createSequentialGroup()
																								.addComponent(
																										btnSave,
																										javax.swing.GroupLayout.PREFERRED_SIZE,
																										67,
																										javax.swing.GroupLayout.PREFERRED_SIZE)
																								.addPreferredGap(
																										javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
																								.addComponent(
																										btnExport,
																										javax.swing.GroupLayout.DEFAULT_SIZE,
																										javax.swing.GroupLayout.DEFAULT_SIZE,
																										Short.MAX_VALUE))
																				.addComponent(
																						lblTraceName,
																						javax.swing.GroupLayout.DEFAULT_SIZE,
																						javax.swing.GroupLayout.DEFAULT_SIZE,
																						Short.MAX_VALUE)
																				.addComponent(
																						lblSessionName,
																						javax.swing.GroupLayout.DEFAULT_SIZE,
																						javax.swing.GroupLayout.DEFAULT_SIZE,
																						Short.MAX_VALUE)))
												.addComponent(
														logPanel,
														javax.swing.GroupLayout.PREFERRED_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.PREFERRED_SIZE))
								.addContainerGap()));
		layout.setVerticalGroup(layout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(
						javax.swing.GroupLayout.Alignment.TRAILING,
						layout.createSequentialGroup()
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.TRAILING)
												.addGroup(
														layout.createSequentialGroup()
																.addContainerGap(
																		javax.swing.GroupLayout.DEFAULT_SIZE,
																		Short.MAX_VALUE)
																.addComponent(
																		userPanel,
																		javax.swing.GroupLayout.PREFERRED_SIZE,
																		javax.swing.GroupLayout.DEFAULT_SIZE,
																		javax.swing.GroupLayout.PREFERRED_SIZE))
												.addGroup(
														layout.createSequentialGroup()
																.addGap(22, 22,
																		22)
																.addComponent(
																		lblTraceName)
																.addPreferredGap(
																		javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
																.addComponent(
																		lblSessionName)
																.addPreferredGap(
																		javax.swing.LayoutStyle.ComponentPlacement.RELATED,
																		javax.swing.GroupLayout.DEFAULT_SIZE,
																		Short.MAX_VALUE)
																.addComponent(
																		btnStartPauseRun)
																.addPreferredGap(
																		javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
																.addGroup(
																		layout.createParallelGroup(
																				javax.swing.GroupLayout.Alignment.BASELINE)
																				.addComponent(
																						btnSave)
																				.addComponent(
																						btnExport))))
								.addGap(18, 18, Short.MAX_VALUE)
								.addComponent(logPanel,
										javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addContainerGap()));

		// this panel contains:
		// - a list of currently registered users
		// - a button to start/pause the assessment run (toggle button)
		// - a button to save the current trace model into a file

		return sessionPanel;
	}

	/**
	 * Evaluation panel
	 */
	private JPanel createEvaluationPanel() {
		JPanel evaluationPanel = new JPanel();

		// FIXME this currently is a fake dataset.. should be replaced by one
		// resulting from an evaluation of the trace model
		final XYDataset emptyDataset = DefaultSCTADataset.getInstance(); // an
																			// empty
		// data set

		JFreeChart chart = createChart(evaluationPanel, emptyDataset);

		final ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setPreferredSize(new java.awt.Dimension(400, 400));

		TitledBorder title = BorderFactory.createTitledBorder(
				BorderFactory.createLineBorder(Color.BLACK), "SCTA-4I diagram");
		title.setTitlePosition(TitledBorder.DEFAULT_POSITION);

		chartPanel.setBorder(title);
		evaluationPanel.add(chartPanel);

		// FIXME run evaluation button triggers evaluation.. the result is a
		JButton btnRunEvaluation1 = new JButton("Run Evaluation v1");
		btnRunEvaluation1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					chartPanel.setChart(mediatorScreenSPI
							.with(SctaV1EvaluationImpl.getInstance())
							.runEvaluation().getChart());
					chartPanel.updateUI();
				} catch (ModelMissingException e) {
					mediatorScreenSPI.getEnvironment().getLogger().log(e.getLocalizedMessage());
				}

			}
		});
		evaluationPanel.add(btnRunEvaluation1);

		return evaluationPanel;
	}

	private JFreeChart createChart(JPanel evaluationPanel,
			final XYDataset dataset) {
		JFreeChart chart = ChartFactory.createXYLineChart("Trace: TestSystem",
				"Response time/forgetting time ratio [%]", "Error rate [%]",
				dataset, PlotOrientation.VERTICAL, true, false, false);

		chart.setBackgroundPaint(evaluationPanel.getBackground());

		XYLineAndShapeRenderer renderer = new SCTARenderer();

		// background image
		URL picURL = getClass().getResource("/background.gif");
		try {
			Image image = ImageIO.read(picURL);
			chart.getPlot().setBackgroundImage(image);

		} catch (IOException e) {
			// failed to load background image for chart
			e.printStackTrace();
		}

		XYPlot plot = (XYPlot) chart.getPlot();
		plot.getDomainAxis().setAutoRange(false);
		plot.getDomainAxis().setRange(new Range(0, 100));
		plot.getRangeAxis().setAutoRange(false);
		plot.getRangeAxis().setRange(new Range(0, 100));

		renderer.setBaseItemLabelGenerator(new SCTAItemLabelGenerator());
		renderer.setBaseItemLabelsVisible(true);
		renderer.setSeriesVisibleInLegend(0, false);

		chart.getXYPlot().setRenderer(renderer);
		return chart;
	}

	/**
	 * Configuration panel
	 */
	private JPanel createConfigurationPanel() {
		JPanel configurationPanel = new JPanel();
		configurationPanel.setLayout(new BorderLayout());

		JPanel runParameter = new JPanel();
		runParameter.setSize(500, 300);
		runParameter.setLayout(new GridLayout(2, 2, 10, 10));
		runParameter.add(new JLabel("Assessment run duration"));
		runParameter.add(new JTextField(25));
		runParameter.add(new JLabel("Number of freeze probes"));
		runParameter.add(new JTextField(25));

		TitledBorder runParameterTitle = BorderFactory.createTitledBorder(
				BorderFactory.createLineBorder(Color.BLACK),
				"Assessment Run Parameters");
		runParameterTitle.setTitlePosition(TitledBorder.DEFAULT_POSITION);

		runParameter.setBorder(runParameterTitle);

		JPanel sessionPrameter = new JPanel();
		sessionPrameter.setLayout(new GridLayout(2, 2, 10, 10));
		sessionPrameter.add(new JLabel("Assessment run duration"));
		sessionPrameter.add(new JTextField(25));
		sessionPrameter.add(new JLabel("Number of freeze probes"));
		sessionPrameter.add(new JTextField(25));

		TitledBorder sessionParameterTitle = BorderFactory.createTitledBorder(
				BorderFactory.createLineBorder(Color.BLACK),
				"Assessment Session Parameters");
		sessionParameterTitle.setTitlePosition(TitledBorder.DEFAULT_POSITION);

		sessionPrameter.setBorder(sessionParameterTitle);

		JPanel parameterPanel = new JPanel();
		parameterPanel.setLayout(new GridLayout(3, 1));
		parameterPanel.add(new JLabel("The follwing parameters..."));
		parameterPanel.add(sessionPrameter);
		parameterPanel.add(runParameter);

		JPanel buttonPanel = new JPanel();
		buttonPanel.add(new JButton("Save"));
		buttonPanel.add(new JButton("Cancel"));
		configurationPanel.add(parameterPanel, BorderLayout.PAGE_START);
		configurationPanel.add(buttonPanel, BorderLayout.PAGE_END);

		return configurationPanel;
	}

	/**
	 * @see IScreen#show()
	 */
	@Override
	public void show() {
		this.f.setVisible(true);
	}

	/**
	 * @see IScreen#hide()
	 */
	@Override
	public void hide() {
		f.setVisible(false);
	}

}
