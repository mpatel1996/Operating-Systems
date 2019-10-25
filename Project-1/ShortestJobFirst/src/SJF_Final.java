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

public class SJF {
    
   // static vars for program to use. 
    public static int overallTotal = 0;
    
    // Addon variable is used for try method when looking for a file inside a folder. 
    // Explain in line 40. 
    public static int addon = 15;
    
    public static void main(String [] argv) throws IOException {
        
        ArrayList<Integer> id = new ArrayList<>(); // Wait Time
        ArrayList<Integer> wTime = new ArrayList<>(); // Wait Time
        ArrayList<Integer> bTime = new ArrayList<>(); // job time
        
        int total = 0;
        float avg;
        
        // Try to save file to folder. Continue if successfull.
        try (FileWriter fw = new FileWriter("../jobs/output_SJF_" + addon +".txt")) {
            
            // Keep looping until all 10 files are scanned
            for(int k = 0; k <10; k++){
                
                // Go to folder jobs, look for file name job_addon_k.txt
               // Addon is number of the job, and k is number for trail. 
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
                
                // Convert the ArrayList to arrays.
                Integer[] wtimearr = new Integer[bTime.size()];
                Integer[] btimearr = new Integer[bTime.size()];
                Integer[] idarr = new Integer[bTime.size()];
                int[] completearr = new int[bTime.size()];
                
                wtimearr = wTime.toArray(wtimearr);
                btimearr = bTime.toArray(btimearr);
                idarr = id.toArray(idarr);
                
                //Reset the arrays to 0. 
                for (int j =0; j < bTime.size(); j++){
                    idarr[j] = j+1;
                    wtimearr[j] = 0;
                    completearr[j] = 0;
                }
                
                // insert bubble sort here to sort id and btimearr   
                // Sorting the shortest job first.
                for (int i = 0; i < btimearr.length-1; i++){ 
                    for (int j = 0; j < btimearr.length-i-1; j++){ 
                        if (btimearr[j] > btimearr[j+1]) 
                        { 
                            // swap arr[j+1] and arr[i] 
                            int btemp = btimearr[j]; 
                            btimearr[j] = btimearr[j+1]; 
                            btimearr[j+1] = btemp; 
                            
                            int idtemp = idarr[j]; 
                            idarr[j] = idarr[j+1]; 
                            idarr[j+1] = idtemp; 
                        } 
                    }
                }
                
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
                
                // Start writing the output to the File. 
                fw.write("Trial #: " + (k + 1));
                fw.write("\nProcess_ID\tBurst_time\tWait_time\tcomplete_time\n");
                
                for(int i=0;i<n;i++)
                {
                    fw.write(idarr[i]+"\t\t\t"+btimearr[i]+"\t\t\t"+wtimearr[i]+"\t\t\t"+completearr[i] + "\n");
                }
                
                //System.out.println("\nTotal wait time: "+total+"\nAverage wait time: "+avg);
                fw.write("\nTotal time: "+total+"\nAverage Turn around Time: "+avg + "\n\n");
                
                overallTotal += total;
               
                // clear out the arraylist to make room for the new file to add its values to the list. 
                id.clear();
                wTime.clear();
                bTime.clear();
                
                  total = 0;
                avg = 0;
            } // end of for loop
            
            // Write to file the overall total time and overall average time. useful for part 1c of the write up.
            fw.write("Total of all " + addon+ " total time: " + overallTotal);
            fw.write("\nTotal average of all " + addon+ " time: " + (float)(overallTotal)/10);
            
            // close the file writer once done using the file. 
            fw.close();
           
        }// End of try method that saves file to output folder
        
    }// end of main
} // end of class
