import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;

import javax.swing.JButton;
import javax.swing.JFrame;

public class ControleurMastermind implements ActionListener, Serializable {

	private static final long serialVersionUID = 6227308283881655247L;

	private enum EtatControleur {
		DEBUT_COMBINAISON, CHOIX_COULEUR, CHOIX_POSITION, FIN
	}

	private VueMasterMind vue;
	private ModeleMastermind modele;
	private EtatControleur etatCourant;
	private Color couleurChoisie;
	private int numCombinaison;

	public ControleurMastermind(VueMasterMind laVue) {
		this.vue = laVue;
		int nbCouleurs = this.vue.getNbCouleurs();
		int taille = this.vue.getTaille();
		this.modele = new ModeleMastermind(taille, nbCouleurs);
		this.traitementInit();
		this.etatCourant = EtatControleur.DEBUT_COMBINAISON;
		this.numCombinaison = 0;
	}

	@Override
	public void actionPerformed(ActionEvent evt) {
		// on recupère le bouton clique
		JButton boutonClique = (JButton) evt.getSource();

		if (boutonClique.getText().equals("Abandonner")) {
			this.abandon();
			this.etatCourant = EtatControleur.FIN;
		} else {
			// traitement en fonction de l'etat courant
			switch (this.etatCourant) {
			case DEBUT_COMBINAISON:
				// si c'est un bouton de la palette
				if (this.vue.appartientPalette(boutonClique)) {
					// sauvegarde de la couleur choisie
					this.couleurChoisie = boutonClique.getBackground();
					// màj de l'etat courant
					this.etatCourant = EtatControleur.CHOIX_COULEUR;
				}
				break;
			case CHOIX_COULEUR:
				// si c'est un bouton de la combinaison courante du joueur
				if (this.vue.appartientCombinaison(boutonClique, this.numCombinaison)) {
					// mettre la couleur choisie
					boutonClique.setBackground(this.couleurChoisie);
					// màj de l'etat courant
					this.etatCourant = EtatControleur.CHOIX_POSITION;
				}
				// sinon, si c'est un bouton de la palette
				else if (this.vue.appartientPalette(boutonClique)) {
					// sauvegarde dela couleur choisie
					this.couleurChoisie = boutonClique.getBackground();
				}
				break;
			case CHOIX_POSITION:
				// si c'est le bouton de validation
				if (boutonClique.getText().equals("Valider")) {
					this.traitBoutonValider();
				}
				// sinon si c'est un bouton de la palette
				else if (this.vue.appartientPalette(boutonClique)) {
					// sauvegarde de la couleur choisie
					this.couleurChoisie = boutonClique.getBackground();
					// màj de l'etat courant
					this.etatCourant = EtatControleur.CHOIX_COULEUR;
				}
				break;
			case FIN:
				break;
			}
		}
	}

	private void traitBoutonValider() {
		// si la combinaison est complète
		if (this.vue.combinaisonComplete(this.numCombinaison)) {
			this.traitAnalyserCombinaison();

			int nbBiens = this.modele
					.nbChiffresBienPlaces(this.vue.combinaisonEnEntiers(this.numCombinaison));
			// s'il reste au moins 1 essai et combinaison non
			// trouvee
			if (numCombinaison < this.vue.getNbMaxCombinaisons() - 1 && nbBiens != this.vue.getTaille()) {
				this.traitPassageSuivant();
				this.etatCourant = EtatControleur.DEBUT_COMBINAISON;
			}
			// sinon si dernier essai ou combinaison trouvee
			else if (numCombinaison == this.vue.getNbMaxCombinaisons() - 1
					|| nbBiens == this.vue.getTaille()) {
				this.etatCourant = EtatControleur.FIN;
			}
		}
	}

	private void traitementInit() {
		this.modele.genererCombinaison();
	}

	private void traitAnalyserCombinaison() {
		int nbBP = this.modele.nbChiffresBienPlaces(this.vue.combinaisonEnEntiers(this.numCombinaison));
		this.vue.afficherBP(this.numCombinaison, nbBP);
		int nbMP = this.modele.nbChiffresMalPlaces(this.vue.combinaisonEnEntiers(this.numCombinaison));
		this.vue.afficherMP(this.numCombinaison, nbMP);
		this.vue.desactiverCombinaison(this.numCombinaison);
	}

	private void traitPassageSuivant() {
		this.numCombinaison++;
		this.vue.activerCombinaison(this.numCombinaison);
	}

	private void abandon() {
		this.vue.desactiverCombinaison(this.numCombinaison);
		int[] combi = this.modele.getCombinaison();
		this.vue.afficherCombinaisonOrdinateur(combi);
	}

}
