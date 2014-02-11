package net.oemig.scta.tracer.coordination.question;

import java.util.Collections;
import java.util.List;
import java.util.Random;

import net.oemig.scta.model.data.UserName;
import net.oemig.scta.model.exception.NoCurrentRunSelectedException;
import net.oemig.scta.tracer.coordination.ICoordinationSupportSystem;
import net.oemig.scta.tracer.question.IQuestionGenerator;
import net.oemig.scta.tracer.question.Question;
import net.oemig.scta.tracer.question.exception.QuestionGenerationFailedException;

public class CoordinationWhoQuestionGenerator implements IQuestionGenerator {

	private final UserName currentUserName;
	private final ICoordinationSupportSystem coordinationSupportSystem;
	private final List<UserName> allUserNames;


	public CoordinationWhoQuestionGenerator(UserName aUserName, ICoordinationSupportSystem aCoordinationSupportSystem, List<UserName> someUserNames){
		currentUserName=aUserName;
		coordinationSupportSystem=aCoordinationSupportSystem;
		allUserNames=someUserNames;
	}
	
	
	@Override
	public Question generate() throws NoCurrentRunSelectedException, QuestionGenerationFailedException {

		Collections.shuffle(allUserNames);
		UserName randomUser=allUserNames.get(0);
		String randomUserLetterSet=coordinationSupportSystem.getLetterSet(randomUser);
		
		String c=null;
		for(int i=0; i<randomUserLetterSet.length();i++ ){
			int ci=new Random().nextInt(randomUserLetterSet.length());
			c=String.valueOf(randomUserLetterSet.charAt(ci));
			if(!c.equals("-")){
				break; //get me out of the for loop
			}
			
			throw new QuestionGenerationFailedException("the letter set contains no more characters: "+randomUserLetterSet);
		}
		
		
		return null;
	}

}

