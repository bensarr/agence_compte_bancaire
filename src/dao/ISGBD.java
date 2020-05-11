/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author user
 */
public interface ISGBD {
    public  void getConnection();
    public void initPS(String sql);
    public int executeMaj();
    public ResultSet executeSelect();
    public PreparedStatement getPstm();
    public void CloseConnection();
}
