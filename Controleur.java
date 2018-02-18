import java.awt.Color;
import java.awt.event.ActionEvent;

import javax.swing.JButton;

public class Controleur {


	private VueMasterMind vue;
	private Color newColor;
	private JButton btn;
	private ModeleMastermind modele;
	private int lastActiveRow;

	private enum Etat {
		start, colorChosen, positionChosen, gameOver
	};

	private Etat state;

	public Controleur(VueMasterMind vue) {
		this.vue = vue;
		this.modele = new ModeleMastermind();
		this.state = Etat.start;
		this.btn = new JButton();
		this.lastActiveRow = 0;
	}

	public void activerBtns() {
		this.modele.genererCombinaison();
	}

	public void saveColor(ActionEvent e) {
		switch (this.state) {
		case start:
			for (int i = 0; i < vue.getnbCouleurs(); i++) {
				if (!(vue.getPaletteIHM()[i].equals((JButton) e.getSource()))) {
					this.btn = (JButton) e.getSource();
					this.newColor = this.btn.getBackground();
					this.state = Etat.colorChosen;
				}
			}
			break;
		case positionChosen:
			for (int i = 0; i < vue.getnbCouleurs(); i++) {
				if (!(vue.getPaletteIHM()[i].equals((JButton) e.getSource()))) {
					this.btn = (JButton) e.getSource();
					this.newColor = this.btn.getBackground();
					this.state = Etat.colorChosen;
				}
			}
			break;
		default:
			break;
		}
	}

	public void putColorInRow(ActionEvent e) {
		switch (this.state) {
		case colorChosen:
			for (int i = 0; i < VueMasterMind.getNBMAX_COMBINAISONS(); i++) {
				for (int j = 0; j < vue.getTaille(); j++) {
					if (!(vue.getCombinaisonsJoueurIHM()[j][i]
							.equals((JButton) e.getSource()))) {
						vue.activerCombinaison(i);
						this.btn = (JButton) e.getSource();
						vue.setButtonColorUser(newColor, j, i);
						this.state = Etat.colorChosen;
					}
				}
			}
			break;
		default:
			break;
		}

	}

	public void activateNextRow() {
		if (this.state == Etat.positionChosen) {
			if (vue.getCombinaisonsJoueurIHM()[0][this.lastActiveRow]
					.isEnabled() == true) {
				vue.activerCombinaison(this.lastActiveRow + 1);
			}
		}
	}

	public void goodOrBad(int r) {
		int i = 0;
		switch (this.state) {
		case positionChosen:
			for (int j = 0; j < vue.getTaille(); j++) {
				if (vue.getCombinaisonsJoueurIHM()[r][j].equals(VueMasterMind
						.chiffreEnCouleur(Integer.parseInt(vue
								.getCombinaisonOrdiIHM()[j].getText())))) {
					i++;
				}
			}
			vue.afficherBP(r, i);
			vue.afficherMP(r, vue.getTaille() - i);
			vue.desactiverCombinaison(r);
			break;
		default:
			break;
		}

	}

	public void showAnswer() {
		int[] tableauCouleurs = new int[4];
		switch (this.state) {
		case positionChosen:
			vue.afficherCombinaisonOrdinateur(tableauCouleurs);
			break;
		default:
			break;
		}
	}

}
