package get.me.a.tiramisu.entity;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PersistenceContext;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
@Configurable
@Entity
public class Lieu {
/**
 * liste des champs de l'entité
 * utilisée pour les tris
 */
    public static final List<String> fieldNames4OrderClauseFilter = java.util.Arrays.asList("entityManager", "nom", "adresse", "ville", "dateajout", "datevalidation", "datesuppression", "tiramisus", "codepostal", "id");
    
    //TODO dfd NDA sdsd
    //NDA:ssd sds
    @PersistenceContext
    transient EntityManager entityManager;

    /**
     */
    private String nom;

    /**
     * adresse
     *
     */
    private String adresse;

    /**
     * ville du lieu
     */
    private String ville;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "M-")
    private Calendar dateajout;

    /**
     */
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "M-")
    private Calendar datevalidation;

    /**
     */
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "M-")
    private Calendar datesuppression;

    /**
     * liste des tiramisus associés au lieu
     */
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "lieu")
    private List<Tiramisu> tiramisus = new ArrayList<Tiramisu>();

    /**
     */
    @Size(max = 5)
    @Pattern(regexp = "^[0-9]*$")
    private String codepostal;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public static List<Lieu> findAllLieus() {
        return entityManager().createQuery("SELECT o FROM Lieu o", Lieu.class).getResultList();
    }

    public static final EntityManager entityManager() {
        EntityManager em = new Lieu().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setDateajout(Calendar dateajout) {
        this.dateajout = dateajout;
    }

    @Override
    public String toString() {
        return nom;
    }

    public String getAdresse() {
        return this.adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getVille() {
        return this.ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public Calendar getDateajout() {
        return this.dateajout;
    }

    public Calendar getDatevalidation() {
        return this.datevalidation;
    }

    public void setDatevalidation(Calendar datevalidation) {
        this.datevalidation = datevalidation;
    }

    public Calendar getDatesuppression() {
        return this.datesuppression;
    }

    public void setDatesuppression(Calendar datesuppression) {
        this.datesuppression = datesuppression;
    }

    public List<Tiramisu> getTiramisus() {
        return this.tiramisus;
    }

    public void setTiramisus(List<Tiramisu> tiramisus) {
        this.tiramisus = tiramisus;
    }

    public String getCodepostal() {
        return this.codepostal;
    }

    public void setCodepostal(String codepostal) {
        this.codepostal = codepostal;
    }
    @Version
    @Column(name = "version")
    private Integer version;
    
    public Integer getVersion() {
        return this.version;
    }
    
    public void setVersion(Integer version) {
        this.version = version;
    }
}
