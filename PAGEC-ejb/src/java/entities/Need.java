/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import enumerations.TRMAT;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

/**
 *
 * @author joaop
 */
@Entity
public class Need implements Serializable {

    @Id
    private int id;
    @NotNull (message = "Need name must not be empty")
    private String name;
    
    private List<Patient> patients;
    
    private List<TrainingMaterial> trainingMaterials;
    
    public Need(){
        patients = new LinkedList<>();
        trainingMaterials = new LinkedList<>();
    }
    
    public Need(int id, String name){
        this.id = id;
        this.name = name;
        patients = new LinkedList<>();
        trainingMaterials = new LinkedList<>();
        
    }
}
