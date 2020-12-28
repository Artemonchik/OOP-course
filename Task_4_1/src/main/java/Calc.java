import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

class SinOperator implements ICommand {

    @Override
    public String getOperatorRepresentation() {
        return "sin";
    }

    @Override
    public void Apply(Stack<String> numStack) {
        numStack.push(String.valueOf(Math.sin(Double.parseDouble(numStack.pop()))));
    }

    @Override
    public int getArity() {
        return 1;
    }
}

class CosOperator implements ICommand {

    @Override
    public String getOperatorRepresentation() {
        return "cos";
    }

    @Override
    public void Apply(Stack<String> numStack) {
        numStack.push(String.valueOf(Math.cos(Double.parseDouble(numStack.pop()))));
    }

    @Override
    public int getArity() {
        return 1;
    }
}

class LnOperator implements ICommand {

    @Override
    public String getOperatorRepresentation() {
        return "ln";
    }

    @Override
    public void Apply(Stack<String> numStack) {
        numStack.push(String.valueOf(Math.log(Double.parseDouble(numStack.pop()))));
    }

    @Override
    public int getArity() {
        return 1;
    }
}

class PowOperator implements ICommand {

    @Override
    public String getOperatorRepresentation() {
        return "pow";
    }

    @Override
    public void Apply(Stack<String> numStack) {
        double base = Double.parseDouble(numStack.pop());
        double power = Double.parseDouble(numStack.pop());
        numStack.push(String.valueOf(Math.pow(base, power)));
    }

    @Override
    public int getArity() {
        return 2;
    }
}

class SqrtOperator implements ICommand {

    @Override
    public String getOperatorRepresentation() {
        return "sqrt";
    }

    @Override
    public void Apply(Stack<String> numStack) {
        numStack.push(String.valueOf(Math.sqrt(Double.parseDouble(numStack.pop()))));
    }

    @Override
    public int getArity() {
        return 1;
    }
}

class Plus implements ICommand {
    @Override
    public String getOperatorRepresentation() {
        return "+";
    }

    @Override
    public void Apply(Stack<String> numStack) {
        double l = Double.parseDouble(numStack.pop());
        double r = Double.parseDouble(numStack.pop());
        numStack.push(String.valueOf(l + r));
    }

    @Override
    public int getArity() {
        return 2;
    }
}

class Minus implements ICommand {
    @Override
    public String getOperatorRepresentation() {
        return "-";
    }

    @Override
    public void Apply(Stack<String> numStack) {
        double l = Double.parseDouble(numStack.pop());
        double r = Double.parseDouble(numStack.pop());
        numStack.push(String.valueOf(l - r));
    }

    @Override
    public int getArity() {
        return 2;
    }
}

class Multiplication implements ICommand {
    @Override
    public String getOperatorRepresentation() {
        return "*";
    }

    @Override
    public void Apply(Stack<String> numStack) {
        double l = Double.parseDouble(numStack.pop());
        double r = Double.parseDouble(numStack.pop());
        numStack.push(String.valueOf(l * r));
    }

    @Override
    public int getArity() {
        return 2;
    }
}

class Division implements ICommand {
    @Override
    public String getOperatorRepresentation() {
        return "/";
    }

    @Override
    public void Apply(Stack<String> numStack) {
        double l = Double.parseDouble(numStack.pop());
        double r = Double.parseDouble(numStack.pop());
        numStack.push(String.valueOf(l / r));
    }

    @Override
    public int getArity() {
        return 2;
    }
}

public class Calc {
    private final HashMap<String, ICommand> operators = new HashMap<>();

    /**
     * Calculate the string
     *
     * @param string to be calculated
     * @return string representation of result
     */
    public String calculate(String string) {
        String[] arr = string.split(" ");
        Stack<String> numStack = new Stack<>();
        for (int i = arr.length - 1; i >= 0; i--) {
            String elem = arr[i];
            if (isNumber(elem)) {
                numStack.push(elem);
            } else if (operators.containsKey(elem)) {
                computeOperation(elem, numStack);
            } else {
                throw new IllegalArgumentException("This is illegal value: " + "elem");
            }
        }
        if(numStack.size() != 1){
            throw new IllegalArgumentException("String non computable");
        }
        return numStack.pop();
    }

    private boolean isNumber(String str) {
        try {
            double d = Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private void computeOperation(String operation, Stack<String> numStack) {
        if (numStack.size() < operators.get(operation).getArity()) {
            throw new IllegalArgumentException("Cannot parse this expression");
        }
        operators.get(operation).Apply(numStack);
    }

    /**
     * Register new command for our calculator
     *
     * @param command operator we need to add to calculator
     */
    public void registerOperator(ICommand command) {
        if (command == null) {
            throw new IllegalArgumentException("command is null");
        }
        operators.put(command.getOperatorRepresentation(), command);
    }


    public static void main(String[] args) {
        if (args.length != 1) {
            throw new IllegalArgumentException("Pass the only one string with the expression");
        }

        Calc calc = new Calc();
        calc.registerOperator(new SinOperator());
        calc.registerOperator(new CosOperator());
        calc.registerOperator(new Plus());
        calc.registerOperator(new LnOperator());
        calc.registerOperator(new Minus());
        calc.registerOperator(new Division());
        calc.registerOperator(new Multiplication());
        calc.registerOperator(new PowOperator());
        System.out.println(calc.calculate(args[0]));
    }
}
