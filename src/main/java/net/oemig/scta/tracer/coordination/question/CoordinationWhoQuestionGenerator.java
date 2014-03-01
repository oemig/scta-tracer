package net.oemig.scta.tracer.coordination.question;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.Set;

import net.oemig.scta.model.data.QuestionType;
import net.oemig.scta.model.data.UserName;
import net.oemig.scta.model.exception.NoCurrentRunSelectedException;
import net.oemig.scta.tracer.coordination.ICoordinationSupportSystem;
import net.oemig.scta.tracer.question.IQuestionGenerator;
import net.oemig.scta.tracer.question.Question;
import net.oemig.scta.tracer.question.exception.QuestionGenerationFailedException;

import com.google.common.collect.Lists;

public class CoordinationWhoQuestionGenerator implements IQuestionGenerator {

	private final ICoordinationSupportSystem coordinationSupportSystem;
	private final List<UserName> allUserNames;
	private final ResourceBundle resourceBundle;


	public CoordinationWhoQuestionGenerator(ICoordinationSupportSystem aCoordinationSupportSystem, Set<UserName> someUserNames, ResourceBundle aResourceBundle){
		coordinationSupportSystem=aCoordinationSupportSystem;
		allUserNames=Lists.newArrayList(someUserNames);
		resourceBundle=aResourceBundle;
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
		List<String> answers=Lists.newArrayList();
		for(UserName u:allUserNames){
			answers.add(u.toString());
		}
		answers.add(resourceBundle.getString("q.c.who.nobody"));
		//array out of bounds exception protection.. in case there is only one user
		if(answers.size()<3){
			answers.add("");
		}
		
		
		Collections.shuffle(answers);
		
		return new Question(resourceBundle.getString("q.c.who.1")+c+resourceBundle.getString("q.c.who.2"), 
				answers.get(0), 
				answers.get(1), 
				answers.get(2), 
				randomUser.toString(), 
				QuestionType.CoordinationWho);
	}

}

