package interfaces;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;

import bot.Bot;
import cartes.CarteInfluence;
import elements.Colonne;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Screen;
import javafx.stage.Stage;
import moteur.Data;
import pp.InterfacePlateau;
import reseau.Message;
import reseau.ReponseMessageTCP;
import reseau.ReponseMessageUDP;
import reseau.TypeDeMessage;


/**
 * Cette classe permet de controler les interfaces, c'est elle qui fera la liaison entre les interfaces et le programme principal.
 * 
 * @author S3T - G1
 * 
 * @since 1.0
 */
public class GestionnaireInterface extends Application {
	
	public Stage MainStage;
	public Group root = new Group();
	private Node ecranCourant = null;
	private boolean estFinie = false;
	protected static Data data;
	public InterfaceJeu Jeux = null; // must be done to pass data from creerPartie to Jeu
	public InterfaceFin Fin = null;
	public InterfaceLobby Lobby = null;
	public MediaPlayer musique;
	private double effet = 1;

	public final String INPUT_STYLE = "-fx-font: bold 2.5em 'Pristina'; -fx-text-fill: rgb(95, 95, 95); -fx-border-color: rgba(255, 246, 220, 0.8); -fx-border-width: 1px; -fx-border-radius: 8px;"
			+ "-fx-effect: dropshadow(three-pass-box, rgba(75,75,10,0.5), 10, 0, 0, 0); -fx-background-radius: 8px; -fx-background-color: rgba(238, 226, 190, 1);";
	public final String BOUTON_STYLE = "-fx-font: bold 2.5em 'Pristina'; -fx-text-fill: rgb(95, 95, 95); -fx-border-color: transparent;"
			+ "-fx-effect: dropshadow(three-pass-box, rgba(75,75,10,0.5), 10, 0, 0, 0); -fx-background-radius: 8px; -fx-background-color: rgba(238, 226, 190, 0.8);";
	public final String BOUTON_STYLE_SURVOL = "-fx-font: bold 2.5em 'Pristina'; -fx-text-fill: rgb(95, 95, 95); -fx-border-color: rgba(255, 246, 220, 0.8); -fx-border-width: 1px; -fx-border-radius: 8px;"
			+ "-fx-effect: dropshadow(three-pass-box, rgba(75,75,10,0.5), 10, 0, 0, 0); -fx-background-radius: 8px; -fx-background-color: rgba(238, 226, 190, 1);";
	public final String BOUTON_STYLE_WOOD = "-fx-font: bold 4em 'Pristina'; -fx-text-fill: rgb(95, 95, 95); -fx-base: rgba(196, 189, 167, 0.9);  -fx-border-color: transparent; -fx-pref-width: 350; -fx-pref-height: 100;"
			+ "-fx-effect: dropshadow(three-pass-box, rgba(75,75,10,0.5), 15, 0, 0, 0); -fx-background-radius: 8px; -fx-background-color: rgba(238, 226, 190, 0.8);";
	public final String BOUTON_STYLE_WOOD_SURVOL = "-fx-font: bold 4em 'Pristina'; -fx-text-fill: rgb(80, 80, 80); -fx-base: rgba(196, 189, 167, 0.9); -fx-border-color: rgba(255, 246, 220, 0.8); -fx-border-width: 1px; -fx-border-radius: 8px; -fx-pref-width: 350; -fx-pref-height: 100;"
			+ "-fx-effect: dropshadow(three-pass-box, rgba(75,75,10,0.5), 15, 0, 0, 0); -fx-background-radius: 8px; -fx-background-color: rgba(238, 226, 190, 1);";
	
	public GestionnaireInterface self = this;
	
	private boolean finManche = false;
	
	public Node UIParentID = null;
	public Node UIGrandParentID = null;
	
	protected Rectangle2D screenBounds = Screen.getPrimary().getBounds();
	private Scene scene = new Scene(root, screenBounds.getWidth(), screenBounds.getHeight());
	
	public LinkedHashMap<String, Pane> InterfaceMap = new LinkedHashMap<String, Pane>(); // store interface as Pane
	public ArrayList<InterfaceBase> InterfaceBaseList = new ArrayList<InterfaceBase>(); // store interfaces as InterfaceBase
	
//	public Properties texte; // text data
	HashMap<String,Properties> texteLangue = new HashMap<String,Properties>(); // list of text files
	String langueSelectionne;
	
	static HashMap<String,Image> Cartes = new HashMap<String,Image>();
//	public String PropertiesLocalisation = "./resources/textes/"; // text file location
	
	
	/**
	 * Cette m??thode permet de lancer l'interface graphique.
	 * Elle va initialiser tous les ??crans disponible et afficher le premier ??cran qui sera le menu principal.
	 * Le jeu sera mis direct en plein ??cran.
	 * 
	 * @param primaryStage Le composant qui va contenir toutes les sc??nes
	 * 
	 * @since 1.0
	 */
	
	public void start(Stage primaryStage) throws Exception {
		
		Cartes = ChargeCartes();
		//System.out.println(Cartes.get("agricole_1").toString());
		
		texteLangue.put("francais",readPropertiesFile("/textes/texte_fr.properties"));
		texteLangue.put("english",readPropertiesFile("/textes/texte_eng.properties"));
		langueSelectionne = "francais";

		//Properties texte = readPropertiesFile("/textes/texte_fr.properties"); // initialise
		//System.out.println(texte.getProperty("texte.titre"));
		//fichierTexte.getProperty(element.getValue())
		//System.out.println(texte.getProperty("bouton.regle"));
		
		//Code de base qui marche pour impl??menter la musique dans le jeu en le lan??ant sous eclipse
		
		/*
		Media sound = new Media(new File("Dossier-General/Programme-Principal/src/interfaces/resources/Musique/Menu.mp3").toURI().toString());
		musique = new MediaPlayer(sound);
		musique.setCycleCount(MediaPlayer.INDEFINITE);
		musique.setVolume(1);
		musique.play();
	*/
		
		String url = getClass().getResource("/Musique/Menu.mp3").toExternalForm();
		Media son = new Media(url);
		musique = new MediaPlayer(son);
		musique.setCycleCount(MediaPlayer.INDEFINITE);
		musique.setVolume(1);
		musique.play();
		

		
		InterfaceBase IMenu = new InterfaceMenu(this);
		InterfaceBase IParametres =  new InterfaceParametres(this);
		InterfaceBase ILocalCreerPartie = new InterfaceLocalCreerPartie(this);
		InterfaceBase IRegles = new InterfaceRegles(this);
		InterfaceBase IRejoindrePartie = new InterfaceRejoindrePartie(this);
		InterfaceBase IChargement = new InterfaceChargement(this);
		InterfaceBase ICredit = new InterfaceCredit(this);
		Jeux = new InterfaceJeu(this);
		Fin = new InterfaceFin(this);
		Lobby = new InterfaceLobby(this);
		
		InterfaceBaseList.add(IMenu);
		InterfaceBaseList.add(IParametres);
		InterfaceBaseList.add(ILocalCreerPartie);
		InterfaceBaseList.add(IRegles);
		InterfaceBaseList.add(ICredit);
		InterfaceBaseList.add(IChargement);
		InterfaceBaseList.add(IRejoindrePartie);
		InterfaceBaseList.add(Jeux);
		InterfaceBaseList.add(Fin);
		InterfaceBaseList.add(Lobby);
		
		InterfaceMap.put("menu", IMenu);
		InterfaceMap.put("credit", ICredit);
		InterfaceMap.put("parametres", IParametres);
		InterfaceMap.put("creerPartie", ILocalCreerPartie);
		InterfaceMap.put("jeu", Jeux );
		InterfaceMap.put("regles", IRegles);
		InterfaceMap.put("chargement", IChargement);
		InterfaceMap.put("rejoindre", IRejoindrePartie);
		InterfaceMap.put("finPartie", Fin);
		InterfaceMap.put("lobby", Lobby);
		
		//add instances of the interfaces in the root
		
		for (Map.Entry<String, Pane> mapElement : InterfaceMap.entrySet()) {
            root.getChildren().add(mapElement.getValue());
        }
		
		primaryStage.setScene(scene);
		primaryStage.setX((screenBounds.getWidth() - primaryStage.getWidth()) / 2);
		primaryStage.setY((screenBounds.getHeight() - primaryStage.getHeight()) / 4);
		primaryStage.setMaximized(true);
//		primaryStage.setResizable(false);
		primaryStage.setFullScreen(true);
		//primaryStage.setFullScreenExitHint(""); stop fullscreen message
		primaryStage.setTitle("De cape et d'epee"); //name of the stage
		
		for(int i=0; i < root.getChildren().size(); i++) { //set everyone invisible
			root.getChildren().get(i).setVisible(false);
		}
		
		//System.out.println(screenBounds.getWidth());
		//System.out.println(screenBounds.getHeight());
		
		afficherEcran(InterfaceMap.get("menu"));// show menu

		

		
		
		
		
		
		
		primaryStage.show();
		MainStage = primaryStage;
	}
	
	/**
	 * Cette m??thode lance le jeu ainsi que l'interface.
	 * 
	 * @param args ?.
	 * 
	 * @since 1.0
	 */
	
	public static void lancement(String[] args) {
		GestionnaireInterface.launch(args);
	}
	
	/**
	 * Cette m??thode permet d'afficher un ??cran.
	 * On l'utilisera surtout pour changer d'??cran lorsque l'utilisateur veut naviguer dans l'interface.
	 * Exemple : Menu -> Option
	 * 
	 * @param n L'??cran qu'on veut afficher.
	 * 
	 * @since 1.0
	 */
	
	public void afficherEcran(Node n) { // function used to switch Node visibility
		UIGrandParentID = UIParentID;
		UIParentID = ecranCourant;
		
		if (ecranCourant != null)
			ecranCourant.setVisible(false);
		n.setVisible(true);
		ecranCourant = n;
	}
	
	/**
	 * Cette m??thode permet de lancer un son
	 * On l'utilisera surtout lorsque l'utilisateur chnagera d'??cran
	 * 
	 * @since 1.0
	 */
	
    public void bruitInterface() {
    	String url = getClass().getResource("/Musique/Bruitage_lance.wav").toExternalForm();
    	Media son = new Media(url);
		MediaPlayer bruit = new MediaPlayer(son);
		bruit.setVolume(effet);
		bruit.play();
    }
    
    public void bruitCarte() {
    	String url = getClass().getResource("/Musique/Carte.wav").toExternalForm();
    	Media son = new Media(url);
		MediaPlayer bruit = new MediaPlayer(son);
		bruit.setVolume(effet);
		bruit.play();
    }
	
	/**
	 * Cette m??thode permet de jouer une partie.
	 * Elle appelle la fonction rafraichir ?? chaque fois qu'un joueur joue.
	 * Elle peut aussi mettre fin ?? une manche et ?? une partie.
	 * 
	 * 
	 * @since 1.0
	 */
	public void doitJouer() throws Exception {
		if(!verifManche(data) && estFinie == false) {
	    	if(data.getJoueurs()[data.getCurrentJoueur()] instanceof Bot) {
	    		
	    		data.getJoueurs()[data.getCurrentJoueur()].jouer(data, 0, 0);
	    		
	    		class TaskDelay extends TimerTask { // Timer pour voir la fin de la manche
					public void run() {
						
						Platform.runLater(() -> {
							try {
								
								bruitCarte();
					    		rafraichir(self);
					    		doitJouer();
							} catch (Exception e) {
								e.printStackTrace();
							}
						});
					}
	    		}
	    		Timer T = new Timer();
	    		TimerTask tache = new TaskDelay();
	    		T.schedule(tache, 250); // ------------------------------------------------------------------ delai bot
	    		/*
	    		data.getJoueurs()[data.getCurrentJoueur()].jouer(data, 0, 0);
	    		rafraichir(this);
	    		doitJouer();*/
	    	}
	    	rafraichir(this);
    	}
    	else {
    		if(!data.partieFinie()) {
	    		estFinie = true;
	    		finManche = true;
	        	data.retournerCarte();
	        	rafraichir(this);
				data.activerCartesARetardement();
	        	data.retournerCarte();
	        	
	    		
	        	class TaskDelay extends TimerTask { // Timer pour voir la fin de la manche
					public void run() {
						Platform.runLater(() -> {
							rafraichir(self);
				        	data.finDeManche();
				        	data.calculScoreJoueurs();
				        	//System.out.println(" ");
						});
					}
	    		}
	    		Timer T1 = new Timer();
	    		TimerTask tach1 = new TaskDelay();
	    		T1.schedule(tach1, 2000); // ------------------------------------------------------------------ delai affichage carte fin de manche
	        	
	    		class TaskDelay2 extends TimerTask { // Timer pour voir la fin de la manche
					public void run() {
						Platform.runLater(() -> {
							try {
								finManche = false;
								self.getData().setCurrentManche(self.getData().getCurrentManche()+1);
								rafraichir(self);
					    		estFinie = false;
					    		doitJouer();
							} catch (Exception e) {
								e.printStackTrace();
							}
						});
					}
	    		}
	    		Timer T2 = new Timer();
	    		TimerTask tach2 = new TaskDelay2();
	    		T2.schedule(tach2, 4000); // ------------------------------------------------------------------ delai fin de manche
    		}
    		else {
	    		estFinie = true;
	        	data.retournerCarte();
	        	rafraichir(this);
				data.activerCartesARetardement();
	        	data.retournerCarte();
	        	rafraichir(this);
	        	data.finDeManche();
	        	finManche = true;
	        	Jeux.EnPartie = false;
	        	//rafraichir(this);
    			//this.afficherEcran(InterfaceMap.get("finPartie"));
    			//Fin.afficherStats(data);
	        	class TaskDelay extends TimerTask { // Timer pour voir la fin de la manche
					public void run() {
						Platform.runLater(() -> {
							finManche = false;
							data.calculScoreJoueurs();
							self.afficherEcran(InterfaceMap.get("finPartie"));
			    			Fin.afficherStatsSolo(data);
						});
					}
	    		}
	        	estFinie = false;
	    		Timer T = new Timer();
	    		TimerTask tache = new TaskDelay();
	    		T.schedule(tache, 3000); // ------------------------------------------------------------------ delai fin de partie
    		}
    	}
	}
	
	/**
	 * Permet de rafraichir l'??cran du joueur afin d'avoir le r??sultat des actions des joueurs pendant la partie.
	 * 
	 * @param data Donn??e du jeu qui permettront de savoir o?? en est le jeu.
	 * @throws InterruptedException 
	 * 
	 * @since 1.0
	 */
	
    public void rafraichir(GestionnaireInterface GI) { // Rafraichissement de l'??cran courant
	    	for(int i=0; i<GI.getData().getPlateau().getColonnes().length; i++) {
	    		for(int j=0; j<GI.getData().getPlateau().getColonnes()[i].getCartesInfluences().length; j++) {
	    			
	    			Jeux.drawPartie(GI);
	    			
	    		}
	    	}
    }
    
	/**
	 * Permet de rafraichir l'??cran du plateau afin d'avoir le r??sultat des actions des joueurs pendant la partie.
	 * 
	 * @param data Donn??e du jeu qui permettront de savoir o?? en est le jeu.
	 * @throws InterruptedException 
	 * 
	 * @since 1.0
	 */
    
    // Mettre en place l'impl??mentation r??seau
    
//    public void rafraichirPlateau(GestionnaireInterface GI) { // Rafraichissement de l'??cran courant
//    	for(int i=0; i<GI.getData().getPlateau().getColonnes().length; i++) {
//    		for(int j=0; j<GI.getData().getPlateau().getColonnes()[i].getCartesInfluences().length; j++) {
//    			
//    			Plateau.drawPartie(GI);
//    			
//    		}
//    	}
//    }
    
    
    /**
     * Cette m??thode v??rifie qu'une carte peut ??tre jou??e.
     * 
     * @param data Donn??es de la partie actuelle.
     * 
     * @param carte Carte test?? pour ??tre jou??.
     * 
     * @param index le num??ro de colonne o?? la carte va ??tre jou??.
     * 
     * @return true si on peut la jouer, false si on peut pas la jouer.
     * 
     * @since 1.0
     */
    
    public boolean arbitre(Data data, CarteInfluence carte, int index) {
    	Colonne col = data.getPlateau().getColonnes()[index];
    	if(col.estPleine()) {
    		// Impossible de placer la carte -> statut erreur
    		return false;
    	}
    	else {
    		//verif si c'est bien a ce joueur de jouer
    		if(carte.getCouleur() != data.getJoueurs()[data.getCurrentTour()].getCouleur()) {
    			return false;
    		}
    		//verif si la carte n'est pas deja joue
    		//n'est pas utilis??? pour l'instant car on utilise plusieurs fois la meme carte (pour cette version)
//    		for(int i=0; i<col.getCartesInfluences().length; i++) {
//    			if(col.getCartesInfluences()[i] == carte) {
//    				return false;
//    			}
//    		}
    	}
    	return true;
    }
    
    /**
     * Cette m??thode v??rifie si la manche est termin??e.
     * 
     * @param data Donn??es de la partie actuelle.
     * 
     * @return true si la manche est finie, false si la manche n'est pas encore fini.
     * 
     * @since 1.0
     */
    
    public boolean verifManche(Data data) {
    	boolean verif = true;
    	for(int i = 0; i<data.getPlateau().getColonnes().length; i++) {
    		if(!data.getPlateau().getColonnes()[i].estPleine() && !data.getPlateau().getColonnes()[i].estFiniEtreRempli()) {
    			verif = false;
    		}
    	}
    	return verif;
    }
    
    /**
     * Cette m??thode permet de retourner les donn??es actuelles du jeu
     * 
     * @return les donn??es du jeu
     * 
     * @since 1.0
     */
    public Data getData() {
    	return data;
    }
    
    /**
     * Cette m??thode permet de d??finir les donn??es actuelles du jeu
     * 
     * @param data Donn??es actuelles du jeu
     */
    public void setData(Data data) {
    	GestionnaireInterface.data = data;
    }
    
    /**
     * Cette m??thode permet de charger les fichiers textuels .properties
     * 
     * @param filename l'emplacement de la ressource en fonction du classpath
     */
    public static Properties readPropertiesFile(String fileName) throws IOException {
	      InputStream fis = null;
	      Properties prop = null;
	      try {	 
	    	  ClassLoader loader = Thread.currentThread().getContextClassLoader();
	    	  //fis = loader.getResourceAsStream(fileName);
	    	  fis = GestionnaireInterface.class.getResourceAsStream(fileName);
	    	  //if (fis == null) fis = loader.getResourceAsStream("resources/" + fileName);
	    	  prop = new Properties();
	    	  prop.load(fis);
	      } catch(FileNotFoundException fnfe) {
	         fnfe.printStackTrace();
	      } catch(IOException ioe) {
	         ioe.printStackTrace();
	      } finally {
	         fis.close();
	      }
	      return prop;
	   }
    
    /**
     * Cette m??thode permet de charger les fichiers .png
     * 
     * @param filename l'emplacement de la ressource en fonction du classpath
     */
    
    public static Image readPngFile(String fileName) {
    	  double coeff = 1;
	      InputStream fis = null;
	      Image img = null;
	      fis = GestionnaireInterface.class.getResourceAsStream(fileName);
	      img = new Image(fis);
	      //img = new Image(GestionnaireInterface.class.getResourceAsStream(fileName),img.getWidth()/coeff,img.getHeight()/coeff,false,false);

	      try {
			fis.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	      return img;
	   }
    
    /**
     * Cette m??thode permet de charger toutes les images du programme
     * 
     * 
     */
    
    public HashMap<String,Image> ChargeCartes() {
    	HashMap<String,Image> result = new HashMap<String,Image>();
    	
    	for(int i = 1;i<=5;i++) { // charge cartes objectif
    		result.put("Agriculture_"+i,readPngFile("/sprites/classique/carteObjectif/agricole_"+i+".png"));
    		result.put("Combat_"+i,readPngFile("/sprites/classique/carteObjectif/militaire_"+i+".png"));
    		result.put("Religion_"+i,readPngFile("/sprites/classique/carteObjectif/religion_"+i+".png"));
    		result.put("Musique_"+i,readPngFile("/sprites/classique/carteObjectif/culture_"+i+".png"));
    		result.put("Commerce_"+i,readPngFile("/sprites/classique/carteObjectif/economie_"+i+".png"));
    		result.put("Alchimie_"+i,readPngFile("/sprites/classique/carteObjectif/science_"+i+".png"));
    	}
    	
    	/*
    	for(int i = 1;i<=6;i++) { //charge le dos des cartes
    		result.put("back_"+i,readPngFile("/sprites/classique/carteInfluence/back_"+i+".png"));
    	}*/
    	//result.put("back_1",readPngFile("/sprites/classique/carteInfluence/back_1.png"));
    	result.put("back_0xffff00ff",readPngFile("/sprites/classique/carteInfluence/back_1.png"));
    	result.put("back_0x800080ff",readPngFile("/sprites/classique/carteInfluence/back_2.png"));
    	result.put("back_0xfaebd7ff",readPngFile("/sprites/classique/carteInfluence/back_3.png"));
    	result.put("back_0x006400ff",readPngFile("/sprites/classique/carteInfluence/back_4.png"));
    	result.put("back_0xff0000ff",readPngFile("/sprites/classique/carteInfluence/back_5.png"));
    	result.put("back_0x0000ffff",readPngFile("/sprites/classique/carteInfluence/back_6.png"));
    	
    	
    	
    	//charge les cartes influence
    	result.put("Alchimiste",readPngFile("/sprites/classique/carteInfluence/alchimiste.png"));
    	result.put("Assassin",readPngFile("/sprites/classique/carteInfluence/assasin.png"));
    	result.put("Cape d???invisibilit??",readPngFile("/sprites/classique/carteInfluence/cape_d'invisibilite.png"));
    	result.put("Cardinal",readPngFile("/sprites/classique/carteInfluence/cardinal.png"));
    	result.put("Dragon",readPngFile("/sprites/classique/carteInfluence/dragon.png"));
    	result.put("Ecuyer",readPngFile("/sprites/classique/carteInfluence/ecuyer.png"));
    	result.put("Ermite",readPngFile("/sprites/classique/carteInfluence/ermite.png"));
    	result.put("Explorateur",readPngFile("/sprites/classique/carteInfluence/explorateur.png"));
    	result.put("Juliette",readPngFile("/sprites/classique/carteInfluence/juliette.png"));
    	result.put("Magicien",readPngFile("/sprites/classique/carteInfluence/magicien.png"));
    	result.put("Ma??tre d???armes",readPngFile("/sprites/classique/carteInfluence/maitre_d'armes.png"));
    	result.put("Marchand",readPngFile("/sprites/classique/carteInfluence/marchand.png"));
    	result.put("Mendiant",readPngFile("/sprites/classique/carteInfluence/mendiant.png"));
    	result.put("Petit G??ant",readPngFile("/sprites/classique/carteInfluence/petit_geant.png"));
    	result.put("Prince",readPngFile("/sprites/classique/carteInfluence/prince.png"));
    	result.put("Reine",readPngFile("/sprites/classique/carteInfluence/reine.png"));
    	result.put("Roi",readPngFile("/sprites/classique/carteInfluence/roi.png"));
    	result.put("Rom??o",readPngFile("/sprites/classique/carteInfluence/romeo.png"));
    	result.put("Seigneur",readPngFile("/sprites/classique/carteInfluence/seigneur.png"));
    	result.put("Sorci??re",readPngFile("/sprites/classique/carteInfluence/sorciere.png"));
    	result.put("Sosie",readPngFile("/sprites/classique/carteInfluence/sosie.png"));
    	result.put("Temp??te",readPngFile("/sprites/classique/carteInfluence/tempete.png"));
    	result.put("Traitre",readPngFile("/sprites/classique/carteInfluence/traitre.png"));
    	result.put("Trois Mousquetaires",readPngFile("/sprites/classique/carteInfluence/trois_mousquetaires.png"));
    	result.put("Troubadour",readPngFile("/sprites/classique/carteInfluence/troubadour.png"));
    	
    	return result;
    }
	
	public boolean getFinManche() {
		return finManche;
	}
	
	public double getEffet() {
		return effet;
	}

	public void setEffet(double effet) {
		this.effet = effet;
	}

	
}

