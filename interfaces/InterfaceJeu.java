package interfaces;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Map.Entry;

import cartes.CarteInfluence;
import cartes.CarteObjectif;
import javafx.animation.FadeTransition;
import javafx.animation.Animation.Status;
import javafx.animation.Interpolator;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderWidths;
import javafx.scene.input.DragEvent;
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
import javafx.util.Duration;
import joueur.Joueur;
import moteur.Data;

/**
 * Cette classe est l'interface du jeu.
 * C'est sur cette interface que toutes actions entre les joueurs et le systeme vont se passer.
 * 
 * @author S3T - G1
 * 
 * @since 1.0
 */
public class InterfaceJeu extends InterfaceBase {
    
	/**
     *  Ce constructeur permet de créer tous les éléments de l'interface, c'est-à-dire le bouton pour quitter, le bouton pour voir 
     *  les règles, le bouton pour aller le texte pour voir qui doit jouer, la grille des cartes du plateau et la main du joueur.
     * 
     * @param gi Le gestionnaire d'interface permettra de relier cette interface aux autres pour qu'elle puisse communiquer ensemble.
     * 
     * @since 1.0
     */
	
	private double LargeurCote;
	private VignetteSpriteCarteInfluence VSCI = new VignetteSpriteCarteInfluence();
	// liste des cartes objectifs du joueur
    private VBox cartesObjectifPossede = new VBox();
    public boolean EnPartie = false;
    
    private int selfID = 0;
	
    public InterfaceJeu(GestionnaireInterface GI) {
    	dessineInterface(GI);
    }
    
    /**
     *  Dessine l'interface.
     * 
     * @param gi le gestionnaire d'interface permettra de dessiner l'interface dans la langue séléctionné.
     * 
     * @since 1.0
     */
    
	public void dessineInterface(GestionnaireInterface GI) {
		//taille des cotés proportionnelle à la taille de l'écran
    	LargeurCote = GI.screenBounds.getWidth()/5;
    	
    	// fond de jeu
        this.setBackground(new Background(new BackgroundFill(Color.BURLYWOOD,CornerRadii.EMPTY,null)));
    	
        // bouton quitter
        
        Button boutonQuitter = new Button(GI.texteLangue.get(GI.langueSelectionne).getProperty("bouton.quitter"));
        boutonQuitter.setFont(Font.font("Comic Sans MS", 20));
        boutonQuitter.setOnAction(e -> {
        	GI.bruitInterface();
        	Platform.exit();
        });
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
        
    	// bouton règle 
        
        Button BoutonRegle = new Button(GI.texteLangue.get(GI.langueSelectionne).getProperty("bouton.regle"));  
        BoutonRegle.setFont(Font.font("Comic Sans MS", 20));
        BoutonRegle.setOnAction(e -> {
        	GI.afficherEcran(GI.InterfaceMap.get("regles"));
        	GI.bruitInterface();
        });
        BoutonRegle.setStyle(GI.BOUTON_STYLE);
		BoutonRegle.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
            	BoutonRegle.setStyle(GI.BOUTON_STYLE_SURVOL);
            }
        });
		BoutonRegle.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
            	BoutonRegle.setStyle(GI.BOUTON_STYLE);
            }
        });
        // Bouton option
        
        Button BoutonOption = new Button(GI.texteLangue.get(GI.langueSelectionne).getProperty("bouton.parametres"));
        BoutonOption.setFont(Font.font("Comic Sans MS", 20));
        BoutonOption.setOnAction(e -> {
        	GI.afficherEcran(GI.InterfaceMap.get("parametres"));
        	GI.bruitInterface();
        });
		BoutonOption.setStyle(GI.BOUTON_STYLE);
		BoutonOption.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
            	BoutonOption.setStyle(GI.BOUTON_STYLE_SURVOL);
            }
        });
		BoutonOption.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
            	BoutonOption.setStyle(GI.BOUTON_STYLE);
            }
        });
        
        // met tout le monde dans des boites
        HBox HBRegleOption = new HBox(BoutonRegle,BoutonOption);
        HBRegleOption.setSpacing(10);
        
        // coté droit de l'écran
        AnchorPane coteDroit= new AnchorPane(boutonQuitter, HBRegleOption, cartesObjectifPossede); 
        
        //position boutonQuiter
        AnchorPane.setRightAnchor(boutonQuitter,20.0);
        AnchorPane.setTopAnchor(boutonQuitter, 20.0);
        
        //position BoutonRegle et BoutonOption
        AnchorPane.setBottomAnchor(HBRegleOption, 20.0);
        AnchorPane.setRightAnchor(HBRegleOption, 20.0);
        
        AnchorPane.setBottomAnchor(cartesObjectifPossede, GI.screenBounds.getHeight()/2 + cartesObjectifPossede.getBoundsInParent().getHeight()/2);
        AnchorPane.setRightAnchor(cartesObjectifPossede, 20.0);
        cartesObjectifPossede.setSpacing(-40);
        
        // délimitation de coteDroit
        coteDroit.setPrefSize(LargeurCote, GI.screenBounds.getHeight());
        
    	this.setRight(coteDroit);
    	
    	InterfaceJeu self = this;
    	
    	// catch mouseEvent
    	this.addEventFilter(MouseEvent.DRAG_DETECTED , new EventHandler<MouseEvent>() {
    	    @Override
    	    public void handle(MouseEvent mouseEvent) {
    	    	self.startFullDrag();
    	    }
    	});
           	
	}
    
	/**
     * Cette méthode permet de dessiner la grille de la partie pour jouer.
     * 
     * 
     * @param gi Le gestionnaire d'interface permettra de relier cette interface aux autres pour qu'elle puisse communiquer ensemble.
     * 
     * @since 1.0
     */
    
    public void drawPartie(GestionnaireInterface GI) {
    	EnPartie = true;
    	selfID = GI.getData().getIndexJoueurParCouleur(GI.getData().getMaster().getCouleur());
    	VBox v = new VBox();
    	v.setBackground(new Background(new BackgroundFill(Color.BURLYWOOD,CornerRadii.EMPTY,null)));
    	v.setAlignment(Pos.TOP_CENTER);
    	v.setPrefSize(GI.screenBounds.getWidth()-LargeurCote*2, GI.screenBounds.getHeight());
    	
    	HBox HC = drawColonne(GI);
    	HBox HM = drawMain(GI);
    	Label TexteJoueur = drawTexteJoueur(GI);
    	
    	Label TexteManche = drawManche(GI);
    	Label TexteScore = drawScore(GI);
    	
    	drawCarteObjectifGagne(GI);
    	
    	//affichage du cot� gauche de l'�cran
    	AnchorPane coteGauche= new AnchorPane(TexteJoueur,TexteScore,VSCI, TexteManche); 
    	
    	AnchorPane.setTopAnchor(TexteJoueur, 20.0);
    	AnchorPane.setLeftAnchor(TexteJoueur, 20.0);
    	
    	AnchorPane.setTopAnchor(TexteManche,80.0);
    	AnchorPane.setLeftAnchor(TexteManche,20.0);
    	
    	AnchorPane.setTopAnchor(TexteScore,110.0);
    	AnchorPane.setLeftAnchor(TexteScore,20.0);
    	
    	AnchorPane.setBottomAnchor(VSCI,20.0);
    	AnchorPane.setLeftAnchor(VSCI,20.0);
    	
    	
    	
    	coteGauche.setPrefSize(LargeurCote, GI.screenBounds.getHeight());
    	
    	v.getChildren().add(HC);
    	v.setPadding(new Insets(50,0,50,0));
    	v.getChildren().add(HM);
    	GI.Jeux.setCenter(null);
    	GI.Jeux.setCenter(v);
    	GI.Jeux.setLeft(coteGauche);
    }
    
    /**
     * Cette méthode permet de dessiner la main du joueur.
     * 
     * 
     * @param data Données actuelles du jeu.
     * 
     * @since 1.0
     */
    
    public HBox drawMain(GestionnaireInterface GI) { 
    	Data data = GI.getData();
        HBox mainJoueur = new HBox();
        mainJoueur.setSpacing(10);
        mainJoueur.setAlignment(Pos.BOTTOM_CENTER);
        
        for(int i = 0; i < data.getMaster().getMain().length ;i++) {
        //for(CarteInfluence x: data.getMaster().getMain()) {
        	SpriteCarteInfluence SPI = new SpriteCarteInfluence(data.getMaster().getMain()[i],GI);
        	final int j = i;
        	//SPI.addEventFilter(MouseEvent.MOUSE_CLICKED, e -> data.getMaster().setCarteSelectionnee(j));
        	
        	// ------------------------------------------------------------------------------ detection pour la vignette
        	SPI.setOnMouseEntered( e -> { VSCI.rafraichir(SPI.getCarteSource(),GI); });
        	
        	SPI.setOnMouseExited( e -> { VSCI.flush(); });
        	
        	// ------------------------------------------------------------------------------ detection pour le drag and drop
        	
        	if(GI.getData().getCurrentJoueur() == selfID) {
        	
	        	SPI.setOnMousePressed( e-> { // prime condition for catching the card
	            		ScaleTransition st = new ScaleTransition();
			    	    st.setFromX(1);
			    	    st.setFromY(1);
			    	    st.setToX(1.1);
			    	    st.setToY(1.1);
			    	    st.setCycleCount(1);
			    	    st.setDuration(Duration.millis(200));
			    	    st.setInterpolator(Interpolator.EASE_BOTH);
			    	    st.setAutoReverse(true);
			    	    st.setNode(SPI);  
			    	    st.play();
			    	    SPI.getParent().setMouseTransparent(true);
			    	    data.getMaster().setCarteSelectionnee(j);
	            });
	        	
	        	//SPI.setOnDragDetected(e -> SPI.startFullDrag());
	        	
	        	SPI.setOnMouseDragged(new EventHandler<MouseEvent>() { // catch the card
	            	@Override public void handle(MouseEvent mouseEvent) {
	            		
	            		//SPI.getParent().setPickOnBounds(false);
	            		
	            		double easing = 0.25;
	            		double targetX = mouseEvent.getX() + SPI.getTranslateX() - SPI.getBoundsInParent().getWidth()/2;
	            		double dx = targetX - SPI.translateX;
	            		SPI.translateX += dx * easing;
	            		
	            		double targetY = mouseEvent.getY() + SPI.getTranslateY() - SPI.getBoundsInParent().getHeight()/2;
	            		double dy = targetY - SPI.translateY;
	            		SPI.translateY += dy * easing;
	            		
	            		SPI.setTranslateX(SPI.translateX);
	            		SPI.setTranslateY(SPI.translateY);
	            		
	            		SPI.ombre(GI);
	      		  	}
	            });
	        	
	        	SPI.setOnMouseReleased( e -> { // begone
	            	
	            		ScaleTransition st = new ScaleTransition();
			    	    st.setFromX(1.1);
			    	    st.setFromY(1.1);
			    	    st.setToX(1);
			    	    st.setToY(1);
			    	    st.setCycleCount(1);
			    	    st.setDuration(Duration.millis(200));
			    	    st.setInterpolator(Interpolator.EASE_BOTH);
			    	    st.setAutoReverse(true);
			    	    st.setNode(SPI);  
			    	    st.play();
	            		
	            		TranslateTransition translate = new TranslateTransition();
	            		translate.setDuration(Duration.millis(200));
	            		translate.setCycleCount(1);
	            		translate.setInterpolator(Interpolator.EASE_BOTH);
	            		translate.setFromX(SPI.translateX);
	            		translate.setFromY(SPI.translateY);
	            		translate.setToX(0);
	            		translate.setToY(0);
	            		translate.setNode(SPI);
	            		translate.play();
	            		
	            		translate.statusProperty().addListener(new ChangeListener<Status>() {
	        		        @Override
	        		        public void changed(ObservableValue<? extends Status> observableValue, Status oldValue, Status newValue) {
	        		              if(newValue==Status.STOPPED){
	        		            	  SPI.getParent().setMouseTransparent(false);
	        		            	  SPI.retireOmbre();
	        		            	  SPI.translateX = 0;
	        		            	  SPI.translateY = 0;
	        		            	  data.getMaster().setCarteSelectionnee(-1); /*    /!\ à surveiller /!\    */
	        		              }            
	        		        }
	        		    });
	            	});
        	}
        	
        	//SPI.addEventFilter(MouseEvent.MOUSE_CLICKED, e -> System.out.println(data.getMaster().getMain()[i].getNom()));
        	mainJoueur.getChildren().add(SPI);
        }
    	return mainJoueur;
    }
    
    /**
     * Cette méthode permet de dessiner les colonnes.
     * 
     * 
     * @param gi Le gestionnaire d'interface permettra de relier cette interface aux autres pour qu'elle puisse communiquer ensemble.
     * 
     * @since 1.0
     */

	public HBox drawColonne(GestionnaireInterface GI) { 
    	Data data = GI.getData();
    	HBox Colonnes = new HBox();
    	Colonnes.setPrefHeight(800);
        Colonnes.setSpacing(10);
        Colonnes.setAlignment(Pos.CENTER);
        
        for(int i=0;i<data.getJoueurs().length;i++) {
        	
        	VBox h = new VBox();
        	h.setMinWidth(30);
        	h.setPrefHeight(800);
        	
        	
        	//h.setOpacity(0.0);
        	
        	// si colonne complète
        	if(GI.getData().getPlateau().getColonne(i).estPleine()) {
        		ColorAdjust col = new ColorAdjust();
        		col.setContrast(-0.3);
      	      	col.setSaturation(-0.8);
      	      	h.setEffect(col);
        	}
        	
        	
        	VBox hitbox = new VBox();
        	
        	StackPane HCarte = new StackPane();
        	
        	//HCarte.setBackground(new Background(new BackgroundFill(new Color(1,1,1,1), null, null)));
        	//HCarte.setPrefHeight(400);
        	//HCarte.setMaxHeight(400);
        	
        	final int k = i;
        	hitbox.setOnMouseEntered(e -> {
            	
            		if(data.getMaster().getCarteSelectionnee() != -1 && GI.getData().getCurrentJoueur() == selfID && !GI.getFinManche()) {
	            		try {
							data.jouerCarte(data.getMaster().getCarteSelectionnee(),k);
							
							GI.rafraichir(GI);
							
							GI.bruitCarte();
							
							class TaskDelay extends TimerTask { // Timer pour voir la fin de la manche
								public void run() {
									
									Platform.runLater(() -> {
										try {
											GI.doitJouer();
										} catch (Exception e) {
											e.printStackTrace();
										}
									});
								}
				    		}
				    		Timer T = new Timer();
				    		TimerTask tache = new TaskDelay();
				    		T.schedule(tache, 250); // ------------------------------------------------------------------ delai bot
							
							
							
							
						} catch (Exception ex) {
							ex.printStackTrace();
						}
            		}
            });
        	// highlight colonne jouable
        	hitbox.setOnMouseDragEntered(e -> {
        		if(data.getMaster().getCarteSelectionnee() != -1 && !GI.getData().getPlateau().getColonne(k).estPleine()) {
	        		ColorAdjust col = new ColorAdjust();
					col.setBrightness(0.3);
	      	      	col.setSaturation(0.2);
	      	      	h.setEffect(col);
        		}
        	});
        	
        	hitbox.setOnMouseDragExited(e -> {
        		if(!GI.getData().getPlateau().getColonne(k).estPleine())
        			h.setEffect(null);
        	});
        	
        	//HCarte.setSpacing(-80);
        	h.setSpacing(0);
        	hitbox.setSpacing(10);
        	
        	SpriteCarteObjectif SpriteCO = new SpriteCarteObjectif(data.getPlateau().getColonnes()[i].getCarteObjectif(), null, GI);
        	hitbox.getChildren().add(SpriteCO); // carte objectif
        	
        	SpriteCO.setOnMouseClicked(new EventHandler<MouseEvent>() {
            	@Override public void handle(MouseEvent mouseEvent) {
            		
            		if(SpriteCO.getTraitreSelection() == true) {
            			SpriteCO.setTraitreSelection(false);
            		} else {
            			SpriteCO.setTraitreSelection(true);
            		}
      		  	}
            });
        	
        	
        	int CLenght = 0;
        	while(CLenght < data.getPlateau().getColonnes()[i].getCartesInfluences().length && data.getPlateau().getColonnes()[i].getCartesInfluences()[CLenght] != null)
        		CLenght++;
        	//System.out.println(CLenght);
        			
        	for(int j=0;j < data.getPlateau().getColonnes()[i].getCartesInfluences().length;j++) { // carte influences
        		if(data.getPlateau().getColonnes()[i].getCartesInfluences()[j] != null) {
	        		SpriteCarteInfluence SPI = new SpriteCarteInfluence(data.getPlateau().getColonnes()[i].getCartesInfluences()[j], GI);
	        		HCarte.getChildren().add(SPI);
	        		
	        		double spacing = 30 * GI.screenBounds.getWidth() / 1536;
	        		double spacingTop = 115 * GI.screenBounds.getWidth() / 1536;
	        		
        			if(j<CLenght-3) {
        				HCarte.getChildren().get(j).setTranslateY(spacing * j);
        			} else {
        				int nCStack = 0;
        				int nCLibre = 0;
        				
        				nCStack = (CLenght-3 > 0) ? CLenght-3 : 0;
        				nCLibre = j - nCStack;
        				
        				if(HCarte.getChildren().size() > j)
        					HCarte.getChildren().get(j).setTranslateY(spacing * nCStack + spacingTop * nCLibre);
        			}

	        		
	        		
	        			
	        		
	        		SPI.setOnMouseEntered(e -> { VSCI.rafraichir(SPI.getCarteSource(),GI); });
	        		
	        		SPI.setOnMouseExited(e -> { VSCI.flush(); });
	        		
        		}
        	}
        	hitbox.getChildren().add(HCarte);
        	h.getChildren().add(hitbox);
        Colonnes.getChildren().add(h);
        }
        return Colonnes;
    }
    
    /**
     * Cette methode permet d'afficher quel est le joueur en train de jouer
     * 
     * @param gi Le gestionnaire d'interface permettra de relier cette interface aux autres pour qu'elle puisse communiquer ensemble.
     * 
     * @since 1.0
     */
    
    public Label drawTexteJoueur(GestionnaireInterface GI) {
    	
    	String joueur = GI.getData().getJoueurs()[GI.getData().getCurrentJoueur()].getPseudo();
    	String prochainJoueur;
    	
    	if(GI.getData().getCurrentJoueur() == GI.getData().getJoueurs().length-1) {
    		prochainJoueur = GI.getData().getJoueurs()[0].getPseudo();
    	} else {
    		prochainJoueur = GI.getData().getJoueurs()[GI.getData().getCurrentJoueur()+1].getPseudo();
    	}
    	
    	Label textJoueur = new Label(GI.texteLangue.get(GI.langueSelectionne).getProperty("texte.tour") + joueur +"\n"+ GI.texteLangue.get(GI.langueSelectionne).getProperty("texte.prochainTour") + prochainJoueur );
    	textJoueur.setFont(Font.font("Comic Sans MS", 15));
        //textJoueur.setMaxWidth(150);
        textJoueur.setWrapText(true);
		return textJoueur;
    	
    }
    
    /**
     * Cette methode permet d'afficher quel est le joueur en train de jouer
     * 
     * @param gi Le gestionnaire d'interface permettra de relier cette interface aux autres pour qu'elle puisse communiquer ensemble.
     * 
     * @since 1.0
     */
    
    public Label drawManche(GestionnaireInterface GI) {
    	Label l = new Label();
    	l.setFont(Font.font("Comic Sans MS", 15));
    	
    	l.setText(GI.texteLangue.get(GI.langueSelectionne).getProperty("texte.manche")+" : "+(GI.getData().getCurrentManche()+1)+"/6");
    	
    	return l;
    }
    
    /**
     * Cette methode permet d'afficher le score des joueurs de la partie du plateau
     * 
     * @param gi Le gestionnaire d'interface permettra de relier cette interface aux autres pour qu'elle puisse communiquer ensemble.
     * 
     * @since 1.0
     */
    
    public Label drawScore(GestionnaireInterface GI) {
    	
    	Label l = new Label();
    	l.setFont(Font.font("Comic Sans MS", 15));
    	
    	Map<String, Integer> score = new HashMap<String, Integer>();
    	for(Joueur j : GI.getData().getJoueurs()) {
    		score.put(j.getPseudo(), j.getScore());
    	}
    	
    	List<Entry<String, Integer>> list = new LinkedList<Entry<String, Integer>>(score.entrySet());  
    	
    	Collections.sort(list, new Comparator<Entry<String, Integer>>() {  
	    	public int compare(Entry<String, Integer> o1, Entry<String, Integer> o2) {  
	    		return o2.getValue().compareTo(o1.getValue());
	    	}  
    	});  
    	
    	for(int i = 0; i<score.size(); i++) {
    		l.setText(l.getText()+list.get(i).getKey()+" : "+list.get(i).getValue()+"\n");
    	}
    	
    	return l;
    }
    
    /**
     * Cette methode permet d'afficher les cartes objetcifs gagnées par le joueurs pour chaque manche sur le plateau de jeu.
     * 
     * @param gi Le gestionnaire d'interface permettra de relier cette interface aux autres pour qu'elle puisse communiquer ensemble.
     * 
     * @since 1.0
     */
    
    public void drawCarteObjectifGagne(GestionnaireInterface GI) {
    	
    	cartesObjectifPossede.getChildren().clear();
    	ArrayList<CarteObjectif> array = GI.getData().getJoueurs()[selfID].getObjectif();
    	double height = 0;
    	
    	for(CarteObjectif CO : array) {
    		if(CO != null) {
	    		SpriteCarteObjectif SpriteCO = new SpriteCarteObjectif(CO, null, GI);
	    		cartesObjectifPossede.getChildren().add(SpriteCO);
	    		height += SpriteCO.getBoundsInParent().getHeight() + cartesObjectifPossede.getSpacing();
    		}
    	}
    	AnchorPane.setBottomAnchor(cartesObjectifPossede, GI.screenBounds.getHeight()/2 - height/2);
    }
}
