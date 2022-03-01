package interfaces;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

/**
 * Cette classe est une interface qui représente les paramètres.
 * 
 *   
 * @author S3T - G1
 * 
 * @since 1.0
 */
public class InterfaceCredit extends InterfaceBase {
    
    public GestionnaireInterface GI;
    
	/**
     *  Ce constructeur permet de creer tous les élements de l'interface, c'est-a-dire le titre "Paramètres", le bouton "retour", 
     *  le bouton "Paramètre Graphiques", le bouton "Paramètre Musicaux", le bouton "Paramètres Sonores", le bouton "Theme", le bouton "Langue".
     *  
     * 
     * @param gi le gestionnaire d'interface permettra de relier cette interface aux autres pour qu'elle puisse communiquer ensemble
     * 
     * @since 1.0
     */
    public InterfaceCredit(GestionnaireInterface gi) { 
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
    
    public void dessineInterface(GestionnaireInterface GI) {
    	// --------------------------------------- fond ----------------------------------------- //
        this.setBackground(new Background(new BackgroundFill(Color.MOCCASIN,CornerRadii.EMPTY,null)));
		
		// --------------------------------------- bouton retour ----------------------------------------- //
		Button boutonRetour = new Button(GI.texteLangue.get(GI.langueSelectionne).getProperty("bouton.retour"));
		boutonRetour.setOnAction(e -> {
			GI.bruitInterface();
			GI.afficherEcran(GI.UIGrandParentID);
		});
		boutonRetour.setFont(Font.font("Comic Sans MS", 20));
		boutonRetour.setPrefWidth(GI.screenBounds.getWidth()*0.08);
		
		// --------------------------------------- contenu ----------------------------------------- //
		VBox credit = new VBox();
		credit.setAlignment(Pos.CENTER);
		credit.setPrefSize(GI.screenBounds.getWidth()*0.73, GI.screenBounds.getHeight()*0.93);
		credit.setPadding(new Insets(0,GI.screenBounds.getWidth()*0.08,0,GI.screenBounds.getWidth()*0.08));
		
		String contenu = "";
		
		if(GI.langueSelectionne== "francais")
			contenu = "\t Musique de fond :\r\n"
					+ "https://www.auboutdufil.com/index.php?id=587\r\n"
					+ "Titre:  Beyond The Warriors\r\n"
					+ "Auteur: Guifrog\r\n"
					+ "Source: https://guifrog.bandcamp.com/\r\n"
					+ "Licence: https://creativecommons.org/licenses/by/3.0/deed.fr\r\n"
					+ "Téléchargement (5MB): https://auboutdufil.com/?id=587"
					+ "\r\n"
					+ "\t Bruit changement d'interface :\r\n"
					+ "Source : https://lasonotheque.org/detail-1950-plante-cartoon-1.html\r\n"
					+ "Licence : http://www.wtfpl.net/";
		else
			contenu = "\t Background music:\r\n"
					+ "https://www.auboutdufil.com/index.php?id=587\r\n"
					+ "Title:  Beyond The Warriors\r\n"
					+ "Author: Guifrog\r\n"
					+ "Source: https://guifrog.bandcamp.com/\r\n"
					+ "License: https://creativecommons.org/licenses/by/3.0/deed.fr\r\n"
					+ "Download (5MB): https://auboutdufil.com/?id=587"
					+ "\r\n"
					+ "\t Interface change noise :\r\n"
					+ "Source : https://lasonotheque.org/detail-1950-plante-cartoon-1.html\r\n"
					+ "License : http://www.wtfpl.net/";

	    Label contenu_credit = new Label(contenu);
	    
	    credit.getChildren().add(contenu_credit);
		// --------------------------------------- titre ----------------------------------------- //
		
		Text titre = new Text(GI.texteLangue.get(GI.langueSelectionne).getProperty("bouton.credit"));
		titre.setFont(Font.font("Pristina", FontWeight.BOLD,120));
		titre.setTextAlignment(TextAlignment.CENTER);
		titre.setWrappingWidth(500);
		
		// --------------------------------------- disposition ----------------------------------------- //
		AnchorPane APTop = new AnchorPane(titre,boutonRetour);
		APTop.setPrefWidth(GI.screenBounds.getWidth());
		APTop.setPrefHeight(GI.screenBounds.getHeight()/10*2);
		
		AnchorPane.setRightAnchor(boutonRetour, 40.0);
		AnchorPane.setTopAnchor(boutonRetour, 40.0);
		
		AnchorPane.setLeftAnchor(titre, GI.screenBounds.getWidth()/2 - titre.getWrappingWidth()/2);
		AnchorPane.setTopAnchor(titre, 40.0);
		
		this.setTop(APTop);
		
		StackPane SPCenter = new StackPane();
		SPCenter.setPrefWidth(GI.screenBounds.getWidth());
		SPCenter.setPrefHeight(GI.screenBounds.getHeight()/10*8);
		SPCenter.getChildren().add(contenu_credit);
		
		this.setCenter(SPCenter);
		
		/*
		VBox VBTopCentre = new VBox();
		VBTopCentre.getChildren().add(titre);
		VBTopCentre.setPadding(new Insets(0,0,0,GI.screenBounds.getWidth()*0.42));

		VBox HBTopDroite = new VBox(boutonRetour);
		HBTopDroite.setPadding(new Insets(GI.screenBounds.getHeight()*0.04,0,0,GI.screenBounds.getWidth()*0.35));
		
		HBox HBTop = new HBox(VBTopCentre, HBTopDroite);
		this.setTop(HBTop);
		
		HBox HBCentre = new HBox();
		HBCentre.setAlignment(Pos.CENTER);
		HBCentre.setPadding(new Insets(GI.screenBounds.getHeight()*-0.09,GI.screenBounds.getWidth()*0.07,GI.screenBounds.getHeight()*0.01,GI.screenBounds.getWidth()*0.06));
		HBCentre.getChildren().add(credit);
		this.setCenter(HBCentre);*/

	}
}
