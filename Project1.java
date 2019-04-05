import java.io.PrintWriter;


public class Project1 {

    public static double B = 0.1;
    public static double C = -0.05;
    public static double T = -0.1;
    public static final int n = 100; // node num
    public static final int Nf = 6; // random set
    public static final int flipTime = n*Nf;
    public static final int Nt = 1000; // thread num
    public static int Nm = 83; // each thread runtime;
    public static final int points = 19; // how many points we need;
    public static final double interval = 0.1;

    public static double[] totalM = new double[Nt]; // each thread <m>
    public static double[] totalAllCp = new double[Nt]; // each thread <cp>

    public static double sumtotalM = 0;
    public static double sumtotalAllCp = 0;


    public static void main(String[] args)
    {


        try {

            PrintWriter pw = new PrintWriter("370Project_challeng1.csv");


                    for (int k = 0;k<=points; k++) {

                        T = T + interval + 0.01 ;

                            new Project1().excuteThread(pw, k);
                    }

           pw.close();

        }catch (Exception e){}

    }

    public void excuteThread(PrintWriter pw, int k)
    {
        for (int i = 0; i < Nt; i++)
        {
            ThreadExecute[] myThread = new ThreadExecute[Nt];

            myThread[i] = new ThreadExecute(i);

            myThread[i].start();

            try {

                if (myThread[i].isAlive())

                    myThread[i].join();

            } catch (Exception e) {}


            totalM[i] = myThread[i].totalE; //

            totalAllCp[i] = myThread[i].totalCp;
        }

        for (int i = 0; i < Nt; i++) {

            sumtotalM = sumtotalM + totalM[i];

            sumtotalAllCp = sumtotalAllCp + totalAllCp[i];
        }

        sumtotalM = sumtotalM / Nt;  // it is um;

        sumtotalAllCp = sumtotalAllCp / Nt; // it is uc;

        System.out.println(sumtotalM + "###" + sumtotalAllCp);

        pw.println(T + "," + sumtotalM + "," + sumtotalAllCp);
    }

}
