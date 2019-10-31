/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CapaNegocio;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
/**
 *
 * @author Fundamentos DS
 */
public class ClsConexion {
    private Connection _base;
    private Statement _tabla;  
    private ResultSet _registros;
    
    public ClsConexion (String dirbase){
        try{
        _base = DriverManager.getConnection("jdbc:ucanaccess://" + dirbase);
        _tabla = _base.createStatement(ResultSet.FETCH_UNKNOWN, ResultSet.TYPE_SCROLL_SENSITIVE);
        System.out.println("*** conexion exitosa ***");
        
        }catch (SQLException err){
              System.out.println("*** conexion errada ***" + err);
        }
    }

    public ResultSet registros() {
        return _registros;
    }
    public boolean consulta(String tabla) throws SQLException{
        boolean resp=false;
        String cadena= "SELECT * FROM " + tabla;
        _registros=null;
        _tabla.execute(cadena);
        _registros=_tabla.getResultSet();
        if(_registros!=null){
            resp=true;
        }
        return resp;
    }
        public void anterior()throws SQLException{
            try{
           if (!(_registros.previous())){
               _registros.last();
           }
       }catch(SQLException err){
             System.out.println("*** error recorrer ***" + err);
        }
    }
    public void siguiente()throws SQLException{
       try{
           if (!(_registros.next())){
               _registros.first();
           }
       }catch(SQLException err){
             System.out.println("*** error recorrer ***" + err);
        }        
    }  
     public boolean insertarC(String tabla, String nom,String telf) throws SQLException{
        String values = "'"+nom+"', '"+telf+"'";
        String comando = "INSERT INTO " + tabla + "(Nombre,Telefono) VALUES("+values+")";
        int rowCount = _tabla.executeUpdate(comando);
       this.consulta("TbAgenda");
        return true;
    } 
      public void actualizarC(String tabla, String nom, String telf, int clave) throws SQLException{
        String comando ="UPDATE " + tabla + " SET "+"nombre='"+nom+"', telefono='"+telf+"' WHERE Id_Agenda='"+clave+"'";
        _tabla.executeUpdate(comando);
        this.consulta("TbAgenda");
    }
      public void eliminarC(String tabla, int clave) throws SQLException{
        String cadena= "DELETE FROM "+tabla+" WHERE Id_Agenda="+clave;
        _tabla.executeUpdate(cadena);
        this.consulta("TbAgenda");
    }        
}


