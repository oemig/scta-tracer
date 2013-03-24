package net.oemig.scta.tracer.question.who;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import net.oemig.scta.tracer.data.UserName;
import net.oemig.scta.tracer.evaluation.CountDataUtil;
import net.oemig.scta.tracer.model.ITraceModel;
import net.oemig.scta.tracer.model.binding.Trace.Session.Run.CountData;
import net.oemig.scta.tracer.question.IQuestionGenerator;
import net.oemig.scta.tracer.question.Question;
import net.oemig.scta.tracer.question.QuestionException;
import net.oemig.scta.tracer.question.QuestionType;

import com.google.common.collect.Lists;

public abstract class AbstractWhoQuestionGenerator implements
		IQuestionGenerator {

	private UserName userName;
	private ITraceModel model;

	public AbstractWhoQuestionGenerator(final UserName newUserName, 
										final ITraceModel newModel) {
		this.userName = newUserName;
		this.model = newModel;
	}

	@Override
	public Question generate() {

		//initialize answers with the user's name and nobody
		List<String> answers = Lists.newArrayList();
		answers.add(userName.toString());
		answers.add(CountDataUtil.NOBODY);

		//then try to add a third answer from count data
		//or any other random letter that nobody has counted
		//yet
		String letter;
		String correct;
		try {
			// get the "others" data
			CountData cd = CountDataUtil.random(getCountData());
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

	public abstract Collection<CountData> getCountData();

	public UserName getUserName() {
		return userName;
	}

	public ITraceModel getModel() {
		return model;
	}

}
