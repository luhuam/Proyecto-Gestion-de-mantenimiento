/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HistorialClinico;

/**
 *
 * @author Julian
 */
public class registroHistorial {
    int DNIPaciente;
    int codMedico;
    float peso;
    float talla;
    float presion;
    String especialidad;
    String Tsangre;
    String observaciones;

    public registroHistorial() {
    }

    public int getDNIPaciente() {
        return DNIPaciente;
    }

    public void setDNIPaciente(int DNIPaciente) {
        this.DNIPaciente = DNIPaciente;
    }

    public int getcodMedico() {
        return codMedico;
    }

    public void setcodMedico(int DNIMedico) {
        this.codMedico = codMedico;
    }

    public float getPeso() {
        return peso;
    }

    public void setPeso(float peso) {
        this.peso = peso;
    }

    public float getTalla() {
        return talla;
    }

    public void setTalla(float talla) {
        this.talla = talla;
    }

    public float getPresion() {
        return presion;
    }

    public void setPresion(float presion) {
        this.presion = presion;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public String getTsangre() {
        return Tsangre;
    }

    public void setTsangre(String Tsangre) {
        this.Tsangre = Tsangre;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    @Override
    public String toString() {
        return "registroHistorial{" + "DNIPaciente=" + DNIPaciente + ", codMedico=" + codMedico + ", peso=" + peso + ", talla=" + talla + ", presion=" + presion + ", especialidad=" + especialidad + ", Tsangre=" + Tsangre + ", observaciones=" + observaciones + '}';
    }
    
    
}


