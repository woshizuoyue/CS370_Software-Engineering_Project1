import java.util.concurrent.ThreadLocalRandom;

public class ThreadExecute extends Thread {

    int id;

    double sumE = 0;

    double sumCp = 0;

    double totalE = 0;

    double totalCp = 0;


    public ThreadExecute(int i)
    {
        id = i;

    }

    @Override
    public void run()
    {
        for(int i=0;i<Project1.Nm;i++)
        {

            metro();
        }

        totalE = sumE / Project1.Nm;

        totalCp = sumCp / Project1.Nm;

    }

    public void metro()
    {
        int[] currentConfig = new int[Project1.n];
        int[] prevConfig = new int[Project1.n];
        double deltaE = 0;
        double averageE = 0;
        double averageCP = 0;

        //init;
        for(int i=0;i<Project1.n;i++)
        {
            if(Project1.C >= 0) {

                currentConfig[i] = 1;

            }
            else{

                if(i%2==0)
                {
                    currentConfig[i] = 1;
                }
                else
                {
                    currentConfig[i] = -1;
                }
            }
        }

        //copy currentconfig;

        for(int i=0;i<Project1.n;i++)
        {

            prevConfig[i] = currentConfig[i];
        }

        //flip,get new config;

        for(int i=0;i<Project1.flipTime;i++) {

            int tempIndex = ThreadLocalRandom.current().nextInt(0, Project1.n - 1);

            if (currentConfig[tempIndex] == 1) currentConfig[tempIndex] = -1;
            else currentConfig[tempIndex] = 1;

            //get delataE;

            deltaE = compDeltaE(currentConfig, prevConfig);


            // make decision to replace or not;

            if (deltaE <= 0) {

                replace(currentConfig, prevConfig);


            } else {

                double exponent = -(deltaE / Project1.T);

                //could check;
                double prob = Math.exp(exponent);

                double rand = ThreadLocalRandom.current().nextDouble(0, 1);

                if (rand <= prob){

                    replace(currentConfig, prevConfig);

                }

                else{
                    replace(prevConfig, currentConfig);
                }

            }
        }

        //after all the flip;

        averageE = computeM(currentConfig);

        averageCP = computeCP(currentConfig);

        sumE = sumE + averageE;

        sumCp = sumCp + averageCP;

    }

    public double compDeltaE(int[] current, int[] prev)
    {
        double tempCurrentE = 0;
        double tempPrevE = 0;

        for(int i = 0; i< Project1.n; i++)
        {
            tempCurrentE += Project1.B*current[i]+Project1.C*current[i]*current[(i+1)% Project1.n];
            tempPrevE += Project1.B*prev[i]+Project1.C*prev[i]*prev[(i+1)% Project1.n];

        }

        tempCurrentE = -tempCurrentE;
        tempPrevE = -tempPrevE;

        return tempCurrentE - tempPrevE;
    }

    public void replace(int[] current, int[] prev)
    {
        for(int i = 0; i< Project1.n; i++)
        {
            prev[i] = current[i];
        }
    }

    public void showConfig(int[] config)
    {
        for(int i=0;i<config.length;i++)
        {
            System.out.print(config[i]);
        }
        System.out.println();
    }
    // add function to computeM and cp;

    public double computeM(int[] current)
    {
        double temp = 0;

        for(int i=0;i<Project1.n;i++)
        {
            temp += current[i];
        }

        return temp/Project1.n;
    }

    public double computeCP(int[] current)
    {
        double temp = 0;

        for(int i=0;i<Project1.n;i++)
        {
            temp += current[i]*current[(i+1)%Project1.n];
        }

        return temp/Project1.n;
    }
}


