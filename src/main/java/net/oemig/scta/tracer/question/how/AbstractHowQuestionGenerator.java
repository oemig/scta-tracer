package net.oemig.scta.tracer.question.how;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import net.oemig.scta.model.ITraceModel;
import net.oemig.scta.model.binding.Trace.Session.Run.CountData;
import net.oemig.scta.model.data.QuestionType;
import net.oemig.scta.model.data.UserName;
import net.oemig.scta.tracer.evaluation.CountDataUtil;
import net.oemig.scta.tracer.question.IQuestionGenerator;
import net.oemig.scta.tracer.question.Question;
import net.oemig.scta.tracer.question.QuestionException;

import com.google.common.collect.Lists;

public abstract class AbstractHowQuestionGenerator implements
		IQuestionGenerator {

	private UserName userName;
	private ITraceModel model;

	public AbstractHowQuestionGenerator(final UserName newUserName, ITraceModel newModel) {
		this.userName = newUserName;
		this.model = newModel;
	}

	@Override
	public Question generate() {
		List<String> answers = Lists.newArrayList();
		answers.add(CountDataUtil.NONE);
		answers.add(Integer.toString(CountDataUtil.getRandomNumber(25)));

		String letter;
		String correct;
		try {
			// get the "others" data
			CountData cd = CountDataUtil.random(getCountData());
			answers.add(cd.getQuantity().toString());
			letter = cd.getLetter();
			correct = cd.getQuantity().toString();
		} catch (QuestionException qe) {
			// otherwise select uncounted letters.. since nobody counted these
			List<String> uncountedLetters = CountDataUtil
					.getUncountedLetters(model.getCurrentRun().getCountData());
			Collections.shuffle(uncountedLetters);
			letter = uncountedLetters.get(0);
			answers.add(Integer.toString(CountDataUtil.getRandomNumber(25)));

			correct = CountDataUtil.NONE;
		}

		Collections.shuffle(answers);

		return new Question("How many " + letter + "'s were counted? ("
				+ getType().name() + ")", answers.get(0), answers.get(1),
				answers.get(2), correct, getType());
	}

	public abstract QuestionType getType();

	public abstract Collection<CountData> getCountData();

	public UserName getUserName() {
		return userName;
	}

//	public void setUserName(UserName userName) {
//		this.userName = userName;
//	}

	public ITraceModel getModel() {
		return model;
	}

	public void setModel(ITraceModel model) {
		this.model = model;
	}

}
