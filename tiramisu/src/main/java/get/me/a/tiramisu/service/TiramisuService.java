package get.me.a.tiramisu.service;

import java.util.List;

import get.me.a.tiramisu.constantes.Arrondissement;
import get.me.a.tiramisu.dto.RechercheTiramisuDTO;
import get.me.a.tiramisu.entity.Tiramisu;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.roo.addon.layers.service.RooService;

/**
 * Interface de Service Tiramisu
 * 
 * @author loxos
 *
 */
public interface TiramisuService {
	/**
	 * (U)PDATE
	 * 
	 * @param tiramisu
	 */
	public void saveTiramisu(Tiramisu tiramisu);

	/**
	 * (R)ETRIEVE
	 * 
	 * @param id
	 * @return Tiramisu
	 */
	public Tiramisu findTiramisu(Long id);

	/**
	 * (R)ETRIEVE ALL
	 * 
	 * @return
	 */
	public List<Tiramisu> findAllTiramisus();

	// List<Tiramisu> findByPrix(Float prix);
	//
	// List<Tiramisu> findByPRiceandCode(Float prix, String codepostal);

	/**
	 * 
	 * @param recherche
	 * @return
	 */
	List<Tiramisu> rechercheByDTO(RechercheTiramisuDTO recherche);

	/**
	 * Recherche
	 * 
	 * @param recherche
	 * @return
	 */
	List<Tiramisu> getTiramisus(RechercheTiramisuDTO recherche);

	// List<Tiramisu> findTiramisuEntries(int firstResult, int sizeNo);
	/**
	 * (U)PDATE TIRAMISU
	 * 
	 * @param tiramisu
	 * @return
	 */
	Tiramisu updateTiramisu(Tiramisu tiramisu);

	/**
	 * renvoie les tiramisus entre le nrésultat et le m résultat
	 * 
	 * @param firstResult
	 * @param sizeNo
	 * @return
	 */
	List<Tiramisu> findTiramisuEntries(int firstResult, int sizeNo);

	/**
	 * compte le nombre de tiramisu
	 * 
	 * @return
	 */
	long countAllTiramisus();

	/**
	 * compte le nombre de tiramisu dans cet arrondissement ( useful pour la pagination)
	 * 
	 * @param : code postal souhaité
	 */
	long countAllTiramisusInThisArrondissement(String cp);
	/**
	 * (D)ELETE TIRAMISU
	 * 
	 * @param tiramisu
	 */
	void deleteTiramisu(Tiramisu tiramisu);

	/**
	 * Renvoi le nombre de tiramisus valides (dont date de validation non nulle)
	 * 
	 * @return nombre
	 */
	long countAllValidateTiramisus();

	/**
	 * renvoi l'ensemble des tiramisus selon le critère donnée et l'ordre
	 * 
	 * @param direction
	 *            ASC ou DESC
	 * @param property
	 *            attribut de tiramisu utilisé pour trier
	 * @return liste de tiramisu trié sur l'attribut en paramètre
	 */
	List<Tiramisu> findAllOrdedByField(Direction direction, String property);

	/**
	 * renvoie une liste de tiramisu triée sur un critère et dans un ordre de
	 * façon paginée
	 * 
	 * @param direction
	 *            ASC ou DESC
	 * @param property
	 *            sur laquelle se baser pour le tri
	 * @param page
	 *            page souhaitée (commence à 0)
	 * @param size
	 *            taille par page
	 * @return liste de tiramisu
	 */
	List<Tiramisu> findAllOrdedByFieldAndPaged(Sort sort, int page, int size);

	/**
	 * renvoie les tiramisus selon leur arrondissement
	 * 
	 * @param arrondissement
	 *            souhaité
	 * @return liste de tiramisu
	 */
	List<Tiramisu> findAllTiramisuByArrondissement(Arrondissement arr);


}
