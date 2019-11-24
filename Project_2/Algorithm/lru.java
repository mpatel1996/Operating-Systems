import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;

public class lru{

   static int pageFault;
   static PrintWriter pw;

   // pageFaults retuns the number of pageFault in LRU per string. 
   public static int pageFaults(String refString, int capacity) throws IOException{
      
      pageFault = 0;
      char oneChar;

      /* Creating File to output an Example of what is happening
         in my algorithm. I display an example of a string and 
         their corresponding frame values for each number being 
         read in the string until end or string(which is 30).
      */
      pw = new PrintWriter("LRU_" + capacity + "_OutPut.txt");
      pw.println("Example of the last String and its frames: " + refString);
      
      // Creating LinkedList to store all frames of capacity 
      // Capacity is either 3,4,5 or 6.
      LinkedList<String> pageFrames = new LinkedList<>();

      // loop through all of chars in refString
      for (int i = 0; i < refString.length(); i++){
         oneChar = refString.charAt(i);

         // If all page Frame is not occupied, add new char to it
         if(pageFrames.size() < capacity){

            // If frame already has value in it Do nothing. Page Hit.
            if(pageFrames.contains(Character.toString(oneChar))){
               // System.out.println(Arrays.toString(pageFrames.toArray()));
               pw.println(pageFrames.toString());
            }else{
               //add to frames
               pageFrames.addLast(Character.toString(oneChar));
               // System.out.println(Arrays.toString(pageFrames.toArray()));

               pw.println(pageFrames.toString());
               pageFault++;
            }

         }else{// frame is full 
            
            if(pageFrames.contains(Character.toString(oneChar))){

                  // If current number is last in pageFrames, do nothing.
               if(pageFrames.indexOf(Character.toString(oneChar)) == (capacity-1)){
                  // DO NOTHING. Because page Hit.
               }else{
                  // Put it in last Place for Linked List.
                  // Removed the oneChar from pageFrames. pageFrames.addlast(oneChar);
                  int removeIndex = pageFrames.indexOf(Character.toString(oneChar));

                  pageFrames.remove(removeIndex);
                  pageFrames.addLast(Character.toString(oneChar));
               }
               // System.out.println(Arrays.toString(pageFrames.toArray()));
               pw.println(pageFrames.toString());
            }else{ 
               // remove the first node then, pageFrames.addLast(oneChar);
               pageFrames.removeFirst();
               pageFrames.addLast(Character.toString(oneChar));
               // System.out.println(Arrays.toString(pageFrames.toArray()));
               pw.println(pageFrames.toString());
               pageFault++;
            }
         }
      }

      pw.println("Page Faults for last example string is: " + pageFault);
      pw.close();

      return pageFault;
   }

}