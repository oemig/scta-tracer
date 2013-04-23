package net.oemig.scta.tracer.question.how;

import java.util.Collection;

import net.oemig.scta.model.ICountData;
import net.oemig.scta.model.ITraceModel;
import net.oemig.scta.model.data.QuestionType;
import net.oemig.scta.model.data.UserName;
import net.oemig.scta.tracer.evaluation.CountDataUtil;

public class GroupHowQuestionGenerator extends AbstractHowQuestionGenerator {

	public GroupHowQuestionGenerator(final UserName newUserName, ITraceModel newModel) {
		super(newUserName, newModel);
	}

	@Override
	public QuestionType getType() {
		return QuestionType.GroupHow;
	}

	@Override
	public Collection<ICountData> getCountData() {
		return CountDataUtil.filterOthersCountData(getModel().getCurrentRun()
				.getCountData(), getUserName());
	}

}
