package get.me.a.tiramisu.service.impl;

import java.util.List;

import javax.persistence.TypedQuery;

import get.me.a.tiramisu.constantes.TiramisuBeans;
import get.me.a.tiramisu.entity.Lieu;
import get.me.a.tiramisu.repo.LieuDAO;
import get.me.a.tiramisu.repo.LieuQueryDAO;
import get.me.a.tiramisu.service.LieuService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service(TiramisuBeans.LIEU_SERVICE)
public class LieuServiceImpl implements LieuService {
	/**
	 * spring data
	 */
	@Autowired
	LieuDAO lieuDAO;
	/**
	 * repos Lieu
	 */
	@Autowired
	LieuQueryDAO lieuqueryDAO;

	public List<Lieu> findAllLieus() {
		return lieuDAO.findAll();
	}

	@Override
	public void saveLieu(Lieu lieu) {
		lieuDAO.save(lieu);
	}

	@Override
	public Lieu findLieu(Long id) {
		return lieuDAO.findOne(id);
	}

	@Override
	public Lieu updateLieu(Lieu lieu) {
		return lieuDAO.save(lieu);
	}

	@Override
	public long countAllLieus() {
		return lieuDAO.count();
	}

	@Override
	public void deleteLieu(Lieu lieu) {
		lieuDAO.delete(lieu);
	}

	@Override
	public List<Lieu> findLieuEntries(int firstResult, int maxResults) {
		return lieuDAO.findAll(
				new org.springframework.data.domain.PageRequest(firstResult
						/ maxResults, maxResults)).getContent();
	}

	@Override
	public TypedQuery<Lieu> findLieusByCodepostalLike(String codepostal,
			String sortFieldName, String sortOrder) {
		return lieuqueryDAO.findLieusByCodepostalLike(codepostal,sortFieldName,sortOrder);
	}
	@Override
	public List<Lieu> getAllLieuxFromAnArrondissement(String codepostal){
		return lieuqueryDAO.getAllLieuxFromThisArrondissement(codepostal);
	}
	@Override
	public float countFindLieusByCodepostalLike(String codepostal) {
		return lieuqueryDAO.countFindLieusByCodepostalLike(codepostal);
	}

	@Override
	public Object findAllLieus(String sortFieldName, String sortOrder) {
		lieuqueryDAO.findAllLieus(sortFieldName, sortOrder);
		return null;
	}

	@Override
	public List<Lieu> findLieuEntries(int firstResult, int maxResults,
			String sortFieldName, String sortOrder) {
		return lieuqueryDAO.findLieuEntries(firstResult, maxResults, sortFieldName, sortOrder);
	}
}
