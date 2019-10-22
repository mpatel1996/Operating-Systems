/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author mihirkumarp
 */

import java.io.*;
import java.util.Scanner;
import java.util.ArrayList;

public class RoundRobin {
    
    // changes the quantum time between jobs
    public static int quantum = 5;
    
    // picking which jobs to take. 5,10,15 jobs file
    public static int addon = 10; 
    public static void main(String [] argv) throws IOException {
        int counter = 1;
        ArrayList<Integer> id = new ArrayList<>(); // Wait Time
        ArrayList<Integer> wTime = new ArrayList<>(); // Wait Time
        ArrayList<Integer> bTime = new ArrayList<>(); // job time
        
        int total = 0;
        float avg;
        
        try (FileWriter fw = new FileWriter("../jobs/output_RR"+ quantum+ "_" + addon + ".txt")) {
            for(int k = 0; k <10; k++){
                fw.write("Trial #: " + counter + "\tQuantum #: " + quantum);
                counter++;
                File myFile = new File("jobs/job_"+addon+"_" + k +".txt");

                // Loops until there is next line
                try (Scanner s = new Scanner(myFile)) {
                    // Loops until there is next line
                    while (s.hasNext()) {
                        //if there is int as next line, store it. Else, skip to next line.
                        if(s.hasNextInt()){
                            bTime.add(s.nextInt());
                        }else
                            s.nextLine();
                    }
                }
                
                Integer[] wtimearr = new Integer[bTime.size()];
                Integer[] btimearr = new Integer[bTime.size()];
                Integer[] idarr = new Integer[bTime.size()];
                int[] completearr = new int[bTime.size()];
                
                wtimearr = wTime.toArray(wtimearr);
                btimearr = bTime.toArray(btimearr);
                idarr = id.toArray(idarr);
                
                for (int j =0; j < bTime.size(); j++){
                    idarr[j] = j+1;
                    wtimearr[j] = 0;
                    completearr[j] = 0;
                }
                
                total = roundRobin(wtimearr, btimearr, completearr, total);
                
                completearr[wtimearr.length - 1] = wtimearr[wtimearr.length -1] + btimearr[wtimearr.length- 1];
                
                int n = bTime.size();
                avg = (float)total/n;
                //System.out.println("\nProcess_ID\tBurst_time\tWait_time\tcomplete_time");
                fw.write("\nProcess_ID\tBurst_time\tWait_time\tcomplete_time\n");
                for(int i=0;i<n;i++)
                {
                    //System.out.println(idarr[i]+"\t\t"+btimearr[i]+"\t\t"+wtimearr[i]+"\t\t"+completearr[i]);
                    fw.write(idarr[i]+"\t\t\t"+btimearr[i]+"\t\t\t"+wtimearr[i]+"\t\t\t"+completearr[i] + "\n");
                    
                }
                //System.out.println("\nTotal wait time: "+total+"\nAverage wait time: "+avg);
                fw.write("\nTotal wait time: "+total+"\nAverage wait time: "+avg + "\n\n");
                
                id.clear();
                wTime.clear();
                bTime.clear();
                
                total = 0;
                avg = 0;
            } // end of for loop
        }
    }// end of main

    private static int roundRobin(Integer[] wtimearr, Integer[] btimearr, 
                                    int[] carr, int t ) {
        
        int n = btimearr.length;
         int rem_bt[] = new int[n]; 
        for (int i = 0 ; i < n ; i++) 
            rem_bt[i] =  btimearr[i]; 
       
       
        // Keep traversing processes in round robin manner 
        // until all of them are not done. 
        while(true) 
        { 
            boolean done = true; 
       
            // Traverse all processes one by one repeatedly 
            for (int i = 0 ; i < n; i++) 
            { 
                // If burst time of a process is greater than 0 
                // then only need to process further 
                if (rem_bt[i] > 0) 
                { 
                    done = false; // There is a pending process 
       
                    if (rem_bt[i] > quantum) 
                    { 
                        // Increase the value of t i.e. shows 
                        // how much time a process has been processed 
                        t += quantum; 
       
                        // Decrease the burst_time of current process 
                        // by quantum 
                        rem_bt[i] -= quantum; 
                    } 
       
                    // If burst time is smaller than or equal to 
                    // quantum. Last cycle for this process 
                    else
                    { 
                        // Increase the value of t i.e. shows 
                        // how much time a process has been processed 
                        t = t + rem_bt[i]; 
       
                        // Waiting time is current time minus time 
                        // used by this process 
                        wtimearr[i] = t - btimearr[i]; 
       
                        // As the process gets fully executed 
                        // make its remaining burst time = 0 
                        rem_bt[i] = 0; 
                    } 
                    carr[i] = wtimearr[i] + btimearr[i];
                } 
            }
             if (done == true) 
              break; 
        }
        
        return t;
    }
}
