package get.me.a.tiramisu.repo;

import get.me.a.tiramisu.constantes.Arrondissement;
import get.me.a.tiramisu.dto.RechercheTiramisuDTO;
import get.me.a.tiramisu.entity.Tiramisu;

import java.util.List;

import javax.persistence.TypedQuery;

/**
 * interface du repo
 * 
 * @author loxos
 *
 */
public interface ITiramisuQueryDAO {
	
	
	
	/**
	 * méthode de recherche des tiramisus
	 * 
	 * @param recherche
	 * @return
	 */
	List<Tiramisu> searchTiramisu(RechercheTiramisuDTO recherche);

	/**
	 * renvoie le nombre de tiramisu valide (date de validation non nulle, et
	 * date de suppression nulle)
	 * 
	 * @return nombre de tiramisu valide
	 */
	long countAllValidateTiramisu();

	/**
	 * * renvoie le nombre de tiramisu valide (date de validation non nulle, et
	 * date de suppression nulle)
	 * 
	 * @param cp
	 *            code postal souhaité
	 * @return nb tiramisu valide dans cet arrondissement
	 */
	long countAllValidateTiramisuInThisArrondissement(String cp);

	/**
	 * renvoie liste de tiramisu correspondant à un arrondissement
	 * 
	 * @param arrondissement
	 *            souhaité
	 * @return resultat
	 */
	List<Tiramisu> getTiramisuByArrondissement(Arrondissement arr);

	/**
	 * QUERY TIRAMISU
	 * 
	 * si firstResult et maxResult nulles, on renvoie tout les tiramisus
	 * 
	 * @param arr
	 * @param firstResult
	 * @param maxResult
	 * @return
	 */
	TypedQuery<Tiramisu> creationQueryTiramisu(Arrondissement arr, Integer firstResult, Integer maxResult);

}
