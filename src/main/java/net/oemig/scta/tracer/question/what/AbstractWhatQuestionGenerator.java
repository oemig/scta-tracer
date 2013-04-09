package net.oemig.scta.tracer.question.what;

import java.util.Collection;
import java.util.List;

import net.oemig.scta.model.ITraceModel;
import net.oemig.scta.model.binding.Trace.Session.Run.CountData;
import net.oemig.scta.model.data.QuestionType;
import net.oemig.scta.model.data.UserName;
import net.oemig.scta.tracer.evaluation.CountDataUtil;
import net.oemig.scta.tracer.question.IQuestionGenerator;
import net.oemig.scta.tracer.question.Question;
import net.oemig.scta.tracer.question.QuestionException;

import com.google.common.collect.ImmutableList;

/**
 * The {@link AbstractWhatQuestionGenerator} is the basis for questions that
 * ask what was counted.
 * 
 * @author christoph oemig, info@oemig.net
 */
public abstract class AbstractWhatQuestionGenerator implements
		IQuestionGenerator {

	private UserName userName;
	private ITraceModel model;
	
	/**
	 * Constructor 
	 * @param aUserName
	 * @param aTraceModel
	 */
	public AbstractWhatQuestionGenerator(
			final UserName aUserName,
			final ITraceModel aTraceModel
			) {
		userName=aUserName;
		model=aTraceModel;
	}

	@Override
	public Question generate() {
		List<String>answers=ImmutableList.of("ja","nein");
		String letter;
		String correct;
		//flip coin to either proceed with counted
		//letters (group or individual) or uncounted letters
		//50:50 chance
		if(1==CountDataUtil.getRandomNumber(1)){
			//proceed with counted
			try {
				CountData cd=CountDataUtil.random(getCountData());
				letter=cd.getLetter();
				correct="ja";
			} catch (QuestionException e) {
				//no letters yet counted
				letter=CountDataUtil.getRandomLetter();
				correct="nein";
			}

		}else{
			//proceed with uncounted
			letter=CountDataUtil.random(CountDataUtil.getUncountedLetters(model.getCurrentRun().getCountData()));
			correct="nein";
		}
		
		
		//no shuffle necessary
		return new Question("Where "+letter+"'s counted?",
				answers.get(0),
				answers.get(1),
				"",
				correct,
				getType());
	}
	
	public abstract QuestionType getType();
	public abstract Collection<CountData> getCountData();

	public UserName getUserName() {
		return userName;
	}

	public ITraceModel getModel() {
		return model;
	}

}
