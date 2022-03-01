package cartes;

import javafx.scene.paint.Color;
import moteur.Data;

/**
 * 
 * Cette classe définit les cartes <i>Influence</i> Roméo dont la valeur est 5 et qui a une capacité qui s'active en fin de manche. </br>
 * <b>CP</b> : Si Juliette (meme couleur que Roméo) se trouve dans la colonne, Roméo a une valeur de 15.  
 * 
 * @author S3T - G1
 * 
 * @since 1.0
 *
 */

public class Romeo extends CarteARetardement{

	/**
	 * Ce constructeur produit une carte <i>Influence</i> spéciale à retardement Romeo de la couleur passée en paramètre.
	 * 
	 * @param couleur Couleur de la carte.
	 * 
	 * @since 1.0
	 */
	public Romeo(Color couleur) {
		super(couleur, "Roméo", 5);
	}

	/**
	 * Active l'effet de la carte.
	 *
	 * @since 1.0
	 */
	@Override
	public void activer(Data data) throws Exception {
		for(CarteInfluence carte : data.getPlateau().getColonne(data.getPlateau().getIndexColonneCarte(this)).getCartesInfluences()) {
			if(carte instanceof Juliette && carte.getCouleur() == this.getCouleur()) {
					this.setValeur(this.getValeur() + 10);
			}
		}
	}

}
