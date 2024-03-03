public class Main {
    public static void main(String[] args) {

        String exp = "(1 + 2) * 10 - 6 / (2 + 1)";
        String newExp = exp.replace(" ", "");

        double result = calculate(newExp);

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
            result = operate(subExp);
            exp = exp.replace(exp.substring(startIndex, endIndex + 1), String.valueOf(result));
            System.out.println(exp);
            return calculate(exp);
        } else {
            //check if "*" and "/" exist then create a sub string to calculate it first and replace it
            //all left is "+" and "-" we can do operation from left to right
            String[] tokens = exp.split("[-+]");
            for (String token : tokens) {
                if (token.contains("*") || token.contains("/")) {
                    result = operate(token);
                    exp = exp.replace(token, String.valueOf(result));
                    System.out.println(exp);
                }
            }

            tokens = exp.split("(?=[+-])");
            result = 0;
            for (String token : tokens) {
                result += Double.parseDouble(token);
            }

            return result;
        }
    }

    public static double operate (String exp) {
        String[] operators = {"+", "-", "*", "/"};
        String operator = "";
        String[] operands = new String[2];

        for(String op : operators) {
            if(exp.contains(op)) {
                operands = exp.split("[-+*/]");
                operator = op;
            }
        }

        double a = Double.parseDouble(operands[0]);
        double b = Double.parseDouble(operands[1]);

        double result = 0;
        switch (operator) {
            case "+" -> result = a + b;
            case "-" -> result = a - b;
            case "*" -> result = a * b;
            case "/" -> result = a / b;
        }

        return result;
    }
}