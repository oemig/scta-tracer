package net.oemig.scta.tracer.question.who;

import java.util.Collection;
import java.util.ResourceBundle;

import net.oemig.scta.model.ICountData;
import net.oemig.scta.model.ITraceModel;
import net.oemig.scta.model.data.QuestionType;
import net.oemig.scta.model.data.UserName;
import net.oemig.scta.model.exception.NoCurrentRunSelectedException;
import net.oemig.scta.tracer.evaluation.CountDataUtil;

/**
 * GroupWhoQuestion Get a count result from the model. Counting person must not
 * be the current user.
 * 
 * "Who counted"+countresult.letter+"?"
 * 
 */
public class GroupWhoQuestionGenerator extends AbstractWhoQuestionGenerator {

	public GroupWhoQuestionGenerator(final UserName newUserName, ITraceModel newModel, final ResourceBundle aResourceBundle) {
		super(newUserName, newModel, aResourceBundle);
	}

	@Override
	public QuestionType getType() {
		return QuestionType.GroupWho;
	}

	@Override
	public Collection<ICountData> getCountData() throws NoCurrentRunSelectedException {
		return CountDataUtil.filterOthersCountData(getModel().getCurrentRun()
				.getCountData(), getUserName());
	}
}
