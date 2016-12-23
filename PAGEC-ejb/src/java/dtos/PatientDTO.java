/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtos;

import java.io.Serializable;

/**
 *
 * @author rockandre
 */
public class PatientDTO implements Serializable{

    private int id;
    private String name;
    private int caregiverId;
    private String carevigerName;
    
    public PatientDTO() {
        
    }

    public PatientDTO(int id, String name, int caregiverId, String carevigerName) {
        this.id = id;
        this.name = name;
        this.caregiverId = caregiverId;
        this.carevigerName = carevigerName;
    }

    public int getCaregiverId() {
        return caregiverId;
    }

    public void setCaregiverId(int caregiverId) {
        this.caregiverId = caregiverId;
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
