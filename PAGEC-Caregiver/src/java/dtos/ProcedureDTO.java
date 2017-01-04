/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtos;

import java.io.Serializable;
import java.util.Date;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author joaop
 */
@XmlRootElement(name = "Procedure")
@XmlAccessorType(XmlAccessType.FIELD)
public class ProcedureDTO implements Serializable{
    int id;
    Date date;
    int patientId;
    String patientName;
    String caregiverUsername;
    String caregiverName;
    int trainingMaterialId;
    String trainingMaterialName;

    public ProcedureDTO() {
    }

    public ProcedureDTO(int id, Date date, int patientId, String patientName, String caregiverUsername, String caregiverName, int trainingMaterialId, String trainingMaterialName) {
        this.id = id;
        this.date = date;
        this.patientId = patientId;
        this.patientName = patientName;
        this.caregiverUsername = caregiverUsername;
        this.trainingMaterialId = trainingMaterialId;
        this.trainingMaterialName = trainingMaterialName;
        this.caregiverName = caregiverName;
    }

    

    

    public void reset(){
        setId(0);
        setDate(null);
        setCaregiverUsername(null);
        setCaregiverName(null);
        setPatientId(0);
        setTrainingMaterialId(0);
        setPatientName(null);
        setTrainingMaterialName(null);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public String getCaregiverUsername() {
        return caregiverUsername;
    }

    public void setCaregiverUsername(String caregiverUsername) {
        this.caregiverUsername = caregiverUsername;
    }

    public int getTrainingMaterialId() {
        return trainingMaterialId;
    }

    public void setTrainingMaterialId(int trainingMaterialId) {
        this.trainingMaterialId = trainingMaterialId;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getTrainingMaterialName() {
        return trainingMaterialName;
    }

    public void setTrainingMaterialName(String trainingMaterialName) {
        this.trainingMaterialName = trainingMaterialName;
    }

    public String getCaregiverName() {
        return caregiverName;
    }

    public void setCaregiverName(String caregiverName) {
        this.caregiverName = caregiverName;
    }

    
    
    
}
