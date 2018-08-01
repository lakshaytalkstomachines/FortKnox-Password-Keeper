/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package area.pkg51.core;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import javax.xml.transform.Result;

/**
 *
 * @author SilverPanda
 */
public class SocialMedia 
{
   
    private String providerName;
    private String emailAddress;
    private byte[] password;
    private String hash;
    private String comment;
    private final String tableName = "socialmediatable";
    private final String primarykey = "Username";
    
    SocialMedia()
    {     
      /* This constructor will be called then user wants to decrypt their password
         getDataFromDataBase() will be called and load the data members with this
         Then call decryptPass() member method                 
      */    
      
      /*
        After IMPLEMENTING call getDataFromDataBase() function here.
        */
      
         
       
       
    }
  
    public SocialMedia(String pName, String eAdd, String pass, String comment) throws Exception
    {
        /* 
            When user clicks on Update Button a function called 
                getDatafromDataBase() which is implemented in another class is 
                called which fills the text fields of the dialog box with data 
                recieved.
            Then this constructor will be called
                Which then calls addMember function
                which loads the values from the text fields to the instance variables
            Delete the previous entry
            addDataToDataBase() function is called which loads the data from the
                instance variables to the Data Base.       
        
        */
        
        /* When user clicks on Add button, A dialog box appears
        and user fills that information, 
        when he clicks ok -
            Constructor is called
                Which then calls addMember function
                which loads the values from the text fields to the instance variables
            addDataToDataBase() function is called which loads the data from the
                instance variables to the Data Base.
                
         
      */
      this.addMember(pName, eAdd, pass, comment);
      
      /*
         AFTER IMPLEMENTING addDataToDataBase(), call this function below this.
      */
      addDataToDataBase();
        
    }
    
    public void addMember(String pName, String eAdd, String pass, String comment) throws Exception
    {
        this.providerName  = pName;
        this.emailAddress = eAdd;
        this.comment = comment;
        this.hash = hashing.createHashOfTwoString(this.providerName, this.emailAddress);
        
        /* Remove "String hashMaster" after implemnting class which has functions
           to extract hash of Master from DataBase        
        */
        
        String hashMaster = "$2a$10$OXwR/Bu0DESq184nvwkwXuP4zfcofhEJFwY3aCHcto0ptn7gFeU2i";
        
        
        byte[] key = new byte[32];
        key =  Encryption.generateKey(hashMaster, this.hash);
                
        this.password = Encryption.encryptIt(key, pass);
    }
     
    
    public static String decryptPass(byte[] pass,String hash) throws Exception
    {
       
        
        /* Remove "String hashMaster" after implemnting class which has functions
           to extract hash of Master from DataBase        
        */
        
        String hashMaster = "$2a$10$OXwR/Bu0DESq184nvwkwXuP4zfcofhEJFwY3aCHcto0ptn7gFeU2i";
        
        
        byte[] key = new byte[32];
        key =  Encryption.generateKey(hashMaster, hash);
        
        String passWord = Encryption.decryptIt(key, pass);
        return passWord;
       
    }
    
    public void addDataToDataBase()throws Exception
    {
          
        Connection conn=null;
        Class.forName("com.mysql.jdbc.Driver");
        conn =DriverManager.getConnection("jdbc:mysql://localhost:3306/area51","root","root");
           
        Result rs;
        PreparedStatement pst;
        String sql;
    
        
        sql=" insert into "+this.tableName+" (Provider ,Username,Password, Comment, Hash)" + " values (?,?,?,?,?)";
        pst=conn.prepareStatement(sql);
        pst.setString(1,this.providerName);
        pst.setString(2, this.emailAddress);
        pst.setBytes(3,this.password);
        pst.setString(4,this.comment);
        pst.setString(5,this.hash);
        
        pst.execute();      
      

    } 
}
