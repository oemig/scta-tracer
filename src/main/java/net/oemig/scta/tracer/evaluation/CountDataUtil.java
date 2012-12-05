package net.oemig.scta.tracer.evaluation;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.oemig.scta.tracer.model.binding.Trace.Session.Run.CountData;
import net.oemig.scta.tracer.question.QuestionException;

public class CountDataUtil {
	public static final String NOBODY = "Nobody";
	public static final String NONE = "None";

	public static List<CountData> filterOthersCountData(
			List<CountData> countData, String userName) {
		List<CountData> others = new ArrayList<CountData>();

		for (CountData c : countData) {
			if (!c.getParticipant().equals(userName)) {
				others.add(c);
			}
		}

		return others;
	}

	public static List<CountData> filterMyCountData(List<CountData> countData,
			String userName) {
		List<CountData> others = new ArrayList<CountData>();

		for (CountData c : countData) {
			if (c.getParticipant().equals(userName)) {
				others.add(c);
			}
		}

		return others;
	}

	public static CountData getRandomCountData(List<CountData> countData)
			throws QuestionException {

		Random randomGenerator = new Random();
		if (countData.size() < 1) {
			throw new QuestionException("No other count data");
		} else {
			return countData.get(randomGenerator.nextInt(countData.size()));
		}
	}

	public static List<String> getUncountedLetters(List<CountData> countData) {
		String[] alphabet = new String[] { "a", "b", "c", "d", "e", "f", "g",
				"h", "i", "j", "k", "l", "m", "n", "o", "p", "r", "s", "t",
				"u", "v", "w", "x", "y", "z", "A", "B", "C", "D", "E", "F",
				"G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R",
				"S", "T", "U", "V", "W", "X", "Y", "Z" };

		List<String> counted = new ArrayList<String>();
		List<String> uncounted = new ArrayList<String>();
		for (CountData c : countData) {
			counted.add(c.getLetter());
		}

		for (String letter : alphabet) {
			if (!counted.contains(letter)) {
				uncounted.add(letter);
			}
		}

		return uncounted;
	}

	public static int getRandomNumber(int max) {
		Random random = new Random();
		return random.nextInt(max);
	}
}
