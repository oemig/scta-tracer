package net.oemig.scta.tracer.question.what;

import java.util.Collection;
import java.util.List;
import java.util.ResourceBundle;

import net.oemig.scta.model.ICountData;
import net.oemig.scta.model.ITraceModel;
import net.oemig.scta.model.data.QuestionType;
import net.oemig.scta.model.data.UserName;
import net.oemig.scta.model.exception.NoCurrentRunSelectedException;
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

	private final UserName userName;
	private final ITraceModel model;
	
	private final ResourceBundle resourceBundle;
	
	/**
	 * Constructor 
	 * @param aUserName
	 * @param aTraceModel
	 */
	public AbstractWhatQuestionGenerator(
			final UserName aUserName,
			final ITraceModel aTraceModel,
			final ResourceBundle aResourceBundle
			) {
		userName=aUserName;
		model=aTraceModel;
		resourceBundle=aResourceBundle;
//		if(locale.equals(Locale.GERMANY)){
//			yes="ja";
//			no="nein";
//			questionFragment1="Wurde der Buchstabe <b>";
//			questionFragment2="</b> gezählt?";
//		}else{
//			yes="yes";
//			no="no";
//			questionFragment1="Was the letter <b>";
//			questionFragment2="</b> counted?";
//		}
	}

	@Override
	public Question generate() throws NoCurrentRunSelectedException {
		List<String>answers=ImmutableList.of(resourceBundle.getString("q.yes"),resourceBundle.getString("q.no"));
		String letter;
		String correct;
		//flip coin to either proceed with counted
		//letters (group or individual) or uncounted letters
		//50:50 chance
		if(1==CountDataUtil.getRandomNumber(1)){
			//proceed with counted
			try {
				ICountData cd=CountDataUtil.random(getCountData());
				letter=cd.getLetter();
				correct=resourceBundle.getString("q.yes");
			} catch (QuestionException e) {
				//no letters yet counted
				letter=CountDataUtil.getRandomLetter();
				correct=resourceBundle.getString("q.no");
			}

		}else{
			//proceed with uncounted
			letter=CountDataUtil.random(CountDataUtil.getUncountedLetters(model.getCurrentRun().getCountData()));
			correct=resourceBundle.getString("q.no");	
		}
		
		
		//no shuffle necessary
		return new Question(resourceBundle.getString("q.what.1")+letter+resourceBundle.getString("q.what.2"),
				answers.get(0),
				answers.get(1),
				"",
				correct,
				getType());
	}
	
	public abstract QuestionType getType();
	public abstract Collection<ICountData> getCountData() throws NoCurrentRunSelectedException;

	public UserName getUserName() {
		return userName;
	}

	public ITraceModel getModel() {
		return model;
	}

}
