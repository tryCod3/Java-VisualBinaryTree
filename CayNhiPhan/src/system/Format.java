package system;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Format {
	
	private static final Format FORMAT = new Format();
	
	private static final String NUMBER = "[\\d]";

	public static Format getFormat() {
		return FORMAT;
	}
	private Matcher matcher;
	
	private Pattern pattern;
	
	
	public boolean isNumber(String text) {
		pattern = Pattern.compile(NUMBER);
		matcher = pattern.matcher(text);
		int count = 0;
		while(matcher.find()) {
			count++;
		}
		return ( text.length() > 0 && count == text.length());
	}
	
	
	
}
