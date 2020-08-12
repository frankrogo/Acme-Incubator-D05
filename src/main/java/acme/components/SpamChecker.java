
package acme.components;

import java.util.ArrayList;
import java.util.Collection;

import acme.entities.configurations.Configuration;

public class SpamChecker {

	public static boolean spamChecker(final Configuration config, final String text) {

		Collection<String> spamReady;
		Collection<String> textReady;
		String spamWords;
		double spamThreshold;
		double spamResult;
		double spamCounter = 0;
		double numWords;
		boolean result;

		spamWords = config.getSpamWords();
		spamThreshold = config.getSpamThreshold();
		spamReady = SpamChecker.beforeChecking1(spamWords);
		textReady = SpamChecker.beforeChecking2(text);

		numWords = textReady.size();

		for (String s : spamReady) {
			for (String t : textReady) {
				if (s.equals(t) || t.toLowerCase().contains(s.toLowerCase())) {
					spamCounter++;
				}
			}
		}

		spamResult = Double.valueOf(spamCounter / numWords) * 100;

		if (spamResult >= spamThreshold) {
			result = true;
		} else {
			result = false;
		}

		return result;
	}

	public static Collection<String> beforeChecking1(final String text) {

		Collection<String> result = new ArrayList<String>();
		result.add(text);

		String[] s = text.split(",");

		for (String word : s) {
			//word.trim().replaceAll(" +", " ");
			result.add(word);
		}
		return result;
	}

	public static Collection<String> beforeChecking2(final String text) {

		Collection<String> result = new ArrayList<String>();
		result.add(text);
		String[] s;
		if (text.contains(",")) {
			s = text.split(",");
			for (String word : s) {
				result.add(word);
			}

		} else {
			return result;
		}

		return result;
	}

}
