 package net.oemig.scta.tracer.question.who;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

import net.oemig.scta.model.ICountData;
import net.oemig.scta.model.ITraceModel;
import net.oemig.scta.model.data.QuestionType;
import net.oemig.scta.model.data.UserName;
import net.oemig.scta.model.exception.NoCurrentRunSelectedException;
import net.oemig.scta.tracer.evaluation.CountDataUtil;
import net.oemig.scta.tracer.question.IQuestionGenerator;
import net.oemig.scta.tracer.question.Question;
import net.oemig.scta.tracer.question.QuestionException;

import com.google.common.collect.Lists;

public abstract class AbstractWhoQuestionGenerator implements
		IQuestionGenerator {

	private final UserName userName;
	private final ITraceModel model;
	private final ResourceBundle resourceBundle;

	public AbstractWhoQuestionGenerator(final UserName newUserName, 
										final ITraceModel newModel,
										final ResourceBundle aResourceBundle) {
		this.userName = newUserName;
		this.model = newModel;
		this.resourceBundle=aResourceBundle;
	}

	@Override
	public Question generate() throws NoCurrentRunSelectedException {

		//initialize answers with the user's name and nobody
		List<String> answers = Lists.newArrayList();
		answers.add(userName.toString());
		answers.add(resourceBundle.getString("q.who.nobody"));

		//then try to add a third answer from count data
		//or any other random letter that nobody has counted
		//yet
		String letter;
		String correct;
		try {
			// get the "others" data
			ICountData cd = CountDataUtil.random(getCountData());
			letter = cd.getLetter();
			correct = cd.getParticipant().toString();
			answers.add(cd.getParticipant().toString());
		} catch (QuestionException qe) {
			// otherwise select uncounted letters.. since nobody counted these
			List<String> uncountedLetters = CountDataUtil
					.getUncountedLetters(model.getCurrentRun().getCountData());
			Collections.shuffle(uncountedLetters);
			letter = uncountedLetters.get(0);
			correct = resourceBundle.getString("q.who.nobody");
			answers.add("");
		}

		Collections.shuffle(answers);

		return new Question(resourceBundle.getString("q.who.1") + letter + resourceBundle.getString("q.who.2"), answers.get(0), answers.get(1),
				answers.get(2), correct, getType());
	}

	public abstract QuestionType getType();

	public abstract Collection<ICountData> getCountData() throws NoCurrentRunSelectedException;

	public UserName getUserName() {
		return userName;
	}

	public ITraceModel getModel() {
		return model;
	}

}
