package net.oemig.scta.tracer.question;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import net.oemig.scta.model.data.UserName;
import net.oemig.scta.model.exception.NoCurrentRunSelectedException;
import net.oemig.scta.tracer.Environment;
import net.oemig.scta.tracer.coordination.question.CoordinationWhoQuestionGenerator;
import net.oemig.scta.tracer.question.exception.QuestionGenerationFailedException;
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

	public static Question[] getMultiple(final UserName userName, Environment anEnvironment, Set<UserName> someUserNames) throws NoCurrentRunSelectedException, QuestionGenerationFailedException {
		List<IQuestionGenerator> generators = Lists.newArrayList();
//		generators.add(new GroupHowQuestionGenerator(userName, model));
		generators.add(new GroupWhoQuestionGenerator(userName, anEnvironment.getTraceModel()));
		generators.add(new GroupWhatQuestionGenerator(userName, anEnvironment.getTraceModel()));
//		generators.add(new IndividualHowQuestionGenerator(userName, model));
		generators.add(new IndividualWhoQuestionGenerator(userName, anEnvironment.getTraceModel()));
		generators.add(new IndividualWhatQuestionGenerator(userName, anEnvironment.getTraceModel()));
		//
		//Coordination questions
		generators.add(new CoordinationWhoQuestionGenerator(anEnvironment.getCoordinationSupportSystem(), someUserNames));

		// create random order for questions
		Collections.shuffle(generators);

		List<Question> questions = Lists.newArrayList();
		for (IQuestionGenerator g : generators) {
			questions.add(g.generate());
		}

		return questions.toArray(new Question[questions.size()]);
	}

}
