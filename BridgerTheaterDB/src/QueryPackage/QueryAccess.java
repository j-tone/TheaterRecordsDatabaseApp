/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package QueryPackage;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 *
 * @author Justin
 */
public class QueryAccess {
    private String file; //= "net.ucanaccess.jdbc.UcanaccessDriver";
    private String DBLocation;// = "jdbc::ucanaccess://d:/CodeLibraries/MovieDB.accdb";
    public ArrayList<String> resultColNames;
    public int counter;
    QueryAccess(String filename, String DB){
        file = filename;
        DBLocation = DB;
    }
    
    public ArrayList<String> getQuery(String sql) throws ClassNotFoundException, SQLException{
        //Making the connection to the database
        //using a library called "Ucanaccess"
        Class.forName(file);
        
        Connection conn = DriverManager.getConnection(DBLocation);
        
        Statement st = conn.createStatement();
        
        //this is for reference to describe the results found in the GUI,
        //but for some reason does not do what I want it to so I may just take
        //it out
        ArrayList<String> colNames = new ArrayList();
        int c = 0;
        
        //Mmm, the resultset extracted from the query.
        ResultSet rs = st.executeQuery(sql);
        //Meta data helps determine what to do in some cases
        ResultSetMetaData holyLight = rs.getMetaData();
        int colCount = rs.getMetaData().getColumnCount();
        //this info also for future futile reference
        for (int j = 1; j <= colCount; j++){
            colNames.add(holyLight.getColumnName(j));
        }
        //what will be returned
        ArrayList<String> data = new ArrayList();
        
        while (rs.next()){
            c++;
            String addData = new String();
            
            for (int i = 1; i <= colCount; i++){
                
                String currData = new String();
                
                //if the retreieved data is a timestamp and the last object
                //convert and add to addData without the spacing
                if((holyLight.getColumnType(i) == 93)&&(i == colCount)){
                    currData = timeConvert((Timestamp)rs.getObject(i), holyLight.getColumnName(i));
                    addData += (currData);
                }
                //if the retrieved data is a timestamp
                //conevert and add to addData
                else if (holyLight.getColumnType(i) == 93){
                    currData = timeConvert((Timestamp)rs.getObject(i), holyLight.getColumnName(i));
                    addData += (currData+"  ---  ");
                }
                //if the retrieved data is a double and is last object
                //keep it from friggin turning into scientific notation and add to addData
                //without the spacing
                else if ((holyLight.getColumnType(i) == 8)&&(i == colCount)){
                    currData = String.format("%.0f", (Double)rs.getObject(i));
                    addData += (currData);
                }
                //if the retrieved data is a double
                //make it a pretty number and add to addData
                else if (holyLight.getColumnType(i) == 8){
                    currData = String.format("%.0f", (Double)rs.getObject(i));
                    addData += (currData+"  ---  ");
                }
                //if the data is the last in the row
                //don't add spaces
                else if(i == colCount){
                    currData = rs.getObject(i).toString();
                    addData += (currData);
                }
                //if the data does not exist
                //do not attempt to add
                else if(rs.getObject(i) == null){
                    break;
                }
                //ADD DATAAAAAA
                else{
                    currData = rs.getObject(i).toString();
                    addData += (currData+"  ---  ");
                }
            }
            //add addData to a list of strings that contain all the data!
            data.add(addData);
        }
        //supposed to be used later. *Sighhhh*
        counter = c;
        resultColNames = colNames;
        //return the glorious string
        return data;
    }
    //converts a timestamp into the right sort of string
    private String timeConvert(Timestamp t, String id){
        String s;
        if (id.equals("Date"))
            s = new SimpleDateFormat("MM/dd/yyyy").format(t);
        else if (id.equals("Time"))
            s = new SimpleDateFormat("HH:mm:ss a").format(t);
        else 
            s = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss a").format(t);
        return s;
    }
    
    
}
