package cartes;

import javafx.scene.paint.Color;
import moteur.Data;

/**
 * Cette classe définit les cartes <i>Influence</i> Explorateur dont la valeur est 13 (treize) et qui ont une capacité spéciale qui s'active dès que la carte est retournée.</br>
 * <b>CP</b> : Lorsque la carte est retournée, l'Explorateur se déplace immédiatement sur la colonne de droite (ou la premiere colonne de gauche si il est dans la derniere colonne de droite).
 * Il y est alors posé face cachée en bas de la colonne. Cette action se produit a chaque fois que l'Explorateur est dévoilé.
 * 
 * @author S3T - G1
 * 
 * @since 1.0
 */
public class Explorateur extends CarteSpeciale{
	
	private int indexColonneVisee;
	
	/**
	 * Ce constructeur produit une carte <i>Influence</i> spéciale Explorateur de la couleur passée en paramètre.
	 * 
	 * @param couleur Couleur de la carte.
	 * 
	 * @since 1.0
	 */
	public Explorateur(Color couleur) {
		super(couleur, "Explorateur", 13);
	}

	
	/**
	 * Active la capacité spéciale de la carte Explorateur.
	 * 
	 * @since 1.0
	 */
	@Override
	public void activer(Data data) throws Exception {
		int indexColonneActuelle = data.getPlateau().getIndexColonneCarte(this);
		int indexCarte = data.getPlateau().getColonne(indexColonneActuelle).getIndexCarteInfluence(this);
		indexColonneVisee = data.prochaineColonne(indexColonneActuelle);
		
		if (indexColonneVisee != indexColonneActuelle) {
			
			data.eliminerCarteDataSansDefausse(indexColonneActuelle, this); 
			data.decalerCartes(indexColonneActuelle, indexCarte);
			data.getPlateau().getColonne(indexColonneVisee).ajouterCarteInfluence(this);
			
			int indexCarteNew = data.getPlateau().getColonne(indexColonneVisee).getIndexCarteInfluence(this);
			if (indexCarteNew != 0 && data.getPlateau().getColonne(indexColonneVisee).getCarteInfluence(indexCarteNew-1) instanceof CarteSpeciale) {
				data.activerCapaciteCarteSpeciale((CarteSpeciale) data.getPlateau().getColonne(indexColonneVisee).getCarteInfluence(indexCarteNew-1));
			}
		}
	}
	
	/**
	 * retourne la colonne visée.
	 *
	 * @since 1.0
	 */
	public int getIndexColonneVisee() {
		return this.indexColonneVisee;
	}
	
	
	/**
	 * définit la colonne visée.
	 *	@param nc le numéro de la colonne visée.
	 * @since 1.0
	 */
	public void setIndexColonneVisee (int nc) {
		this.indexColonneVisee = nc;
	}
}
