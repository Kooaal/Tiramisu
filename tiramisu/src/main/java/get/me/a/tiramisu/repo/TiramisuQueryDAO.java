package get.me.a.tiramisu.repo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import get.me.a.tiramisu.constantes.Arrondissement;
import get.me.a.tiramisu.constantes.TiramisuBeans;
import get.me.a.tiramisu.dto.RechercheTiramisuDTO;
import get.me.a.tiramisu.entity.Tiramisu;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Component;

/**
 * @author loxos
 *
 */
@Component(TiramisuBeans.TIRAMISU_QUERY_DAO)
public class TiramisuQueryDAO implements ITiramisuQueryDAO, CommonPersistence {

	@PersistenceContext
	EntityManager em;

	@Override
	public List<Tiramisu> searchTiramisu(RechercheTiramisuDTO recherche) {
		List<Tiramisu> resultat = new ArrayList<Tiramisu>();
		boolean firstcritere = true;
		Map<String, String> criteres = new HashMap<String, String>();
		StringBuilder sb = new StringBuilder();
		sb.append(REQ_SELECT).append(" tiramisu ");
		sb.append(REQ_FROM).append(TIRAMISU_NOM_TABLE).append(REQ_AS).append(" tiramisu ");
		// ICI on fait les inner joins si nécessaire (criteres non nulles)

		if (recherche.getAdresse() != null || recherche.getCodepostal() != null || recherche.getVille() != null) {
			sb.append(REQ_INNERJOIN).append("tiramisu.lieu").append(REQ_AS).append(" lieu ");
		}
		// *********************************
		// critere NOM
		// *********************************
		// if (recherche.getName() != null) {
		// sb.append(REQ_WHERE).append(" tiramisu.name ").append(REQ_LIKE)
		// .append(":name");
		// firstcritere = false;
		// criteres.put("name", REQ_JOK+recherche.getName()+REQ_JOK);
		// }

		// *********************************
		// critere ADRESSE
		// *********************************
		if (recherche.getAdresse() != null) {
			addWhereOrAnd(sb,firstcritere);
			sb.append(" lieu.adresse").append(REQ_LIKE).append(":adresse");
			criteres.put("adresse", REQ_JOK + recherche.getAdresse() + REQ_JOK);
		}

		// *********************************
		// critere VILLE
		// *********************************
		if (recherche.getVille() != null && recherche.getVille().length() != 0) {
			addWhereOrAnd(sb,firstcritere);
			sb.append(" lieu.ville").append(REQ_LIKE).append(":ville");
			criteres.put("ville", REQ_JOK + recherche.getVille() + REQ_JOK);
		}
		// *********************************
		// critere Code Postale
		// *********************************
		if (recherche.getCodepostal() != null && !recherche.getCodepostal().equals("")) {
			addWhereOrAnd(sb,firstcritere);
			sb.append(" lieu.codepostal").append(REQ_LIKE).append(":codepostal");
			criteres.put("codepostal", REQ_JOK + recherche.getCodepostal() + REQ_JOK);
		}
		// *********************************
		// critere PRIX
		// *********************************
		if (recherche.getPrix() > 0) {
			addWhereOrAnd(sb,firstcritere);
			sb.append(" tiramisu.prix ").append(REQ_INF).append(" :prix");
			criteres.put("prix", String.valueOf(recherche.getPrix()));
		}

		// *********************************
		// critere Keyword que l'on cherche dans la descri
		// *********************************
		if (recherche.getKeywords() != null && recherche.getKeywords().length != 0) {
			for (int i = 0; i < recherche.getKeywords().length; i++) {
				addWhereOrAnd(sb,firstcritere);
				sb.append(" tiramisu.description ").append(REQ_LIKE).append(":keyword" + i);

				criteres.put("keyword" + i, REQ_JOK + recherche.getKeywords()[i] + REQ_JOK);
			}
		}
		// creation requête
		TypedQuery<Tiramisu> q = em.createQuery(sb.toString(), Tiramisu.class);
		// Query q =em.createNamedQuery(sb.toString(),Tiramisu.class);

		// settage parametre
		for (Map.Entry<String, String> entry : criteres.entrySet()) {
			System.out.println("Key :" + entry.getKey() + "-" + entry.getValue());
			q.setParameter(entry.getKey(), entry.getValue());
		}

		System.out.println(q.toString());
		resultat = q.getResultList();

		return resultat;

	}

	@Override
	public long countAllValidateTiramisu() {
		TypedQuery<Long> query = creationQueryCountAllValidateTiramisu();
		return query.getFirstResult();
	}

	@Override
	public long countAllValidateTiramisuInThisArrondissement(String cp) {
		TypedQuery<Long> query = creationQueryCountAllValidateTiramisuInThisArrondissement(cp);
		return query.getFirstResult();
	}

	/**
	 * crée la requête pour compter le nombre de tiramisu valide
	 * 
	 * @return Query
	 */
	private TypedQuery<Long> creationQueryCountAllValidateTiramisu() {
		StringBuilder hql = new StringBuilder();
		hql.append(REQ_SELECT_DISTINCT).append(REQ_COUNT).append(REQ_FROM).append(Tiramisu.NOM_ENTITE).append(REQ_AS).append("tiramisu").append(REQ_WHERE).append("tiramisu.datevalidation")
				.append(REQ_IS_NOT_NULL).append(REQ_AND).append("tiramisu.datesuppression").append(REQ_IS_NULL);
		return em.createQuery(hql.toString(), Long.class);
	}

	/**
	 * crée la requête pour compter le nombre de tiramisu valide dans cet
	 * arrondissement (cp)
	 * 
	 * @param cp
	 *            souhaité
	 * @return
	 */
	private TypedQuery<Long> creationQueryCountAllValidateTiramisuInThisArrondissement(String cp) {
		StringBuilder hql = new StringBuilder();
		hql.append(REQ_SELECT_DISTINCT).append(REQ_COUNT).append(REQ_FROM).append(Tiramisu.NOM_ENTITE).append(REQ_AS).append("tiramisu").append(REQ_INNERJOIN).append("tiramisu.lieu lieu")
				.append(REQ_WHERE).append("tiramisu.datevalidation").append(REQ_IS_NOT_NULL).append(REQ_AND).append("tiramisu.datesuppression").append(REQ_IS_NULL).append(REQ_AND)
				.append("lieu.codepostal=").append("'" + cp + "'");
		return em.createQuery(hql.toString(), Long.class);
	}

	@Override
	public List<Tiramisu> getTiramisuByArrondissement(Arrondissement arr) {
		TypedQuery<Tiramisu> query = creationQueryTiramisu(arr, null, null);
		List<Tiramisu> resultats = query.getResultList();
		return resultats;
	}

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
	@Override
	public TypedQuery<Tiramisu> creationQueryTiramisu(Arrondissement arr, Integer firstResult, Integer maxResult) {
		StringBuilder hql = new StringBuilder();
		hql.append(REQ_FROM).append(Tiramisu.NOM_ENTITE).append(REQ_AS).append("tiramisu").append(REQ_WHERE).append("tiramisu.lieu.codepostal=").append(arr.getCodepostal());
		TypedQuery<Tiramisu> query;
		if (firstResult != null && maxResult != null) {
			query = em.createQuery(hql.toString(), Tiramisu.class).setFirstResult(firstResult).setMaxResults(maxResult);
		} else {
			query = em.createQuery(hql.toString(), Tiramisu.class);

		}
		return query;
	}
	/**
	 * rajoute Where ou And à la requête selon si c'est le premier critère ou pas
	 * @param sb Stringbuilder contenant la requête
	 * @param firstcritere boolean indiquant s'il s'agit du premier critère ou pas
	 */
	private void addWhereOrAnd(StringBuilder sb,boolean firstcritere){
		if (firstcritere) {
			sb.append(REQ_WHERE);
			firstcritere = false;
		} else {
			sb.append(REQ_AND);
		}
	}

}
