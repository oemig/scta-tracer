package net.oemig.scta.tracer.question.who;

import java.util.Collection;

import net.oemig.scta.tracer.data.UserName;
import net.oemig.scta.tracer.evaluation.CountDataUtil;
import net.oemig.scta.tracer.model.ITraceModel;
import net.oemig.scta.tracer.model.binding.Trace.Session.Run.CountData;
import net.oemig.scta.tracer.question.QuestionType;

public class IndividualWhoQuestionGenerator extends
		AbstractWhoQuestionGenerator {

	public IndividualWhoQuestionGenerator(final UserName userName, ITraceModel model) {
		super(userName, model);
	}

	@Override
	public QuestionType getType() {
		return QuestionType.IndividualWho;
	}

	@Override
	public Collection<CountData> getCountData() {
		return CountDataUtil.filterMyCountData(getModel().getCurrentRun()
				.getCountData(), getUserName());
	}

}
