package com.devcalc;

import com.devcalc.service.CalculatorService;
import io.javalin.Javalin;
import io.javalin.http.Context;

import java.util.HashMap;
import java.util.Map;

public class App {
    
    private static final CalculatorService calculator = new CalculatorService();

    public static void main(String[] args) {
        Javalin app = Javalin.create(config -> {
            config.showJavalinBanner = false;
        }).start(7000);

        app.get("/", ctx -> {
            Map<String, String> response = new HashMap<>();
            response.put("message", "DevCalc API está funcionando!");
            response.put("info", "Use os endpoints: /add, /subtract, /multiply, /divide com parâmetros a e b");
            ctx.json(response);
        });

        app.get("/add", ctx -> {
            handleOperation(ctx, calculator::add);
        });

        app.get("/subtract", ctx -> {
            handleOperation(ctx, calculator::subtract);
        });

        app.get("/multiply", ctx -> {
            handleOperation(ctx, calculator::multiply);
        });

        app.get("/divide", ctx -> {
            try {
                handleOperation(ctx, calculator::divide);
            } catch (ArithmeticException e) {
                Map<String, String> error = new HashMap<>();
                error.put("error", e.getMessage());
                ctx.status(400).json(error);
            }
        });

        System.out.println("DevCalc API rodando em http://localhost:7000");
    }

    @FunctionalInterface
    interface Operation {
        double execute(double a, double b);
    }

    private static void handleOperation(Context ctx, Operation operation) {
        String aParam = ctx.queryParam("a");
        String bParam = ctx.queryParam("b");

        if (aParam == null || bParam == null) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Parâmetros 'a' e 'b' são obrigatórios");
            ctx.status(400).json(error);
            return;
        }

        try {
            double a = Double.parseDouble(aParam);
            double b = Double.parseDouble(bParam);
            double result = operation.execute(a, b);

            Map<String, Double> response = new HashMap<>();
            response.put("a", a);
            response.put("b", b);
            response.put("result", result);
            ctx.json(response);
        } catch (NumberFormatException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Parâmetros inválidos. Use números válidos para 'a' e 'b'");
            ctx.status(400).json(error);
        }
    }
}
