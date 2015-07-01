package access.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DbAccess
{
    public static void main(String[] args)
    {
	    try
	    {

	    	Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
//	    	Connection conn=DriverManager.getConnection("jdbc:ucanaccess://c:/pippo.mdb");
	        Connection conn=DriverManager.getConnection("jdbc:ucanaccess://W:\\Fundos\\FIDC\\CARTHAGO_130356\\Cota\\Cotas.accdb");
	        Statement stment = (Statement) conn.createStatement();
	        String qry = "SELECT * FROM Table1";

	        ResultSet rs = stment.executeQuery(qry);
	        while(((ResultSet) rs).next())
	        {
	            double cota = ((ResultSet) rs).getDouble("Cota");

	            System.out.println(cota);
	        }
	    }
	    catch(Exception err)
	    {
	        System.out.println(err);
	    }


	    //System.out.println("Hasith Sithila");
    }
}