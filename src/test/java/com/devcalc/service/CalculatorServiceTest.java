package com.devcalc.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes unitários para o CalculatorService.
 */
@DisplayName("Calculator Service Tests")
class CalculatorServiceTest {

    private CalculatorService calculatorService;

    @BeforeEach
    void setUp() {
        calculatorService = new CalculatorService();
    }

    @Test
    @DisplayName("Deve somar dois números positivos corretamente")
    void testAddPositiveNumbers() {
        double result = calculatorService.add(10, 5);
        assertEquals(15, result, 0.0001);
    }

    @Test
    @DisplayName("Deve somar números negativos corretamente")
    void testAddNegativeNumbers() {
        double result = calculatorService.add(-10, -5);
        assertEquals(-15, result, 0.0001);
    }

    @Test
    @DisplayName("Deve somar números decimais corretamente")
    void testAddDecimalNumbers() {
        double result = calculatorService.add(10.5, 5.3);
        assertEquals(15.8, result, 0.0001);
    }

    @Test
    @DisplayName("Deve subtrair dois números positivos corretamente")
    void testSubtractPositiveNumbers() {
        double result = calculatorService.subtract(10, 5);
        assertEquals(5, result, 0.0001);
    }

    @Test
    @DisplayName("Deve subtrair números negativos corretamente")
    void testSubtractNegativeNumbers() {
        double result = calculatorService.subtract(-10, -5);
        assertEquals(-5, result, 0.0001);
    }

    @Test
    @DisplayName("Deve subtrair números decimais corretamente")
    void testSubtractDecimalNumbers() {
        double result = calculatorService.subtract(10.5, 5.2);
        assertEquals(5.3, result, 0.0001);
    }

    @Test
    @DisplayName("Deve multiplicar dois números positivos corretamente")
    void testMultiplyPositiveNumbers() {
        double result = calculatorService.multiply(10, 5);
        assertEquals(50, result, 0.0001);
    }

    @Test
    @DisplayName("Deve multiplicar por zero corretamente")
    void testMultiplyByZero() {
        double result = calculatorService.multiply(10, 0);
        assertEquals(0, result, 0.0001);
    }

    @Test
    @DisplayName("Deve multiplicar números decimais corretamente")
    void testMultiplyDecimalNumbers() {
        double result = calculatorService.multiply(2.5, 4);
        assertEquals(10, result, 0.0001);
    }

    @Test
    @DisplayName("Deve dividir dois números positivos corretamente")
    void testDividePositiveNumbers() {
        double result = calculatorService.divide(10, 5);
        assertEquals(2, result, 0.0001);
    }

    @Test
    @DisplayName("Deve dividir números decimais corretamente")
    void testDivideDecimalNumbers() {
        double result = calculatorService.divide(10.5, 2);
        assertEquals(5.25, result, 0.0001);
    }

    @Test
    @DisplayName("Deve lançar exceção ao dividir por zero")
    void testDivideByZero() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            calculatorService.divide(10, 0);
        });
        
        assertEquals("Divisão por zero não é permitida", exception.getMessage());
    }

    @Test
    @DisplayName("Deve dividir números negativos corretamente")
    void testDivideNegativeNumbers() {
        double result = calculatorService.divide(-10, 5);
        assertEquals(-2, result, 0.0001);
    }
}
