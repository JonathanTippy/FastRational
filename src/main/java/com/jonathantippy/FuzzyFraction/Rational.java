package com.jonathantippy.FuzzyFraction;

/*
for now uses longs but eventually want to make hexaLong or octaLong 
(hexaLong would represent integers up to about 10^300)
*/

public class Rational 
{

    public static final Rational ZERO = new Rational(0L, 1L);
    public static final Rational ONE = new Rational(1L, 1L);

    public static final Rational MAX_VALUE = new Rational(Long.MAX_VALUE, 1L);
    public static final Rational MIN_VALUE = new Rational(-Long.MAX_VALUE, 1L);

    public static final Rational MIN_POSITIVE_VALUE = new Rational(1L, Long.MAX_VALUE);
    public static final Rational MAX_NEGATIVE_VALUE = new Rational(-1L, Long.MAX_VALUE);


    private final long numerator;
    private final long denomenator;

    // Constructors with both numerator and denomenator
    public Rational(long numerator, long denomenator) 
    throws ArithmeticException {
        if (!(denomenator==0)) {;} else {
            throw new ArithmeticException("/ by zero");
        }
        if (!(numerator==Long.MIN_VALUE||denomenator==Long.MIN_VALUE)) {;} else {
            throw new ArithmeticException("input too small");
        }

        this.numerator = numerator;
        this.denomenator = denomenator;
    }

    // Constructors with integers
    public Rational(long numerator) {
        if (!(numerator==Long.MIN_VALUE)) {;} else {
            throw new ArithmeticException("input too small");
        }
        this.numerator = numerator; 
        this.denomenator = 1L;
    }


    // Adaptive constructors
    public Rational(String fraction) 
    throws ArithmeticException, IllegalArgumentException {
        if (fraction.matches("^(-?)\\d+(/(-?)\\d+)?")) {
            if (fraction.contains("/")) {
                String[] terms = fraction.split("/");
                long maybeNumerator = Long.parseLong(terms[0]);
                long maybeDenomenator = Long.parseLong(terms[1]);
                if (!(maybeDenomenator==0)) {;} else {
                    throw new ArithmeticException("/ by zero");
                }
                if (
                    !(
                    maybeNumerator==Long.MIN_VALUE
                    ||maybeDenomenator==Long.MIN_VALUE
                    )
                ) {;} else {
                    throw new ArithmeticException("input too small");
                }
                this.numerator = maybeNumerator;
                this.denomenator = maybeDenomenator;
            } else {
                long maybeNumerator = Long.parseLong(fraction);
                if (!(maybeNumerator==Long.MIN_VALUE)) {;} else {
                    throw new ArithmeticException("input too small");
                }
                this.numerator = maybeNumerator;
                this.denomenator = 1L;
            }
        } else {
            throw new IllegalArgumentException("Not a fraction");
        }
    }

    // Accessors
    public long getNumerator() {
        return this.numerator;
    }
    public long getDenomenator() {
        return this.denomenator;
    }

    // Display
    @Override
    public String toString() {
        StringBuilder numberConstruct = new StringBuilder();

        if (signum() >= 0) {;} else {
            numberConstruct.append('-');
        }
        numberConstruct.append(Math.abs(numerator));
        numberConstruct.append('/');
        numberConstruct.append(Math.abs(denomenator));
        return numberConstruct.toString();
    }

    // Division
    public Rational divide(Rational divisor) {
        return new Rational(
            this.numerator * divisor.denomenator
            , this.denomenator * divisor.numerator
            );
    }

    // Multiplication
    public Rational multiply(Rational factor) {
        return new Rational(
            this.numerator * factor.numerator
            , this.denomenator * factor.denomenator
        );
    }

    // Addition
    public Rational add(Rational addend) {
        return new Rational(
            (this.numerator * addend.denomenator)
            + (addend.numerator * this.denomenator)
            , this.denomenator * addend.denomenator
        );
    }

    // Negation
    public Rational negate(Rational input) {
        return new Rational(
            this.numerator * -1
            , this.denomenator
        );
    }

    // Subtraction
    public Rational subtract(Rational minuend) {
        return new Rational(
            (this.numerator * minuend.denomenator)
            - (minuend.numerator * this.denomenator)
            , this.denomenator * minuend.denomenator
        );
    }

    // Absoulte Value
    public Rational abs() {
        return new Rational(
            Math.abs(this.numerator)
            , Math.abs(this.denomenator)
            );
    }


    // UTILS

    // Squeezes (incomplete simplification)
    public Rational twoSimplify() {
        int extraTwos = 
            Math.min(               
            Long.numberOfTrailingZeros(this.numerator)
            , Long.numberOfTrailingZeros(this.denomenator)
            );
        return new Rational(
            this.numerator >> extraTwos
            , this.denomenator >> extraTwos
        );
    }

     // Crushes (approximate simplfication)

     protected Rational bitShiftSimplify(int maxBitLength) {

        // Purposefully performing the shift on negatives so that the result is 
        // more accurate, just like how we round up on .5
        int sign = signum();
        long absNumerator = Math.abs(this.numerator);
        long absDenomenator = Math.abs(this.denomenator);

        int unwantedBits = 
            Math.max(
            Math.max(
                (63 - Long.numberOfLeadingZeros(absNumerator)) - (maxBitLength-1)
            , (63 - Long.numberOfLeadingZeros(absDenomenator)) - (maxBitLength-1)
            )
            , 0);

        long newDenomenator = -((-absDenomenator) >> unwantedBits);

        if (! (newDenomenator == 0)) {;} else {newDenomenator = 1;}
        System.out.println("input: " + this + "\nunwanted bits: " + unwantedBits + "\nnumpostshift:" + (this.numerator >> unwantedBits) + "\ndenomenpostshify" + newDenomenator);
        return new Rational(
            (-((-absNumerator) >> unwantedBits))*sign
            , newDenomenator
            );
    }
/*
    protected static long bitLimitRoundUp(long input, int bitLimit) {
        long signBit = (input & Long.MIN_VALUE) >>> 63;
        long signMask = signBit * (-1);
        long absInput = (input ^ signMask);
    }
*/



    // UTILS

    public int signum() {
        int numeratorSign = Long.signum(this.numerator);
        int denomenatorSign = Long.signum(this.denomenator);
        int sign = numeratorSign*denomenatorSign;
        return sign;
    }

    protected static long branchlessAbs(long input) {
        long signBit = (input & Long.MIN_VALUE) >>> 63;
        long signMask = signBit * (-1);
        return (input ^ signMask) + signBit;
    }

    // FuzzyFractions stuff

    public short[] bitsAfterMultiply(Rational that) {
        return new short[]{
            bitsAfterMultiply(this.numerator, that.numerator)
            , bitsAfterMultiply(this.denomenator, that.denomenator)
            };
    }

    public static short bitsAfterMultiply(long a, long b) {
        return (short) (
            (63 - Long.numberOfLeadingZeros(a))
            + (63 - Long.numberOfLeadingZeros(b))
            );

    }
/*
    public Rational lowMultiply(Rational multiplier) {
        short[] bitsAfterMultiply = this.bitsAfterMultiply(multiplier);
        short numeratorBAM = bitsAfterMultiply[0];
        short denomenatorBAM = bitsAfterMultiply[1];


    }
*/
}
