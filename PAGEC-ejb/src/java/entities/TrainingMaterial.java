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
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@Entity
@Table(name = "TRAININGMATERIALS",
        uniqueConstraints
        = @UniqueConstraint(columnNames = {"NAME"}))
@NamedQueries({
    @NamedQuery(name = "getAllTrainingMaterials",
            query = "SELECT c FROM TrainingMaterial c ORDER BY c.name")
})
@XmlRootElement
public class TrainingMaterial implements Serializable {

    @Id
    private int id;
    @NotNull(message = "Training Material name must not be empty")
    private String name;

    @ManyToMany(mappedBy = "trainingMaterials")
    private List<Need> needs;

    private TRMAT tipoTM;

    private String link;

    @OneToMany(mappedBy = "trainingMaterial", cascade = CascadeType.REMOVE)
    private List<Procedure> procedures;

    public TrainingMaterial() {
        needs = new LinkedList<>();
        procedures = new LinkedList<>();
    }

    public TrainingMaterial(int id, String name, TRMAT tipoTM, String link) {
        this.id = id;
        this.name = name;
        this.tipoTM = tipoTM;
        this.link = link;
        needs = new LinkedList<>();
        procedures = new LinkedList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Procedure> getProcedures() {
        return procedures;
    }

    public void setProcedures(List<Procedure> procedures) {
        this.procedures = procedures;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlTransient
    public List<Need> getNeeds() {
        return needs;
    }

    public void setNeeds(List<Need> needs) {
        this.needs = needs;
    }

    public TRMAT getTipoTM() {
        return tipoTM;
    }

    public void setTipoTM(TRMAT tipoTM) {
        this.tipoTM = tipoTM;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public void addNeed(Need need) {
        needs.add(need);
    }

    public void removeNeed(Need need) {
        needs.remove(need);
    }

    public void addProcedure(Procedure procedure) {
        procedures.add(procedure);
    }

    public void removeProcedure(Procedure procedure) {
        procedures.remove(procedure);
    }

}
