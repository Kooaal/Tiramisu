package get.me.a.tiramisu.service.impl;

import get.me.a.tiramisu.constantes.Arrondissement;
import get.me.a.tiramisu.constantes.TiramisuBeans;
import get.me.a.tiramisu.dto.RechercheTiramisuDTO;
import get.me.a.tiramisu.entity.Tiramisu;
import get.me.a.tiramisu.repo.ITiramisuQueryDAO;
import get.me.a.tiramisu.repo.TiramisuDAO;
import get.me.a.tiramisu.repo.TiramisuQueryDAO;
import get.me.a.tiramisu.service.TiramisuService;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implémentation du code métier sur les tiramisus
 * 
 * @author loxos
 *
 */
@Transactional
@Component
public class TiramisuServiceImpl implements TiramisuService {
	@Autowired
	private TiramisuDAO repository;

	@Autowired
	@Qualifier(TiramisuBeans.TIRAMISU_QUERY_DAO)
	private ITiramisuQueryDAO tiraquerydao;

	@Override
	public void saveTiramisu(Tiramisu tiramisu) {
		repository.save(tiramisu);
	}

	@Override
	public Tiramisu findTiramisu(Long id) {
		return repository.findOne(id);
	}

	@Override
	public List<Tiramisu> findAllTiramisus() {
		return repository.findAll();
	}

	@Override
	public List<Tiramisu> rechercheByDTO(RechercheTiramisuDTO recherche) {
		// nous donne le nombre de critère non nulles
		List<Integer> resultat = critereNotNull(getCritereFromDTO(recherche));
		if (resultat.size() == 1) {
			// cas ou nom non nulle
			if (resultat.get(0) == 0) {

			}
			// keyword
			else if (resultat.get(0) == 1) {

			}
			// adresse
			else if (resultat.get(0) == 2) {

			}
			// ville
			else if (resultat.get(0) == 3) {

			}
			// code postal
			else if (resultat.get(0) == 4) {

			}
			// price
			else if (resultat.get(0) == 5) {

			}
		} else if (resultat.size() == 2) {

		} else if (resultat.size() == 3) {

		} else if (resultat.size() == 4) {

		} else if (resultat.size() == 5) {

		}
		// tous les critères sont presents
		else if (resultat.size() == 6) {

		}
		return null;

	}

	/**
	 * renvoie une liste de position des critères non nulles
	 */
	public List<Integer> critereNotNull(List<Boolean> criteresIsNull) {
		List<Integer> te = new ArrayList<Integer>();
		for (int i = 0; i < criteresIsNull.size(); i++) {
			if (!criteresIsNull.get(i)) {
				te.add(i);
			}
		}
		return te;
	}

	/**
	 * donne la liste des resultats sur les tests de nullité des critères TODO:
	 * TODO :passer par une map!
	 */
	public List<Boolean> getCritereFromDTO(RechercheTiramisuDTO recherche) {
		// ici on va tester les critères pour savoir quelle méthode appelée
		boolean isVilleNull = (recherche.getVille() == null) ? true : false;
		boolean isPriceNull = (recherche.getPrix() == 0) ? true : false;
		boolean isCodePostalNull = (recherche.getCodepostal() == null) ? true : false;
		boolean isAdresseNull = (recherche.getAdresse() == null) ? true : false;
		boolean isKeyWordNull = (recherche.getKeywords() == null) ? true : false;
		List<Boolean> criteres = new ArrayList<Boolean>();
		// ===========================================================
		// ORDRE IMPORTANT
		// ===========================================================
		criteres.add(isKeyWordNull);
		criteres.add(isAdresseNull);
		criteres.add(isVilleNull);
		criteres.add(isCodePostalNull);
		criteres.add(isPriceNull);
		return criteres;
	}

	@Override
	public List<Tiramisu> getTiramisus(RechercheTiramisuDTO recherche) {
		return tiraquerydao.searchTiramisu(recherche);
	}

	@Override
	public Tiramisu updateTiramisu(Tiramisu tiramisu) {
		return repository.save(tiramisu);
	}

	// TODO :s sd
	@Override
	public List<Tiramisu> findTiramisuEntries(int firstResult, int maxResults) {
		return repository.findAll(new org.springframework.data.domain.PageRequest(firstResult / maxResults, maxResults)).getContent();
	}

	@Override
	public long countAllTiramisus() {
		return repository.count();
	}

	/**
	 * 
	 * @return
	 */
	@Override
	public long countAllValidateTiramisus() {
		return tiraquerydao.countAllValidateTiramisu();
	}

	@Override
	public void deleteTiramisu(Tiramisu tiramisu) {
		repository.delete(tiramisu);
	}

	@Override
	public List<Tiramisu> findAllOrdedByField(Direction direction, String property) {
		Order order = new Order(direction, property);
		Sort a = new Sort(order);
		return repository.findAll(a);
	}

	@Override
	public List<Tiramisu> findAllTiramisuByArrondissement(Arrondissement arr) {
		return tiraquerydao.getTiramisuByArrondissement(arr);

	}

	@Override
	public List<Tiramisu> findAllOrdedByFieldAndPaged(Sort sort, int page, int size) {
		//TO_KNOW: avec pageRequest possibilité de faire des sorts sur plusieurs champs
		final PageRequest pageRequest = new PageRequest(page, size, sort);
		return repository.findAll(pageRequest).getContent();
	}

	@Override
	public long countAllTiramisusInThisArrondissement(String cp) {
		return tiraquerydao.countAllValidateTiramisuInThisArrondissement(cp);
	}
}
