package hr.fer.zemris.java.tecaj.hw3.prob1;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

@SuppressWarnings("javadoc")
public class Prob1Test {

	// Helper method for checking if lexer generates the same stream of tokens
	// as the given stream.
	private void checkToken(final Token actual, final Token expected) {
		final String msg = "Token are not equal.";
		assertEquals(msg, expected.getType(), actual.getType());
		assertEquals(msg, expected.getValue(), actual.getValue());
	}

	private void checkTokenStream(final Lexer lexer, final Token[] correctData) {
		int counter = 0;
		for (final Token expected : correctData) {
			final Token actual = lexer.nextToken();
			final String msg = "Checking token " + counter + ":";
			assertEquals(msg, expected.getType(), actual.getType());
			assertEquals(msg, expected.getValue(), actual.getValue());
			counter++;
		}
	}

	@Test
	public void testCombinedInput() {
		// Lets check for several symbols...
		final Lexer lexer = new Lexer("Janko 3! Jasmina 5; -24");

		final Token correctData[] = { new Token(TokenType.WORD, "Janko"), new Token(TokenType.NUMBER, Long.valueOf(3)),
				new Token(TokenType.SYMBOL, Character.valueOf('!')), new Token(TokenType.WORD, "Jasmina"),
				new Token(TokenType.NUMBER, Long.valueOf(5)), new Token(TokenType.SYMBOL, Character.valueOf(';')),
				new Token(TokenType.SYMBOL, Character.valueOf('-')), new Token(TokenType.NUMBER, Long.valueOf(24)),
				new Token(TokenType.EOF, null) };

		checkTokenStream(lexer, correctData);
	}

	@Test
	public void testEmpty() {
		final Lexer lexer = new Lexer("");

		assertEquals("Empty input must generate only EOF token.", TokenType.EOF, lexer.nextToken().getType());
	}

	@Test
	public void testEmptyInExtended() {
		final Lexer lexer = new Lexer("");
		lexer.setState(LexerState.EXTENDED);

		assertEquals("Empty input must generate only EOF token.", TokenType.EOF, lexer.nextToken().getType());
	}

	@Test
	public void testGetReturnsLastNext() {
		// Calling getToken once or several times after calling nextToken must
		// return each time what nextToken returned...
		final Lexer lexer = new Lexer("");

		final Token token = lexer.nextToken();
		assertEquals("getToken returned different token than nextToken.", token, lexer.getToken());
		assertEquals("getToken returned different token than nextToken.", token, lexer.getToken());
	}

	@Test
	public void testGetReturnsLastNextInExtended() { // Calling
		final Lexer lexer = new Lexer("");
		lexer.setState(LexerState.EXTENDED);

		final Token token = lexer.nextToken();
		assertEquals("getToken returned different token than nextToken.", token, lexer.getToken());
		assertEquals("getToken returned different token than nextToken.", token, lexer.getToken());
	}

	@Test(expected = LexerException.class)
	public void testInvalidEscape() {
		final Lexer lexer = new Lexer("   \\a    ");

		// will throw!
		lexer.nextToken();
	}

	@Test(expected = LexerException.class)
	public void testInvalidEscapeEnding() {
		final Lexer lexer = new Lexer("   \\"); // this is three spaces and a
												// single backslash -- 4 letters
												// string

		// will throw!
		lexer.nextToken();
	}

	@Test
	public void testMultipartInput() { // Test input which has parts
		final Lexer lexer = new Lexer("Janko 3# Ivana26\\a 463abc#zzz");

		checkToken(lexer.nextToken(), new Token(TokenType.WORD, "Janko"));
		checkToken(lexer.nextToken(), new Token(TokenType.NUMBER, Long.valueOf(3)));
		checkToken(lexer.nextToken(), new Token(TokenType.SYMBOL, Character.valueOf('#')));

		lexer.setState(LexerState.EXTENDED);

		checkToken(lexer.nextToken(), new Token(TokenType.WORD, "Ivana26\\a"));
		checkToken(lexer.nextToken(), new Token(TokenType.WORD, "463abc"));
		checkToken(lexer.nextToken(), new Token(TokenType.SYMBOL, Character.valueOf('#')));

		lexer.setState(LexerState.BASIC);

		checkToken(lexer.nextToken(), new Token(TokenType.WORD, "zzz"));
		checkToken(lexer.nextToken(), new Token(TokenType.EOF, null));

	}

	@Test
	public void testNoActualContent() {
		// When input is only of spaces, tabs, newlines, etc...
		final Lexer lexer = new Lexer("   \r\n\t    ");

		assertEquals("Input had no content. Lexer should generated only EOF token.", TokenType.EOF,
				lexer.nextToken().getType());
	}

	@Test
	public void testNoActualContentInExtended() { // When input is only
		final Lexer lexer = new Lexer("   \r\n\t    ");
		lexer.setState(LexerState.EXTENDED);

		assertEquals("Input had no content. Lexer should generated only EOF token.", TokenType.EOF,
				lexer.nextToken().getType());
	}

	@Test
	public void testNotNull() {
		final Lexer lexer = new Lexer("");

		assertNotNull("Token was expected but null was returned.", lexer.nextToken());
	}

	@Test
	public void testNotNullInExtended() {
		final Lexer lexer = new Lexer("");
		lexer.setState(LexerState.EXTENDED);

		assertNotNull("Token was expected but null was returned.", lexer.nextToken());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNullInput() {
		// must throw!
		new Lexer(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNullState() {
		new Lexer("").setState(null);
	}

	@Test(expected = LexerException.class)
	public void testRadAfterEOF() {
		final Lexer lexer = new Lexer("");

		// will obtain EOF
		lexer.nextToken();
		// will throw!
		lexer.nextToken();
	}

	@Test(expected = LexerException.class)
	public void testRadAfterEOFInExtended() {
		final Lexer lexer = new Lexer("");
		lexer.setState(LexerState.EXTENDED);

		// will obtain EOF
		lexer.nextToken();
		// will throw!
		lexer.nextToken();
	}

	// ----------------------------------------------------------------------------------------------------------
	// --------------------- Second part of tests; uncomment when everything
	// above works ------------------------
	// ----------------------------------------------------------------------------------------------------------

	@Test
	public void testSingleEscapedDigit() {
		final Lexer lexer = new Lexer("  \\1  ");

		// We expect the following stream of tokens
		final Token correctData[] = { new Token(TokenType.WORD, "1"), new Token(TokenType.EOF, null) };

		checkTokenStream(lexer, correctData);
	}

	@Test
	public void testSomeSymbols() {
		// Lets check for several symbols...
		final Lexer lexer = new Lexer("  -.? \r\n\t ##   ");

		final Token correctData[] = { new Token(TokenType.SYMBOL, Character.valueOf('-')),
				new Token(TokenType.SYMBOL, Character.valueOf('.')),
				new Token(TokenType.SYMBOL, Character.valueOf('?')),
				new Token(TokenType.SYMBOL, Character.valueOf('#')),
				new Token(TokenType.SYMBOL, Character.valueOf('#')), new Token(TokenType.EOF, null) };

		checkTokenStream(lexer, correctData);
	}

	@Test(expected = LexerException.class)
	public void testTooBigNumber() {
		final Lexer lexer = new Lexer("  12345678912123123432123   ");

		// will throw!
		lexer.nextToken();
	}

	@Test
	public void testTwoNumbers() {
		// Lets check for several numbers...
		final Lexer lexer = new Lexer("  1234\r\n\t 5678   ");

		final Token correctData[] = { new Token(TokenType.NUMBER, Long.valueOf(1234)),
				new Token(TokenType.NUMBER, Long.valueOf(5678)), new Token(TokenType.EOF, null) };

		checkTokenStream(lexer, correctData);
	}

	@Test
	public void testTwoWords() {
		// Lets check for several words...
		final Lexer lexer = new Lexer("  Štefanija\r\n\t Automobil   ");

		// We expect the following stream of tokens
		final Token correctData[] = { new Token(TokenType.WORD, "Štefanija"), new Token(TokenType.WORD, "Automobil"),
				new Token(TokenType.EOF, null) };

		checkTokenStream(lexer, correctData);
	}

	@Test
	public void testWordStartingWithEscape() {
		final Lexer lexer = new Lexer("  \\1st  \r\n\t   ");

		// We expect the following stream of tokens
		final Token correctData[] = { new Token(TokenType.WORD, "1st"), new Token(TokenType.EOF, null) };

		checkTokenStream(lexer, correctData);
	}

	@Test
	public void testWordWithManyEscapes() {
		// Lets check for several words...
		final Lexer lexer = new Lexer("  ab\\1\\2cd\\3 ab\\2\\1cd\\4\\\\ \r\n\t   ");

		// We expect the following stream of tokens
		final Token correctData[] = { new Token(TokenType.WORD, "ab12cd3"), new Token(TokenType.WORD, "ab21cd4\\"), // this
																													// is
																													// 8-letter
																													// long,
																													// not
																													// nine!
																													// Only
																													// single
																													// backslash!
				new Token(TokenType.EOF, null) };

		checkTokenStream(lexer, correctData);
	}

	@Test
	public void testWordWithManyEscapesAndNumbers() {
		// Lets check for several words...
		final Lexer lexer = new Lexer("  ab\\123cd ab\\2\\1cd\\4\\\\ \r\n\t   ");

		// We expect following stream of tokens
		final Token correctData[] = { new Token(TokenType.WORD, "ab1"), new Token(TokenType.NUMBER, Long.valueOf(23)),
				new Token(TokenType.WORD, "cd"), new Token(TokenType.WORD, "ab21cd4\\"), // this
																							// is
																							// 8-letter
																							// long,
																							// not
																							// nine!
																							// Only
																							// single
																							// backslash!
				new Token(TokenType.EOF, null) };

		checkTokenStream(lexer, correctData);
	}

}
