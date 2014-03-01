package net.oemig.scta.tracer.question.what;

import java.util.Collection;
import java.util.ResourceBundle;

import net.oemig.scta.model.ICountData;
import net.oemig.scta.model.ITraceModel;
import net.oemig.scta.model.data.QuestionType;
import net.oemig.scta.model.data.UserName;
import net.oemig.scta.model.exception.NoCurrentRunSelectedException;
import net.oemig.scta.tracer.evaluation.CountDataUtil;

public class IndividualWhatQuestionGenerator extends
		AbstractWhatQuestionGenerator {

	public IndividualWhatQuestionGenerator(final UserName aUserName,
			ITraceModel aTraceModel, final ResourceBundle aResourceBundle) {
		super(aUserName, aTraceModel, aResourceBundle);
	}

	@Override
	public QuestionType getType() {
		return QuestionType.IndividualWhat;
	}

	@Override
	public Collection<ICountData> getCountData() throws NoCurrentRunSelectedException {
		return CountDataUtil.filterMyCountData(getModel().getCurrentRun().getCountData(), getUserName());
	}

}
