package get.me.a.tiramisu.web;
import get.me.a.tiramisu.constantes.Arrondissement;
import get.me.a.tiramisu.constantes.Constantes;
import get.me.a.tiramisu.entity.Lieu;
import get.me.a.tiramisu.entity.Tiramisu;
import get.me.a.tiramisu.service.LieuService;
import get.me.a.tiramisu.service.TiramisuService;

import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.joda.time.format.DateTimeFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.roo.addon.web.mvc.controller.finder.RooWebFinder;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.UriUtils;
import org.springframework.web.util.WebUtils;

@RequestMapping("/lieus")
@Controller
@RooWebScaffold(path = "lieus", formBackingObject = Lieu.class)
@RooWebFinder
public class LieuController {

    @Autowired
    LieuService lieuservice;
    
    

    @Autowired
    TiramisuService tiramisuService;
/**
 *  CREATE
 *  
 * @param lieu
 * @param bindingResult
 * @param uiModel
 * @param httpServletRequest
 * @return
 */
    @RequestMapping(method = RequestMethod.POST, produces = "text/html")
    public String create(@Valid Lieu lieu, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, lieu);
            return "lieus/create";
        }
        uiModel.asMap().clear();
        //set de la date de creation
        lieu.setDateajout(Calendar.getInstance());
        lieuservice.saveLieu(lieu);
        return "redirect:/lieus/" + encodeUrlPathSegment(lieu.getId().toString(), httpServletRequest);
    }

    /**
     * rajoute au model le lieu , des formats de date ainsi que tous les tiramisus
     * @param uiModel
     * @param lieu
     */
    void populateEditForm(Model uiModel, Lieu lieu) {
        uiModel.addAttribute("lieu", lieu);
        addDateTimeFormatPatterns(uiModel);
        uiModel.addAttribute("tiramisus", tiramisuService.findAllTiramisus());
    }

    /**
     * ajoute au model des formats de date
     * @param uiModel
     */
    static void addDateTimeFormatPatterns(Model uiModel) {
        uiModel.addAttribute("lieu_dateajout_date_format", DateTimeFormat.patternForStyle("M-", LocaleContextHolder.getLocale()));
        uiModel.addAttribute("lieu_datevalidation_date_format", DateTimeFormat.patternForStyle("M-", LocaleContextHolder.getLocale()));
        uiModel.addAttribute("lieu_datesuppression_date_format", DateTimeFormat.patternForStyle("M-", LocaleContextHolder.getLocale()));
    }

    String encodeUrlPathSegment(String pathSegment, HttpServletRequest httpServletRequest) {
        String enc = httpServletRequest.getCharacterEncoding();
        if (enc == null) {
            enc = WebUtils.DEFAULT_CHARACTER_ENCODING;
        }
        try {
            pathSegment = UriUtils.encodePathSegment(pathSegment, enc);
        } catch (UnsupportedEncodingException uee) {
        }
        return pathSegment;
    }
    /**
     *   SHOW 
     *      
     * @param id
     * @param uiModel
     * @return
     */
    @RequestMapping(value = "/{id}", produces = "text/html")
    public String show(@PathVariable("id") Long id, Model uiModel) {
        addDateTimeFormatPatterns(uiModel);
        uiModel.addAttribute("lieu", lieuservice.findLieu(id));
        uiModel.addAttribute("itemId", id);
        return "lieus/show";
    }
    /**
     * UPDATE
     * 
     * @param lieu
     * @param bindingResult
     * @param uiModel
     * @param httpServletRequest
     * @return
     */
    @RequestMapping(method = RequestMethod.PUT, produces = "text/html")
    public String update(@Valid Lieu lieu, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, lieu);
            return "lieus/update";
        }
        uiModel.asMap().clear();
        lieuservice.updateLieu(lieu);
        return "redirect:/lieus/" + encodeUrlPathSegment(lieu.getId().toString(), httpServletRequest);
    }

    
    @RequestMapping(params = "form", produces = "text/html")
    public String createForm(Model uiModel) {
        populateEditForm(uiModel, new Lieu());
        return "lieus/create";
    }
    
    /**
     * 
     * @param page
     * @param size
     * @param sortFieldName
     * @param sortOrder
     * @param arrondissement
     * @param uiModel
     * @return
     */
    @RequestMapping(produces = "text/html")
    public String list(@RequestParam(value = "page", required = false) Integer page, 
    		@RequestParam(value = "size", required = false) Integer size, 
    		@RequestParam(value = "sortFieldName", required = false) String sortFieldName,
    		@RequestParam(value = "sortOrder", required = false) String sortOrder, 
    		@RequestParam(value = "arrondissement", required = false) String arrondissement,
    		Model uiModel) {
    	// initialisation des valeurs
		initValue(size, page);
		uiModel.addAttribute("arrondissements", Arrondissement.getAllArrondissement());
		// la conversion doit permettre d'éviter l'injection
		int sizeNo = size.intValue();
		float nrOfPages = 0;

		// si critère sur le codepostal
		 if (arrondissement != null) {
			critereCodePostal(arrondissement, nrOfPages, uiModel, sizeNo);
		}
		// on renvoie un nombre de lieus
		else {
			// par défaut premier resultat =0 sinon premier resultat de la page
			final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
			getSetOfLieu(firstResult, sizeNo, uiModel, nrOfPages);
		}
		 addDateTimeFormatPatterns(uiModel);
		return "lieus/list";
//    	uiModel.addAttribute("arrondissements", Arrondissement.getAllArrondissement());
//        if (page != null || size != null) {
//        	
//            int sizeNo = size == null ? 10 : size.intValue();
//            final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
//            uiModel.addAttribute("lieus", lieuservice.findLieuEntries(firstResult, sizeNo, sortFieldName, sortOrder));
//            float nrOfPages = (float) lieuservice.countAllLieus() / sizeNo;
//            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
//        } else {
//            uiModel.addAttribute("lieus", lieuservice.findAllLieus(sortFieldName, sortOrder));
//        }
//        addDateTimeFormatPatterns(uiModel);
//        return "lieus/list";
    }
    
	/**
	 * renvoie un set de lieus
	 * 
	 * @param firstResult
	 *            de resultat
	 * @param sizeNo
	 *            taille de la page
	 * @param uiModel
	 *            données
	 * @param nrOfPages
	 *            nb de pages totales
	 */
	private void getSetOfLieu(int firstResult, int sizeNo, Model uiModel, float nrOfPages) {
		List<Lieu> resultat = lieuservice.findLieuEntries(firstResult, sizeNo);
		// renvoie les resultats correspondant à la page demandé
		uiModel.addAttribute("lieus", resultat);
		nrOfPages = (float) lieuservice.countAllLieus() / sizeNo;
	}
    
	/**
	 * recupere les lieux liés à un arrondissement
	 * 
	 * @param codepostal
	 *            souhaité
	 * @param nrOfPages
	 *            nombre de page avec ces résultats
	 * @param uiModel
	 *            model pour le passage de données
	 * @param sizeNo
	 *            nb tiramisu par page
	 */
	private void critereCodePostal(String codepostal, float nrOfPages, Model uiModel, int sizeNo) {
		Arrondissement arr = Arrondissement.getFromCodepostal(codepostal);
		// si arrondissement non null ou non séléctionné
		if (arr != null && !codepostal.equals(Arrondissement.ARR_NULL.getCodepostal())) {
			List<Lieu> resultats = lieuservice.getAllLieuxFromAnArrondissement(codepostal);
			
			// TOCHECK
			nrOfPages = (float) lieuservice.countFindLieusByCodepostalLike(codepostal);
			uiModel.addAttribute("lieus", resultats);
			uiModel.addAttribute("selectedcodepostal", arr.getCodepostal());
		}
		// si pas d'arrondissement->on renvoie tout et on selectionne le
		// nulle
		else {
			uiModel.addAttribute("lieus", lieuservice.findAllLieus());
			nrOfPages = (float) lieuservice.countAllLieus() / sizeNo;
			uiModel.addAttribute("selectedcodepostal", Arrondissement.ARR_NULL.getCodepostal());
		}
	}
    
	/**
	 * init value Page and Size
	 * 
	 * @param size
	 *            nb tiramisus par page
	 * @param page
	 *            page souhaité
	 */
	private void initValue(Integer size, Integer page) {
		page = page == null ? new Integer(1) : page;
		size = (size == null || size > 20) ? Constantes.NB_LIEU_LIST_PAGE : size;
	}

    
    @RequestMapping(value = "/{id}", params = "form", produces = "text/html")
    public String updateForm(@PathVariable("id") Long id, Model uiModel) {
        populateEditForm(uiModel, lieuservice.findLieu(id));
        return "lieus/update";
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
    public String delete(@PathVariable("id") Long id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        Lieu lieu = lieuservice.findLieu(id);
        lieuservice.deleteLieu(lieu);
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/lieus";
    }
    
	/**
	 * recherche des Lieux par Code Postal.
	 * (form de recherche)
	 * 
	 * @param codepostal
	 * @param page
	 * @param size
	 * @param sortFieldName
	 * @param sortOrder
	 * @param uiModel
	 * @return
	 */
	@RequestMapping(params = "find=ByCodepostalLike", method = RequestMethod.GET)
	public String findLieusByCodepostalLike(@RequestParam("codepostal") String codepostal, @RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size, @RequestParam(value = "sortFieldName", required = false) String sortFieldName,
			@RequestParam(value = "sortOrder", required = false) String sortOrder, Model uiModel) {
		uiModel.addAttribute("arrondissements", Arrondissement.getAllArrondissement());
		uiModel.addAttribute("selectedcodepostal", codepostal);
		if (page != null || size != null) {
			int sizeNo = size == null ? 10 : size.intValue();
			final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
			uiModel.addAttribute("lieus", lieuservice.findLieusByCodepostalLike(codepostal, sortFieldName, sortOrder).setFirstResult(firstResult).setMaxResults(sizeNo).getResultList());
			float nrOfPages = (float) lieuservice.countFindLieusByCodepostalLike(codepostal) / sizeNo;
			uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
		} else {
			uiModel.addAttribute("lieus", lieuservice.findLieusByCodepostalLike(codepostal, sortFieldName, sortOrder).getResultList());
		}
		LieuController.addDateTimeFormatPatterns(uiModel);
		return "lieus/list";
	}
        /**
     * renvoie le form contenant la recherche par codePostal
     * @param uiModel
     * @return
     */
    @RequestMapping(params = { "find=ByCodepostalLike", "form" }, method = RequestMethod.GET)
    public String findLieusByCodepostalLikeForm(Model uiModel) {
        return "lieus/findLieusByCodepostalLike";
    }
}
