import java.util.Stack;

public interface ICommand {
    String getOperatorRepresentation();
    void Apply(Stack<String> numStack);
    int getArity();
}
