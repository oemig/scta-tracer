package net.oemig.scta.tracer.question;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.google.common.collect.Lists;

import net.oemig.scta.tracer.model.ITraceModel;

public class QuestionFactory {

	public static Question[] getMultiple(String userName, ITraceModel model) {
		List<IQuestionGenerator> generators = Lists.newArrayList();
		generators.add(new GroupHowQuestionGenerator(userName, model));
		generators.add(new GroupWhoQuestionGenerator(userName, model));
		generators.add(new IndividualHowQuestionGenerator(userName, model));
		generators.add(new IndividualWhoQuestionGenerator(userName, model));

		// create random order for questions
		Collections.shuffle(generators);

		List<Question> questions = new ArrayList<Question>();
		for (IQuestionGenerator g : generators) {
			questions.add(g.generate());
		}

		return questions.toArray(new Question[questions.size()]);
	}

}
