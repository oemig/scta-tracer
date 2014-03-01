package net.oemig.scta.tracer.question.who;

import java.util.Collection;
import java.util.ResourceBundle;

import net.oemig.scta.model.ICountData;
import net.oemig.scta.model.ITraceModel;
import net.oemig.scta.model.data.QuestionType;
import net.oemig.scta.model.data.UserName;
import net.oemig.scta.model.exception.NoCurrentRunSelectedException;
import net.oemig.scta.tracer.evaluation.CountDataUtil;

public class IndividualWhoQuestionGenerator extends
		AbstractWhoQuestionGenerator {

	public IndividualWhoQuestionGenerator(final UserName userName, ITraceModel model, final ResourceBundle aResourceBundle) {
		super(userName, model, aResourceBundle);
	}

	@Override
	public QuestionType getType() {
		return QuestionType.IndividualWho;
	}

	@Override
	public Collection<ICountData> getCountData() throws NoCurrentRunSelectedException {
		return CountDataUtil.filterMyCountData(getModel().getCurrentRun()
				.getCountData(), getUserName());
	}

}
