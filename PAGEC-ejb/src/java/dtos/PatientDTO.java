/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtos;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author rockandre
 */
@XmlRootElement(name = "Patient")
@XmlAccessorType(XmlAccessType.FIELD)
public class PatientDTO implements Serializable{

    private int id;
    private String name;
    private String caregiverUsername;
    private String carevigerName;
    
    public PatientDTO() {
        
    }

    public PatientDTO(int id, String name, String caregiverUsername, String carevigerName) {
        this.id = id;
        this.name = name;
        this.caregiverUsername = caregiverUsername;
        this.carevigerName = carevigerName;
    }

    public void reset(){
        setId(0);
        setName(null);
        setCaregiverUsername(null);
        setCarevigerName(null);
    }
    
    
    public String getCaregiverUsername() {
        return caregiverUsername;
    }

    public void setCaregiverUsername(String caregiverUsername) {
        this.caregiverUsername = caregiverUsername;
    }

    
    public String getCarevigerName() {
        return carevigerName;
    }

    public void setCarevigerName(String carevigerName) {
        this.carevigerName = carevigerName;
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
}
