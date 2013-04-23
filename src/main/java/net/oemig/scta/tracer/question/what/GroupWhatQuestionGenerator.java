package net.oemig.scta.tracer.question.what;

import java.util.Collection;

import net.oemig.scta.model.ICountData;
import net.oemig.scta.model.ITraceModel;
import net.oemig.scta.model.data.QuestionType;
import net.oemig.scta.model.data.UserName;
import net.oemig.scta.tracer.evaluation.CountDataUtil;

public class GroupWhatQuestionGenerator extends AbstractWhatQuestionGenerator {

	public GroupWhatQuestionGenerator(final UserName aUserName, ITraceModel aTraceModel) {
		super(aUserName, aTraceModel);
	}

	@Override
	public QuestionType getType() {
		return QuestionType.GroupWhat;
	}

	@Override
	public Collection<ICountData> getCountData() {
		return CountDataUtil.filterOthersCountData(getModel().getCurrentRun().getCountData(), getUserName());
	}

}
