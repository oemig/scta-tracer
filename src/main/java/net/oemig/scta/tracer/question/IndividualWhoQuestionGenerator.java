package net.oemig.scta.tracer.question;

import java.util.List;

import net.oemig.scta.tracer.evaluation.CountDataUtil;
import net.oemig.scta.tracer.model.ITraceModel;
import net.oemig.scta.tracer.model.binding.Trace.Session.Run.CountData;

public class IndividualWhoQuestionGenerator extends
		AbstractWhoQuestionGenerator {

	public IndividualWhoQuestionGenerator(String userName, ITraceModel model) {
		super(userName, model);
	}

	@Override
	public QuestionType getType() {
		return QuestionType.IndividualWho;
	}

	@Override
	public List<CountData> getCountData() {
		return CountDataUtil.filterMyCountData(getModel().getCurrentRun()
				.getCountData(), getUserName());
	}

}
