package net.oemig.scta.tracer.question;

import java.util.List;

import net.oemig.scta.tracer.evaluation.CountDataUtil;
import net.oemig.scta.tracer.model.ITraceModel;
import net.oemig.scta.tracer.model.binding.Trace.Session.Run.CountData;

public class IndividualHowQuestionGenerator extends
		AbstractHowQuestionGenerator {

	public IndividualHowQuestionGenerator(String newUserName,
			ITraceModel newModel) {
		super(newUserName, newModel);
	}

	@Override
	public QuestionType getType() {
		return QuestionType.IndividualHow;
	}

	@Override
	public List<CountData> getCountData() {
		return CountDataUtil.filterMyCountData(getModel().getCurrentRun()
				.getCountData(), getUserName());
	}

}
