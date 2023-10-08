import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Main extends Thread  {

    public static void main(String[] args) throws InterruptedException, IOException {
        /*
        ArrayList<Integer> test = new ArrayList<>();
        test.add(0);
        test.add(1);
        test.add(0);
        Data dat1 = new DataRef(test,2);

        ArrayList<Integer> test2 = new ArrayList<>();
        test2.add(1);
        test2.add(1);
        test2.add(1);
        Data dat2= new DataRef(test2,1);
        ArrayList<Integer> test3 = new ArrayList<>();
        test3.add(2);
        test3.add(2);
        test3.add(2);
        Data dat3= new DataRef(test3,3);


        ParallelCalculator parallelCalculator = new ParallelCalculator();
        parallelCalculator.setThreadsNumber(2);



        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Enter String");
        String s = br.readLine();
        parallelCalculator.addData(dat1);
        System.out.print("Enter Integer:");
        try {
            int i = Integer.parseInt(br.readLine());
            parallelCalculator.addData(dat2);
            parallelCalculator.addData(dat3);
        } catch(NumberFormatException nfe) {
            System.err.println("Invalid Format!");
        }
        System.out.print("Enter Integer:");



        try {
            int i = Integer.parseInt(br.readLine());
            ArrayList<Integer> testasd = new ArrayList<>();
            testasd.add(3);
            testasd.add(3);
            testasd.add(3);

            parallelCalculator.addData(new DataRef(testasd,4));
        } catch(NumberFormatException nfe) {
            System.err.println("Invalid Format!");
        }*/
/*
        try {
            int i = Integer.parseInt(br.readLine());
            ArrayList<Integer> testasd = new ArrayList<>();
            testasd.add(4);
            testasd.add(4);
            testasd.add(4);

            deltaParallelCalculator.addData(new Data(testasd,5));
        } catch(NumberFormatException nfe) {
            System.err.println("Invalid Format!");
        }

        try {
            int i = Integer.parseInt(br.readLine());
            ArrayList<Integer> testasd = new ArrayList<>();
            testasd.add(5);
            testasd.add(5);
            testasd.add(5);

            deltaParallelCalculator.addData(new Data(testasd,6));
        } catch(NumberFormatException nfe) {
            System.err.println("Invalid Format!");
        }
*/
        /*
        System.out.println("Hello world!");
        int coiuna = 3;
        System.out.println("Enter a year to check if it is a leap year");
        while(true){
            Scanner input = new Scanner(System.in);
            int year = input.nextInt();
            if(year == 0)
                break;
            if((year % 100 == 0 && year % 400 == 0) || (year % 4 == 0 && year % 100 != 0))
                System.out.println(year + " is a leap year");
            else
                System.out.println(year + " is not a leap year");
            ArrayList<Integer> testasd = new ArrayList<>();
            test2.add((int )(Math.random() * 50 + 1));
            test2.add((int )(Math.random() * 50 + 1));
            test2.add((int )(Math.random() * 50 + 1));

            deltaParallelCalculator.addData(new Data(testasd,coiuna));
            coiuna++;

        }
    */
    }
}