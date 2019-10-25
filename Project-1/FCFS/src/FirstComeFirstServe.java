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

public class FirstComeFirstServe {
    public static int overallTotal = 0;
    public static int addon = 15;
    
    public static void main(String [] argv) throws IOException {
        int counter = 1;
        ArrayList<Integer> id = new ArrayList<>(); // Wait Time
        ArrayList<Integer> wTime = new ArrayList<>(); // Wait Time
        ArrayList<Integer> bTime = new ArrayList<>(); // job time
        
        int total = 0;
        float avg;
        
       try (FileWriter fw = new FileWriter("../jobs/output_FCFS_" + addon +".txt")) {
            for(int k = 0; k <10; k++){
                fw.write("Trial #: " + counter);
                counter++;
               File myFile = new File("../jobs/job_"+ addon + "_" + k +".txt");

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
                
                for(int i = 1; i < wtimearr.length; i++){
                    wtimearr[i]= btimearr[i-1] + wtimearr[i-1] ;                    
                    completearr[i-1] = wtimearr[i-1] + btimearr[i-1];
                    total=total+completearr[i-1];
                }
                
                completearr[wtimearr.length - 1] = wtimearr[wtimearr.length -1] + btimearr[wtimearr.length- 1];
                total=total+completearr[wtimearr.length-1];
                
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
                fw.write("\nTotal time: "+total+"\nAverage Turn Around Time: "+avg + "\n\n");
                
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
}
