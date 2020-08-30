
package acme.components;

import java.util.ArrayList;
import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import acme.entities.configurations.Configuration;

public class SpamChecker {

	public static boolean spamChecker(final Configuration config, final String text) {

		Collection<String> spamReady;
		//Collection<String> textReady;
		String textReady;
		String spamWords;
		double spamThreshold;
		double spamResult;
		double spamCounter = 0;
		boolean result;

		spamWords = config.getSpamWords();
		spamThreshold = config.getSpamThreshold();
		spamReady = SpamChecker.beforeChecking2(spamWords);
		//textReady = SpamChecker.beforeChecking2(text);
		textReady = text;
		for (String s : spamReady) {
			Pattern p = Pattern.compile(s);
			Matcher m = p.matcher(textReady);
			while (m.find()) {
				spamCounter++;
			}
		}

		spamResult = Double.valueOf(spamCounter / SpamChecker.textWord(textReady)) * 100;
		if (spamResult >= spamThreshold) {
			result = true;
		} else {
			result = false;
		}
		return result;
	}

	public static Integer textWord(final String text) {
		Integer result = 1;
		String[] s;
		if (text.contains(" ")) {
			s = text.split(" ");
			for (String word : s) {
				result = result + 1;
			}
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
