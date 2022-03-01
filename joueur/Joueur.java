package joueur;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import bot.Bot;
import cartes.*;
import javafx.scene.paint.Color;
import moteur.Data;

/**
 * Cette classe définit les joueurs de la partie qui possèdent chacun une couleur assignée aux cartes <i>Influence</i> de leur réserve
 * et de leur défausse, un pseudo et un paquet de carte <i>Objectif</i> qu'ils auront gagnées au fil des manches.
 * 
 * @author S3T - G1
 * 
 * @since 1.0
 */
public class Joueur /*extends JsonTraitement implements JsonInterface*/ {

    private CarteInfluence  main[];
    private CarteInfluence  defausse[];
	private CarteInfluence  reserve[];
	private ArrayList<CarteObjectif>	objectif;
    private Color           couleur;
    private String          pseudo;
    private int score = 0;
    private int CarteSelectionnee = -1;

    /**
     * Ce constructeur produit un joueur en spécifiant sa couleur et donc par définition celle de ses cartes <i>Influence</i> ainsi
     * que son pseudo, identité du joueur au cours de la partie.
     * 
     * @param couleur La couleur du joueur et donc celle des ses cartes <i>Influence</i>.
     * 
     * @param pseudo L'identité du joueur.
     * 
     * @since 1.0
     */
	public Joueur (Color couleur, String pseudo) {
		this.pseudo = pseudo;
		this.couleur = (Color) couleur;
		main = new CarteInfluence[3];
	    defausse = new CarteInfluence[25];
		reserve = new CarteInfluence[25];
		objectif = new ArrayList<CarteObjectif>();
		score = 0;
	}

	/**
	 * retoune la liste des cartes <i>objectif<i> du joueur.
	 * @return
	 */
	public ArrayList<CarteObjectif> getObjectif() {
		return objectif;
	}

	/**
	 * retoune la liste des cartes <i>objectif<i> du joueur.
	 * @param objectif la liste des cartes <i>objectif<i>.
	 */
	public void setObjectif(ArrayList<CarteObjectif> objectif) {
		this.objectif = objectif;
	}

	/**
     * Retourne la couleur du joueur.
     * 
     * @return La couleur du joueur.
     * 
     * @since 1.0
     */
    public Color getCouleur() {
        return this.couleur;
    }

    /**
     * Modifie la couleur du joueur, qui va servir à identitifier ses cartes <i>Influence</i>.
     * 
     * @param couleur La couleur à assigner au joueur.
     * 
     * @since 1.0
     */
    public void setCouleur(Color couleur) {
        this.couleur = (Color) couleur;
    }

    /**
     * Retourne le pseudo du joueur qui représente son identité dans le jeu.
     * 
     * @return Le pseudo du joueur.
     * 
     * @since 1.0
     */
    public String getPseudo() {
        return this.pseudo;
    }

    /**
     * Modifie le pseudo du joueur.
     * 
     * @param pseudo Le pseudo à assigner au joueur.
     * 
     * @since 1.0
     */
    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }
	
    /**
     * Retourne la réserve de cartes <i>Influence</i> du joueur.
     * 
     * @return La réserve du joueur.
     * 
     * @since 1.0
     */
	public CarteInfluence[] getReserve() {
		return reserve;
	}

	/**
     * Modifie la réserve de cartes <i>Influence</i> du joueur.
     * 
     * @param reserve La réserve à assigner au joueur.
     * 
     * @since 1.0
     */
	public void setReserve(CarteInfluence[] reserve) {
		this.reserve = reserve;
	}
	
	/**
     * Retourne la main de cartes <i>Influence</i> du joueur.
     * 
     * @return La main du joueur.
     * 
     * @since 1.0
     */
    public CarteInfluence[] getMain() {
		return main;
	}

    /**
     * Modifie la main de cartes <i>Influence</i> du joueur.
     * 
     * @param main La main à assigner au joueur.
     * 
     * @since 1.0
     */
	public void setMain(CarteInfluence[] main) {
		this.main = main;
	}

	/**
     * Retourne la défausse de cartes <i>Influence</i> du joueur.
     * 
     * @return La défausse du joueur.
     * 
     * @since 1.0
     */
	public CarteInfluence[] getDefausse() {
		return defausse;
	}

	/**
     * Modifie la défausse de cartes <i>Influence</i> du joueur.
     * 
     * @param defausse La défausse à assigner au joueur.
     * 
     * @since 1.0
     */
	public void setDefausse(CarteInfluence[] defausse) {
		this.defausse = defausse;
	}


	/**
     * Sélectionne la carte <i>Influence</i> spécifiée.
     * 
     * @param carte La carte <i>Influence</i> à sélectionner.
     * 
     * @since 1.0
     */
	public void setCarteSelectionnee(int carte) {
		CarteSelectionnee = carte;
	}
	/**
	 * Retourne la carte <i>Influence</i> spécifiée.
	 * @return
	 */
	public int getCarteSelectionnee() {
		return CarteSelectionnee;
	}

	/**
     * Initialise la main de cartes <i>Influence</i> avec trois cartes prises aléatoirement dans la réserve.
     * Cette méthode lève une exception si le nombre de carte dans la réserve est inférieur à trois.
     * 
     * @since 1.0
     */
	public void initMain() {
		int i=0;
		for(CarteInfluence carte : main) {
			if(carte == null) 
				i++;
		}
		if(i<3) {
			//exception
		}
		else {
			for(CarteInfluence carte : main) {
				int indexAleatoire = getCarteInfluenceAleatoireDansReserve();
				carte = reserve[indexAleatoire];
				reserve[indexAleatoire] = null;
			}
		}
	}
	
	/**
     * Retourne aléatoirement une carte de la réserve.
     * 
     * @return Une carte aléatoire de la réserve.
     * 
     * @since 1.0
     */
    public int getCarteInfluenceAleatoireDansReserve() {
    	if(reserveNulle()) {
    		reserve = defausse.clone();
    		defausse = new CarteInfluence[25];
    	}
    	Random rand = new Random();
    	ArrayList<Integer> listIndex = new ArrayList<>();
    	for(int i = 0 ; i< reserve.length ; i++) {
    		if(reserve[i] != null) {
    			listIndex.add(i);
    		}
    	}
    	return listIndex.get(rand.nextInt(listIndex.size()));
    }
    
    /**
     * Ajoute la carte <i>Influence</i> spécifiée dans la main du joueur.
     * 
     * @param carteI Carte <i>Influence</i> à placer dans la main du joueur.
     * 
     * @since 1.0
     */
    public void ajouterCarteInfluence(CarteInfluence carteI) {
		for(CarteInfluence carte : main) {
			if(carte == null) {
				carte = carteI;
				break;
			}
		}
    }

    /**
     * Initialise la réserve du joueur avec les vingt-cinq cartes <i>Influence</i> correspondant à sa couleur.
     * 
     * @since 1.0
     */
	public void initReserve(){
		Roi king = new Roi(couleur);
		Reine queen = new Reine(couleur);
		Juliette julie = new Juliette(couleur);
		Romeo romeo = new Romeo(couleur);
		Mendiant mendiant = new Mendiant(couleur);
		Assassin assassin = new Assassin(couleur);
		Alchimiste alchimiste = new Alchimiste(couleur);
		CarteInfluence capeDInvisibilite = new CapeDInvisibilite(couleur);
		CarteInfluence cardi = new Cardinal(couleur);
		CarteInfluence dragon = new Dragon(couleur);
		CarteInfluence ecuyer = new Ecuyer(couleur);
		CarteInfluence ermite = new Ermite(couleur);
		CarteInfluence explo = new Explorateur(couleur);
		CarteInfluence magicien = new Magicien(couleur);
		CarteInfluence maitreDArme = new MaitreDArme(couleur);
		CarteInfluence marchand = new Marchand(couleur);
		CarteInfluence petitgeant = new PetitGeant(couleur);
		CarteInfluence prince = new Prince(couleur);
		CarteInfluence seigneur = new Seigneur(couleur);
		CarteInfluence sorciere = new Sorciere(couleur);
		CarteInfluence sosie = new Sosie(couleur);
		CarteInfluence tempete = new Tempete(couleur);
		CarteInfluence traitre = new Traitre(couleur);
		CarteInfluence troisMousquetaires = new TroisMousquetaires(couleur);
		CarteInfluence troubadour = new Troubadour(couleur);
		
		CarteInfluence[] cartes = new CarteInfluence[]{alchimiste/*, traitre*/, assassin/*, capeDInvisibilite*/, cardi, dragon, ecuyer, ermite, explo, julie, king, queen,
				romeo, mendiant, magicien, maitreDArme, marchand, petitgeant, prince, seigneur, sorciere, sosie, tempete, troisMousquetaires, troubadour};
		
		int i = 0;
		for(CarteInfluence carte : cartes) { 
			reserve[i] = carte;
			i++;
		}
	}
	
	/**
     * Initialise la main de cartes <i>Influence</i> du joueur avec trois cartes prises aléatoirement dans la réserve.
     * 
     * @since 1.0
     */
	public void initMainJoueur(){
		for(int i = 0 ; i < main.length ; i++) {
			int selected = this.getCarteInfluenceAleatoireDansReserve();
			main[i] = reserve[selected];
			reserve[selected]=null;
		}
	}
	
	

	/**
     * Retourne un booléen vrai si la réserve est vide, faux si elle ne l'est pas.
     * 
     * @return Vrai si la réserve est vide, faux si elle ne l'est pas.
     * 
     * @since 1.0
     */
    public boolean reserveNulle() {
    	for(int i = 0; i < reserve.length; i++) {
    		if(reserve[i] != null)
    			return false;
    	}
    	return true;
    }
    
    /**
     * Ajoute une carte <i>Influence</i> dans la main du joueur si le nombre de est inférieure à trois.
     * 
     * @param indexMain L'emplacement dans la main du joueur de la carte <i>Influence</i> à rajouter.
     * 
     * @param carte Carte <i>Influence</i> à rajouter dans la main du joueur.
     * 
     * @since 1.0
     */
    public void setMain(int indexMain, CarteInfluence carte) {
    	if(indexMain < 3) {
    		main[indexMain] = carte;
    	}
    }
    
    /**
     * Pour jouer une carte
     * 
     * @param data les données de la partie.
     * 
     * @param indexMain index de la carte à jouer.
     * 
     * @param indexColonne la colonne dans laquelle on joue.
     * 
     * @throws Exception
     */
    public void jouer(Data data, int indexMain, int indexColonne) throws Exception {
    if(data.getJoueurIntermediaire() == data.getIndexJoueurParCouleur(couleur)) {
    	//faire les differents cas
    	data.jouerCarte(indexMain, indexColonne);
    }
    else if(data.getCurrentJoueur() == data.getIndexJoueurParCouleur(couleur) && data.getJoueurIntermediaire() == -1) {
    	data.jouerCarte(indexMain, indexColonne);
    }
    }
    /**
     * Ajoute la carte <i>Influence</i> spécifiée dans la réserve du joueur à l'index spécifié.
     * 
     * @param indexReserve L'emplacement de la carte <i>Influence</i> à rajouter dans la réserve du joueur.
     * 
     * @param carte Carte <i>Influence</i> à rajouter dans la réserve du joueur.
     * 
     * @since 1.0
     */
    public void setCarteDansReserve(int indexReserve, CarteInfluence carte) {
    	reserve[indexReserve] = carte;
    }
    
    /**
     * ajoute une carte <i>Objectif</i>.
     * 
     * @param obj la carte à ajouter.
     */
    public void addCarteObjectif(CarteObjectif obj) {
    	if(obj != null)
    		objectif.add(obj);
    }
    
    /**
     * ajoute une carte dans la fausse.
     * 
     * @param carte la carte à jeter.
     */
    public void ajouterDansLaDefausse(CarteInfluence carte) {
    	for(int i = 0; i<defausse.length; i++) {
    		if(defausse[i] == null) {
    			defausse[i]=carte;
    			break;
    		}
    	}
    }
    
    /**
     * Retourne le score du joueur.
     * 
     * @return Score du joueur.
     * 
     * @since 1.0
     */
    public int getScore() {
    	return score;
    	}
    
    /**
     * Calcule la valeur qui sera assignée à l'attribut score du joueur.
     * 
     * 
     * @since 1.0
     */
    public void calculScore() {
    	score = 0;
    	for(CarteObjectif carteObj : this.objectif)
    		score+=carteObj.getValeur();
    }
}