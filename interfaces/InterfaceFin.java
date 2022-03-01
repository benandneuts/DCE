package interfaces;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;

import cartes.CarteObjectif;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Screen;
import joueur.Joueur;
import moteur.Data;

/**
 * Cette classe permet de créer une partie.
 * C'est sur cette interface qu'on peut choisir le nombre de joueurs, rentrer son pseudo et lancer la partie.
 * 
 * @author S3T - G1
 * 
 * @since 1.0
 */

public class InterfaceFin extends InterfaceBase {
	
	private GestionnaireInterface GI;
	private boolean Solo = true;
	
	Label titre;
	Label pseudo;
	Button boutonMenu;
	Button boutonVue;
	Button boutonQuitter;
	Button boutonRejouerLigne;
	
	HBox HBHaut;
	HBox HBMilieu;
	HBox HBBas;
	
	VBox VBDroite;
	VBox VBGauche;

	VBox VBJoueur[];	
	HBox HBClassement[];
	HBox HBPseudo[];
	HBox HBCouleur[];
	HBox HBNbCarte[];
	HBox HBScore[];
	
	
	/**
     *  Ce constructeur permet de créer tous les éléments de l'interface, c'est-à-dire le titre
     *  la glissière, la zone d'insertion de texte, le bouton retour et le bouton pour lancer la partie.
     * 
     * @param gi Le gestionnaire d'interface permettra de relier cette interface aux autres pour qu'elle puisse communiquer ensemble.
     * 
     * @since 1.0
     */
	public InterfaceFin(GestionnaireInterface gi){
		super();
		GI = gi;
		dessineInterface(GI);
	}
	
	/**
     *  Dessine l'interface.
     * 
     * @param gi le gestionnaire d'interface permettra de dessiner l'interface dans la langue séléctionné.
     * 
     * @since 1.0
     */
	
	public void dessineInterface(GestionnaireInterface gi) {
		this.setBackground(new Background(new BackgroundFill(Color.MOCCASIN,CornerRadii.EMPTY,null)));
		
		titre = new Label("Score");
		titre.setFont(Font.font("Pristina", FontWeight.BOLD,80));
		
		boutonMenu = new Button(gi.texteLangue.get(gi.langueSelectionne).getProperty("bouton.menu"));
		boutonMenu.setOnAction(e -> gi.afficherEcran(gi.InterfaceMap.get("menu")));
		boutonMenu.setPrefWidth(gi.screenBounds.getWidth()*0.08);
        boutonMenu.setStyle(GI.BOUTON_STYLE);
        boutonMenu.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
            	boutonMenu.setStyle(GI.BOUTON_STYLE_SURVOL);
            }
        });
        boutonMenu.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
            	boutonMenu.setStyle(GI.BOUTON_STYLE);
            }
        });
		
		AnchorPane elementTop= new AnchorPane(titre, boutonMenu);
		
		elementTop.setPrefSize(gi.screenBounds.getWidth(), (gi.screenBounds.getHeight()/9)*1);
		
		AnchorPane.setLeftAnchor(titre,gi.screenBounds.getWidth()/2 - titre.getBoundsInParent().getWidth());
        AnchorPane.setTopAnchor(titre, 20.0);
		
		AnchorPane.setRightAnchor(boutonMenu,20.0);
        AnchorPane.setTopAnchor(boutonMenu, 20.0);
        
		this.setTop(elementTop);
		
		boutonVue = new Button(gi.texteLangue.get(gi.langueSelectionne).getProperty("bouton.switch"));
		boutonVue.setOnAction(e -> {
			if(Solo) {
				afficherStats(gi.getData()); 
				Solo = !Solo;
			} else {
				afficherStatsSolo(gi.getData());
				Solo = !Solo;
			}
		});
		boutonVue.setStyle(GI.BOUTON_STYLE);
		boutonVue.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
            	boutonVue.setStyle(GI.BOUTON_STYLE_SURVOL);
            }
        });
		boutonVue.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
            	boutonVue.setStyle(GI.BOUTON_STYLE);
            }
        });
		
		boutonQuitter = new Button(gi.texteLangue.get(gi.langueSelectionne).getProperty("bouton.quitter"));
		boutonQuitter.setOnAction(e ->{
										Platform.exit();
										gi.Fin = new InterfaceFin(gi);
										gi.Jeux = new InterfaceJeu(gi);
										gi.Lobby = new InterfaceLobby(gi);
		});
		boutonQuitter.setPrefWidth(gi.screenBounds.getWidth()*0.08);
		boutonQuitter.setStyle(GI.BOUTON_STYLE);
		boutonQuitter.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
            	boutonQuitter.setStyle(GI.BOUTON_STYLE_SURVOL);
            }
        });
		boutonQuitter.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
            	boutonQuitter.setStyle(GI.BOUTON_STYLE);
            }
        });
		
/*		boutonRejouerLigne = new Button("Rejouer en Ligne");
		boutonRejouerLigne.setOnAction(e -> gi.afficherEcran(gi.InterfaceMap.get("creerPartieEnLigne")));
		boutonRejouerLigne.setFont(Font.font("Comic Sans MS", 20));
	*/	
		VBox coteDroit  = new VBox(boutonQuitter,boutonVue/*, boutonRejouerLigne*/);
		coteDroit.setPrefSize(gi.screenBounds.getWidth()/7, gi.screenBounds.getHeight() - (gi.screenBounds.getHeight()/9));
		coteDroit.setAlignment(Pos.CENTER);
		coteDroit.setSpacing(10);
		
		Pane coteGauche = new Pane();
		coteGauche.setPrefSize(gi.screenBounds.getWidth()/7, gi.screenBounds.getHeight() - (gi.screenBounds.getHeight()/9));
		
		
		this.setRight(coteDroit);
		this.setLeft(coteGauche);
	}
	/**
     * Cette fonction permet d'afficher les statistiques d'un joueur
     * 
     * @param J le joueur dont on souhaite voir les statistiques
     * 
     * @since 1.0
     */
	
	public VBox afficherResultatJoueur(Joueur J, int Max) {
		
		Rectangle2D screenBounds = Screen.getPrimary().getBounds();
		int VolumeCoeff = 7;
		int taillePolice = 24;
		
		VBox StatsJoueur = new VBox();
		StatsJoueur.setAlignment(Pos.CENTER);
		StatsJoueur.setPrefSize(160, (screenBounds.getHeight()/9)*6);
		
		// necessaire pour l'équilibrage de l'interface
		Pane espaceVolume = new Pane(); 
		espaceVolume.setPrefSize(USE_COMPUTED_SIZE,Max*VolumeCoeff - J.getScore()*VolumeCoeff);
		
		//score of the player
		Text textScore = new Text(J.getScore()+""); 
		textScore.setFont(new Font("Comic Sans MS", taillePolice));
		
		// visual representation of the score of the player
		Pane scoreVolume = new Pane(); 
		scoreVolume.setBackground(new Background(new BackgroundFill(J.getCouleur(),CornerRadii.EMPTY,null)));
		scoreVolume.setPrefSize(USE_COMPUTED_SIZE, J.getScore()*VolumeCoeff);
		
		//name of the player
		Text textName = new Text(J.getPseudo()+""); 
		textName.setFont(new Font("Comic Sans MS", taillePolice));
		
		//number of card of the player
		Text textcarteNB = new Text(J.getObjectif().size()+""); //name of the player
		textcarteNB.setFont(new Font("Comic Sans MS", taillePolice));
		
		StatsJoueur.getChildren().addAll(espaceVolume,textScore,scoreVolume,textName,textcarteNB);
		return StatsJoueur;
	}
	
	/**
     * Cette procédure permet d'afficher les statistiques des joueurs en fin de partie
     * 
     * @param data les données d'une partie
     * 
     * @since 1.0
     */
	
	public void afficherStats(Data data) {
		
		Rectangle2D screenBounds = Screen.getPrimary().getBounds();
		
		HashMap<Integer, Joueur> scores = new HashMap<>();
		for(Joueur j : data.getJoueurs())
			scores.put(j.getScore(), j);
		TreeMap<Integer, Joueur> triScores = new TreeMap<>(scores);
		ArrayList<Joueur> triJoueur = new ArrayList<>(triScores.values());
		//System.out.println(triScores);
		
		HBMilieu = new HBox();
		HBMilieu.setAlignment(Pos.CENTER);
		HBMilieu.setSpacing(20);
		HBMilieu.setMinWidth(screenBounds.getWidth() - (screenBounds.getWidth()/7)*2);
		HBMilieu.setMinHeight(screenBounds.getHeight()-screenBounds.getHeight()/9);
		//HBMilieu.setPrefSize(screenBounds.getWidth(), screenBounds.getHeight()-screenBounds.getHeight()*9);
		
		int Max = triJoueur.get(triJoueur.size()-1).getScore();
		
		//ajoute la colonne de chaque joueur 
		/*
		for(Joueur j : triJoueur) {
			HBMilieu.getChildren().add(afficherResultatJoueur(j,Max));
		}*/
		
		for(int j = triJoueur.size()-1;j>=0;j--)
			HBMilieu.getChildren().add(afficherResultatJoueur(triJoueur.get(j),Max));

		this.setCenter(HBMilieu);
	}
	
	// ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	
	/**
     * Cette procédure permet d'afficher les statistiques d'un joueur en fin de partie
     * 
     * @param data les données d'une partie
     * 
     * @since 1.0
     */
	
	
	public void afficherStatsSolo(Data data) {
		
		VBox VBMid = new VBox();
		
		HBMilieu = new HBox();
		HBMilieu.setMinSize(1, 1);
		HBMilieu.setAlignment(Pos.TOP_CENTER);
		HBMilieu.setSpacing(10);
		
		StackPane p = new StackPane();
		p.setMinHeight(250.0);
		Text tScore = new Text(GI.texteLangue.get(GI.langueSelectionne).getProperty("texte.score")+" : "+GI.getData().getJoueurs()[0].getScore());
		tScore.setFont(new Font("Comic Sans MS", 28));
		tScore.setTextAlignment(TextAlignment.CENTER);
		p.getChildren().add(tScore);
		
		VBMid.getChildren().addAll(p,HBMilieu);
		
		ArrayList<VBox> listVB = new ArrayList<VBox>();
		ArrayList<Text> listLabel = new ArrayList<Text>();
		ArrayList<Integer> listScore = new ArrayList<Integer>();
		
		for(int i =0 ;i<6;i++) {
			VBox v = new VBox();
			v.setMinWidth(110);
			v.setAlignment(Pos.TOP_CENTER);
			listVB.add(v);
			
			Text t = new Text(GI.texteLangue.get(GI.langueSelectionne).getProperty("texte.manche")+" "+(i+1)+" : ");
			t.setFont(new Font("Comic Sans MS", 24));
			t.setTextAlignment(TextAlignment.CENTER);
			
			listLabel.add(t);
			listVB.get(listVB.size()-1).getChildren().add(listLabel.get(i));
			listScore.add(0);
			HBMilieu.getChildren().add(listVB.get(listVB.size()-1));
			
		}
		
		for(CarteObjectif CO : data.getJoueurs()[data.getIndexJoueurParCouleur(data.getMaster().getCouleur())].getObjectif()) {
			listScore.set(CO.getManche(),CO.getValeur() + listScore.get(CO.getManche()));
			//listLabel.get(CO.getManche()).setText(GI.texteLangue.get(GI.langueSelectionne).getProperty("texte.manche")+" "+CO.getManche()+": "+CO.getValeur());
			listVB.get(CO.getManche()).getChildren().add(new SpriteCarteObjectif(CO, null, GI));
		}
		for(int i =0 ;i<6;i++) {
			listLabel.get(i).setText(listLabel.get(i).getText()+listScore.get(i));
		}
		
		this.setCenter(VBMid);
	}
}
