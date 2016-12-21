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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 *
 * @author joaop
 */
@Entity
@Table(name = "TRAININGMATERIALS")
@NamedQueries({
    @NamedQuery(name = "getAllTrainingMaterials",
    query = "SELECT c FROM TrainingMaterial c ORDER BY c.name"),
    //@NamedQuery(name = "getAllCoursesNames",
    //query = "SELECT c.name FROM Course c ORDER BY c.name")
})
public class TrainingMaterial implements Serializable {

    @Id
    private int id;
    @NotNull (message = "Training Material name must not be empty")
    private String name;
    
    private List<Need> needs;
    
    private String tipoSuporte;
    
    private TRMAT tipoTM;
    
    private String link;
    
    private byte[] bytes;
    
    public TrainingMaterial(){
        needs = new LinkedList<>();
    }
    
    public TrainingMaterial(int id, String name, String tipoSuporte, TRMAT tipoTM){
        this.id = id;
        this.name = name;
        this.tipoSuporte = tipoSuporte;
        this.tipoTM = tipoTM;
        needs = new LinkedList<>();
    }
    
    
}
