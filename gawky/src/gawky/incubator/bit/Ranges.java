package gawky.incubator.bit;

/** A class demonstrating bit operations to determine integer ranges */
public class Ranges {
	public static void main(String args[]) {
		
		System.out.print(1 << 1);
		byteRange();
		shortRange();
		intRange();
		longRange();
	}

	/** uses complement operations */
	static void intRange() {
		System.out.println("int\t" + (~0 >>> 1) + "\t" + (~(~0 >>> 1)));
	}

	/** maximum and minimum long value */
	static final long maxLong = ~0L >>> 1, minLong = ~(~0L >>> 1);

	static void longRange() {
		System.out.println("long\t" + maxLong + "\t" + minLong);
	}

	/** uses casts and literals */
	static void shortRange() {
		System.out.println("short\t" + (short) 077777 + "\t" + (short) 0x8000);
	}

	/** shifts ones until no further changes occur */
	static void byteRange() {
		byte i, j = 1;

		do {
			i = j;
			j = (byte) (i << 1 | 1);
		} while (j > i);
		System.out.print("byte\t" + i);

		do {
			i = j;
			j = (byte) (i << 1);
		} while (j < i);
		System.out.println("\t" + i);
	}
}
