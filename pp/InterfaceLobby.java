package pp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javafx.event.Event;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class InterfaceLobby extends InterfaceBase{
	
	private VBox VBJoueurs;
	int nbjoueur;
	int nbBox = 0;
	int nbBot = 0;
	GestionnaireInterfacePP gi;
	HashMap<Integer, ChoiceBox<String>> listDifficulteBots = new HashMap<Integer, ChoiceBox<String>>();
	HashMap<Integer, ChoiceBox<String>> listCouleur = new HashMap<Integer, ChoiceBox<String>>();
	HashMap<Integer, TextField> listPseudo = new HashMap<Integer, TextField>();
	String[] tabCouleur = {"Rouge", "Violet", "Jaune", "Vert", "Blanc", "Bleu"};

	
	public InterfaceLobby(GestionnaireInterfacePP GI){
		GI = gi;
	}
	
	public void dessineInterface(GestionnaireInterfacePP GI) {
		   //TODO Quand un joueur rejoind le lobby, on appelle dessineBoxJoueur
		this.setBackground(new Background(new BackgroundFill(Color.MOCCASIN,CornerRadii.EMPTY,null)));
		this.setHeight(GI.screenBounds.getHeight());
		this.setWidth(GI.screenBounds.getWidth());
		
		HBox HBTop = new HBox();
		Label idPartie = new Label(GI.texteLangue.get(GI.langueSelectionne).getProperty("texte.nomPartie")+": " + "");
		idPartie.setFont(Font.font("Comic Sans MS", 30));
		idPartie.setPadding(new Insets(GI.screenBounds.getHeight()*0.02, 0, 0, GI.screenBounds.getWidth()*0.04));
		HBTop.getChildren().add(idPartie);
		this.setTop(HBTop);
		
		nbjoueur = GI.data.getJoueurs().length;
		HBox[] HBJoueurs = new HBox[nbjoueur];
		Button[] ajouterJoueurs = new Button[nbjoueur];
		VBJoueurs = new VBox();
		VBJoueurs.setPrefSize(GI.screenBounds.getHeight(), GI.screenBounds.getWidth());
		VBJoueurs.setPadding(new Insets(GI.screenBounds.getHeight()*0.05, 0, 0, GI.screenBounds.getWidth()*0.25));
		
		for(int i = 0; i < nbjoueur; i++) {
			ajouterJoueurs[i] = new Button("+");
			ajouterJoueurs[i].setPrefSize(GI.screenBounds.getHeight()*0.75, GI.screenBounds.getWidth()*0.06);
			ajouterJoueurs[i].setAlignment(Pos.CENTER);
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
		
		HBox HBJouer = new HBox();
		HBJouer.setPadding(new Insets(GI.screenBounds.getHeight()*0.32,0,0,150));
		VBox VBBouton = new VBox();
		//Button jouer = new Button("Jouer");
		jouer.setFont(Font.font("Comic Sans MS", 30));
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
	
	public void dessineBoxJoueur(GestionnaireInterfacePP GI) {
		HBox BoxJoueur = new HBox();
		BoxJoueur.setAlignment(Pos.CENTER);
		BoxJoueur.setPrefSize(GI.screenBounds.getHeight()*0.75, GI.screenBounds.getWidth()*0.06);
		nbBox++;
		
        VBox VBBouton = new VBox();
        
        Button sup = new Button("^");
        sup.setPrefSize(38, 50);
        sup.setFont(Font.font("Comic Sans MS", 18));
        sup.setOnAction(e -> hausserBox(GI, BoxJoueur));
        
        Button inf = new Button("v");
        inf.setPrefSize(38, 50);
        inf.setFont(Font.font("Comic Sans MS", 18));
        inf.setOnAction(e -> descendreBox(GI, BoxJoueur));
        
        VBBouton.getChildren().addAll(sup, inf);
        VBBouton.setPadding(new Insets(8));
		
		TextField pseudo = new TextField(GI.texteLangue.get(GI.langueSelectionne).getProperty("texte.pseudo"));
		//TextField pseudo = new TextField("Pseudo");
		pseudo.setDisable(true);
//		pseudo.setPromptText(GI.texteLangue.get(GI.langueSelectionne).getProperty("texte.pseudo"));
        pseudo.setFont(Font.font("Comic Sans MS", 20));
        pseudo.setPrefSize(220, 50);
        listPseudo.put(nbBox, pseudo);
        
        ChoiceBox<String> difficulteBots = new ChoiceBox<>();
        difficulteBots.setPrefSize(100, 40);
        difficulteBots.setDisable(true);
        
        ChoiceBox<String> choixCouleur = new ChoiceBox<>();
        String rouge = new String(GI.texteLangue.get(GI.langueSelectionne).getProperty("rouge"));
        String violet = new String(GI.texteLangue.get(GI.langueSelectionne).getProperty("violet"));
        String jaune = new String(GI.texteLangue.get(GI.langueSelectionne).getProperty("jaune"));
        String blanc = new String(GI.texteLangue.get(GI.langueSelectionne).getProperty("blanc"));
        String vert = new String(GI.texteLangue.get(GI.langueSelectionne).getProperty("vert"));
        String bleu = new String(GI.texteLangue.get(GI.langueSelectionne).getProperty("bleu"));
        choixCouleur.getItems().addAll(rouge, violet, jaune, blanc, vert, bleu);
     /*
		BoxJoueur.getChildren().addAll(VBBouton, pseudo, difficulteBots, choixCouleur);
		VBJoueurs.getChildren().add(nbBox, BoxJoueur);
        choixCouleur.setPrefSize(100, 40);
        for (int i = 1; i<=6; i++)
        	choixCouleur.getItems().add(tabCouleur[i-1]);
        choixCouleur.setValue(tabCouleur[nbBox-1]);*/
        
        VBJoueurs.getChildren().remove(nbjoueur-1);
		BoxJoueur.getChildren().addAll(VBBouton, pseudo, difficulteBots, choixCouleur);
		VBJoueurs.getChildren().add(nbBox-1, BoxJoueur);
	}
	
	public void dessineBoxBot(GestionnaireInterfacePP GI) {
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
        
        Button inf = new Button("v");
        inf.setPrefSize(38, 50);
        inf.setFont(Font.font("Comic Sans MS", 18));
        inf.setOnAction(e -> descendreBox(GI, BoxBot));
        
        VBBouton.getChildren().addAll(sup, inf);
        VBBouton.setPadding(new Insets(8));
        
        TextField pseudo = new TextField();
        pseudo.setText("bot" + nbBot);
//        pseudo.setPromptText(GI.texteLangue.get(GI.langueSelectionne).getProperty("texte.pseudo"));
        pseudo.setFont(Font.font("Comic Sans MS", 20));
        pseudo.setPrefSize(220, 40);
        listPseudo.put(nbBox, pseudo);

        ChoiceBox<String> difficulteBots = new ChoiceBox<>();
        difficulteBots.setPrefSize(100, 40);
        difficulteBots.getItems().addAll(GI.texteLangue.get(GI.langueSelectionne).getProperty("texte.facile"),GI.texteLangue.get(GI.langueSelectionne).getProperty("texte.normal"),
        		GI.texteLangue.get(GI.langueSelectionne).getProperty("texte.difficile"));
        difficulteBots.setValue(GI.texteLangue.get(GI.langueSelectionne).getProperty("texte.facile"));
        listDifficulteBots.put(nbBot, difficulteBots);
        
        ChoiceBox<String> choixCouleur = new ChoiceBox<>();
        choixCouleur.setPrefSize(100, 40);
        String rouge = new String(GI.texteLangue.get(GI.langueSelectionne).getProperty("rouge"));
        String violet = new String(GI.texteLangue.get(GI.langueSelectionne).getProperty("violet"));
        String jaune = new String(GI.texteLangue.get(GI.langueSelectionne).getProperty("jaune"));
        String blanc = new String(GI.texteLangue.get(GI.langueSelectionne).getProperty("blanc"));
        String vert = new String(GI.texteLangue.get(GI.langueSelectionne).getProperty("vert"));
        String bleu = new String(GI.texteLangue.get(GI.langueSelectionne).getProperty("bleu"));
        choixCouleur.getItems().addAll(rouge, violet, jaune, blanc, vert, bleu);
		/*
        ChoiceBox<String> difficulteBots = new ChoiceBox<>();
        difficulteBots.setPrefSize(100, 40);
        difficulteBots.getItems().addAll("Facile", "Moyen", "Difficile");
        difficulteBots.setValue("Facile");
        listDifficulteBots.put(nbBot, difficulteBots);*/
        /*
        ChoiceBox<String> choixCouleur = new ChoiceBox<>();
        choixCouleur.setPrefSize(100, 40);
        choixCouleur.setValue(tabCouleur[nbBox-1]);
        for (int i = 1; i<=6; i++)
        	choixCouleur.getItems().add(tabCouleur[i-1]);*/
        listCouleur.put(nbBox, choixCouleur);
        
        HBox boxMoins = new HBox();
        boxMoins.setPadding(new Insets(0,0,15,15));
        
        Button retirerBox = new Button("-");
        retirerBox.setFont(Font.font("Comic Sans MS", 15));
        retirerBox.setPrefSize(30, 30);
        retirerBox.setOnAction(e -> retirerBox(GI, BoxBot));
        
        boxMoins.getChildren().add(retirerBox);
        BoxBot.getChildren().addAll(VBBouton, pseudo, difficulteBots, choixCouleur, boxMoins);
        
        VBJoueurs.getChildren().remove(nbjoueur - 1);
        VBJoueurs.getChildren().add(nbBox-1, BoxBot);
    }
	
	public void retirerBox(GestionnaireInterfacePP GI, HBox Box) {
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
	
	public void descendreBox(GestionnaireInterfacePP GI, HBox Box) {
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
			
			Node HBbas = VBJoueurs.getChildren().get(indexBoxAMonter);
			VBJoueurs.getChildren().removeAll(HBbas, Box);
			VBJoueurs.getChildren().add(indexBoxADescendre, HBbas);
			VBJoueurs.getChildren().add(indexBoxAMonter, Box);
		}
	}
	
	public void hausserBox(GestionnaireInterfacePP GI, HBox Box) {
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
			
			Node HBhaut = VBJoueurs.getChildren().get(indexBoxADescendre);
			VBJoueurs.getChildren().removeAll(HBhaut, Box);
			VBJoueurs.getChildren().add(indexBoxADescendre, Box);
			VBJoueurs.getChildren().add(indexBoxAMonter, HBhaut);
		}
	}
	
	public void lancerPartie(GestionnaireInterfacePP GI, VBox Box) {
		Boolean lancable = true;
		if(Box.getChildren().size()==2)
			Box.getChildren().remove(1);
		
		Label jeu = new Label();
		//System.out.println("!");
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
				//System.out.println(listPseudo.get(i).getText() + " : " + listPseudo.get(k).getText());
				if(listCouleur.get(i).getValue() == listCouleur.get(k).getValue() || listPseudo.get(i).getText() == listPseudo.get(k).getText()) {
					lancable = false;
					jeu.setPadding(new Insets(GI.screenBounds.getHeight()*0.02,0,0,0));
					if(listCouleur.get(i).getValue().equals(listCouleur.get(k).getValue()))
						jeu.setText("Des joueurs ont la même couleur !");
				}
			}
		}
		
		if(lancable == true)
			jeu.setText("Le jeu peut se lancer !");
			GI.Plateau.drawPartie(GI);
			GI.afficherEcran(GI.InterfaceMap.get("Plateau"));
		
		Box.getChildren().add(jeu);		
	}	
}
