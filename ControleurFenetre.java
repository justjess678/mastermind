import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

import javax.swing.JMenuItem;


public class ControleurFenetre implements ActionListener{
	
	private FenetreMastermind fen;
	
	public ControleurFenetre(FenetreMastermind fen){
		this.fen=fen;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		///creer de nouveaux controlleurs individuels pour chaque boutton sauf quitter!!!
		JMenuItem choice = (JMenuItem)e.getSource();
		switch (choice.getText()){
		case "rejouer": 
			this.fen.creerNouvelleVueMastermind();
			break;
		case "sauvegarder":
			this.fen.sauvegarderVueMastermindFichier("toto");
			break;
		case "restaurer":
			this.fen.restaurerVueMastermindFichier("toto");
			break;
		case "quitter":
			System.exit(0);
			break;
		default: break;
		}
		
	}
	
	//item.addActionListener()

}
