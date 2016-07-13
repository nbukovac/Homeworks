package hr.fer.zemris.java.custom.scripting.exec;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

@SuppressWarnings("javadoc")
public class MultistackTests {

	@Test
	public void testBothDoubles() {
		final ValueWrapper valueDouble = new ValueWrapper(4.0);

		valueDouble.decrement(1.5);
		assertEquals(true, valueDouble.getValue() instanceof Double);
		assertEquals(2.5, valueDouble.getValue());

		valueDouble.increment(15.2);
		assertEquals(true, valueDouble.getValue() instanceof Double);
		assertEquals(17.7, valueDouble.getValue());

		valueDouble.divide(17.7);
		assertEquals(true, valueDouble.getValue() instanceof Double);
		assertEquals(1.0, valueDouble.getValue());

		valueDouble.multiply(15.6);
		assertEquals(true, valueDouble.getValue() instanceof Double);
		assertEquals(15.6, valueDouble.getValue());

		assertEquals(0, valueDouble.numCompare(15.6));
		assertEquals(-1, valueDouble.numCompare(16.6));
		assertEquals(1, valueDouble.numCompare(11.6));
	}

	@Test
	public void testBothValuesIntegers() {
		final ValueWrapper valueInteger = new ValueWrapper(4);

		valueInteger.decrement(3);
		assertEquals(true, valueInteger.getValue() instanceof Integer);
		assertEquals(1, valueInteger.getValue());

		valueInteger.increment(50);
		assertEquals(true, valueInteger.getValue() instanceof Integer);
		assertEquals(51, valueInteger.getValue());

		valueInteger.divide(10);
		assertEquals(true, valueInteger.getValue() instanceof Integer);
		assertEquals(5, valueInteger.getValue());

		valueInteger.multiply(5);
		assertEquals(true, valueInteger.getValue() instanceof Integer);
		assertEquals(25, valueInteger.getValue());

		assertEquals(0, valueInteger.numCompare(25));
		assertEquals(-1, valueInteger.numCompare(26));
		assertEquals(1, valueInteger.numCompare(24));
	}

	@Test(expected = ArithmeticException.class)
	public void testDivideByZero() {
		final ValueWrapper value = new ValueWrapper(4);

		// throws
		value.divide("0");
	}

	@Test
	public void testIntegerStrings() {
		final ValueWrapper valueIntegerString = new ValueWrapper("4");

		valueIntegerString.decrement("3");
		assertEquals(true, valueIntegerString.getValue() instanceof Integer);
		assertEquals(1, valueIntegerString.getValue());

		valueIntegerString.setValue("3");

		valueIntegerString.multiply("3");
		assertEquals(true, valueIntegerString.getValue() instanceof Integer);
		assertEquals(9, valueIntegerString.getValue());

	}

	@Test
	public void testMixedValueWrapper() {
		final ValueWrapper valueWrapper = new ValueWrapper("4");

		valueWrapper.decrement(5);
		assertEquals(true, valueWrapper.getValue() instanceof Integer);
		assertEquals(-1, valueWrapper.getValue());

		valueWrapper.multiply("-1");
		assertEquals(true, valueWrapper.getValue() instanceof Integer);
		assertEquals(1, valueWrapper.getValue());

		assertEquals(0, valueWrapper.numCompare("1"));
		assertEquals(0, valueWrapper.numCompare(1.0));

		valueWrapper.multiply("4.5");
		assertEquals(true, valueWrapper.getValue() instanceof Double);
		assertEquals(4.5, valueWrapper.getValue());

		valueWrapper.increment(3);
		assertEquals(true, valueWrapper.getValue() instanceof Double);
		assertEquals(7.5, valueWrapper.getValue());
	}

	@Test
	public void testMultistackOperation() {
		final ObjectMultistack multiStack = new ObjectMultistack();

		multiStack.push("Nikola", new ValueWrapper("3"));
		multiStack.push("Nikola", new ValueWrapper(2));

		assertEquals(2, multiStack.peek("Nikola").getValue());

		multiStack.pop("Nikola");
		assertEquals(false, multiStack.isEmpty());

		assertEquals("3", multiStack.pop("Nikola").getValue());
		assertEquals(true, multiStack.isEmpty());

	}

	@Test
	public void testNumCompareWithNull() {
		final ValueWrapper valueWrapper = new ValueWrapper(null);

		assertEquals(0, valueWrapper.numCompare(null));
		assertEquals(0, valueWrapper.numCompare(0.0));
		assertEquals(0, valueWrapper.numCompare("0.0"));
		assertEquals(-1, valueWrapper.numCompare(5));

		valueWrapper.setValue("0");

		assertEquals(0, valueWrapper.numCompare(null));
		assertEquals(0, valueWrapper.numCompare("0.0"));
		assertEquals(1, valueWrapper.numCompare(-3));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testPushKeyNull() {
		final ObjectMultistack multiStack = new ObjectMultistack();

		// throws
		multiStack.push(null, new ValueWrapper("3"));
	}

	@Test(expected = EmptyStackException.class)
	public void testPushOnEmptyStack() {
		final ObjectMultistack multiStack = new ObjectMultistack();

		multiStack.push("Nikola", new ValueWrapper("3"));
		multiStack.push("Nikola", new ValueWrapper(2));

		multiStack.pop("Nikola");
		multiStack.pop("Nikola");

		// throws
		multiStack.pop("Nikola");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testPushValueNull() {
		final ObjectMultistack multiStack = new ObjectMultistack();

		// throws
		multiStack.push("burek", null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testValueWrapperIllegalType() {
		final ValueWrapper valueWrapper = new ValueWrapper(new ValueWrapper("3"));

		// throws
		valueWrapper.decrement(1);
	}

	@Test(expected = NumberFormatException.class)
	public void testWrongStringValue() {
		final ValueWrapper valueWrapper = new ValueWrapper("3.3.3");

		// throws
		valueWrapper.increment("1");
	}
}
