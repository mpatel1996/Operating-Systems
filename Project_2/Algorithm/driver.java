import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

class driver {
   public static void main(String[] args) throws IOException {

      // Creating a refString file to output all strings 
      FileWriter fw = new FileWriter("referenceString.txt");

      // This will hold the value when stringGen is called later on.
      String currString;
      int numberOfStrings = 50;
      
      // To hold averages for the 3 algorithm. 
      int[][] average = new int[3][4];

      // Create 50 strings by and find averages for 3 algo.
      for(int i = 0; i < numberOfStrings; i++){         

         currString = stringGen();
         fw.write(currString + "\n");

         // call each algorithm and add with their previous page fault 
         average[0][0] += fifo.pageFaults(currString, 3);
         average[0][1] += fifo.pageFaults(currString, 4);
         average[0][2] += fifo.pageFaults(currString, 5);
         average[0][3] += fifo.pageFaults(currString, 6);

         average[1][0] += lru.pageFaults(currString, 3);
         average[1][1] += lru.pageFaults(currString, 4);
         average[1][2] += lru.pageFaults(currString, 5);
         average[1][3] += lru.pageFaults(currString, 6);

         average[2][0] += optimal.pageFaults(currString, 3);
         average[2][1] += optimal.pageFaults(currString, 4);
         average[2][2] += optimal.pageFaults(currString, 5);
         average[2][3] += optimal.pageFaults(currString, 6);
      }

      // Loop through all total page Fault and average it up.
      for(int i=0;i<3;i++){
         for(int j=0;j<4;j++){
             average[i][j] /= numberOfStrings;
         }
     }

      // Close FW for referenceString.txt
      fw.close();

      // This prints all averages to file called "Averages.txt"
      printAvrg(average);

   } // END of Main

   
   // Creates a new File called "Averages.txt" and prints all averages to that. 
   private static void printAvrg(int[][] average) throws IOException {
      FileWriter  outPuWriter = new FileWriter("Averages.txt");

      outPuWriter.write("Page Frame Size 3");
      outPuWriter.write("\n******************");
      outPuWriter.write("\nFIFO average size: " + average[0][0]);
      outPuWriter.write("\nLRU average size: " + average[1][0]);
      outPuWriter.write("\nOPTIMAL average size: " + average[2][0]);
      outPuWriter.write("\n\nPage Frame Size 4");
      outPuWriter.write("\n******************");
      outPuWriter.write("\nFIFO average size: " + average[0][1]);
      outPuWriter.write("\nLRU average size: " + average[1][1]);
      outPuWriter.write("\nOPTIMAL average size: " + average[2][1]);
      outPuWriter.write("\n\nPage Frame Size 5");
      outPuWriter.write("\n******************");
      outPuWriter.write("\nFIFO average size: " + average[0][2]);
      outPuWriter.write("\nLRU average size: " + average[1][2]);
      outPuWriter.write("\nOPTIMAL average size: " + average[2][2]);
      outPuWriter.write("\n\nPage Frame Size 6");
      outPuWriter.write("\n******************");
      outPuWriter.write("\nFIFO average size: " + average[0][3]);
      outPuWriter.write("\nLRU average size: " + average[1][3]);
      outPuWriter.write("\nOPTIMAL average size: " + average[2][3]);

      outPuWriter.close();
   }


   // Randomly generates a string of integers of length 30
   // Returns a randomly generated String
   public static String stringGen() {
      //Create instance of rand
      Random rand = new Random();
      // create a StringBuilder object 
      StringBuilder str = new StringBuilder();
      for(int i=0;i<30;i++){
          //add 30 random page references to the 8 pages (0-7)
          str.append(rand.nextInt(8));  
      }
      return str.toString();
  }
}
