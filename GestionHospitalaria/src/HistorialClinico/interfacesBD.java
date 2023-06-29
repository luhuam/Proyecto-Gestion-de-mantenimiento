/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HistorialClinico;
import java.rmi.Remote; //Permitir realizar llamadas a nuestros métodos remotos
import java.rmi.RemoteException;    //Con RMI resulta muy dificil controlar los casos de excepción, por eso se recomienda 
                                    //el uso de esta librería  por si se produce una excepción al momento del llamado del método
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Julian
 */
public interface interfacesBD extends Remote{
    public DefaultTableModel Historial(String buscar) throws RemoteException;
    public void metodoListarHistorial() throws RemoteException;
}
