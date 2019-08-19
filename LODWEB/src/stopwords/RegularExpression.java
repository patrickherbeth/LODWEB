package stopwords;

public class RegularExpression {
	public static final String URL_REGEX = "((www\\.[\\s]+)|(https?://[^\\s]+))";
	public static final String CONSECUTIVE_CHARS = "([a-z])\\1{1,}";
	public static final String STARTS_WITH_NUMBER = "[1-9]\\s*(\\w+)";
	public static final String HASHTAG_REGEX = "[#]+[A-Za-z0-9-_]+";
	public static final String DOUBLE_WHITE_SPACES = "\\\\s{2}|\\\\u00A0";
	public static final String SPECIAL_CHARACTERS = "['!@#$%¨&*()_—\\-+=§ª\\[\\]\\{\\}?\\/;:.,\\\\\\|°ºª<>\"]";
}