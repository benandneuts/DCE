package cartes;

import javafx.scene.paint.Color;
import joueur.Joueur;
import moteur.Data;

/**
 * Cette classe définit les cartes <i>Influences</i> Prince dont la valeur est 14 et qui a une capacité qui s'active en fin de manche.</br>
 * <b>CP</b> : Si le Prince est dans la meme colonne que l'Ecuyer (meme couleur que le Prince), le joueur obtient l'objectif.
 * S'il y a plusieurs cartes Prince et Ecuyer dans la meme colonne, le joueur dont la carte Ecuyer ou Prince est la plus proche
 * de la carte <i>Objectif</i> remporte l'objectif. 
 * 
 * @author S3T - G1
 * 
 * @since 1.0
 */

public class Prince extends CarteARetardement{

	/**
	 * Ce constructeur définit la carte <i>Influence</i> spéciale Prince de la couleur passée en paramètre.
	 * 
	 * @param couleur Couleur de la carte.
	 * 
	 * @author S3T - G1
	 * 
	 * @since 1.0
	 */
	public Prince(Color couleur) {
		super(couleur, "Prince", 14);
	}
	private boolean gagne=false;
	
	public boolean isGagne() {
		return gagne;
	}

	public void setGagne(boolean gagne) {
		this.gagne = gagne;
	}

	/**
	 * Active la capacité du Prince
	 * 
	 * @throws Exception 
	 * 
	 * @author S3T - G1
	 * 
	 * @since 1.0
	 */
	@Override
	public void activer(Data data) throws Exception {
		int indexColonne = data.getPlateau().getIndexColonneCarte(this);
		int indexProcheAutre=-1;
		int indexProche=-1;
		for(CarteInfluence carte : data.getPlateau().getColonne(indexColonne).getCartesInfluences()) {
			if((carte instanceof Ecuyer) && (carte.getCouleur() != this.getCouleur())){
				for(CarteInfluence carte2 : data.getPlateau().getColonne(indexColonne).getCartesInfluences()) {
					if((carte2 instanceof Prince) && (carte.getCouleur() == carte.getCouleur())){
						int indexCarte=data.getPlateau().getColonne(indexColonne).getIndexCarteInfluence(carte);
						int indexCarte2=data.getPlateau().getColonne(indexColonne).getIndexCarteInfluence(carte2);
						int indexP;
						if(indexCarte<indexCarte2) {
							indexP=indexCarte;
						}
						else {
							indexP=indexCarte2;
						}
						if(indexProcheAutre==-1 || indexProcheAutre>indexP ) {
							indexProcheAutre=indexP;
						}
					}
				}
			}
			else if((carte instanceof Ecuyer) && (carte.getCouleur() == this.getCouleur())){
				for(CarteInfluence carte3 : data.getPlateau().getColonne(indexColonne).getCartesInfluences()) {
					if((carte3 instanceof Prince) && (carte.getCouleur() == carte.getCouleur())){
						int indexCarte=data.getPlateau().getColonne(indexColonne).getIndexCarteInfluence(this);
						int indexCarte2=data.getPlateau().getColonne(indexColonne).getIndexCarteInfluence(carte);
						if(indexCarte<indexCarte2) {
							indexProche=indexCarte;
						}
						else {
							indexProche=indexCarte2;
						}
					}
				}
			}	
			
		}
		if(indexProche!=-1) {
			this.gagne=true;
			if((indexProche>indexProcheAutre) && (indexProcheAutre !=-1)){
				this.gagne=false;
			}
		}

	}
}
