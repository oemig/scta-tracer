package net.oemig.scta.tracer.question;

import java.util.Collections;
import java.util.List;

import net.oemig.scta.tracer.data.UserName;
import net.oemig.scta.tracer.model.ITraceModel;
import net.oemig.scta.tracer.question.how.GroupHowQuestionGenerator;
import net.oemig.scta.tracer.question.how.IndividualHowQuestionGenerator;
import net.oemig.scta.tracer.question.what.GroupWhatQuestionGenerator;
import net.oemig.scta.tracer.question.what.IndividualWhatQuestionGenerator;
import net.oemig.scta.tracer.question.who.GroupWhoQuestionGenerator;
import net.oemig.scta.tracer.question.who.IndividualWhoQuestionGenerator;

import com.google.common.collect.Lists;

/**
 * {@link QuestionFactory} wraps multiple {@link IQuestionGenerator}s that
 * produce questions for freeze probes.
 * @see GroupHowQuestionGenerator
 * @see GroupWhoQuestionGenerator
 * @see IndividualHowQuestionGenerator
 * @see IndividualWhoQuestionGenerator
 * @author oemig
 */
public final class QuestionFactory {
	
	//private constructor
	private QuestionFactory() {
	}

	public static Question[] getMultiple(final UserName userName, ITraceModel model) {
		List<IQuestionGenerator> generators = Lists.newArrayList();
//		generators.add(new GroupHowQuestionGenerator(userName, model));
		generators.add(new GroupWhoQuestionGenerator(userName, model));
		generators.add(new GroupWhatQuestionGenerator(userName, model));
//		generators.add(new IndividualHowQuestionGenerator(userName, model));
		generators.add(new IndividualWhoQuestionGenerator(userName, model));
		generators.add(new IndividualWhatQuestionGenerator(userName, model));

		// create random order for questions
		Collections.shuffle(generators);

		List<Question> questions = Lists.newArrayList();
		for (IQuestionGenerator g : generators) {
			questions.add(g.generate());
		}

		return questions.toArray(new Question[questions.size()]);
	}

}
