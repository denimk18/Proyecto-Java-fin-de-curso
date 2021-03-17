/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.Date;

/**
 *
 * @author Denitsa
 */
public class Visitas {
    String nombre_familiar;
    String dni;
    Date fecha;
    int cod_paciente;

    public String getNombre_familiar() {
        return nombre_familiar;
    }

    public void setNombre_familiar(String nombre_familiar) {
        this.nombre_familiar = nombre_familiar;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public int getCod_paciente() {
        return cod_paciente;
    }

    public void setCod_paciente(int cod_paciente) {
        this.cod_paciente = cod_paciente;
    }

    public Visitas(String nombre_familiar, String dni, Date fecha, int cod_paciente) {
        this.nombre_familiar = nombre_familiar;
        this.dni = dni;
        this.fecha = fecha;
        this.cod_paciente = cod_paciente;
    }

    @Override
    public String toString() {
        return "Visitas{" + "nombre_familiar=" + nombre_familiar + ", dni=" + dni + ", fecha=" + fecha + ", cod_paciente=" + cod_paciente + '}';
    }
            
            
    
}
