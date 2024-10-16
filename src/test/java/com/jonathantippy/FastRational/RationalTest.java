package com.jonathantippy.FastRational;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.math.BigInteger;
public class RationalTest {

    //////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////
                                //BASIC CONSTRUCTOR
    //////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////

    @Test
    public void basicConstructorTest() {
        Rational r = new Rational(BigInteger.ONE, BigInteger.ONE);
        String s = r.toString();
        assertEquals("1/1", s);
    }


    //////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////
                                //STRINGS
    //////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////

    @Test
    public void stringPositiveTest() {
        Rational r = new Rational("50/7");
        String s = r.toString();
        assertEquals("50/7", s);
    }

    @Test
    public void stringNegativeTest() {
        Rational r = new Rational("-50/7");
        String s = r.toString();
        assertEquals("-50/7", s);
    }

    @Test
    public void stringNegativeSimplifyTest() {
        Rational r = new Rational("-50/-7");
        String s = r.toString();
        assertEquals("50/7", s);
    }

    @Test
    public void stringIntegerTest() {
        Rational r = new Rational("5");
        String s = r.toString();
        assertEquals("5/1", s);
    }

    @Test
    public void hardStringIntegerTest() {
        Rational r = new Rational("60");
        String s = r.toString();
        assertEquals("60/1", s);
    }


    //////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////
                                //INTEGERS
    //////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////

    @Test
    public void intPositiveTest() {
        Rational r = new Rational(50L);
        String s = r.toString();
        assertEquals("50/1", s);
    }

    @Test
    public void intNegativeTest() {
        Rational r = new Rational(-50L);
        String s = r.toString();
        assertEquals("-50/1", s);
    }

    //////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////
                                //DIVISION
    //////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////

    @Test
    public void divisionTest() {
        Rational dividend = new Rational(1);
        Rational divisor = new Rational(3);
        Rational answer = dividend.divide(divisor);
        String s = answer.toString();
        assertEquals("1/3", s);
    }

    @Test
    public void hardDivisionTest() {
        Rational dividend = new Rational("5/6");
        Rational divisor = new Rational("2/3");
        Rational answer = dividend.divide(divisor);
        String s = answer.toString();
        assertEquals("15/12", s);
    }

    //////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////
                                //MULTIPLICATION
    //////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////

    @Test
    public void multiplicationTest() {
        Rational factorOne = new Rational(1);
        Rational factorTwo = new Rational(3);
        Rational answer = factorOne.multiply(factorTwo);
        String s = answer.toString();
        assertEquals("3/1", s);
    }

    @Test
    public void hardMultiplicationTest() {
        Rational factorOne = new Rational("5/7");
        Rational factorTwo = new Rational("2/3");
        Rational answer = factorOne.multiply(factorTwo);
        String s = answer.toString();
        assertEquals("10/21", s);
    }


    //////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////
                                //SIMPLIFICATION
    //////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////

    @Test
    public void simplificationTest() {
        Rational a = new Rational("50/20");
        Rational answer = a.slowSimplify();
        String s = answer.toString();
        assertEquals("5/2", s);
    }

    @Test
    public void simplifyTwosTest() {
        Rational a = new Rational("50/20");
        Rational answer = a.twoSimplify();
        String s = answer.toString();
        assertEquals("25/10", s);
    }


    //////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////
                                //ADDITION
    //////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////

    @Test
    public void additionTest() {
        Rational a = new Rational(5);
        Rational b = new Rational(6);
        Rational answer = a.add(b);
        String s = answer.toString();
        assertEquals("11/1", s);
    }

}