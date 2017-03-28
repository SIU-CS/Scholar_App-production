import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class OutputCSVasJSON {

   public static void main(String[] args) {
      
      //put the file address here   
      String csvFile = "/Documents/GitHub/Scholar_App-production/CSP_ClassList.csv";
      
      BufferedReader reader = null; //buffered reader to read file
      String line = ""; //used to store each csv line 
      
      Scholar[] scholars = new Scholar[123]; //Array for scholars
      
      //loop to initialize each scholar
      for(int i = 0; i < 123; i++)
      {
         scholars[i] = new Scholar(); 
      }
      
      try //catch exceptions for file operations
      { 
      
         reader = new BufferedReader(new FileReader(csvFile));
         int i = 0;
         
         //loop through reading file until eof
         while ((line = reader.readLine()) != null) 
         {
            //split line into a string array by comma
            String[] lineArray = line.split(","); 
            
            //set values in scholar array   
            scholars[i].setFaculty(lineArray[0]);
            scholars[i].setFirstName(lineArray[1]);
            scholars[i].setLastName(lineArray[2]);
            scholars[i].setEmailAddr(lineArray[3]);
            scholars[i].setClassYear(lineArray[4]);
               
            i++;
         }
      } 
      catch (FileNotFoundException e) {
         e.printStackTrace();
      } 
      catch (IOException e) 
      {
         e.printStackTrace();
      }//end try/catch
      
      
     try //close the buffered reader
     {
         reader.close();
     }
     catch (IOException e) 
     {
         e.printStackTrace();
     }//end try/catch
     
     
     //loop through the array of scholars and print out the info in JSON format
     for(int i = 0; i < 123; i++)
     {
         String userName = (scholars[i].getFirstName().toLowerCase().charAt(0) + scholars[i].getLastName().toLowerCase());
            String output = "";
            
            output =  "    \"" + userName + "\" : {\n";
            output += "      \"class\" : " + scholars[i].getClassYear() + ",\n";
            output += "      \"email\" : \"" + scholars[i].getEmailAddr() + "\",\n";
            output += "      \"faculty\" : \"" + scholars[i].getFaculty() + "\",\n";
            output += "      \"firstName\" : \"" + scholars[i].getFirstName() + "\",\n";
            output += "      \"lastName\" : \"" + scholars[i].getLastName() + "\"\n";
            output += "      \"serviceHours\" : \"" + 0 + "\"\n";
            output += "    },\n";
            
            
            System.out.print(output);
     }
   }
}