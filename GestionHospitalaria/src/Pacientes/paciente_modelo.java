/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Pacientes;

import java.util.Date;

/**
 *
 * @author DELL
 */
public class paciente_modelo {
    private int dni;
    private int hc;
    private int telef;
    private String nombres;
    private String apellidom;
    private String apellidop;
    private String sexo;
    private String eps;
    private Date nc;

    /**
     * @return the dni
     */
    public int getDni() {
        return dni;
    }

    /**
     * @param dni the dni to set
     */
    public void setDni(int dni) {
        this.dni = dni;
    }

    /**
     * @return the hc
     */
    public int getHc() {
        return hc;
    }

    /**
     * @param hc the hc to set
     */
    public void setHc(int hc) {
        this.hc = hc;
    }

    /**
     * @return the telef
     */
    public int getTelef() {
        return telef;
    }

    /**
     * @param telef the telef to set
     */
    public void setTelef(int telef) {
        this.telef = telef;
    }

    /**
     * @return the nombres
     */
    public String getNombres() {
        return nombres;
    }

    /**
     * @param nombres the nombres to set
     */
    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    /**
     * @return the apellidom
     */
    public String getApellidom() {
        return apellidom;
    }

    /**
     * @param apellidom the apellidom to set
     */
    public void setApellidom(String apellidom) {
        this.apellidom = apellidom;
    }

    /**
     * @return the apellidop
     */
    public String getApellidop() {
        return apellidop;
    }

    /**
     * @param apellidop the apellidop to set
     */
    public void setApellidop(String apellidop) {
        this.apellidop = apellidop;
    }

    /**
     * @return the sexo
     */
    public String getSexo() {
        return sexo;
    }

    /**
     * @param sexo the sexo to set
     */
    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    /**
     * @return the eps
     */
    public String getEps() {
        return eps;
    }

    /**
     * @param eps the eps to set
     */
    public void setEps(String eps) {
        this.eps = eps;
    }

    /**
     * @return the nc
     */
    public Date getNc() {
        return nc;
    }

    /**
     * @param nc the nc to set
     */
    public void setNc(Date nc) {
        this.nc = nc;
    }
    
    
}
