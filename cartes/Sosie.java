package cartes;

import elements.Colonne;
import javafx.scene.paint.Color;
import moteur.Data;

/**
 * Cette classe définit les cartes <i>Influence</i> Sosie qui n'ont pas de valeur initiale et dont la capacité s'active en fin de manche.</br>
 * <b>CP</b> : Le Sosie prend la valeur de la carte qui se trouve en-dessous de lui. Il imite la valeur mais pas la capacité spéciale.
 * Si la carte en-dessous du Sosie est éliminée, il prend la valeur de la carte suivante.
 * Si il n'y a pas de carte en-dessous, le Sosie n'a pas de valeur.
 * Si un autre Sosie se trouve en-dessous du Sosie, la carte supérieure prend la valeur du Sosie inférieur si celui-ci a une valeur.
 *   
 * 
 * @author S3T - G1
 * 
 * @since 1.0
 *
 */

public class Sosie extends CarteARetardement{

	/**
	 * Ce constructeur produit une carte <i>Influence</i> spéciale à retardement Sosie de la couleur passée en paramètre.
	 * 
	 * @param couleur Couleur de la carte.
	 * 
	 * @since 1.0
	 */
	public Sosie(Color couleur) {
		super(couleur, "Sosie", 0);
	}

	/**
	 * Active la capacité spéciale de la carte Sosie.
	 * 
	 * @throws Exception 
	 * 
	 * @since 1.0
	 */
	@Override
	public void activer(Data data) throws Exception {
		Colonne colonne = data.getPlateau().getColonne(data.getPlateau().getIndexColonneCarte(this));
		int indexCarte = colonne.getIndexCarteInfluence(this);
		if (colonne.getCarteInfluence(indexCarte) != null && colonne.getCarteInfluence(indexCarte+1) != null) {
			//System.out.println("Carte : " + colonne.getCarteInfluence(indexCarte+1).getNom() );
			if (colonne.getCarteInfluence(indexCarte + 1) instanceof CarteDouble){
				this.setValeur(((CarteDouble) colonne.getCarteInfluence(indexCarte + 1)).valeurUtilisee(colonne.getCarteObjectif()));
			}
			else if (colonne.getCarteInfluence(indexCarte+1) instanceof Sosie) { 
				((Sosie)colonne.getCarteInfluence(indexCarte+1)).activer(data);
				((Sosie)colonne.getCarteInfluence(indexCarte+1)).setDesactivee(true);
				this.setValeur(colonne.getCarteInfluence(indexCarte + 1).getValeur());
			}
			else {
				this.setValeur(colonne.getCarteInfluence(indexCarte + 1).getValeur()); 
			}
		}
			
		this.setValeur(this.getValeur() - detecteurDragonMechant(colonne, indexCarte));
		if(this.getValeur() < 0) {
			this.setValeur(0);
		} 
	}
	
	/**
	 * Recherche combien il y a eu de carte Dragon de couleur differente avant l'index passé en parametre.
	 * 
	 * @param colonne La colonne ou on fait la recherche.
	 * 
	 * @param indexCarte L'index de la carte qui arrete la recherche.
	 * 
	 * @return Les dégâts infligés par l'ensemble des dragons placés avant la carte désignée par l'index passé en paramètre.
	 * 
	 * @throws Exception 
	 * 
	 * @since 1.0
	 */
	public int detecteurDragonMechant(Colonne colonne, int indexCarte) {
		int nbDragon=0;
		for(int i = 0; i<indexCarte; i++) {
			if (colonne.getCarteInfluence(i) instanceof Dragon && colonne.getCarteInfluence(i).getCouleur() != this.getCouleur()) {
				nbDragon++;
			}
		}
		return nbDragon * 2;
	}

}
