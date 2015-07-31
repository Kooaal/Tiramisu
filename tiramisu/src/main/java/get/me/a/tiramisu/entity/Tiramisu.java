package get.me.a.tiramisu.entity;

import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PersistenceContext;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.jpa.entity.RooJpaEntity;
import org.springframework.roo.addon.tostring.RooToString;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Lob;

import org.springframework.roo.classpath.operations.jsr303.RooUploadedFile;

@Entity
public class Tiramisu {
	/**
	 * Nom de l'entité pour JPA
	 */
	public static final String NOM_ENTITE = Tiramisu.class.getName();

	/**
     */
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

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "tiramisu")
	private Set<Commentaire> commentaires = new HashSet<Commentaire>();
	/**
     */
	private String name;

	/**
     */
	private String description;

	public String getName() {
		return this.name;
	}

	public Lieu getLieu() {
		return this.lieu;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Calendar getDateajout() {
		return this.dateajout;
	}

	public void setDateajout(Calendar dateajout) {
		this.dateajout = dateajout;
	}

	/**
     */
	@NotNull
	@Digits(integer = 3, fraction = 2)
	private float prix;

	/**
     */
	@ManyToOne
	private Lieu lieu;
	
	
	private String contentType;
	/**
     */
	@RooUploadedFile(contentType = "image/jpeg")
	@Lob
	private byte[] image;

	public byte[] getImage() {
		return this.image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}

	public String getContentType() {
		return this.contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	@Override
	public String toString() {
		StringBuilder hq = new StringBuilder();
		hq.append(name);
		hq.append("-");
		hq.append(lieu.getNom());
		return hq.toString();

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

	public Set<Commentaire> getCommentaires() {
		return this.commentaires;
	}

	public void setCommentaires(Set<Commentaire> commentaires) {
		this.commentaires = commentaires;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public float getPrix() {
		return this.prix;
	}

	public void setPrix(float prix) {
		this.prix = prix;
	}

	public void setLieu(Lieu lieu) {
		this.lieu = lieu;
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
