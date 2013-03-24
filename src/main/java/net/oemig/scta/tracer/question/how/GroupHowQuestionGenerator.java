package net.oemig.scta.tracer.question.how;

import java.util.Collection;

import net.oemig.scta.tracer.data.UserName;
import net.oemig.scta.tracer.evaluation.CountDataUtil;
import net.oemig.scta.tracer.model.ITraceModel;
import net.oemig.scta.tracer.model.binding.Trace.Session.Run.CountData;
import net.oemig.scta.tracer.question.QuestionType;

public class GroupHowQuestionGenerator extends AbstractHowQuestionGenerator {

	public GroupHowQuestionGenerator(final UserName newUserName, ITraceModel newModel) {
		super(newUserName, newModel);
	}

	@Override
	public QuestionType getType() {
		return QuestionType.GroupHow;
	}

	@Override
	public Collection<CountData> getCountData() {
		return CountDataUtil.filterOthersCountData(getModel().getCurrentRun()
				.getCountData(), getUserName());
	}

}
