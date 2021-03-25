import java.util.function.Predicate;

public class PrimeChecker implements Predicate<Integer> {

    public boolean isPrimeFast(Integer n) {
        if (n <= 3) {
            return n > 1;
        }
        if (n % 2 == 0 || n % 3 == 0) {
            return false;
        }
        int i = 5;
        while (i * i <= n) {
            if (n % i == 0 || n % (i + 2) == 0) {
                return false;
            }
            i += 6;
        }
        return true;
    }

    public boolean isPrimeSlow(Integer n) {
        if (n <= 1)
            return false;

        for (int i = 2; i * i < n; i++) {
            if (n % i == 0) {
                return false;
            }
        }

        return true;
    }

    @Override
    public boolean test(Integer integer) {
        return !isPrimeSlow(integer);
    }
}
