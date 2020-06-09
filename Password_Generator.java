import java.util.*;
import java.io.*;

class Password{
    //Initialising all characters
    static final String numbers = "1234567890";
    static final String alphaCaps = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    static final String alphaSmall = "abcdefghijklmnopqrstuvwxyz";
    static final String special = "!@#$%^&*_=+-/";
    
    //Full string of all characters
    static final String pass = numbers+alphaCaps+alphaSmall+special;
    
    public static void main(String [] ak) throws Exception{
        Scanner scan = new Scanner(System.in);
        
        System.out.println("\nWelcome to Password Generator\n");
        
        while(true){
            //Menu
            System.out.println("\n\n1. Generate Password");
            System.out.println("2. Retreive Password");
            System.out.println("3. Exit");

            System.out.print("\nSelect option: ");
            int choice = scan.nextInt();
            
            if(choice == 1){
                scan.nextLine();
                System.out.print("\nEnter Website name: ");
                String website = scan.nextLine();
                
                boolean check = checkWebsite(website); //check if password is already stored
                
                if(check){
                    System.out.println("\nPassword for "+website+" is already stored.");
                    
                    String rpassword = retreivePassword(website);
                    System.out.println("Password: "+rpassword); //prints already stored password
                    
                    continue;
                }

                System.out.print("Enter length of password for "+website+" : ");
                int len = scan.nextInt();

                String password = passwordGenerator(len); //generating password
                System.out.println("Password generated: "+password);

                savePassword(website,password); //saving to file
            }
            
            else if(choice == 2){
                scan.nextLine();
                System.out.print("\nEnter Website name: ");
                String retreiveWebsite = scan.nextLine();
                String rpassword = retreivePassword(retreiveWebsite); //retreives the stored password
                
                System.out.println("Password: "+rpassword);
            }
            
            else if(choice == 3) System.exit(0);
        }
        
                
    }
    
    static boolean checkWebsite(String website){
        try{
            String pwd = System.getProperty("user.dir");
            File file = new File(pwd+"/Credentials/passwords.txt"); //opens up the file

            Scanner scanner = new Scanner(file); //scanner will read the contents

            while(scanner.hasNext()){
                 String websitePassword = scanner.nextLine(); //reads the content
                 String splits[] = websitePassword.split(" "); //splits to get website name

                 if(website.equals(splits[0])){ //checks if website already present
                     return true;
                 }
             } 
            
             return false;
            
        }catch(Exception e){
            return false;
        }
            
    }
    
    static String passwordGenerator(int len){
        String password = "";
        
        //setting up password by choosing single character randomly
        for(int i=0; i<len; i++){
            int index = (int)(Math.random()*75);
            password += pass.charAt(index);
        }
        
        return password; //returning the password
    }
    
    static void savePassword(String website, String password) throws Exception{
        String pwd = System.getProperty("user.dir");
        FileWriter file = new FileWriter(pwd+"/Credentials/passwords.txt", true); //opens file in append mode
        
        file.append(website+" : "+password+"\n"); //appends to the file
        file.close();
    }
    
    static String retreivePassword(String website) throws Exception{
        String pwd = System.getProperty("user.dir"); //sets path to current working directory
        File file = new File(pwd+"/Credentials/passwords.txt"); //opens up file
        
        Scanner scanner = new Scanner(file);
        
       while(scanner.hasNext()){
            String websitePassword = scanner.nextLine(); //reads content
            String splits[] = websitePassword.split(" "); //splits to get website name
            
            if(website.equals(splits[0])){
                return splits[2]; //returns the password
            }
        } 
        
        String statement = "No record found for "+website;
        return statement;
    }
}