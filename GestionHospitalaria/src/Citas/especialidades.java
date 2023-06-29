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
public class especialidades {
    private int idEspecialidades;
    private String especialidades;

    public especialidades() {
    }

    public especialidades(int idEspecialidades, String especialidades) {
        this.idEspecialidades = idEspecialidades;
        this.especialidades = especialidades;
    }

    public int getIdEspecialidades() {
        return idEspecialidades;
    }

    public void setIdEspecialidades(int idEspecialidades) {
        this.idEspecialidades = idEspecialidades;
    }

    public String getEspecialidades() {
        return especialidades;
    }

    public void setEspecialidades(String especialidades) {
        this.especialidades = especialidades;
    }
    
    
}
