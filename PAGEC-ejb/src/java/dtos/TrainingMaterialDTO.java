/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtos;

import java.io.Serializable;

/**
 *
 * @author joaop
 */
public class TrainingMaterialDTO implements Serializable{
    int id;
    String name;
    String tipoSuporte;
    String tipoTrainingMaterial;

    public TrainingMaterialDTO() {
    }

    public TrainingMaterialDTO(int id, String name, String tipoSuporte, String tipoTrainingMaterial) {
        this.id = id;
        this.name = name;
        this.tipoSuporte = tipoSuporte;
        this.tipoTrainingMaterial = tipoTrainingMaterial;
    }

    public void reset(){
        setId(0);
        setName(null);
        setTipoSuporte(null);
        setTipoTrainingMaterial(null);
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTipoSuporte() {
        return tipoSuporte;
    }

    public void setTipoSuporte(String tipoSuporte) {
        this.tipoSuporte = tipoSuporte;
    }

    public String getTipoTrainingMaterial() {
        return tipoTrainingMaterial;
    }

    public void setTipoTrainingMaterial(String tipoTrainingMaterial) {
        this.tipoTrainingMaterial = tipoTrainingMaterial;
    }
    
    
    
}
