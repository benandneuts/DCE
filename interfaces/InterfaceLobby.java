package interfaces;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import bot.Bot;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import joueur.Joueur;
import moteur.Data;

public class InterfaceLobby extends InterfaceBase {
	
	private VBox VBJoueurs;
	protected String pseudoJ;
	int nbjoueur;
	int nbBox = 0;
	int nbBot = 0;
	GestionnaireInterface gi;
	HashMap<Integer, ChoiceBox<String>> listDifficulteBots = new HashMap<Integer, ChoiceBox<String>>();
	HashMap<Integer, ChoiceBox<String>> listCouleur = new HashMap<Integer, ChoiceBox<String>>();
	HashMap<Integer, TextField> listPseudo = new HashMap<Integer, TextField>();
	String[] tabCouleur = {"Rouge", "Violet", "Jaune", "Vert", "Blanc", "Bleu"};
	
	public InterfaceLobby(GestionnaireInterface GI){
		GI = gi;
	}
	
	public void setNbJoueur(int nbj) {
		nbjoueur = nbj;
	}
	
	public int getNbJoueur() {
		return nbjoueur;
	}
	
    /**
     * Cette methode permet de dessiner l'interface du lobby
     * 
     * @param gi Le gestionnaire d'interface permettra de relier cette interface aux autres pour qu'elle puisse communiquer ensemble.
     * 
     * @since 1.0
     */
	
	@Override
	public void dessineInterface(GestionnaireInterface GI) {
		   //TODO Quand un joueur rejoind le lobby, on appelle dessineBoxJoueur
		this.setBackground(new Background(new BackgroundFill(Color.MOCCASIN,CornerRadii.EMPTY,null)));
		this.setHeight(GI.screenBounds.getHeight());
		this.setWidth(GI.screenBounds.getWidth());
		
		HBox HBTop = new HBox();
		Label idPartie = new Label(GI.texteLangue.get(GI.langueSelectionne).getProperty("texte.nomPartie")+": " + "Partie 1");
		idPartie.setFont(Font.font("Pristina", 30));
		idPartie.setPadding(new Insets(GI.screenBounds.getHeight()*0.02, 0, 0, GI.screenBounds.getWidth()*0.04));
		HBTop.getChildren().add(idPartie);
		this.setTop(HBTop);
		
		HBox[] HBJoueurs = new HBox[nbjoueur];
		Button[] ajouterJoueurs = new Button[nbjoueur];
		VBJoueurs = new VBox();
		VBJoueurs.setPrefSize(GI.screenBounds.getHeight(), GI.screenBounds.getWidth());
		VBJoueurs.setPadding(new Insets(GI.screenBounds.getHeight()*0.05, 0, 0, GI.screenBounds.getWidth()*0.25));
		
		for(int i = 0; i < nbjoueur; i++) {
			ajouterJoueurs[i] = new Button("+");
			ajouterJoueurs[i].setPrefSize(GI.screenBounds.getHeight()*0.75, GI.screenBounds.getWidth()*0.06);
			ajouterJoueurs[i].setAlignment(Pos.CENTER);
			ajouterJoueurs[i].setStyle(GI.BOUTON_STYLE);
		}
		
		for(int i = 0; i < nbjoueur; i++) {
			HBJoueurs[i] = new HBox();
			HBJoueurs[i].getChildren().add(ajouterJoueurs[i]);
			VBJoueurs.getChildren().add(HBJoueurs[i]);
			ajouterJoueurs[i].setOnAction(e -> dessineBoxBot(GI));
		}
		
		Button jouer = new Button(GI.texteLangue.get(GI.langueSelectionne).getProperty("texte.jouer2"));
		Label espace = new Label("                               ");
		espace.setFont(Font.font(70));
		jouer.setStyle(GI.BOUTON_STYLE_WOOD);
		jouer.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                jouer.setStyle(GI.BOUTON_STYLE_WOOD_SURVOL);
            }
        });
        jouer.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                jouer.setStyle(GI.BOUTON_STYLE_WOOD);
            }
        });
		
		HBox HBJouer = new HBox();
		HBJouer.setPadding(new Insets(GI.screenBounds.getHeight()*0.32,0,0,150));
		VBox VBBouton = new VBox();
		jouer.setFont(Font.font("Pristina", 30));
		jouer.setPrefSize(250, 120);
		jouer.setAlignment(Pos.CENTER);
		VBBouton.getChildren().add(jouer);
		
		//TODO Démarrer une partie en cliquant sur ce bouton
		jouer.setOnAction(e -> lancerPartie(GI, VBBouton));
		HBJouer.getChildren().add(VBBouton);
		
		HBox HBMilieu = new HBox();
		HBMilieu.getChildren().addAll(VBJoueurs, HBJouer, espace);
		
		this.setCenter(HBMilieu);	
	}	

	 /**
     * Cette methode permet de dessiner la box du joueur réel
     * 
     * @param gi Le gestionnaire d'interface permettra de relier cette interface aux autres pour qu'elle puisse communiquer ensemble.
     * 
     * @since 1.0
     */
	
	public void dessineBoxJoueur(GestionnaireInterface GI, String nom) {
		
		Platform.runLater(new Runnable() {
			@Override
			public void run( ) {
				HBox BoxJoueur = new HBox();
				BoxJoueur.setAlignment(Pos.CENTER);
				BoxJoueur.setPrefSize(GI.screenBounds.getHeight()*0.75, GI.screenBounds.getWidth()*0.06);
				nbBox++;
				
		        VBox VBBouton = new VBox();

		        Button sup = new Button("^");
		        sup.setPrefSize(38, 50);
		        sup.setFont(Font.font("Comic Sans MS", 18));
		        sup.setOnAction(e -> hausserBox(GI, BoxJoueur));
				sup.setStyle(GI.BOUTON_STYLE);
				sup.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
		            @Override
		            public void handle(MouseEvent e) {
		                sup.setStyle(GI.BOUTON_STYLE_SURVOL);
		            }
		        });
		        sup.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
		            @Override
		            public void handle(MouseEvent e) {
		                sup.setStyle(GI.BOUTON_STYLE);
		            }
		        });
		        
		        Button inf = new Button("v");
		        inf.setPrefSize(38, 50);
		        inf.setFont(Font.font("Comic Sans MS", 18));
		        inf.setOnAction(e -> descendreBox(GI, BoxJoueur));
				inf.setStyle(GI.BOUTON_STYLE);
				inf.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
		            @Override
		            public void handle(MouseEvent e) {
		                inf.setStyle(GI.BOUTON_STYLE_SURVOL);
		            }
		        });
		        inf.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
		            @Override
		            public void handle(MouseEvent e) {
		                inf.setStyle(GI.BOUTON_STYLE);
		            }
		        });
		        VBBouton.getChildren().addAll(sup, inf);
		        VBBouton.setPadding(new Insets(8));
				
				TextField pseudo = new TextField(nom);
				pseudo.setDisable(true);
//				pseudo.setPromptText(GI.texteLangue.get(GI.langueSelectionne).getProperty("texte.pseudo"));
		        pseudo.setFont(Font.font("Pristina", 20));
		        pseudo.setPrefSize(220, 50);
		        pseudo.setStyle(GI.INPUT_STYLE);
		        listPseudo.put(nbBox, pseudo);
		        
		        ChoiceBox<String> difficulteBots = new ChoiceBox<>();
		        difficulteBots.setPrefSize(100, 40);
		        difficulteBots.getItems().add("Joueur");
		        difficulteBots.setValue("Joueur");
		        difficulteBots.setDisable(true);
				difficulteBots.setStyle(GI.BOUTON_STYLE);
		        
		        ChoiceBox<String> choixCouleur = new ChoiceBox<>();
		        choixCouleur.setPrefSize(100, 40);
		        for (int i = 1; i<=6; i++)
		        	choixCouleur.getItems().add(tabCouleur[i-1]);
		        choixCouleur.setValue(tabCouleur[nbBox-1]);
				choixCouleur.setStyle(GI.BOUTON_STYLE);
		        listCouleur.put(nbBox, choixCouleur);
		        
		        VBJoueurs.getChildren().remove(nbjoueur-1);
				BoxJoueur.getChildren().addAll(VBBouton, pseudo, difficulteBots, choixCouleur);
				VBJoueurs.getChildren().add(nbBox-1, BoxJoueur);
		}
		});
		//Thread t = new Thread(r);
	}
	
	 /**
     * Cette methode permet de dessiner la box du bot
     * 
     * @param gi Le gestionnaire d'interface permettra de relier cette interface aux autres pour qu'elle puisse communiquer ensemble.
     * 
     * @since 1.0
     */
	
	public void dessineBoxBot(GestionnaireInterface GI) {
		HBox BoxBot = new HBox();
        BoxBot.setPrefSize(GI.screenBounds.getHeight()*0.75, GI.screenBounds.getWidth()*0.06);
        BoxBot.setAlignment(Pos.CENTER_LEFT);
        nbBot++;
        nbBox++;
        
        VBox VBBouton = new VBox();
        
        Button sup = new Button("^");
        sup.setPrefSize(38, 50);
        sup.setFont(Font.font("Comic Sans MS", 18));
        sup.setOnAction(e -> hausserBox(GI, BoxBot));
		sup.setStyle(GI.BOUTON_STYLE);
		sup.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                sup.setStyle(GI.BOUTON_STYLE_SURVOL);
            }
        });
        sup.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                sup.setStyle(GI.BOUTON_STYLE);
            }
        });
        
        Button inf = new Button("v");
        inf.setPrefSize(38, 50);
        inf.setFont(Font.font("Comic Sans MS", 18));
        inf.setOnAction(e -> descendreBox(GI, BoxBot));
		inf.setStyle(GI.BOUTON_STYLE);
		inf.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                inf.setStyle(GI.BOUTON_STYLE_SURVOL);
            }
        });
        inf.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                inf.setStyle(GI.BOUTON_STYLE);
            }
        });
        
        VBBouton.getChildren().addAll(sup, inf);
        VBBouton.setPadding(new Insets(8));
        
        TextField pseudo = new TextField();
        pseudo.setText("bot" + nbBot);
//        pseudo.setPromptText(GI.texteLangue.get(GI.langueSelectionne).getProperty("texte.pseudo"));
        pseudo.setFont(Font.font("Pristina", 20));
        pseudo.setPrefSize(220, 40);
        pseudo.setStyle(GI.INPUT_STYLE);
        listPseudo.put(nbBox, pseudo);

        ChoiceBox<String> difficulteBots = new ChoiceBox<>();
        difficulteBots.setPrefSize(100, 40);
        difficulteBots.getItems().addAll("Facile", "Moyen", "Difficile");
        difficulteBots.setValue("Facile");
		difficulteBots.setStyle(GI.BOUTON_STYLE);
        listDifficulteBots.put(nbBox, difficulteBots);
        
        ChoiceBox<String> choixCouleur = new ChoiceBox<>();
        choixCouleur.setPrefSize(100, 40);
        choixCouleur.setValue(tabCouleur[nbBox-1]);
		choixCouleur.setStyle(GI.BOUTON_STYLE);
        for (int i = 1; i<=6; i++)
        	choixCouleur.getItems().add(tabCouleur[i-1]);
        listCouleur.put(nbBox, choixCouleur);
        
        HBox boxMoins = new HBox();
        boxMoins.setPadding(new Insets(0,0,15,15));
        
        Button retirerBox = new Button("-");
        retirerBox.setFont(Font.font("Pristina", 15));
        retirerBox.setPrefSize(30, 30);
		retirerBox.setStyle(GI.BOUTON_STYLE);
        retirerBox.setOnAction(e -> retirerBox(GI, BoxBot));
        
        boxMoins.getChildren().add(retirerBox);
        BoxBot.getChildren().addAll(VBBouton, pseudo, difficulteBots, choixCouleur, boxMoins);
        
        VBJoueurs.getChildren().remove(nbjoueur - 1);
        VBJoueurs.getChildren().add(nbBox-1, BoxBot);
    }
	
	public void retirerBox(GestionnaireInterface GI, HBox Box) {
		int index = VBJoueurs.getChildren().indexOf(Box) +1;
		VBJoueurs.getChildren().remove(Box);
		if(index<nbBox) {
			listPseudo.remove(index);
			listCouleur.remove(index);
			for(; index < nbBox; index++) {
				listPseudo.put(index, listPseudo.get(index+1));
				listCouleur.put(index, listCouleur.get(index+1));
			}

		}
		nbBox--;
		nbBot--;
		
		HBox HBPlus = new HBox();
		Button ajouterJoueur = new Button("+");
		
		ajouterJoueur.setPrefSize(GI.screenBounds.getHeight()*0.75, GI.screenBounds.getWidth()*0.06);
		ajouterJoueur.setAlignment(Pos.CENTER);
		ajouterJoueur.setOnAction(e -> dessineBoxBot(GI));
		
		HBPlus.getChildren().add(ajouterJoueur);		
		VBJoueurs.getChildren().add(HBPlus);
	}
	
	 /**
     * Cette methode permet de faire baisser de position un un joueur réel ou un bot.
     * 
     * @param gi Le gestionnaire d'interface permettra de relier cette interface aux autres pour qu'elle puisse communiquer ensemble.
     * 
     * @since 1.0
     */
	
	public void descendreBox(GestionnaireInterface GI, HBox Box) {
		int indexBoxAMonter = VBJoueurs.getChildren().indexOf(Box) + 1;
		int indexBoxADescendre = VBJoueurs.getChildren().indexOf(Box);
		
		if(indexBoxAMonter >= nbBox) {}
		else {
			TextField pseudoAMonter = listPseudo.get(indexBoxAMonter + 1);
			TextField pseudoADescendre = listPseudo.get(indexBoxADescendre +1);
			
			listPseudo.put(indexBoxAMonter + 1, pseudoADescendre);
			listPseudo.put(indexBoxADescendre + 1, pseudoAMonter);	
			
			ChoiceBox<String> couleurAMonter = listCouleur.get(indexBoxAMonter + 1);
			ChoiceBox<String> couleurADescendre = listCouleur.get(indexBoxADescendre +1);
			
			listCouleur.put(indexBoxAMonter + 1,  couleurADescendre);
			listCouleur.put(indexBoxADescendre + 1, couleurAMonter);
			
			ChoiceBox<String> diffAMonter = listDifficulteBots.get(indexBoxAMonter + 1);
			ChoiceBox<String> diffADescendre = listDifficulteBots.get(indexBoxADescendre +1);
			
			if(!listDifficulteBots.containsKey(indexBoxAMonter + 1)) {
				listDifficulteBots.put(indexBoxAMonter + 1,  diffADescendre);
				listDifficulteBots.remove(indexBoxADescendre +1);
			}
			else {
				if(!listDifficulteBots.containsKey(indexBoxADescendre + 1)) {
					listDifficulteBots.put(indexBoxADescendre + 1, diffAMonter);
					listDifficulteBots.remove(indexBoxAMonter +1);
				}
				else {
					listDifficulteBots.put(indexBoxAMonter + 1,  diffADescendre);
					listDifficulteBots.put(indexBoxADescendre + 1, diffAMonter);	
				}
			}
//			System.out.println(listDifficulteBots.keySet().toString());
			
			Node HBbas = VBJoueurs.getChildren().get(indexBoxAMonter);
			VBJoueurs.getChildren().removeAll(HBbas, Box);
			VBJoueurs.getChildren().add(indexBoxADescendre, HBbas);
			VBJoueurs.getChildren().add(indexBoxAMonter, Box);
		}
	}
	 
	/**
     * Cette methode permet de faire monter de position un un joueur réel ou un bot.
     * 
     * @param gi Le gestionnaire d'interface permettra de relier cette interface aux autres pour qu'elle puisse communiquer ensemble.
     * 
     * @since 1.0
     */
	
	public void hausserBox(GestionnaireInterface GI, HBox Box) {
		int indexBoxAMonter = VBJoueurs.getChildren().indexOf(Box);
		int indexBoxADescendre = VBJoueurs.getChildren().indexOf(Box) - 1;
		
		if(indexBoxADescendre < 0) {}
		else {
			TextField pseudoAMonter = listPseudo.get(indexBoxAMonter + 1);
			TextField pseudoADescendre = listPseudo.get(indexBoxADescendre +1);
			
			listPseudo.put(indexBoxAMonter + 1, pseudoADescendre);
			listPseudo.put(indexBoxADescendre + 1, pseudoAMonter);	
			
			ChoiceBox<String> couleurAMonter = listCouleur.get(indexBoxAMonter + 1);
			ChoiceBox<String> couleurADescendre = listCouleur.get(indexBoxADescendre +1);
			
			listCouleur.put(indexBoxAMonter + 1,  couleurADescendre);
			listCouleur.put(indexBoxADescendre + 1, couleurAMonter);
			
			ChoiceBox<String> diffAMonter = listDifficulteBots.get(indexBoxAMonter + 1);
			ChoiceBox<String> diffADescendre = listDifficulteBots.get(indexBoxADescendre +1);
			
			if(!listDifficulteBots.containsKey(indexBoxAMonter + 1)) {
				listDifficulteBots.put(indexBoxAMonter + 1,  diffADescendre);
				listDifficulteBots.remove(indexBoxADescendre +1);
			}
			else {
				if(!listDifficulteBots.containsKey(indexBoxADescendre + 1)) {
					listDifficulteBots.put(indexBoxADescendre + 1, diffAMonter);
					listDifficulteBots.remove(indexBoxAMonter +1);
				}
				else {
					listDifficulteBots.put(indexBoxAMonter + 1,  diffADescendre);
					listDifficulteBots.put(indexBoxADescendre + 1, diffAMonter);	
				}
			}
//			System.out.println(listDifficulteBots.keySet().toString());
			
			Node HBhaut = VBJoueurs.getChildren().get(indexBoxADescendre);
			VBJoueurs.getChildren().removeAll(HBhaut, Box);
			VBJoueurs.getChildren().add(indexBoxADescendre, Box);
			VBJoueurs.getChildren().add(indexBoxAMonter, HBhaut);
		}
	}
	
	 /**
     * Cette methode sert à lancer un partie
     * 
     * @param gi Le gestionnaire d'interface permettra de relier cette interface aux autres pour qu'elle puisse communiquer ensemble.
     * 
     * @since 1.0
     */
	
	public void lancerPartie(GestionnaireInterface GI, VBox Box) {
		Boolean lancable = true;
		Label jeu = new Label();
		if(Box.getChildren().size()==2)
			Box.getChildren().remove(1);
		if(nbBox<nbjoueur) {
			lancable = false;
			jeu.setText("Il manque des joueurs.");
		}
		ArrayList<String> txtf = new ArrayList<>();
		for(TextField t : listPseudo.values()) {
			if(txtf.contains(t.getText())) {
				jeu.setText("Des joueurs ont le même pseudo !");
				lancable = false;
			}
			txtf.add(t.getText());
		}
		for(int i=1; i<=nbBox; i++) {
			for(int k=i+1; k<=nbBox && lancable == true; k++) {
//				System.out.println(listPseudo.get(i).getText() + " : " + listPseudo.get(k).getText());
				if (listCouleur.get(i).getValue() == listCouleur.get(k).getValue() || listPseudo.get(i).getText() == listPseudo.get(k).getText()) {
					lancable = false;
					jeu.setPadding(new Insets(GI.screenBounds.getHeight()*0.02,0,0,0));
					if(listCouleur.get(i).getValue().equals(listCouleur.get(k).getValue()))
						jeu.setText("Des joueurs ont la même couleur !");
				}
			}
		}
		
		if(lancable == true) {
			Joueur[] joueurs = new Joueur[nbBox];
			Joueur master = null;
			for(Integer i : listPseudo.keySet()) {
				Color couleur;
				switch(listCouleur.get(i).getValue()) {
					case "Rouge":
						couleur = Color.RED;
					break;
					case "Violet":
						couleur = Color.PURPLE;
					break;
					case "Jaune":
						couleur = Color.YELLOW;
					break;
					case "Vert":
						couleur = Color.DARKGREEN;					
					break;
					case "Blanc":
						couleur = Color.ANTIQUEWHITE;
					break;
					case "Bleu":
						couleur = Color.BLUE;					
					break;
					default:
						couleur = null;
					break;
				}
				if(listPseudo.get(i).getText().equals(pseudoJ)) {
					master = new Joueur(couleur, listPseudo.get(i).getText());
//					master = new Bot("facile", couleur, listPseudo.get(i).getText());
					joueurs[i-1] = master;
				}
				else {
			        switch(listDifficulteBots.get(i).getValue()) {
			        	case "Easy": listDifficulteBots.get(i).setValue("facile"); break;
			        	case "Medium": listDifficulteBots.get(i).setValue("moyen"); break;
			        	case "Hard": listDifficulteBots.get(i).setValue("difficile"); break;
			        }
			        joueurs[i-1] = new Bot(listDifficulteBots.get(i).getValue().toLowerCase(), couleur, listPseudo.get(i).getText());
				}
			}
			if(master == null) {
				master = joueurs[(new Random()).nextInt(joueurs.length)];
			}
//			System.out.println(master instanceof Bot);
			GI.setData(new Data(joueurs));
			GI.data.setMaster(master);
	        GI.Jeux.drawPartie(GI);
	        GI.afficherEcran(GI.InterfaceMap.get("jeu"));
	        try {
				GI.doitJouer();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        nbBox = 0;
	        nbBot = 0;
	    	listDifficulteBots = new HashMap<Integer, ChoiceBox<String>>();
	    	listCouleur = new HashMap<Integer, ChoiceBox<String>>();
	    	listPseudo = new HashMap<Integer, TextField>();
		}
		
		Box.getChildren().add(jeu);		
	}
}