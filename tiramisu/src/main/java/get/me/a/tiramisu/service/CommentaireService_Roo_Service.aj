// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package get.me.a.tiramisu.service;

import get.me.a.tiramisu.entity.Commentaire;
import get.me.a.tiramisu.service.CommentaireService;
import java.util.List;

privileged aspect CommentaireService_Roo_Service {
    
    public abstract long CommentaireService.countAllCommentaires();    
    public abstract void CommentaireService.deleteCommentaire(Commentaire commentaire);    
    public abstract List<Commentaire> CommentaireService.findAllCommentaires();    
    public abstract List<Commentaire> CommentaireService.findCommentaireEntries(int firstResult, int maxResults);    
    public abstract void CommentaireService.saveCommentaire(Commentaire commentaire);    
    public abstract Commentaire CommentaireService.updateCommentaire(Commentaire commentaire);    
}