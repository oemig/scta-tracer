package net.oemig.scta.tracer.question;

import java.util.List;

import net.oemig.scta.tracer.evaluation.CountDataUtil;
import net.oemig.scta.tracer.model.ITraceModel;
import net.oemig.scta.tracer.model.binding.Trace.Session.Run.CountData;

public class GroupHowQuestionGenerator extends AbstractHowQuestionGenerator {

	public GroupHowQuestionGenerator(String newUserName, ITraceModel newModel) {
		super(newUserName, newModel);
	}

	@Override
	public QuestionType getType() {
		return QuestionType.GroupHow;
	}

	@Override
	public List<CountData> getCountData() {
		return CountDataUtil.filterOthersCountData(getModel().getCurrentRun()
				.getCountData(), getUserName());
	}

}
