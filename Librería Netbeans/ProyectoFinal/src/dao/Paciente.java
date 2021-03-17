package dao;


import java.sql.Date;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Denitsa
 */
public class Paciente {
    int cod_paciente;
    String nombre;
    String direccion;
    String telefono_urgencia;
    Date fecha_alta;
    int cod_medico;

    public Paciente(int cod_paciente, String nombre, String direccion, String telefono_urgencia, Date fecha_alta, int cod_medico) {
        this.cod_paciente = cod_paciente;
        this.nombre = nombre;
        this.direccion = direccion;
        this.telefono_urgencia = telefono_urgencia;
        this.fecha_alta = fecha_alta;
        this.cod_medico = cod_medico;
    }

    public int getCod_paciente() {
        return cod_paciente;
    }

    public void setCod_paciente(int cod_paciente) {
        this.cod_paciente = cod_paciente;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono_urgencia() {
        return telefono_urgencia;
    }

    public void setTelefono_urgencia(String telefono_urgencia) {
        this.telefono_urgencia = telefono_urgencia;
    }

    public Date getFecha_alta() {
        return fecha_alta;
    }

    public void setFecha_alta(Date fecha_alta) {
        this.fecha_alta = fecha_alta;
    }

    public int getCod_medico() {
        return cod_medico;
    }

    public void setCod_medico(int cod_medico) {
        this.cod_medico = cod_medico;
    }

    @Override
    public String toString() {
        return "Paciente{" + "cod_paciente=" + cod_paciente + ", nombre=" + nombre + ", direccion=" + direccion + ", telefono_urgencia=" + telefono_urgencia + ", fecha_alta=" + fecha_alta + ", cod_medico=" + cod_medico + '}';
    }
    
    
}
