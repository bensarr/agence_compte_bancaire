/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dao;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author user
 */
public class DaoMysql implements ISGBD{
    private   Connection cnx;
    private PreparedStatement pstm;
    private int ok;
    private ResultSet rs;
    
    private final String server="localhost";
     private final String dbuser="root";
     private final String dbpass="";
     private final String dbname="java_agence_compte_bancaire";
     private final String port="3306"; 
    @Override
    public  void getConnection()
	{
          cnx=null;  
	try{
                //Chargement du Driver
                Class.forName("com.mysql.jdbc.Driver");
                //Ouvrir la Connexion
		cnx=DriverManager.getConnection("jdbc:mysql://"+server+":"+port+"/"+dbname,
                                                   dbuser,dbpass);
	}catch(ClassNotFoundException | SQLException e)
	{
		e.printStackTrace();
	}
       
	} 
   
    @Override
   public void initPS(String sql)
	{
		getConnection();
		try{
			  if(sql.toLowerCase().startsWith("insert"))
			     {
			    	pstm=cnx.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS );
			     }
			else{
				   pstm=cnx.prepareStatement(sql);
		        }
		    }catch(Exception e)
		{
			e.printStackTrace();
		}



	}
    @Override
	public int executeMaj()
	{
		try {
			ok = pstm.executeUpdate();

		} catch (Exception e) {

			e.printStackTrace();

		}
		return ok;
	}
    @Override
	public ResultSet executeSelect()
	{
		try {

			rs=pstm.executeQuery();

		} catch (Exception e) {
			e.printStackTrace();

		}
		return rs;
	}
    @Override
	public PreparedStatement getPstm()
	{
		return  this.pstm;

	}
   
    @Override
 public void CloseConnection(){
		try{
		if(cnx!=null){
			cnx.close();
		}
		}catch(Exception ex){
			ex.printStackTrace();

		}
	}

  
}
