package cg.edukids.math.utils;

import java.util.Random;

public class ProblemGenerator {
    private Random random;
    private char forcedOperator; // '\0' înseamnă neforțat

    public ProblemGenerator() {
        this.random = new Random();
        this.forcedOperator = '\0'; // implicit: orice operator
    }

    public void setForcedOperator(char op) {
        this.forcedOperator = op;
    }

    public String generateProblem() {
        char operator = (forcedOperator != '\0') ? forcedOperator : getRandomOperator();
        int num1 = 0;
        int num2 = 0;

        switch (operator) {
            case '+':
                num1 = random.nextInt(50) + 1;
                num2 = random.nextInt(50) + 1;
                break;
            case '-':
                num1 = random.nextInt(50) + 1;
                num2 = random.nextInt(num1 + 1); // num2 ≤ num1, fără rezultate negative
                break;
            case '*':
                num1 = random.nextInt(9) + 1; // cifră
                num2 = random.nextInt(9) + 1; // cifră
                break;
            case '/':
                num2 = random.nextInt(9) + 1; // evităm împărțirea la 0
                int result = random.nextInt(9) + 1; // rezultat între 1 și 9
                num1 = num2 * result; // asigură împărțire exactă
                break;
        }

        return num1 + " " + operator + " " + num2;
    }

    public int getCorrectAnswer(String problem) {
        String[] parts = problem.split(" ");
        int num1 = Integer.parseInt(parts[0]);
        int num2 = Integer.parseInt(parts[2]);
        char operator = parts[1].charAt(0);

        switch (operator) {
            case '+': return num1 + num2;
            case '-': return num1 - num2;
            case '*': return num1 * num2;
            case '/': return (num2 != 0) ? num1 / num2 : 0;
            default: return 0;
        }
    }

    private char getRandomOperator() {
        char[] operators = {'+', '-', '*', '/'};
        return operators[random.nextInt(operators.length)];
    }
}
