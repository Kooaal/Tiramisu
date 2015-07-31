package get.me.a.tiramisu.constantes;

import java.util.ArrayList;
import java.util.List;
/**
 * ENUM avec les arrondissements + arrondissement null
 * @author loxos
 *
 */
public  enum Arrondissement {
	/**
	 * Tous les arrondissements
	 */
	ARR_NULL("TOUS","TOUS"),
	/**
	 * 1er Arrondissement
	 */
	ARR_1("1er Arrondissement","75001"),
	ARR_2("2ème Arrondissement","75002"),
	ARR_3("3ème Arrondissement","75003"),
	ARR_4("4ème Arrondissement","75004"),
	ARR_5("5ème Arrondissement","75005"),
	ARR_6("6ème Arrondissement","75006"),
	ARR_7("7ème Arrondissement","75007"),
	ARR_8("8ème Arrondissement","75008"),
	ARR_9("9ème Arrondissement","75009"),
	ARR_10("10ème Arrondissement","75010"),
	ARR_11("11ème Arrondissement","75011"),
	ARR_12("12ème Arrondissement","75012"),
	ARR_13("13ème Arrondissement","75013"),
	ARR_14("14ème Arrondissement","75014"),
	ARR_15("15ème Arrondissement","75015"),
	ARR_16("16ème Arrondissement","75016"),
	ARR_17("17ème Arrondissement","75017"),
	ARR_18("18ème Arrondissement","75018"),
	ARR_19("19ème Arrondissement","75019"),
	ARR_20("20ème Arrondissement","75020");
	
	public String getCodepostal() {
		return codepostal;
	}
	public void setCodepostal(String codepostal) {
		this.codepostal = codepostal;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	private String codepostal;
	
	private String nom;
	
	/**
	 * constructeur de l'enum
	 * @param nom
	 * @param codepostal
	 */
	Arrondissement(String nom,String codepostal){
		this.nom=nom;
		this.codepostal=codepostal;
	}
/**
 * recupere la liste des arrondissements	
 * @return liste
 */
	public static List<Arrondissement> getAllArrondissement(){
		List<Arrondissement> arrondissements=new ArrayList<Arrondissement>();
		for(Arrondissement arr:Arrondissement.values()){
			arrondissements.add(arr);
		}
		return arrondissements;
	}
	/**
	 * renvoie l'enum arrondissement à partir du code postal
	 * @param cp code postal souhaité
	 * @return enum Arrondissement ou null si rien trouvé
	 */
	public static Arrondissement getFromCodepostal(String cp){
		for(Arrondissement arr:Arrondissement.values()){
		if(arr.getCodepostal().equals(cp)){
			return arr;
		}
		}
		return null;
	}
}
