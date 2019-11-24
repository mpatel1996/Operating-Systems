import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class fifo {   

   static int pageFault;
   static int pageHit;
   static int framePointer;

   static PrintWriter pw;
   static int pageFaults (String refString, int capacity) throws IOException{

      pageFault = 0;
      pageHit = 0;
      framePointer = 0;
      
      /* Creating File to output an Example of what is happening
         in my algorithm. I display an example of a string and 
         their corresponding frame values for each number being 
         read in the string until end or string(which is 30).
      */
      pw = new PrintWriter("FIFO_" + capacity + "_OutPut.txt");
      pw.println("Example of the last String and its frames: " + refString);

      // Creating ArrayList to store all frames of capacity 
      // Capacity is either 3,4,5 or 6.
      ArrayList<String> pageFrames = new ArrayList<>();      
      char oneChar;

      // Loop through all numbers in string
      for(int i = 0; i < refString.length(); i ++){
         oneChar = refString.charAt(i);

         // If the frames not occupied
         if(pageFrames.size() < capacity){

            // AND current number is in Frame, Do nothing. Page hit.
            // Else add it to pages, increment pageFault.
            if(isInFrame(oneChar, pageFrames)){
               // Do Nothing. No Faults 
               // System.out.println(Arrays.toString(pageFrames.toArray()));
               pw.println(pageFrames.toString());
            }else{
               pageFrames.add(Character.toString(oneChar));
               // System.out.println(Arrays.toString(pageFrames.toArray()));
               pw.println(pageFrames.toString() );
               pageFault++;
            }
         }else{
            // pageFrames are full and one needs to be replaced. 

            // If current number is in pageFrames. Do Nothing. Page hit.
            if(isInFrame(oneChar, pageFrames)){
               // Do Nothing. No Faults 
               // System.out.println(Arrays.toString(pageFrames.toArray()));
               pw.println(pageFrames.toString() );
            }else{

               // Else add the number to the pageFrames. 
               pageFrames.set(framePointer, Character.toString(oneChar));
               pageFault++;
               framePointer++;
               // System.out.println(Arrays.toString(pageFrames.toArray()));
              pw.println(pageFrames.toString() );
               if(framePointer == capacity){
                  framePointer = 0;
               }
            }
         }        
      }
     
      pw.println("Page Faults for last example string is: " + pageFault);
      pw.close();
    return pageFault;
   } // END of pageFaults

   /* 
   Function: isInFrame()
   isInFrame takes input of current number and all of pageFrames.
    If the value is in page Frame then it is returned true and 
    framePointer keeps track of which frame was added first, so they 
    can be replaced with a new number. 
   */
   public static boolean isInFrame(char value, ArrayList<String> pageFrame){

      for(int i = 0; i < pageFrame.size(); i++){
         if( pageFrame.get(i).equals(Character.toString(value))){
            framePointer = i;
            pageHit++;
            return true;
         }
      }
      return false;
   }

}// END of FIFO

   




