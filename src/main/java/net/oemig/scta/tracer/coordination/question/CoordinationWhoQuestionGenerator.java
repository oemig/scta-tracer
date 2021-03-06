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
 * Generates {@link Question}s that ask who might count a certain letter next.
 * For those with {@link ICoordinationSupportSystem} future options need to be
 * delivered from there. For those without the options stem from yet uncounted
 * letters.
 * 
 * @author christoph.oemig
 */
public class CoordinationWhoQuestionGenerator implements IQuestionGenerator {

	private final ICoordinationSupportSystem coordinationSupportSystem;
	private final List<UserName> allUserNames;
	private final ResourceBundle resourceBundle;
	private final Collection<ICountData> countData;

	public CoordinationWhoQuestionGenerator(
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
		// always add "nobody" to the list
		answers.add(resourceBundle.getString("q.c.who.nobody"));

		String character="?"; //to be determined
		String correct="?"; //to be determined

		try {
			Collections.shuffle(allUserNames);
			UserName randomUser = allUserNames.get(0);
			String randomUserLetterSet = coordinationSupportSystem
					.getLetterSet(randomUser);

			for (int i = 0; i < randomUserLetterSet.length(); i++) {
				int ci = new Random().nextInt(randomUserLetterSet.length());
				character = String.valueOf(randomUserLetterSet.charAt(ci));
				if (!character.equals("-")) {
					break; // get me out of the for loop
				}

				throw new QuestionGenerationFailedException(
						"the letter set contains no more characters: "
								+ randomUserLetterSet);
			}

			for (UserName u : allUserNames) {
				answers.add(u.toString());
			}
			
			correct=randomUser.toString();
			
		} catch (CoordinationSupportUnavailableException e) {
			// alternate approach since to coordination support is available
			// derive from original count data
			List<String> uncounted=CountDataUtil.getUncountedLetters(countData);
			if(uncounted.isEmpty()){
				throw new QuestionGenerationFailedException("there are no uncounted letters left");
			}else{
				Collections.shuffle(uncounted);
				character=uncounted.get(0); //since the list cannot be empty here.. there must at least be one
				
				answers.add(resourceBundle.getString("q.c.who.anybody"));
				
				correct=resourceBundle.getString("q.c.who.anybody");
			}
		}


		// array out of bounds exception protection.. in case there is only one
		// user
		if (answers.size() < 3) {
			answers.add("");
		}

		Collections.shuffle(answers);

		return new Question(resourceBundle.getString("q.c.who.1") + character
				+ resourceBundle.getString("q.c.who.2"), answers.get(0),
				answers.get(1), answers.get(2), correct,
				QuestionType.CoordinationWho);
	}

}
