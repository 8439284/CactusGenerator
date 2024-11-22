package org.ajls.cactusgenerator.advanced;

import java.util.ArrayList;
import java.util.Random;


public class RandomArrayList<T> extends ArrayList<T> {
    T getRandomElement() {
        Random random = new Random();
        int index = random.nextInt(size());
        return get(index);
//        int index = (int) (Math.random() * this.size());
//        return this.get(index);
    }

}
