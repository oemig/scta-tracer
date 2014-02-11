package net.oemig.scta.tracer.question;

import net.oemig.scta.model.exception.NoCurrentRunSelectedException;
import net.oemig.scta.tracer.question.exception.QuestionGenerationFailedException;

public interface IQuestionGenerator {
	public Question generate() throws NoCurrentRunSelectedException, QuestionGenerationFailedException;
}
