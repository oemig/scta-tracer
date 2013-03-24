package net.oemig.scta.tracer.question.what;

import java.util.Collection;

import net.oemig.scta.tracer.data.UserName;
import net.oemig.scta.tracer.evaluation.CountDataUtil;
import net.oemig.scta.tracer.model.ITraceModel;
import net.oemig.scta.tracer.model.binding.Trace.Session.Run.CountData;
import net.oemig.scta.tracer.question.QuestionType;

public class IndividualWhatQuestionGenerator extends
		AbstractWhatQuestionGenerator {

	public IndividualWhatQuestionGenerator(final UserName aUserName,
			ITraceModel aTraceModel) {
		super(aUserName, aTraceModel);
	}

	@Override
	public QuestionType getType() {
		return QuestionType.IndividualWhat;
	}

	@Override
	public Collection<CountData> getCountData() {
		return CountDataUtil.filterMyCountData(getModel().getCurrentRun().getCountData(), getUserName());
	}

}
