public class Scholar 
{
   //variables
   private String classYear;
   private String emailAddr;
   private String faculty;
   private String firstName;
   private String lastName;
   private int serviceHours;
   
   //default constructor
   public Scholar()
   {
      classYear = "empty";
      emailAddr = "empty";
      faculty = "empty";
      firstName = "empty";
      lastName = "empty";
      serviceHours = "0"
   }
   
   //setters
   public void setClassYear(String year)
   {
      this.classYear = year;
   }
   public void setEmailAddr(String addr)
   {
      this.emailAddr = addr;
   }
   public void setFaculty(String fac)
   {
      this.faculty = fac;
   }
   public void setFirstName(String first)
   {
      this.firstName = first;
   }
   public void setLastName(String last)
   {
      this.lastName = last;
   }
   public void setServiceHours(int hours)
   {
	   this.serviceHours = hours;
   }
   
   //getters
   public String getClassYear()
   {
      return this.classYear;
   }
   public String getEmailAddr()
   {
      return this.emailAddr;
   }
   public String getFaculty()
   {
      return this.faculty;
   }
   public String getFirstName()
   {
      return this.firstName;
   }
   public String getLastName()
   {
      return this.lastName;
   }
   public int getServiceHours()
   {
	   return this.serviceHours
   }
   
   //toString method
   public String toString()
   {
      return ("\nFaculty: " + faculty + " First: " + firstName + 
             " Last: " + lastName + " Email: " + emailAddr + 
             " Year: " + classYear + "Service Hours: " + serviceHours);
   }

}