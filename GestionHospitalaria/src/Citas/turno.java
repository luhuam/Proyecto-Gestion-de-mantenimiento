/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Citas;

/**
 *
 * @author Julian
 */
public class turno {
    private int idTurno;
    private String turno;

    public turno() {
    }

    public turno(int idTurno, String turno) {
        this.idTurno = idTurno;
        this.turno = turno;
    }

    public int getIdTurno() {
        return idTurno;
    }

    public void setIdTurno(int idTurno) {
        this.idTurno = idTurno;
    }

    public String getTurno() {
        return turno;
    }

    public void setTurno(String turno) {
        this.turno = turno;
    }
    
    
}
