import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.LinkedList;

public class optimal {

   static int pageFault;
   static int maxIndex;
   static PrintWriter pw;


   // pageFaults retuns the number of pageFault in FIFO per string. 
   public static int pageFaults(String refString, int capacity) throws FileNotFoundException {
      
      pageFault = 0;
      maxIndex = 0;
      char oneChar;
      String oneStr;

      // Creating LinkedList to store all frames of capacity 
      // Capacity is either 3,4,5 or 6.
      LinkedList<String> pageFrames = new LinkedList<>();

      /* Creating File to output an Example of what is happening
         in my algorithm. I display an example of a string and 
         their corresponding frame values for each number being 
         read in the string until end or string(which is 30).
      */
      pw = new PrintWriter("OPTIMAL_" + capacity + "_OutPut.txt");
      pw.println("Example of the last String and its frames: " + refString);

      // Loops through all numers in the string of length 30.
      for(int i = 0; i < refString.length(); i++){
         
         // Taking one char out of refString and turning it into String
         // for easier code typing. 
         oneChar = refString.charAt(i);
         oneStr = Character.toString(oneChar);

         // If frame has space left to add to frames
         if(pageFrames.size() < capacity){

            // If frame already has value in it Do nothing. 
            if(pageFrames.contains(oneStr)){ 
               
               // DO no thing. Page hit.
               // System.out.println(Arrays.toString(pageFrames.toArray()));

               // Prints to file for example of frames. 
               pw.println(pageFrames.toString());
            }else{ 
               // Else add to frame and increase page fault counter. 
               pageFrames.add(oneStr);
               // System.out.println( Arrays.toString(pageFrames.toArray()));
               pw.println(pageFrames.toString() );
               pageFault++;
            } 
         }else{ 

            // When frame size is full
            if(pageFrames.contains(oneStr)){
               // DO Nothing. Page Hit.
               // System.out.println(Arrays.toString(pageFrames.toArray()));
               pw.println(pageFrames.toString() );
            }else{ 
            
               int replacerIndex = findMaxToReplace(i, pageFrames, refString);
               // System.out.println("Index to replace " + replacerIndex);

               pageFrames.remove(replacerIndex);
               pageFrames.add(replacerIndex, oneStr);
               // System.out.println(Arrays.toString(pageFrames.toArray()));
               pw.println(pageFrames.toString() );
               pageFault++;
            }
         }
      }
      
      pw.println("Page Faults for last example string is: " + pageFault);
      pw.close();
      return pageFault;
   }

   /*
   Function: findMaxToReplace
    Looks for the furthest index and returns the index number it needs to remove. 
    Input index is current number in ref string passed through function. 
   */
    static int findMaxToReplace(int index, LinkedList<String> frames, String refString){

      char currChar;
      int maxIndex = 0 ;
      int counter = 1;
      String oneStr;
      
      // Loops thourgh rest of the remaining String.
      for(int i = index; i < refString.length(); i++){
         currChar = refString.charAt(i);
         oneStr = Character.toString(currChar);

         // If any number is frames are closer to number waiting to be swapped in,
         // They will be added to the back of List and first number in the list is
         // the one that will be removed since it is now the furthest one away. 
         if(counter < frames.size()){
            if(frames.contains(oneStr)){
               counter++;
               int removeIndex = frames.indexOf(oneStr);
               frames.remove(removeIndex);
               frames.addLast(oneStr);
            }
         }

         maxIndex = 0;
      }

     
      return maxIndex;
   }


}