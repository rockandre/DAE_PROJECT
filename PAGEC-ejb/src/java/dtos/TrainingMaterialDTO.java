/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtos;

import enumerations.TRMAT;
import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author joaop
 */
@XmlRootElement(name = "TrainingMaterial")
@XmlAccessorType(XmlAccessType.FIELD)
public class TrainingMaterialDTO implements Serializable{
    int id;
    String name;
    TRMAT tipoTM;
    String link;

    public TrainingMaterialDTO() {
    }

    public TrainingMaterialDTO(int id, String name, TRMAT tipoTrainingMaterial, String link) {
        this.id = id;
        this.name = name;
        this.tipoTM = tipoTrainingMaterial;
        this.link = link;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    

    public void reset(){
        setId(0);
        setName(null);
        setTipoTM(null);
        setLink(null);
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


    public TRMAT getTipoTM() {
        return tipoTM;
    }

    public void setTipoTM(TRMAT tipoTrainingMaterial) {
        this.tipoTM = tipoTrainingMaterial;
    }
    
    
    
}
