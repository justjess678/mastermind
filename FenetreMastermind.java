import java.awt.MenuItem;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;


public class FenetreMastermind extends JFrame{
	
	private JMenuItem itemTailleCombinaison;
	private JMenuItem itemNbCouleurs;
	private static long serialVersionUID;
	private VueMasterMind vueMastermind;
	private ControleurFenetre contr;
	
	public FenetreMastermind(VueMasterMind vue){
		JMenuBar menu = new JMenuBar();
		this.add(menu);
		//jouer section of menu
		JMenu jouer = new JMenu("Jouer");
		menu.add(jouer);
		JMenuItem rejouer = new JMenuItem("rejouer");
		jouer.add(rejouer);
		JMenuItem sauvegarder = new JMenuItem("sauvegarder");
		jouer.add(sauvegarder);
		JMenuItem restaurer = new JMenuItem("restaurer");
		jouer.add(restaurer);
		jouer.addSeparator();
		JMenuItem quitter = new JMenuItem("quitter");
		jouer.add(quitter);
		//options section of menu
		JMenu options = new JMenu("Options");
		menu.add(options);
		JMenu nbCouleurs = new JMenu("nombre de couleurs");
		options.add(nbCouleurs);
		for (int i=2; i<11;i++){
			JMenuItem itemNbCouleurs = new JMenuItem(""+i);
			nbCouleurs.add(itemNbCouleurs);
		}
		JMenu taille = new JMenu("taille de la combinaison");
		options.add(taille);
		for (int i=2; i<11;i++){
			JMenuItem itemTailleCombinaison = new JMenuItem(""+i);
			taille.add(itemTailleCombinaison);
		}
		this.setJMenuBar(menu);
		this.vueMastermind=vue;
		this.add(this.vueMastermind);
		this.pack();
		this.setSize(700, 860);
		this.setVisible(true);
		this.contr= new ControleurFenetre(this);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	
	
	public void changerItemNbCouleurs(JMenuItem item){
		//change l'item associe au nombre de couleurs par celui passe en parametre
		this.contr.actionPerformed(vueMastermind.);
		
	}
	
	public void changerItemTailleCombinaison(JMenuItem item){
		//change l'item associe a la taille de la combinaison par celui passe en parametre
	}
	
	/*utiliser la méthode remove() pour enlever un composant graphique d’un composant de
	 * réceptacle, et la méthode repaint() pour rafraichir le panneau après modification
	 * de ses composants.*/
	public void creerNouvelleVueMastermind(){
		//cree une nouvelle vue mastermind qui remplace l'ancienne
	}
	
	public void restaurerVueMastermindFichier(String nomFichier){
		//remplace la vue du jeu de mastermind par celle sauvegardee dans un fichier
	}
	
	
	/*il est nécessaire d’utiliser le mécanisme de persistance des objets mis en oeuvre en
	 * Java au travers de la « sérialisation ».*/
	public void sauvegarderVueMastermindFichier(String nomFichier){
		//sauvegarde la vue du jeu de mastermind dans un fichier
	}
	
}
