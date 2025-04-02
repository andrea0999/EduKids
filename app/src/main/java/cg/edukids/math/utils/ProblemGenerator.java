package cg.edukids.math.utils;
import java.util.Random;

public class ProblemGenerator {
    private int difficultyLevel;
    private Random random;

    public ProblemGenerator(int difficultyLevel) {
        this.difficultyLevel = difficultyLevel;
        this.random = new Random();
    }

    public String generateProblem() {
        int num1 = generateNumber();
        int num2 = generateNumber();
        char operator = getRandomOperator();

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
            case '/': return num2 != 0 ? num1 / num2 : 0;
            default: return 0;
        }
    }

    private int generateNumber() {
        return random.nextInt(difficultyLevel * 10) + 1;
    }

    private char getRandomOperator() {
        char[] operators = {'+', '-', '*', '/'};
        return operators[random.nextInt(operators.length)];
    }
}
