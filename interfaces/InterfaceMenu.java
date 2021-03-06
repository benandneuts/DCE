package interfaces;

import javafx.application.Platform;
import javafx.scene.input.MouseEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.effect.Shadow;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

/**
 * Cette classe est une interface qui represente le menu principal.
 * 
 * @author S3T - G1
 * 
 * @since 1.0
 */
public class InterfaceMenu extends InterfaceBase {
	
	Button boutonOption;
	Button boutonRegles;
	Button boutonJouer;
	Button boutonQuitter;
	
	Text titre;
	
	HBox HBHaut;
	VBox VBDroite;
	VBox VBgauche;
	
	/**
     *  Ce constructeur permet de creer tous les elements de l'interface, c'est-a-dire le titre du jeu,
     *  le bouton qui va rediriger vers les parametres, le bouton qui va rediriger vers les regles, le bouton qui va rediriger vers la creation d'une partie et pour finir le bouton "quitter" qui va permettre de sortir du jeu.
     * 
     * @param gi le gestionnaire d'interface permettra de relier cette interface aux autres pour qu'elle puisse communiquer ensemble
     * 
     * @since 1.0
     */
	public InterfaceMenu(GestionnaireInterface GI) { // javaFX elements goes into the class constructor
		super();
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
		
		// fond de jeu
        this.setBackground(new Background(new BackgroundFill(Color.MOCCASIN,CornerRadii.EMPTY,null)));

//-----------------Créations des composants------------------------------------------------        

//-----------------Créations des composants------------------------------------------------  
		boutonOption = new Button(GI.texteLangue.get(GI.langueSelectionne).getProperty("bouton.parametres")); 
		boutonOption.setOnAction(e -> {
			GI.bruitInterface();
			GI.afficherEcran(GI.InterfaceMap.get("parametres"));
			}); // switch Pane visibility
//		boutonOption.setPrefWidth(GI.screenBounds.getWidth()*0.26);
//		boutonOption.setPrefHeight(GI.screenBounds.getHeight()*0.06);
		boutonOption.setStyle(GI.BOUTON_STYLE_WOOD);
		boutonOption.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                boutonOption.setStyle(GI.BOUTON_STYLE_WOOD_SURVOL);
            }
        });
        boutonOption.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                boutonOption.setStyle(GI.BOUTON_STYLE_WOOD);
            }
        });
		
		boutonRegles = new Button(GI.texteLangue.get(GI.langueSelectionne).getProperty("bouton.regle"));
		boutonRegles.setOnAction(e -> {
			GI.bruitInterface();
			GI.afficherEcran(GI.InterfaceMap.get("regles"));
			}); // switch Pane visibility
		boutonRegles.setStyle(GI.BOUTON_STYLE_WOOD);
		boutonRegles.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                boutonRegles.setStyle(GI.BOUTON_STYLE_WOOD_SURVOL);
            }
        });
        boutonRegles.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                boutonRegles.setStyle(GI.BOUTON_STYLE_WOOD);
            }
        });
//		boutonRegles.setPrefWidth(GI.screenBounds.getWidth()*0.26);
		
		boutonJouer = new Button(GI.texteLangue.get(GI.langueSelectionne).getProperty("bouton.partieLocale"));
		boutonJouer.setOnAction(e -> {
			GI.bruitInterface();
			GI.afficherEcran(GI.InterfaceMap.get("creerPartie"));
			}); // switch Pane visibility
//		boutonJouer.setPrefWidth(GI.screenBounds.getWidth()*0.26);
		boutonJouer.setStyle(GI.BOUTON_STYLE_WOOD);
		boutonJouer.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                boutonJouer.setStyle(GI.BOUTON_STYLE_WOOD_SURVOL);
            }
        });
        boutonJouer.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                boutonJouer.setStyle(GI.BOUTON_STYLE_WOOD);
            }
        });
		
		boutonQuitter = new Button(GI.texteLangue.get(GI.langueSelectionne).getProperty("bouton.quitter"));
			boutonQuitter.setOnAction(e -> {
			GI.bruitInterface();Platform.exit();
		});
		boutonQuitter.setPrefWidth(GI.screenBounds.getWidth()*0.08);
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
		
		titre = new Text(GI.texteLangue.get(GI.langueSelectionne).getProperty("texte.titre"));
		titre.setStyle("-fx-font: normal bold 10em 'Pristina' ");
	      
//------------------Créations des conteneurs------------------------------------------------		
		
//------------------Conteneur titre---------------------------------------------------------
		VBox VBTopCentre = new VBox();
		VBTopCentre.getChildren().add(titre);
		VBTopCentre.setPadding(new Insets(0,0,0,GI.screenBounds.getWidth()*0.03));

//------------------Conteneur bouton quitter------------------------------------------------
		VBox VBTopDroite = new VBox(boutonQuitter);
		VBTopDroite.setPadding(new Insets(GI.screenBounds.getHeight()*0.04,GI.screenBounds.getWidth()*0.03,0,GI.screenBounds.getWidth()*0.49));

//------------------Conteneur bouton quitter------------------------------------------------
		HBox HBTop = new HBox(VBTopCentre, VBTopDroite);
		this.setTop(HBTop);

//------------------Conteneur boutons-------------------------------------------------------
		VBgauche = new VBox(boutonJouer, boutonOption, boutonRegles);
		VBgauche.setMinSize(GI.screenBounds.getWidth()*0.18, GI.screenBounds.getHeight()*0.93);
		this.setLeft(VBgauche);
		VBgauche.setSpacing(GI.screenBounds.getHeight()*0.09);
		VBgauche.setPadding(new Insets(GI.screenBounds.getHeight()*0.14,0,0,GI.screenBounds.getWidth()*0.08));
        
	}
}
