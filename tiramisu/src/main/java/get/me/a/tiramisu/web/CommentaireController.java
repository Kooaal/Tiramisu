package get.me.a.tiramisu.web;
import get.me.a.tiramisu.entity.Commentaire;
import get.me.a.tiramisu.service.CommentaireService;
import get.me.a.tiramisu.service.TiramisuService;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.joda.time.format.DateTimeFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
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

@RequestMapping("/commentaires")
@Controller
@RooWebScaffold(path = "commentaires", formBackingObject = Commentaire.class)
public class CommentaireController {

	/**
	 * service métiers des tiramisus
	 */
    @Autowired
    TiramisuService tiramisuService;
/**
 * service métiers des commentaires
 */
    @Autowired
    CommentaireService commentaireService;
    
    @RequestMapping(method = RequestMethod.POST, produces = "text/html")
    public String create(@Valid Commentaire commentaire, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, commentaire);
            return "commentaires/create";
        }
        uiModel.asMap().clear();
        commentaireService.persist(commentaire);
        return "redirect:/commentaires/" + encodeUrlPathSegment(commentaire.getId().toString(), httpServletRequest);
    }
    
    @RequestMapping(params = "form", produces = "text/html")
    public String createForm(Model uiModel) {
        populateEditForm(uiModel, new Commentaire());
        return "commentaires/create";
    }
    
    @RequestMapping(value = "/{id}", produces = "text/html")
    public String show(@PathVariable("id") Long id, Model uiModel) {
        addDateTimeFormatPatterns(uiModel);
        uiModel.addAttribute("commentaire", commentaireService.findCommentaire(id));
        uiModel.addAttribute("itemId", id);
        return "commentaires/show";
    }
    
    @RequestMapping(produces = "text/html")
    public String list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, @RequestParam(value = "sortFieldName", required = false) String sortFieldName, @RequestParam(value = "sortOrder", required = false) String sortOrder, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
            uiModel.addAttribute("commentaires", commentaireService.findCommentaireEntries(firstResult, sizeNo, sortFieldName, sortOrder));
            float nrOfPages = (float) commentaireService.countCommentaires() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("commentaires", commentaireService.findAllCommentaires(sortFieldName, sortOrder));
        }
        addDateTimeFormatPatterns(uiModel);
        return "commentaires/list";
    }
    
    @RequestMapping(method = RequestMethod.PUT, produces = "text/html")
    public String update(@Valid Commentaire commentaire, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, commentaire);
            return "commentaires/update";
        }
        uiModel.asMap().clear();
        commentaireService.merge(commentaire);
        return "redirect:/commentaires/" + encodeUrlPathSegment(commentaire.getId().toString(), httpServletRequest);
    }
    
    @RequestMapping(value = "/{id}", params = "form", produces = "text/html")
    public String updateForm(@PathVariable("id") Long id, Model uiModel) {
        populateEditForm(uiModel, commentaireService.findCommentaire(id));
        return "commentaires/update";
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
    public String delete(@PathVariable("id") Long id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        Commentaire commentaire = commentaireService.findCommentaire(id);
        commentaireService.remove(commentaire);
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/commentaires";
    }
    
    void addDateTimeFormatPatterns(Model uiModel) {
        uiModel.addAttribute("commentaire_dateajout_date_format", DateTimeFormat.patternForStyle("M-", LocaleContextHolder.getLocale()));
        uiModel.addAttribute("commentaire_datevalidation_date_format", DateTimeFormat.patternForStyle("M-", LocaleContextHolder.getLocale()));
        uiModel.addAttribute("commentaire_datesuppression_date_format", DateTimeFormat.patternForStyle("M-", LocaleContextHolder.getLocale()));
    }
    
    void populateEditForm(Model uiModel, Commentaire commentaire) {
        uiModel.addAttribute("commentaire", commentaire);
        addDateTimeFormatPatterns(uiModel);
        uiModel.addAttribute("tiramisus", tiramisuService.findAllTiramisus());
    }
    
    String encodeUrlPathSegment(String pathSegment, HttpServletRequest httpServletRequest) {
        String enc = httpServletRequest.getCharacterEncoding();
        if (enc == null) {
            enc = WebUtils.DEFAULT_CHARACTER_ENCODING;
        }
        try {
            pathSegment = UriUtils.encodePathSegment(pathSegment, enc);
        } catch (UnsupportedEncodingException uee) {}
        return pathSegment;
    }
	
}
