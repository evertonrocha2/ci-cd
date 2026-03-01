package com.devcalc.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Calculator Service Tests")
public class CalculatorServiceTest {

    private CalculatorService calculator;

    @BeforeEach
    public void setUp() {
        calculator = new CalculatorService();
    }

    @Test
    @DisplayName("Deve somar dois números positivos")
    public void testAddPositiveNumbers() {
        double result = calculator.add(10, 5);
        assertEquals(15.0, result, 0.001);
    }

    @Test
    @DisplayName("Deve somar números negativos")
    public void testAddNegativeNumbers() {
        double result = calculator.add(-10, -5);
        assertEquals(-15.0, result, 0.001);
    }

    @Test
    @DisplayName("Deve subtrair dois números")
    public void testSubtract() {
        double result = calculator.subtract(10, 5);
        assertEquals(5.0, result, 0.001);
    }

    @Test
    @DisplayName("Deve multiplicar dois números")
    public void testMultiply() {
        double result = calculator.multiply(10, 5);
        assertEquals(50.0, result, 0.001);
    }

    @Test
    @DisplayName("Deve dividir dois números")
    public void testDivide() {
        double result = calculator.divide(10, 5);
        assertEquals(2.0, result, 0.001);
    }

    @Test
    @DisplayName("Deve lançar exceção ao dividir por zero")
    public void testDivideByZero() {
        ArithmeticException exception = assertThrows(
            ArithmeticException.class,
            () -> calculator.divide(10, 0)
        );
        assertEquals("Divisão por zero não é permitida", exception.getMessage());
    }

    @Test
    @DisplayName("Deve lidar com números decimais")
    public void testDecimalNumbers() {
        double result = calculator.add(10.5, 5.3);
        assertEquals(15.8, result, 0.001);
    }
}
