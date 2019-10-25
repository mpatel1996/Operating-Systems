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
    
    // Addon variable is used for try method when looking for a file inside a folder. 
    // Explain in line 41.
    public static int addon = 5; 
    public static int overallTotal = 0;
    
    public static void main(String [] argv) throws IOException {
        
        ArrayList<Integer> id = new ArrayList<>(); // Wait Time
        ArrayList<Integer> wTime = new ArrayList<>(); // Wait Time
        ArrayList<Integer> bTime = new ArrayList<>(); // job time
        
        int total = 0;
        float avg;
        
        // Try to save file to folder. Continue if successfull.
        try (FileWriter fw = new FileWriter("../jobs/output_RR"+ quantum+ "_" + addon + ".txt")) {
            
            // Loop until all 10 trials for specific job is finished.
            for(int k = 0; k <10; k++){
                
                // Go to folder jobs, look for file name job_addon_k.txt
               // Addon is number of the job, and k is number for trail. 
                File myFile = new File("../jobs/job_"+addon+"_" + k +".txt");

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
                    s.close();
                }
                
                // Convert the ArrayList to arrays. 
                Integer[] wtimearr = new Integer[bTime.size()];
                Integer[] btimearr = new Integer[bTime.size()];
                Integer[] idarr = new Integer[bTime.size()];
                int[] completearr = new int[bTime.size()];
                
                wtimearr = wTime.toArray(wtimearr);
                btimearr = bTime.toArray(btimearr);
                idarr = id.toArray(idarr);
                
                // Initialize the arrays to 0
                for (int j =0; j < bTime.size(); j++){
                    idarr[j] = j+1;
                    wtimearr[j] = 0;
                    completearr[j] = 0;
                }
                
                // Call the method for Round Robin Algorithm 
                roundRobin(wtimearr, btimearr, completearr, total);
               
                // Start loop from 1 to length of wtimearr. Because First job to wait is always going to be 0,
                // start adding the time for job to wait 
                for(int i = 1; i < wtimearr.length; i++){
                    wtimearr[i]= btimearr[i-1] + wtimearr[i-1] ;                    
                    completearr[i-1] = wtimearr[i-1] + btimearr[i-1];
                    total=total+completearr[i-1];
                }
                
                // Add in the last remaining data since for loop start at 1, not 0. 
                completearr[wtimearr.length - 1] = wtimearr[wtimearr.length -1] + btimearr[wtimearr.length- 1];
                total=total+completearr[wtimearr.length-1];
                
                int n = bTime.size();
                avg = (float)total/n;
                
                // Start writing the output to the file. 
                fw.write("Trial #: " + (k + 1) + "\tQuantum Slice #: " + quantum );
                fw.write("\nProcess_ID\tBurst_time\tWait_time\tcomplete_time\n");
                for(int i=0;i<n;i++)
                {
                    //System.out.println(idarr[i]+"\t\t"+btimearr[i]+"\t\t"+wtimearr[i]+"\t\t"+completearr[i]);
                    fw.write(idarr[i]+"\t\t\t"+btimearr[i]+"\t\t\t"+wtimearr[i]+"\t\t\t"+completearr[i] + "\n");
                    
                }
                //System.out.println("\nTotal wait time: "+total+"\nAverage wait time: "+avg);
                fw.write("\nTotal time: "+total+"\nAverage Turn around Time: "+avg + "\n\n");
                
                // overallTotal is calculating all total average values for 10 trials for specific job. 
                overallTotal += total;
                id.clear();
                wTime.clear();
                bTime.clear();
                
                total = 0;
                avg = 0;
            } // end of for loop
            fw.write("Total of all " + addon+ " total time: " + overallTotal);
            fw.write("\nTotal average of all " + addon+ " time: " + (float)(overallTotal)/10);
            fw.close();
        }
    }// end of main

    // Start of Round Robin algorithm. takes in []wtimearr, []btimearr, []carr, and t integer as total
    private static void roundRobin(Integer[] wtimearr, Integer[] btimearr, 
                                    int[] carr, int t ) {
        
        int n = btimearr.length;
        int rem_bt[] = new int[n]; 
        
        for (int i = 0 ; i < n ; i++) {
            rem_bt[i] =  btimearr[i]; 
        }
       
        // Keep traversing processes in round robin manner 
        // until all of them are not done. 
        while(true){ 
            boolean done = true; 
       
            // Traverse all processes one by one repeatedly 
            for (int i = 0 ; i < n; i++){ 
                // If burst time of a process is greater than 0 
                // then only need to process further 
                if (rem_bt[i] > 0){ 
                    done = false; // There is a pending process 
       
                    if (rem_bt[i] > quantum){ 
                        // Increase the value of t i.e. shows 
                        // how much time a process has been processed 
                        t += quantum; 
       
                        // Decrease the burst_time of current process 
                        // by quantum 
                        rem_bt[i] -= quantum; 
                    } 
       
                    // If burst time is smaller than or equal to 
                    // quantum. Last cycle for this process 
                    else{ 
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
                    // Calculate the completed time for job
                    carr[i] = wtimearr[i] + btimearr[i];
                } 
            } // end of for loop
            
             if (done == true) 
              break; 
        } // end of While Loop
        
    } // end of Round Robin
    
} // End of Class
