package hr.fer.zemris.java.simplecomp;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import hr.fer.zemris.java.simplecomp.impl.instructions.InstrCall;
import hr.fer.zemris.java.simplecomp.impl.instructions.InstrLoad;
import hr.fer.zemris.java.simplecomp.impl.instructions.InstrMove;
import hr.fer.zemris.java.simplecomp.impl.instructions.InstrPop;
import hr.fer.zemris.java.simplecomp.impl.instructions.InstrPush;
import hr.fer.zemris.java.simplecomp.impl.instructions.InstrRet;
import hr.fer.zemris.java.simplecomp.models.Computer;
import hr.fer.zemris.java.simplecomp.models.Instruction;
import hr.fer.zemris.java.simplecomp.models.InstructionArgument;
import hr.fer.zemris.java.simplecomp.models.Memory;
import hr.fer.zemris.java.simplecomp.models.Registers;

@RunWith(MockitoJUnitRunner.class)
@SuppressWarnings("javadoc")
public class SimpleCompTests {

	private class InstructionArgumentImpl implements InstructionArgument {

		private final Object value;

		private final int descriptor;

		public InstructionArgumentImpl(final Object value, final int descriptor) {
			this.value = value;
			this.descriptor = descriptor;
		}

		@Override
		public Object getValue() {
			if (value == null) {
				return descriptor;
			}

			return value;
		}

		@Override
		public boolean isNumber() {
			return value instanceof Integer;
		}

		@Override
		public boolean isRegister() {
			return value == null;
		}

		@Override
		public boolean isString() {
			return value instanceof String;
		}

	}

	@Mock
	private Memory memory;

	@Mock
	private Registers registers;

	@Mock
	private Computer computer;

	@Before
	public void initialize() {
		when(computer.getRegisters()).thenReturn(registers);
		when(computer.getMemory()).thenReturn(memory);
	}

	@Test
	public void testCall() {
		// call 45 -> trenutni pc je 23, stack pointer je 200

		final List<InstructionArgument> arguments = new ArrayList<>();
		arguments.add(new InstructionArgumentImpl(45, 3));

		final Instruction call = new InstrCall(arguments);

		when(registers.getProgramCounter()).thenReturn(23);
		when(registers.getRegisterValue(Registers.STACK_REGISTER_INDEX)).thenReturn(200);

		assertEquals(false, call.execute(computer));

		verify(computer, times(4)).getRegisters();
		verify(computer, times(1)).getMemory();
		verify(memory, times(1)).setLocation(200, 23);
		verify(registers, times(1)).getProgramCounter();
		verify(registers, times(1)).setProgramCounter(45);
		verify(registers, times(1)).setRegisterValue(Registers.STACK_REGISTER_INDEX, 199);
		verify(registers, times(1)).getRegisterValue(Registers.STACK_REGISTER_INDEX);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCallIllegalArgument() {
		final List<InstructionArgument> arguments = new ArrayList<>();
		arguments.add(new InstructionArgumentImpl("", 3));

		new InstrCall(arguments);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCallIllegalNumberOfArguments() {
		final List<InstructionArgument> arguments = new ArrayList<>();
		arguments.add(new InstructionArgumentImpl(33, 3));
		arguments.add(new InstructionArgumentImpl(33, 3));

		new InstrCall(arguments);
	}

	@Test
	public void testLoad() {
		// load r3, 20 -> na adresi 20 je 4

		final List<InstructionArgument> arguments = new ArrayList<>();

		arguments.add(new InstructionArgumentImpl(null, 0x03));
		arguments.add(new InstructionArgumentImpl(20, 0));

		final InstrLoad load = new InstrLoad(arguments);

		when(memory.getLocation(20)).thenReturn(4);

		assertEquals(false, load.execute(computer));

		verify(computer, times(1)).getRegisters();
		verify(computer, times(1)).getMemory();
		verify(memory, times(1)).getLocation(20);
		verify(registers, times(1)).setRegisterValue(3, 4);

	}

	@Test(expected = IllegalArgumentException.class)
	public void testLoadIllegalArguments() {
		final List<InstructionArgument> arguments = new ArrayList<>();
		arguments.add(new InstructionArgumentImpl(null, 0x03));
		arguments.add(new InstructionArgumentImpl(null, 0x03));

		new InstrLoad(arguments);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testLoadIllegalNumberOfArguments() {
		final List<InstructionArgument> arguments = new ArrayList<>();
		arguments.add(new InstructionArgumentImpl(null, 0x03));

		new InstrLoad(arguments);
	}

	@Test
	public void testMoveBothRegisters() {
		// move r2, r3 -> r3 sadrži 15

		final List<InstructionArgument> arguments = new ArrayList<>();

		arguments.add(new InstructionArgumentImpl(null, 2));
		arguments.add(new InstructionArgumentImpl(null, 3));

		final Instruction move = new InstrMove(arguments);

		when(registers.getRegisterValue(3)).thenReturn(15);

		assertEquals(false, move.execute(computer));

		verify(computer, times(2)).getRegisters();
		verify(registers, times(1)).getRegisterValue(3);
		verify(registers, times(1)).setRegisterValue(2, 15);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testMoveIllegalArguments() {
		final List<InstructionArgument> arguments = new ArrayList<>();

		arguments.add(new InstructionArgumentImpl("", 2));
		arguments.add(new InstructionArgumentImpl(null, 3));

		new InstrMove(arguments);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testMoveIllegalNumberOfArguments() {
		final List<InstructionArgument> arguments = new ArrayList<>();

		arguments.add(new InstructionArgumentImpl(null, 2));
		arguments.add(new InstructionArgumentImpl(null, 3));
		arguments.add(new InstructionArgumentImpl(null, 3));

		new InstrMove(arguments);
	}

	@Test
	public void testMoveIndirectIndirect() {
		// move [r2 + 2], [r3 + 1] -> r3 sadrži 15, a u memoriji na mjestu 16 je
		// 60, r2 sadrži 20

		final List<InstructionArgument> arguments = new ArrayList<>();

		arguments.add(new InstructionArgumentImpl(null, 0x1000202));
		arguments.add(new InstructionArgumentImpl(null, 0x1000103));

		final Instruction move = new InstrMove(arguments);

		when(registers.getRegisterValue(3)).thenReturn(15);
		when(registers.getRegisterValue(2)).thenReturn(20);
		when(memory.getLocation(16)).thenReturn(60);

		assertEquals(false, move.execute(computer));

		verify(computer, times(2)).getRegisters();
		verify(computer, times(2)).getMemory();
		verify(registers, times(1)).getRegisterValue(3);
		verify(registers, times(1)).getRegisterValue(2);
		verify(memory, times(1)).getLocation(16);
		verify(memory, times(1)).setLocation(22, 60);
	}

	@Test
	public void testMoveRegisterIndirect() {
		// move r2, [r3 + 1] -> r3 sadrži 15, a u memoriji na mjestu 16 je 60

		final List<InstructionArgument> arguments = new ArrayList<>();

		arguments.add(new InstructionArgumentImpl(null, 2));
		arguments.add(new InstructionArgumentImpl(null, 0x1000103));

		final Instruction move = new InstrMove(arguments);

		when(registers.getRegisterValue(3)).thenReturn(15);
		when(memory.getLocation(16)).thenReturn(60);

		assertEquals(false, move.execute(computer));

		verify(computer, times(2)).getRegisters();
		verify(computer, times(1)).getMemory();
		verify(registers, times(1)).getRegisterValue(3);
		verify(registers, times(1)).setRegisterValue(2, 60);
		verify(memory, times(1)).getLocation(16);
	}

	@Test
	public void testMoveRegisterNumber() {
		// move r2, 1

		final List<InstructionArgument> arguments = new ArrayList<>();

		arguments.add(new InstructionArgumentImpl(null, 2));
		arguments.add(new InstructionArgumentImpl(1, 0));

		final Instruction move = new InstrMove(arguments);

		assertEquals(false, move.execute(computer));

		verify(computer, times(1)).getRegisters();
		verify(registers, times(1)).setRegisterValue(2, 1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testMoveTooManyArguments() {
		final List<InstructionArgument> arguments = new ArrayList<>();

		arguments.add(new InstructionArgumentImpl(null, 0x1000202));
		arguments.add(new InstructionArgumentImpl(null, 0x1000103));
		arguments.add(new InstructionArgumentImpl(null, 0x1000103));

		new InstrMove(arguments);
	}

	@Test
	public void testPop() {
		// pop r3 -> stack pointer je na početku na 199 i pokazuje na 55

		final List<InstructionArgument> arguments = new ArrayList<>();
		arguments.add(new InstructionArgumentImpl(null, 3));

		final Instruction pop = new InstrPop(arguments);

		when(registers.getRegisterValue(Registers.STACK_REGISTER_INDEX)).thenReturn(199);
		when(memory.getLocation(200)).thenReturn(55);

		assertEquals(false, pop.execute(computer));

		verify(computer, times(3)).getRegisters();
		verify(computer, times(2)).getMemory();
		verify(memory, times(1)).getLocation(200);
		verify(memory, times(1)).setLocation(200, null);
		verify(registers, times(1)).getRegisterValue(Registers.STACK_REGISTER_INDEX);
		verify(registers, times(1)).setRegisterValue(Registers.STACK_REGISTER_INDEX, 200);
		verify(registers, times(1)).setRegisterValue(3, 55);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testPopIllegalArgument() {
		final List<InstructionArgument> arguments = new ArrayList<>();
		arguments.add(new InstructionArgumentImpl(3, 3));

		new InstrPop(arguments);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testPopIllegalNumberOfArguments() {
		final List<InstructionArgument> arguments = new ArrayList<>();
		arguments.add(new InstructionArgumentImpl(null, 3));
		arguments.add(new InstructionArgumentImpl(null, 3));

		new InstrPop(arguments);
	}

	@Test
	public void testPush() {
		// push r3 -> r3 sadrži 15, stack pointer je na početku na 200

		final List<InstructionArgument> arguments = new ArrayList<>();
		arguments.add(new InstructionArgumentImpl(null, 3));

		final Instruction push = new InstrPush(arguments);

		when(registers.getRegisterValue(3)).thenReturn(15);
		when(registers.getRegisterValue(Registers.STACK_REGISTER_INDEX)).thenReturn(200);

		assertEquals(false, push.execute(computer));

		verify(computer, times(3)).getRegisters();
		verify(computer, times(1)).getMemory();
		verify(memory, times(1)).setLocation(200, 15);
		verify(registers, times(1)).getRegisterValue(Registers.STACK_REGISTER_INDEX);
		verify(registers, times(1)).setRegisterValue(Registers.STACK_REGISTER_INDEX, 199);
		verify(registers, times(1)).getRegisterValue(3);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testPushIllegalArgument() {
		final List<InstructionArgument> arguments = new ArrayList<>();
		arguments.add(new InstructionArgumentImpl(3, 3));

		new InstrPush(arguments);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testPushIllegalNumberOfArguments() {
		final List<InstructionArgument> arguments = new ArrayList<>();
		arguments.add(new InstructionArgumentImpl(null, 3));
		arguments.add(new InstructionArgumentImpl(null, 3));

		new InstrPush(arguments);
	}

	@Test
	public void testRet() {
		// ret
		final Instruction call = new InstrRet(new ArrayList<>());

		when(memory.getLocation(200)).thenReturn(23);
		when(registers.getRegisterValue(Registers.STACK_REGISTER_INDEX)).thenReturn(199);

		assertEquals(false, call.execute(computer));

		verify(computer, times(3)).getRegisters();
		verify(computer, times(2)).getMemory();
		verify(memory, times(1)).getLocation(200);
		verify(memory, times(1)).setLocation(200, null);
		verify(registers, times(1)).setProgramCounter(23);
		verify(registers, times(1)).setRegisterValue(Registers.STACK_REGISTER_INDEX, 200);
		verify(registers, times(1)).getRegisterValue(Registers.STACK_REGISTER_INDEX);
	}

	@Test
	public void testUnpacking1() {
		final int registerDescriptor = 0x1000102;
		assertEquals(2, RegisterUtil.getRegisterIndex(registerDescriptor));
		assertEquals(1, RegisterUtil.getRegisterOffset(registerDescriptor));
		assertEquals(true, RegisterUtil.isIndirect(registerDescriptor));
	}

	@Test
	public void testUnpacking2() {
		final int registerDescriptor = 0x1FFFF02;
		assertEquals(2, RegisterUtil.getRegisterIndex(registerDescriptor));
		assertEquals(-1, RegisterUtil.getRegisterOffset(registerDescriptor));
		assertEquals(true, RegisterUtil.isIndirect(registerDescriptor));
	}

	@Test
	public void testUnpacking3() {
		final int registerDescriptor = 0x18000FE;
		assertEquals(254, RegisterUtil.getRegisterIndex(registerDescriptor));
		assertEquals(-32768, RegisterUtil.getRegisterOffset(registerDescriptor));
		assertEquals(true, RegisterUtil.isIndirect(registerDescriptor));
	}

	@Test
	public void testUnpacking4() {
		final int registerDescriptor = 0x17FFFFF;
		assertEquals(255, RegisterUtil.getRegisterIndex(registerDescriptor));
		assertEquals(32767, RegisterUtil.getRegisterOffset(registerDescriptor));
		assertEquals(true, RegisterUtil.isIndirect(registerDescriptor));
	}
}
