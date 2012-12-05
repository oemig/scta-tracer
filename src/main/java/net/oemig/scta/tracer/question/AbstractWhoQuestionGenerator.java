package net.oemig.scta.tracer.question;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.oemig.scta.tracer.evaluation.CountDataUtil;
import net.oemig.scta.tracer.model.ITraceModel;
import net.oemig.scta.tracer.model.binding.Trace.Session.Run.CountData;

public abstract class AbstractWhoQuestionGenerator implements
		IQuestionGenerator {

	private String userName;
	private ITraceModel model;

	public AbstractWhoQuestionGenerator(String newUserName, ITraceModel newModel) {
		this.userName = newUserName;
		this.model = newModel;
	}

	@Override
	public Question generate() {

		List<String> answers = new ArrayList<String>();
		answers.add(userName);
		answers.add(CountDataUtil.NOBODY);

		String letter;
		String correct;
		try {
			// get the "others" data
			CountData cd = CountDataUtil.getRandomCountData(getCountData());
			letter = cd.getLetter();
			correct = cd.getParticipant();
			answers.add(cd.getParticipant());
		} catch (QuestionException qe) {
			// otherwise select uncounted letters.. since nobody counted these
			List<String> uncountedLetters = CountDataUtil
					.getUncountedLetters(model.getCurrentRun().getCountData());
			Collections.shuffle(uncountedLetters);
			letter = uncountedLetters.get(0);
			correct = CountDataUtil.NOBODY;
			answers.add("");
		}

		Collections.shuffle(answers);

		return new Question("Who counted " + letter + "'s? ("
				+ getType().name() + ")", answers.get(0), answers.get(1),
				answers.get(2), correct, getType());
	}

	public abstract QuestionType getType();

	public abstract List<CountData> getCountData();

	public String getUserName() {
		return userName;
	}

	public ITraceModel getModel() {
		return model;
	}

}
