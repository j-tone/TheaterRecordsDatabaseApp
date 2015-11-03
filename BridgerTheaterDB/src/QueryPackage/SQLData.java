/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package QueryPackage;

import java.util.ArrayList;

/**
 *
 * @author Justin
 */

//A class to help with the hard-coded data of stored procedures
public class SQLData {
    
    private ArrayList<String> selMovies = new ArrayList();
    private ArrayList<String> selPeople = new ArrayList();
    private ArrayList<String> selTheaters = new ArrayList();
    
//    private ArrayList<String> sqlMovies = new ArrayList();
//    private ArrayList<String> sqlPeople = new ArrayList();
//    private ArrayList<String> sqlTheaters = new ArrayList();
    
    public SQLData(){
        selPeople.add("Find how many people were with me every time I went to the movies");
        selPeople.add("Find each instance I went to a movie with . . . ");
        
        selMovies.add("Find the total amount of companions for each movie I've seen");
        selMovies.add("Find information about each time I visited the movie . . . ");
        
        selTheaters.add("Find how many times I've visited each theater I've been to");
        selTheaters.add("Find how many times I've been to any theater under the company . . . ");
    }
     public String getQuery1_1(){
        String sql = new String("SELECT Showtime, Count(Showtime) AS Guests, NotSure.MovieTitle " +
                                "FROM (SELECT Showtimes AS Showtime, MovieTitle FROM Viewer WHERE FullName <> \"Bridger Deschamps\")  AS NotSure " +
                                "GROUP BY Showtime, NotSure.MovieTitle " +
                                "ORDER BY NotSure.MovieTitle");
        return sql;
    }
     //All stored procedures
    public String getQuery1_2(){
        String sql = new String("SELECT Movie, Count(Movie) AS Guests " +
                                "FROM (SELECT MovieTitle AS Movie FROM Viewer WHERE FullName <> \"Bridger Deschamps\")  AS NotSure " +
                                "GROUP BY Movie " +
                                "ORDER BY Movie");
        return sql;                                            
    }
    public String getQuery2_1(String friend){
        String sql = new String("SELECT V.MovieTitle AS Movie, P.FullName, V.Showtimes, MV.TheaterName " +
                                "FROM Movie AS M, MovieViewing AS MV, Viewer AS V, People AS P " +
                                "WHERE V.Showtimes = MV.Showtimes AND V.MovieTitle = MV.MovieTitle AND MV.MovieTitle = M.MovieTitle AND P.FullName = V.FullName AND P.FullName LIKE " +
                                "'*" + friend + "*' " +
                                "ORDER BY V.Showtimes");
        return sql;
    }
    public String getQuery3(String movie){
        String sql = new String("SELECT MV.MovieTitle, MV.TicketNumber, MV.Showtimes, MV.TheaterName " +
                                "FROM MovieViewing AS MV, Movie AS M, Ticket AS T " +
                                "WHERE MV.MovieTitle = M.MovieTitle AND MV.TicketNumber = T.TicketNumber AND M.MovieTitle LIKE \"*" + movie + "*\"");
        return sql;
    }
    public String getQuery5_2(){
        String sql = new String("SELECT TheaterName AS Theater, COUNT(TheaterName) AS Visits " +
                                "FROM (SELECT TheaterName FROM MovieViewing)  AS DistinctTheaters " +
                                "GROUP BY TheaterName " +
                                "ORDER BY COUNT(TheaterName) DESC , TheaterName");
        return sql;
    }
    public String getQuery9_6(String theaterCompany){
        String sql = new String("SELECT TH.Company, COUNT(MV.TheaterName) AS Visits " +
                                "FROM MovieViewing AS MV, Theater AS TH " +
                                "WHERE MV.TheaterName = TH.TheaterName AND TH.Company LIKE \"*" + theaterCompany + "*\" " +
                                "GROUP BY TH.Company");
        return sql;
    }
    //these help identify the contents of the selection lists
    public String getSelectionStatement (int id, int index) throws Exception{
        switch (id){
            case 1:
                return selMovies.get(index);
            case 2:
                return selPeople.get(index);
            case 3:
                return selTheaters.get(index);
            default:
                throw new Exception();
        }
    }
     public ArrayList<String> getSelectionStatement (int id) throws Exception{
        switch (id){
            case 1:
                return selMovies;
            case 2:
                return selPeople;
            case 3:
                return selTheaters;
            default:
                throw new Exception();
        }
    }
}
