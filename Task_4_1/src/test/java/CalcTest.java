import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.beans.Transient;

public class CalcTest {
    Calc calc;
    @Before
    public void initCalc(){
        calc = new Calc();
        calc.registerOperator(new SinOperator());
        calc.registerOperator(new CosOperator());
        calc.registerOperator(new Plus());
        calc.registerOperator(new LnOperator());
        calc.registerOperator(new Minus());
        calc.registerOperator(new Division());
        calc.registerOperator(new Multiplication());
        calc.registerOperator(new PowOperator());

    }
    @Test
    public void fullTest(){
        String res = calc.calculate("* 3 + 2 ln / pow + cos sin - + 1 2 3 1 10 1024");
        assertEquals(Double.valueOf(6), Double.valueOf(res));
    }
    @Test
    public void incorrectStringTest(){
        try {
            String res = calc.calculate("ln / pow + cos sin - + 1 2 / 3 1 10 1024");
            fail();
        }catch (Exception ignored){

        }
    }

    @Test
    public void incorrectHandlerException(){
        try {
            calc.registerOperator(null);
            fail();
        }catch (Exception ignored){

        }
    }
}
