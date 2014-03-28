package net.oemig.scta.tracer.coordination.question;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.Set;

import net.oemig.scta.model.ICountData;
import net.oemig.scta.model.data.QuestionType;
import net.oemig.scta.model.data.UserName;
import net.oemig.scta.model.exception.NoCurrentRunSelectedException;
import net.oemig.scta.tracer.coordination.ICoordinationSupportSystem;
import net.oemig.scta.tracer.coordination.exception.CoordinationSupportUnavailableException;
import net.oemig.scta.tracer.evaluation.CountDataUtil;
import net.oemig.scta.tracer.question.IQuestionGenerator;
import net.oemig.scta.tracer.question.Question;
import net.oemig.scta.tracer.question.exception.QuestionGenerationFailedException;

import com.google.common.collect.Lists;

/**
 * 
 * @author christoph.oemig
 */
public class CoordinationWhatQuestionGenerator implements IQuestionGenerator {

	private final ICoordinationSupportSystem coordinationSupportSystem;
	private final List<UserName> allUserNames;
	private final ResourceBundle resourceBundle;
	private final Collection<ICountData> countData;

	/**
	 * Constructor.
	 * 
	 * @param aCoordinationSupportSystem
	 * @param someUserNames
	 * @param aResourceBundle
	 * @param someCountData
	 */
	public CoordinationWhatQuestionGenerator(
			ICoordinationSupportSystem aCoordinationSupportSystem,
			Set<UserName> someUserNames, 
			ResourceBundle aResourceBundle,
			Collection<ICountData> someCountData) {
		
		coordinationSupportSystem = aCoordinationSupportSystem;
		allUserNames = Lists.newArrayList(someUserNames);
		resourceBundle = aResourceBundle;
		countData = someCountData;
	}

	@Override
	public Question generate() throws NoCurrentRunSelectedException,
			QuestionGenerationFailedException {
		// general list for answers
		List<String> answers = Lists.newArrayList();

		Collections.shuffle(allUserNames);
		UserName user = allUserNames.get(0);


		String correct="?"; //to be determined

		try {
			String userLetterSet = coordinationSupportSystem
					.getLetterSet(user);

			correct=selectFrom(userLetterSet);
			answers.add(correct);
			
			
			if(allUserNames.size()>1){
				String secondUserLetterSet=coordinationSupportSystem.getLetterSet(allUserNames.get(1));
				
				//add two further answer certainly not to be counted by the user
				answers.add(selectFrom(secondUserLetterSet));
				answers.add(selectFrom(secondUserLetterSet));
			}else{
				//there is no second user
				answers.add("");
				answers.add("");
			}

			
			
		} catch (CoordinationSupportUnavailableException e) {
			// alternate approach since to coordination support is available
			// derive from original count data
			List<String> uncounted=CountDataUtil.getUncountedLetters(countData);
			if(uncounted.isEmpty()){
				throw new QuestionGenerationFailedException("there are no uncounted letters left");
			}else{
				Collections.shuffle(uncounted);
				correct=uncounted.get(0); //since the list cannot be empty here.. there must at least be one
				answers.add(correct);
				
				List<String> counted=Lists.newArrayList(CountDataUtil.getCountedLetters(countData));
				Collections.shuffle(counted);
				
				if(counted.isEmpty()){
					answers.add("");
					answers.add("");
				}else if(counted.size()==1){
					answers.add("");
					answers.add(counted.get(0));
				}else{
					answers.add(counted.get(1));
					answers.add(counted.get(0));
				}
				
			}
		}



		Collections.shuffle(answers);

		return new Question(resourceBundle.getString("q.c.what.1") + user.toString()
				+ resourceBundle.getString("q.c.what.2"), answers.get(0),
				answers.get(1), answers.get(2), correct,
				QuestionType.CoordinationWhat);
	}

	private String selectFrom(String aLetterSet)
			throws QuestionGenerationFailedException {
		
		for (int i = 0; i < aLetterSet.length(); i++) {
			int ci = new Random().nextInt(aLetterSet.length());
			String c = String.valueOf(aLetterSet.charAt(ci));
			if (!c.equals("-")) {
				return c;
			}
		}
		//still here.. throw exception
		throw new QuestionGenerationFailedException(
					"the letter set contains no more characters: "
							+ aLetterSet);
		
	}


}
