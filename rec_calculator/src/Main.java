public class Main {
    public static void main(String[] args) {
        String exp = "(1 + 2) * 10 - 6 / (2 + 1)";
        // (1 + 2) * 10 - 6 / (2 + 1) = 28.0
        // (1 + 2 * 4 * 3) * 10 - 6 + 18 / (2 + (1  + 3) + 2 - (8 / 2 - 2)) = 247.0

        double result = calculate(exp.replace(" ", ""));

        System.out.println("Result: " + result);
    }

    public static double calculate(String exp) {
        double result;

        int startIndex;
        int endIndex;
        String subExp;

        if (exp.contains("(")) {
            startIndex = exp.lastIndexOf("(");
            endIndex = exp.indexOf(")", startIndex);
            subExp = exp.substring(startIndex + 1, endIndex);
            result = evaluate(subExp);
            exp = exp.replace(exp.substring(startIndex, endIndex + 1), String.valueOf(result));
            System.out.println(exp);
            return calculate(exp);
        } else {
            return evaluate(exp);
        }
    }

    public static double evaluate (String exp) {
        double result = 0;
        String[] tokens;

        if (exp.contains("*")) {
            tokens = exp.split("[-+/]");
            for (String token : tokens) {
                if (token.contains("*")) {
                    String[] multiplyTokens = token.split("[*]");
                    result = Double.parseDouble(multiplyTokens[0]);
                    for (int i = 1; i < multiplyTokens.length; i++) {
                        result *= Double.parseDouble(multiplyTokens[i]);
                    }
                    exp = exp.replace(token, String.valueOf(result));
                    System.out.println(exp);
                }
            }
        }

        if (exp.contains("/")) {
            tokens = exp.split("[-+]");
            for (String token : tokens) {
                if (token.contains("/")) {
                    String[] divideTokens = token.split("/");
                    result = Double.parseDouble(divideTokens[0]);
                    for (int i = 1; i < divideTokens.length; i++) {
                        if (Double.parseDouble(divideTokens[i]) == 0.0) {
                            throw new RuntimeException("Can't divide by 0");
                        }
                        result /= Double.parseDouble(divideTokens[i]);
                    }
                    exp = exp.replace(token, String.valueOf(result));
                    System.out.println(exp);
                }
            }
        }

        if (exp.contains("+") || exp.contains("-")) {
            tokens = exp.split("(?=[+-])");
            result = 0;
            for (String token : tokens) {
                result += Double.parseDouble(token);
            }
        }

        return result;
    }
}