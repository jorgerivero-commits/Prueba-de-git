package java_ex;

import java.util.Scanner;

public class Echo {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int n = 0;
        while (input.hasNextLine()) {
            String line = input.nextLine();
	    if(line.contains("stress-ng")){
		n++;
		System.out.println(n + ": "+ line);
	    }
        }
        System.out.println("lines: " + n);
    }
}
