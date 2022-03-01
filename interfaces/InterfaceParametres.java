package interfaces;

import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

/**
 * Cette classe est une interface qui représente les paramètres.
 * 
 *   
 * @author S3T - G1
 * 
 * @since 1.0
 */
public class InterfaceParametres extends InterfaceBase {
    
//    private Joueur joueur;
    public GestionnaireInterface GI;
    
    AnchorPane pane = new AnchorPane();
    
    Button boutonRetour;
    Button boutonGraphique;
    Button boutonMusique;
    Button boutonSon;
    Button boutonCredit;
    
    Slider volumeMusique;
    
    Label titre;
    Label textCredit;
    Label textLangage;
    Label textSon;
    Label textGraphique;
    
    
    HBox HBTop;
    VBox VBRight;
    VBox VBLeft;
    
    Button boutonLangage;
    HBox HBLangue;
    Button boutonFR;
    Button boutonEN;
    Button boutonFenetre;
    
    private double ExSon = 0.0;
    private double ExEffet = 0.0;

	private Slider volumeEffet;
	/**
     *  Ce constructeur permet de creer tous les élements de l'interface, c'est-a-dire le titre "Paramètres", le bouton "retour", 
     *  le bouton "Paramètre Graphiques", le bouton "Paramètre Musicaux", le bouton "Paramètres Sonores", le bouton "Theme", le bouton "Langue".
     *  
     * 
     * @param gi le gestionnaire d'interface permettra de relier cette interface aux autres pour qu'elle puisse communiquer ensemble
     * 
     * @since 1.0
     */
    public InterfaceParametres(GestionnaireInterface gi) { 
		super();
		GI = gi;
		this.dessineInterface(GI);
    }	
	
    /**
     *  Dessine l'interface.
     * 
     * @param gi le gestionnaire d'interface permettra de dessiner l'interface dans la langue séléctionné.
     * 
     * @since 1.0
     */
    
	public void dessineInterface(GestionnaireInterface gi) {



		 
	        
		// fond de jeu
        this.setBackground(new Background(new BackgroundFill(Color.MOCCASIN,CornerRadii.EMPTY,null)));
		
        HBTop = new HBox();
		VBRight = new VBox();
		VBLeft = new VBox();
		
		boutonRetour = new Button(GI.texteLangue.get(GI.langueSelectionne).getProperty("bouton.retour"));
		boutonRetour.setFont(Font.font("Comic Sans MS", 20));
		boutonRetour.setPrefWidth(GI.screenBounds.getWidth()*0.08);
		boutonRetour.setOnAction(e -> { 
			GI.bruitInterface();
			GI.afficherEcran(GI.UIParentID);});
		
		textGraphique = new Label(GI.texteLangue.get(GI.langueSelectionne).getProperty("bouton.graphique")); // ---------------------------------------------------------------------------------------------------------------------------------------
		textGraphique.setStyle("-fx-font: normal bold 5em 'Pristina' ");
		
		Button boutonFenetre = new Button(GI.texteLangue.get(GI.langueSelectionne).getProperty("bouton.fenetre"));
		boutonFenetre.setFont(Font.font("Comic Sans MS", 20));
		boutonFenetre.setOnAction(e -> {
			GI.bruitInterface();
			GI.MainStage.setFullScreen(!GI.MainStage.isFullScreen());});
		
		//------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
		
		textSon = new Label(GI.texteLangue.get(GI.langueSelectionne).getProperty("bouton.son")); // ---------------------------------------------------------------------------------------------------------------------------------------
		textSon.setStyle("-fx-font: normal bold 5em 'Pristina' ");
		
  	  VBox VBMusique = new VBox();
  	  VBMusique.setAlignment(Pos.CENTER_LEFT);
  	  
  	  //---------------------------SLIDER MUSIQUE------------------------
  	  
  	  Label labelMusique = new Label(GI.texteLangue.get(GI.langueSelectionne).getProperty("Label.Musique"));
  	  labelMusique.setAlignment(Pos.CENTER);
  	  
  	  volumeMusique = new Slider();
  	  
  	  CheckBox sonMusique = new CheckBox(GI.texteLangue.get(GI.langueSelectionne).getProperty("CheckBox.couperSon"));
  	  sonMusique.setAlignment(Pos.CENTER);
  	  
  	  volumeMusique.setOrientation(Orientation.HORIZONTAL) ;
  	  volumeMusique.setPrefWidth(250);
  	  volumeMusique.setShowTickMarks(true);
  	  volumeMusique.setShowTickLabels(true);
  	  volumeMusique.setMajorTickUnit(10);
  	  volumeMusique.setMinorTickCount(5);
  	  volumeMusique.setBlockIncrement(1);
  	  volumeMusique.setValue(GI.musique.getVolume()*100);
  	  volumeMusique.valueProperty().addListener(event -> {
  		  if(GI.musique.isMute() && !(volumeMusique.getValue() == 0)) {
  			  GI.musique.setMute(false);
  		  }
  		  else if(GI.musique.isMute() || volumeMusique.getValue() == 0) {
  			  sonMusique.setSelected(true);
  		  }
  		  else {
  			  sonMusique.setSelected(false);
  		  }
  	  	GI.musique.setVolume(volumeMusique.getValue()/100);});
  	  sonMusique.setOnAction(e -> musique(sonMusique.isSelected()));
  	  
  	  //------------------------SLIDER EFFET--------------------
  	  
  	  Label labelEffet = new Label(GI.texteLangue.get(GI.langueSelectionne).getProperty("Label.Effet"));
	  labelMusique.setAlignment(Pos.CENTER);
  	  
  	  volumeEffet = new Slider();
  	  
  	  CheckBox sonEffet = new CheckBox(GI.texteLangue.get(GI.langueSelectionne).getProperty("CheckBox.couperSon"));
	  sonEffet.setAlignment(Pos.CENTER);
  	  
	  volumeEffet.setOrientation(Orientation.HORIZONTAL) ;
	  volumeEffet.setPrefWidth(250);
	  volumeEffet.setShowTickMarks(true);
	  volumeEffet.setShowTickLabels(true);
	  volumeEffet.setMajorTickUnit(10);
	  volumeEffet.setMinorTickCount(5);
	  volumeEffet.setBlockIncrement(1);
	  volumeEffet.setValue(GI.getEffet()*100);
	  volumeEffet.valueProperty().addListener(event -> {
		  if(GI.getEffet() == 0 || volumeEffet.getValue() == 0) {
  			  sonEffet.setSelected(true);
  		  }
  		  else {
  			  sonEffet.setSelected(false);
  		  }
  	  	GI.setEffet(volumeEffet.getValue()/100);});
  	  sonEffet.setOnAction(e -> effet(sonEffet.isSelected()));
  	  
  	  VBMusique.getChildren().addAll(labelMusique, volumeMusique, sonMusique, labelEffet, volumeEffet, sonEffet);
		
		//------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
		
		textCredit = new Label(GI.texteLangue.get(GI.langueSelectionne).getProperty("bouton.credit")); // ---------------------------------------------------------------------------------------------------------------------------------------
		textCredit.setStyle("-fx-font: normal bold 5em 'Pristina' ");
		
		boutonCredit = new Button(GI.texteLangue.get(GI.langueSelectionne).getProperty("bouton.credit")); // -------------------------------------------------------------------------------------------------------------------------------------
		boutonCredit.setFont(Font.font("Comic Sans MS", 20));
		boutonCredit.setPrefWidth(GI.screenBounds.getWidth()*0.156);
		boutonCredit.setOnAction(e -> {
			GI.bruitInterface();
			GI.afficherEcran(GI.InterfaceMap.get("credit"));});
		//------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
		
		textLangage = new Label(GI.texteLangue.get(GI.langueSelectionne).getProperty("bouton.langue")); // ---------------------------------------------------------------------------------------------------------------------------------------
		textLangage.setStyle("-fx-font: normal bold 5em 'Pristina' ");
		
		
		
		HBLangue = new HBox();
		HBLangue.setAlignment(Pos.CENTER_LEFT);
		
		boutonFR = new Button("Français");
		boutonFR.setFont(Font.font("Comic Sans MS", 20));
		boutonFR.setOnAction(e -> {
			francais();
			GI.bruitInterface();
		});
		boutonFR.setPrefWidth(150);
		
		boutonEN = new Button("English");
		boutonEN.setFont(Font.font("Comic Sans MS", 20));
		boutonEN.setOnAction(e -> {
			GI.bruitInterface();
			english();
		;});
		boutonEN.setPrefWidth(150);
		
		HBLangue.getChildren().addAll(boutonFR, boutonEN);
		
		//------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
		titre = new Label(GI.texteLangue.get(GI.langueSelectionne).getProperty("texte.parametre")); // ---------------------------------------------------------------------------------------------------------------------------------------
		titre.setStyle("-fx-font: normal bold 10em 'Pristina' ");
		
		titre.setAlignment(Pos.CENTER);
		pane.setTopAnchor(titre, 20d);
		pane.setRightAnchor(titre, GI.screenBounds.getWidth()*0.38);
		pane.setLeftAnchor(titre, GI.screenBounds.getWidth()*0.38);
		
		//------------------Conteneur bouton---------------------------------------------------------
				pane.setTopAnchor(textGraphique, 300d);
				pane.setLeftAnchor(textGraphique,100d);
				
				pane.setTopAnchor(boutonFenetre, 330d);
				pane.setLeftAnchor(boutonFenetre,700d);
				

				pane.setTopAnchor(textSon, 450d);
				pane.setLeftAnchor(textSon, 100d);
		
				pane.setTopAnchor(VBMusique, 480d);
				pane.setLeftAnchor(VBMusique, 700d);
		
		
				pane.setTopAnchor(textLangage, 600d);
				pane.setLeftAnchor(textLangage, 100d);
				
				pane.setTopAnchor(HBLangue, 630d);
				pane.setLeftAnchor(HBLangue, 700d);
				
				

				
				
				pane.setTopAnchor(textCredit,750d);
				pane.setLeftAnchor(textCredit, 100d);
				
				pane.setTopAnchor(boutonCredit, 780d);
				pane.setLeftAnchor(boutonCredit, 700d);
									
		
		//------------------Conteneur titre---------------------------------------------------------
		
		titre.setAlignment(Pos.CENTER);
		
		//------------------Conteneur bouton retour------------------------------------------------

		
		pane.setTopAnchor(boutonRetour, 20d);
		pane.setRightAnchor(boutonRetour, 20d);
		
		
		pane.setPrefSize(GI.screenBounds.getWidth(), GI.screenBounds.getHeight());
		pane.setBackground(new Background(new BackgroundFill(Color.MOCCASIN,CornerRadii.EMPTY,null)));
		

		pane.getChildren().setAll(titre,boutonRetour,textGraphique,boutonFenetre,textSon,VBMusique,textLangage,HBLangue,textCredit,boutonCredit);
		this.setCenter(pane);
		
		
	}
    
    
    /**
     * Affiche le bloc des paramètres linguistiques.
     * 
     * @since 1.0
     */
    
    public void langue() {
		HBLangue = new HBox();
		HBLangue.setAlignment(Pos.CENTER_LEFT);
		
		boutonFR = new Button("Français");
		boutonFR.setFont(Font.font("Comic Sans MS", 20));
		boutonFR.setOnAction(e -> francais());
		boutonFR.setPrefWidth(150);
		
		boutonEN = new Button("English");
		boutonEN.setFont(Font.font("Comic Sans MS", 20));
		boutonEN.setOnAction(e -> english());
		boutonEN.setPrefWidth(150);
		
		HBLangue.getChildren().addAll(boutonFR, boutonEN);
		//this.setCenter(HBLangue);
		pane.getChildren().add(HBLangue);
		pane.setTopAnchor(HBLangue,725d);
		pane.setRightAnchor(HBLangue, 500d);
    }
    
    /**
     * Méthode qui définit la langue du jeu en français.
     * 
     * @since 1.0
     */    
    
    public void francais() {
    	if(this.GI.langueSelectionne!="francais") {
    		this.GI.langueSelectionne = "francais";
    		for(InterfaceBase i : GI.InterfaceBaseList)
    			i.dessineInterface(GI);
    		if(GI.Jeux.EnPartie) {
    			GI.Jeux.drawPartie(GI);
    		}
    	}
    }
    
    /**
     * Méthode qui définit la langue du jeu en anglais.
     * 
     * @since 1.0
     */
    
    public void english() {
    	if(this.GI.langueSelectionne!="english") {
    		this.GI.langueSelectionne = "english";
    		for(InterfaceBase i : GI.InterfaceBaseList)
    			i.dessineInterface(GI);
    		if(GI.Jeux.EnPartie) {
    			GI.Jeux.drawPartie(GI);
    		}
    	}
    }
    /**
     * Affiche le bloc des paramètres sonores.
     * 
     * @since 1.0
     */
    
    public void son() {
    	  VBox VBMusique = new VBox();
    	  VBMusique.setAlignment(Pos.CENTER_LEFT);
    	  
    	  Label labelMusique = new Label(GI.texteLangue.get(GI.langueSelectionne).getProperty("Label.Musique"));
    	  labelMusique.setAlignment(Pos.CENTER);
    	  
    	  volumeMusique = new Slider();
    	  
    	  CheckBox son = new CheckBox(GI.texteLangue.get(GI.langueSelectionne).getProperty("CheckBox.couperSon"));
    	  son.setAlignment(Pos.CENTER);
    	  
    	  volumeMusique.setOrientation(Orientation.HORIZONTAL) ;
    	  volumeMusique.setPrefWidth(250);
    	  volumeMusique.setShowTickMarks(true);
    	  volumeMusique.setShowTickLabels(true);
    	  volumeMusique.setMajorTickUnit(10);
    	  volumeMusique.setMinorTickCount(5);
    	  volumeMusique.setBlockIncrement(1);
    	  volumeMusique.setValue(GI.musique.getVolume()*100);
    	  volumeMusique.valueProperty().addListener(event -> {
    		  if(GI.musique.isMute() && !(volumeMusique.getValue() == 0)) {
    			  GI.musique.setMute(false);
    		  }
    		  else if(GI.musique.isMute() || volumeMusique.getValue() == 0) {
    			  son.setSelected(true);
    		  }
    		  else {
    			  son.setSelected(false);
    		  }
    	  	GI.musique.setVolume(volumeMusique.getValue()/100);});
    	  son.setOnAction(e -> musique(son.isSelected()));
    	  
    	  VBMusique.getChildren().addAll(labelMusique, volumeMusique, son);
//    	  this.setCenter(VBMusique);
    	  
    	  
    }
    
    private void musique(boolean valeur) {
    	if(valeur) {
    		this.ExSon = GI.musique.getVolume();
    		GI.musique.setMute(valeur);
    		volumeMusique.setValue(0);
    	}
    	else {
    		if(GI.musique.isMute()) {
    			GI.musique.setMute(valeur);
    			volumeMusique.setValue(ExSon*100);
    		}
    	}
	}
    
    private void effet(boolean valeur) {
    	if(valeur) {
    		this.ExEffet = GI.getEffet();
    		GI.setEffet(0);
    		volumeEffet.setValue(0);
    	}
    	else {
    		if(GI.getEffet() == 0) {
    			GI.setEffet(ExEffet);
    			volumeEffet.setValue(ExSon*100);
    		}
    	}
	}

	/**
     * Affiche le bloc du choix des thèmes de cartes.
     * 
     * @since 1.0
     */
    
    public void theme() {
    	/*
    	  HBox HBTheme = new HBox();
    	  HBTheme.setAlignment(Pos.CENTER_LEFT);
    	  
    	  Button boutonCreditModif = new Button("Theme Modifié");
    	  boutonCreditModif.setFont(Font.font("Comic Sans MS", 20);
    	  boutonCreditModif.setPrefWidth(150);
    	  
    	  Button boutonCreditBase = new Button("Theme de base");
    	  boutonCreditBase.setFont(Font.font("Comic Sans MS", 20);
    	  boutonCreditBase.setPrefWidth(150);
    	  
    	  HBTheme.getChildren().addAll(boutonThmeBase, boutonCreditModif);
    	  this.setCenter(HBTheme);*/
    }
}

