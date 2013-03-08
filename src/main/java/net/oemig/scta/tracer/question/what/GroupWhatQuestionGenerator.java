package net.oemig.scta.tracer.question.what;

import java.util.Collection;

import net.oemig.scta.tracer.evaluation.CountDataUtil;
import net.oemig.scta.tracer.model.ITraceModel;
import net.oemig.scta.tracer.model.binding.Trace.Session.Run.CountData;
import net.oemig.scta.tracer.question.QuestionType;

public class GroupWhatQuestionGenerator extends AbstractWhatQuestionGenerator {

	public GroupWhatQuestionGenerator(String aUserName, ITraceModel aTraceModel) {
		super(aUserName, aTraceModel);
	}

	@Override
	public QuestionType getType() {
		return QuestionType.GroupWhat;
	}

	@Override
	public Collection<CountData> getCountData() {
		return CountDataUtil.filterOthersCountData(getModel().getCurrentRun().getCountData(), getUserName());
	}

}
