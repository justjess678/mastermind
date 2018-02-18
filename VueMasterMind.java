
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.io.Serializable;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class VueMasterMind extends JPanel implements Serializable {

	private static Color[] tabCouleurs = { Color.BLUE, Color.RED, Color.GREEN, 
									Color.YELLOW, Color.CYAN, Color.PINK,
									Color.LIGHT_GRAY, Color.ORANGE, 
									Color.MAGENTA, Color.BLACK };

	private static final long serialVersionUID = 6621533929077756561L;

	/**
	 * Tableau des champs contenant le nombre de couleurs bien placées pour
	 * chaque combinaison
	 */
	private JTextField[] bPIHM;

	/**
	 * Tableau des champs contenant les couleurs de la combinaison à trouver
	 */
	private JTextField[] combinaisonOrdiIHM;

	/**
	 * Tableau des champs contenant les combinaisons de couleurs proposées par
	 * l'utilisateur
	 */
	private JButton[][] combinaisonsJoueurIHM;

	/**
	 * Tableau des champs contenant le nombre de couleurs mal placées pour
	 * chaque combinaison
	 */
	private JTextField[] mPIHM;

	/**
	 * Nombre de couleurs possible dans la combinaison a trouver
	 */
	private int nbCouleurs;

	/**
	 * Nombre maximum d'essais pour trouver la combinaison de couleurs
	 */
	private static int NBMAX_COMBINAISONS = 10;

	/**
	 * Ensemble des boutons contenant les couleurs de la palette
	 */
	private JButton[] paletteIHM;

	/**
	 * Taille de la combinaison à trouver
	 */
	private int taille;

	public VueMasterMind() {
		// valeurs par défaut
		this.init(6, 4);
	}

	public VueMasterMind(int nbCouleurs, int tailleCombi) {
		this.init(nbCouleurs, tailleCombi);
	}

	private void init(int nb, int t) {
		this.taille = t;
		this.nbCouleurs = nb;
		this.combinaisonOrdiIHM = new JTextField[this.taille];

		this.paletteIHM = new JButton[this.nbCouleurs];
		this.combinaisonsJoueurIHM = new JButton[NBMAX_COMBINAISONS][this.taille];
		this.bPIHM = new JTextField[NBMAX_COMBINAISONS];
		this.mPIHM = new JTextField[NBMAX_COMBINAISONS];

		this.setLayout(new BorderLayout());

		ActionListener ecouteur = new ControleurMastermind(this);

		JPanel pNorth = this.createPanelNorth(ecouteur);
		this.add(pNorth, BorderLayout.NORTH);

		JPanel pCenter = this.createPanelCenter(ecouteur);
		this.add(pCenter, BorderLayout.CENTER);

		JPanel pSouth = this.createPanelSouth(ecouteur);
		this.add(pSouth, BorderLayout.SOUTH);
	}

	private JPanel createPanelNorth(ActionListener ecouteur) {		
		JPanel panel = new JPanel();
//		panel.setLayout(new FlowLayout());
		panel.setLayout(new BorderLayout());
		// étiquette
		JLabel label = new JLabel("Couleurs disponibles : ", JLabel.CENTER);
		label.setFont(new Font("Serif", Font.ITALIC + Font.BOLD, 20));
//		panel.add(label);
		panel.add(label, BorderLayout.WEST);
		// panneau contenant les boutons de couleurs
		JPanel pColors = new JPanel();
		pColors.setLayout(new GridLayout(1, this.nbCouleurs));
		for (int i = 0; i < this.nbCouleurs; i++) {
			this.paletteIHM[i] = new JButton();
			this.paletteIHM[i].setPreferredSize(new Dimension(36, 20));
			this.paletteIHM[i].addActionListener(ecouteur);
			pColors.add(this.paletteIHM[i]);
			pColors.getComponent(i).setBackground(tabCouleurs[i]);
		}
//		panel.add(pColors);
		panel.add(pColors, BorderLayout.CENTER);
		return panel;
	}

	private JPanel createPanelCenter(ActionListener ecouteur) {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(NBMAX_COMBINAISONS, 2));

		for (int i = 0; i < NBMAX_COMBINAISONS; i++) {
			// panneau de gauche - combinaisons
			JPanel pCombis = new JPanel();
			pCombis.setLayout(new GridLayout(1, this.taille));
			panel.add(pCombis);
			for (int j = 0; j < this.taille; j++) {
				JButton bt = new JButton();
				bt.addActionListener(ecouteur);
				bt.setEnabled(false);
				this.combinaisonsJoueurIHM[i][j] = bt;
				pCombis.add(bt);
			}
			
			// panneau de droite - vérification combinaisons
			JPanel pVerifs = new JPanel();
			pVerifs.setLayout(new GridLayout(2, 2));
			panel.add(pVerifs);

			JLabel lBP = new JLabel("BP", JLabel.CENTER);
			lBP.setFont(new Font("Serif", Font.PLAIN, 24));
						
			JLabel lMP = new JLabel("MP", JLabel.CENTER);
			lMP.setFont(new Font("Serif", Font.PLAIN, 24));
			pVerifs.add(lBP);
			pVerifs.add(lMP);

			JTextField tf = new JTextField();
			tf.setFont(new Font("Serif", Font.BOLD, 20));
			tf.setEditable(false);
			tf.setForeground(new Color(0, 128, 0));

			this.bPIHM[i] = tf;
			pVerifs.add(this.bPIHM[i]);

			tf = new JTextField();
			tf.setFont(new Font("Serif", Font.BOLD, 20));
			tf.setEditable(false);
			tf.setForeground(new Color(255, 0, 0));
			this.mPIHM[i] = tf;
			pVerifs.add(this.mPIHM[i]);
		}
		this.activerCombinaison(0);
		return panel;
	}

	private JPanel createPanelSouth(ActionListener ecouteur) {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1, 2));
		// panneau pour les champs textuels
		JPanel pTextFields = new JPanel();
		pTextFields.setLayout(new GridLayout(1, this.taille));
		for (int i = 0; i < this.taille; i++) {
			JTextField tf = new JTextField("");			
			tf.setEditable(false);
			pTextFields.add(tf);
			this.combinaisonOrdiIHM[i] = tf;
		}
		panel.add(pTextFields);
		// boutons
		JPanel pBoutons = new JPanel();
		pBoutons.setLayout(new GridLayout(1, 2));
		panel.add(pBoutons);
		// bouton de validation
		JButton bValider = new JButton("Valider");		
		bValider.setFont(new Font("Serif", Font.BOLD, 24));
		bValider.addActionListener(ecouteur);
		pBoutons.add(bValider);
		// bouton d'annulation
		JButton bAnnuler = new JButton("Abandonner");		
		bAnnuler.setFont(new Font("Serif", Font.BOLD, 24));
		bAnnuler.addActionListener(ecouteur);
		pBoutons.add(bAnnuler);
		
		return panel;
	}

	/**
	 * Rend actif les boutons de la combinaison de rang donné
	 * 
	 * @param i
	 *            : le rang correspondant
	 */
	public void activerCombinaison(int i) {
		for (int j = 0; j < this.taille; j++) {
			this.combinaisonsJoueurIHM[i][j].setEnabled(true);
		}
	}

	/**
	 * Affiche pour la combinaison de rang i du joueur le nombre de couleurs
	 * bien placées
	 * 
	 * @param i
	 *            : le rang correspondant
	 * @param nbBP
	 *            : nombre de couleurs bien placées
	 */
	public void afficherBP(int i, int nbBP) {
		this.bPIHM[i].setText("" + nbBP);
		this.bPIHM[i].setHorizontalAlignment(JTextField.CENTER);
	}

	/**
	 * Affiche la combinaison de l'ordinateur en bas du panneau en fonction d'un
	 * tableau
	 * 
	 * @param tableauCouleurs
	 *            : tableau d'entiers codant les couleurs
	 */
	public void afficherCombinaisonOrdinateur(int[] tableauCouleurs) {
		for (int i = 0; i < this.taille; i++) {
			Color color = VueMasterMind.chiffreEnCouleur(tableauCouleurs[i]);
			this.combinaisonOrdiIHM[i].setBackground(color);
		}
	}

	/**
	 * Affiche pour la combinaison de rang i du joueur le nombre de couleurs mal
	 * placées
	 * 
	 * @param i
	 *            : le rang correspondant
	 * @param nbBP
	 *            : ?nombre de couleurs mal placées
	 */
	public void afficherMP(int i, int nbMP) {
		this.mPIHM[i].setText("" + nbMP);
		this.mPIHM[i].setHorizontalAlignment(JTextField.CENTER);
	}

	/**
	 * Examine si le bouton b appartient au tableau des boutons contenant la
	 * combinaison de rang donné
	 * 
	 * @param b
	 *            : le bouton correspondant
	 * @param i
	 *            : le rand correspondant
	 * @return true : si b appartient au tableau correspondant false : sinon
	 */
	public boolean appartientCombinaison(JButton b, int i) {
		// recherche d'une correspondance
		int indice;
		for (indice = 0; indice < this.taille && b != this.combinaisonsJoueurIHM[i][indice]; indice++) { }
		// si indice < taille combi
		// alors correspondance trouvée
		// sinon indice > taille combi
		return (indice < this.taille);
	}

	/**
	 * Examine si le bouton b appartient au tableau de la palette des couleurs
	 * possibles
	 * 
	 * @param b
	 *            : le bouton correspondant
	 * @return true : si b appartient aux couleurs possibles false : sinon
	 */
	public boolean appartientPalette(JButton b) {
		// recherche d'une correspondance
		int indice;
		for (indice = 0; indice < this.nbCouleurs && b != this.paletteIHM[indice]; indice++) { }
		// si indice < nb couleurs dispos
		// alors correspondance trouvée
		// sinon indice > nb couleurs dispos
		return (indice < this.nbCouleurs);
	}

	/**
	 * Code un entier en une couleur (par ex : 0 donne BLUE, 1 donne RED, 2
	 * donne GREEN …)
	 * 
	 * @param i
	 *            : l'entier à coder en couleur
	 * @return une couleur de AWT correspondant à l'entier codé
	 */
	public static Color chiffreEnCouleur(int i) {
		// recherche d'une correspondance
		int indice;
		for (indice = 0; indice < tabCouleurs.length && indice != i; indice++) { }
		// si indice < tabCouleurs.length
		// alors correspondance trouvée
		// sinon indice > tabCouleurs.length
		if (indice < tabCouleurs.length)
			return tabCouleurs[i];
		return null;
	}

	/**
	 * Teste si la combinaison de rang donné est complète
	 * 
	 * @param i
	 *            : le rang correspondant
	 * @return true : si tous les boutons ont une couleur false : sinon
	 */
	public boolean combinaisonComplete(int i) {
		int corrects = 0;
		for (JButton bt : this.combinaisonsJoueurIHM[i]) {
			if (couleurEnChiffre(bt.getBackground()) != -1)
				corrects++;
		}
		return (corrects == this.taille);
	}

	/**
	 * Convertit les couleurs contenues dans les boutons d'une combinaison de
	 * rand donné en un tableau d'entiers
	 * 
	 * @param i
	 *            : rang correspondant
	 * @return un tableau d'entiers correspondant aux couleurs des boutons au
	 *         rang i
	 */
	public int[] combinaisonEnEntiers(int i) {
		int[] convert = new int[this.taille];
		for (int k = 0; k < this.taille; k++) {
			JButton bt = this.combinaisonsJoueurIHM[i][k];
			convert[k] = VueMasterMind.couleurEnChiffre(bt.getBackground());
		}
		return convert;
	}

	/**
	 * Code une couleur en un entier (par ex : BLUE donne 0, RED donne 1, GREEN
	 * donne 2 …)
	 * 
	 * @param c
	 *            : la couleur à convertir
	 * @return un entier correspondant à la couleur codée
	 */
	public static int couleurEnChiffre(Color c) {
		// recherche d'une correspondance
		int indice;
		for (indice = 0; indice < tabCouleurs.length && tabCouleurs[indice] != c; indice++) { }
		// si indice < tabCouleurs.length
		// alors correspondance trouvée
		// sinon indice > tabCouleurs.length
		return (indice < tabCouleurs.length ? indice : -1);
	}

	/**
	 * Rend inactif les boutons de la combinaison de rang donné
	 * 
	 * @param i
	 *            : le rang correspondant
	 */
	public void desactiverCombinaison(int i) {
		for (int j = 0; j < this.taille; j++) {
			this.combinaisonsJoueurIHM[i][j].setEnabled(false);
		}
	}

	/**
	 * Acceseur en lecture
	 * 
	 * @return le nombre de couleurs disponibles
	 */
	public int getNbCouleurs() {
		return this.nbCouleurs;
	}

	/**
	 * Accesseur en lecture
	 * 
	 * @return renvoie la taille de la combinaison
	 */
	public int getTaille() {
		return this.taille;
	}

	/**
	 * Acceseur en lecture
	 * 
	 * @return le nombre de combinaison max
	 */
	public int getNbMaxCombinaisons() {
		return NBMAX_COMBINAISONS;
	}

	
}
