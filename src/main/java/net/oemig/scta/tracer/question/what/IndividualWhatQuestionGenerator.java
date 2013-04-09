package net.oemig.scta.tracer.question.what;

import java.util.Collection;

import net.oemig.scta.model.ITraceModel;
import net.oemig.scta.model.binding.Trace.Session.Run.CountData;
import net.oemig.scta.model.data.QuestionType;
import net.oemig.scta.model.data.UserName;
import net.oemig.scta.tracer.evaluation.CountDataUtil;

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
