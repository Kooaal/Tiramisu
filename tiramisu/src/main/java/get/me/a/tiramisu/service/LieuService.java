package get.me.a.tiramisu.service;

import java.util.List;

import javax.persistence.TypedQuery;

import get.me.a.tiramisu.entity.Lieu;

import org.springframework.roo.addon.layers.service.RooService;

@RooService(domainTypes = { get.me.a.tiramisu.entity.Lieu.class })
public interface LieuService {

	void saveLieu(Lieu lieu);

	Lieu findLieu(Long id);

	Lieu updateLieu(Lieu lieu);

	void deleteLieu(Lieu lieu);

	long countAllLieus();

	List<Lieu> findLieuEntries(int firstResult, int maxResults);

	TypedQuery<Lieu> findLieusByCodepostalLike(String codepostal, String sortFieldName, String sortOrder);

	/**
	 * compte le nombre de résultat
	 * 
	 * @param codepostal
	 * @return nombre de Tiramisus
	 */
	float countFindLieusByCodepostalLike(String codepostal);

	Object findAllLieus(String sortFieldName, String sortOrder);

	/**
	 * renvoie un certain nombre de lieux (offset,limit) avec un ordre
	 * (ASC/DESC)
	 * 
	 * @param firstResult
	 * @param maxResults
	 * @param sortFieldName
	 * @param sortOrder
	 * @return
	 */
	List<Lieu> findLieuEntries(int firstResult, int maxResults, String sortFieldName, String sortOrder);

	/**
	 * renvoie tout les lieux
	 * 
	 * @return
	 */
	List<Lieu> findAllLieus();

	/**
	 * renvoie tout les lieux d'un arrondissement
	 * 
	 * @param codepostal
	 * @return
	 */
	List<Lieu> getAllLieuxFromAnArrondissement(String codepostal);
}
