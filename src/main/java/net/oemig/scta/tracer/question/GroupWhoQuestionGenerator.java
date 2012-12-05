package net.oemig.scta.tracer.question;

import java.util.List;

import net.oemig.scta.tracer.evaluation.CountDataUtil;
import net.oemig.scta.tracer.model.ITraceModel;
import net.oemig.scta.tracer.model.binding.Trace.Session.Run.CountData;

/**
 * GroupWhoQuestion Get a count result from the model. Counting person must not
 * be the current user.
 * 
 * "Who counted"+countresult.letter+"?"
 * 
 */
public class GroupWhoQuestionGenerator extends AbstractWhoQuestionGenerator {

	public GroupWhoQuestionGenerator(String newUserName, ITraceModel newModel) {
		super(newUserName, newModel);
	}

	@Override
	public QuestionType getType() {
		return QuestionType.GroupWho;
	}

	@Override
	public List<CountData> getCountData() {
		return CountDataUtil.filterOthersCountData(getModel().getCurrentRun()
				.getCountData(), getUserName());
	}
}
