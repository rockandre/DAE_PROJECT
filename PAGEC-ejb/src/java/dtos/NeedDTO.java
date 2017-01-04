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
 * @author joaop
 */
@XmlRootElement(name = "Need")
@XmlAccessorType(XmlAccessType.FIELD)
public class NeedDTO implements Serializable{
    int id;
    String name;

    public NeedDTO() {
    }

    public NeedDTO(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public void reset(){
        setId(0);
        setName(null);
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
