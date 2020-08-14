package como.sekolah.mywidgets;

import java.util.Random;

public class NumberGenerator {

//    method 1
    public static int Generate(int max) {
        Random random = new Random();
        return random.nextInt(max);
    }
}
