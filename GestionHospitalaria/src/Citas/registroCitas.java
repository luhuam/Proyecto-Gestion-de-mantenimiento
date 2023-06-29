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
public class registroCitas {
    private int DNIPaciente;
    int DNIMedico;
    int numOrden;
    String especialidad;
    String turno;
    String fecha;

    public registroCitas() {
    }

    public int getDNIPaciente() {
        return DNIPaciente;
    }

    public void setDNIPaciente(int DNIPaciente) {
        this.DNIPaciente = DNIPaciente;
    }

    public int getDNIMedico() {
        return DNIMedico;
    }

    public void setDNIMedico(int DNIMedico) {
        this.DNIMedico = DNIMedico;
    }

    public int getNumOrden() {
        return numOrden;
    }

    public void setNumOrden(int numOrden) {
        this.numOrden = numOrden;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public String getTurno() {
        return turno;
    }

    public void setTurno(String turno) {
        this.turno = turno;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    @Override
    public String toString() {
        return "registroCitas{" + "DNIPaciente=" + DNIPaciente + ", DNIMedico=" + DNIMedico + ", numOrden=" + numOrden + ", especialidad=" + especialidad + ", turno=" + turno + ", fecha=" + fecha + '}';
    }
    
    
}
