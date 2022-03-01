package cartes;

import java.util.ArrayList;

import javafx.scene.paint.Color;
import joueur.Joueur;
import moteur.Data;

/**
 * Cette classe définit les cartes <i>Influence<i> Mendiant dont la valeur est 4 et a une capacité qui s'active en fin de manche.</br>
 * <b>CP:</b> Lorsque le Mendiant est joué, le joueur qui totalise le plus faible score dans la colonne remporte l'objectif.
 * S'il y a égalité, le joueur qui a la carte dont la position est la plus basse dans la colonne remporte l'objectif.
 * Si un joueur n'a pas de carte et/ou un Sosie sans valeur dans cette colonne, il ne participe pas à l'attribution de l'objectif.
 * Si un joueur n'a que la carte Cape d'invisibilité dans la colonne, il remporte l'objectif. 
 * 
 * @author S3T - G1
 * 
 * @since 1.0
 *
 */

public class Mendiant extends CarteARetardement{
	
	/**
	 * Ce constructeur définit la carte <i>Influence</i> spéciale Mendiant de la couleur passée en paramètre.
	 * 
	 * @param couleur Couleur de la carte.
	 * 
	 * @author S3T - G1
	 * 
	 * @since 1.0
	 */

	public Mendiant(Color couleur) {
		super(couleur, "Mendiant", 4);
	}

	@Override
	public void activer(Data data) throws Exception {
		double scoreTampon=1000;
		Joueur joueurGagnant = data.getJoueurs()[0];
		//System.out.println("REF: " + joueurGagnant);
		int indexColonne = data.getPlateau().getIndexColonneCarte(this);
		boolean cartePose = false;
		ArrayList<Joueur> listBan = new ArrayList<>();
		ArrayList<Joueur> listeJoueursPourObjectif = new ArrayList<>();
		for(Joueur j : data.getJoueurs()) {
			for(CarteInfluence ci : data.getPlateau().getColonne(indexColonne).getCartesInfluences()) {
				if(ci != null && ci.getCouleur()==j.getCouleur()) {
					if(!(listeJoueursPourObjectif.contains(j)) && !(listBan.contains(j))) {
						cartePose = true;
						//System.out.println("VALEUR BOOLEAN : " + cartePose);
						listeJoueursPourObjectif.add(j);
						//System.out.println("LISTE JOUEURS POUR OBJ: " + listeJoueursPourObjectif);
					}
					if((ci instanceof Sosie) && (ci.getValeur()==0)) {
						listeJoueursPourObjectif.remove(j);
						listBan.add(j);
						//System.out.println("LISTE SI SOSIE NUL: " + listeJoueursPourObjectif);
					}
				}
			}
		}
		for(Joueur j : listeJoueursPourObjectif) {
				if(data.getPlateau().getColonne(indexColonne).getTotalDuJoueur(j.getCouleur()) < scoreTampon) {
					scoreTampon = data.getPlateau().getColonne(indexColonne).getTotalDuJoueur(j.getCouleur());
					//System.out.println("SCORE TAMPON : " + scoreTampon + " Colonne : " + indexColonne);
//					joueurGagnant = j;
					joueurGagnant = data.getJoueurs()[data.getIndexJoueur(j)];
					//System.out.println("GAGNANT INT: " + joueurGagnant + "Colonne : " + indexColonne);
					
				}
				else if(data.getPlateau().getColonne(indexColonne).getTotalDuJoueur(j.getCouleur()) == scoreTampon) {
					if(!data.possedeCarteLaPlusBasse(joueurGagnant, j, indexColonne)) {
//						joueurGagnant = j;
						joueurGagnant = data.getJoueurs()[data.getIndexJoueur(j)];
						//System.out.println("GAGNANT EGAL: " + joueurGagnant + " Colonne : " + indexColonne);
					} else continue;
				}
		}
		joueurGagnant.addCarteObjectif(data.getPlateau().getColonne(indexColonne).getCarteObjectif());
		//System.out.println("SCORE TAMPON FIN M: " + scoreTampon);
		//System.out.println("JOUEUR GAGNANT FIN M: " + joueurGagnant + " Colonne : " + indexColonne);
	
	}



}
