package fr.soat;

/**
 * this code is a sample benchmarked code 
 *
 */
public class UnderBenchmark {

	public static int doSomething() {
        int max;
        if (Math.random() < 0.005) {
            max = 10000;
        } else {
            max = 100;
        }
        int res = 0;
        for (int i = 0; i < max ; i++) {
            res++;
        }
        return res;		
	}
	
}
