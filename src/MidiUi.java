import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class MidiUi extends JFrame{
	private ArrayList<JCheckBox> cBoxes;
	private JButton startBut;
	private JButton stopBut;
	private JButton tempoUpBut;
	private JButton tempoDnBut;
	private String[] labStrings;
	private JPanel background;
	private JPanel leftPan;
	private JPanel centerPan;
	private JPanel rightPan;
	MidiTrack midi;



	
	public MidiUi(){
		midi = new MidiTrack();
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(800, 500);
		cBoxes = new ArrayList<>();
		labStrings = new String[]{"Bass Drum", "Closed Hi-Hat","Open Hi-Hat", "Acoustic Snare", "Crash Cymbal",
				"Hand Clap", "High Tom", "Hi Bongo", "Maracas", "Whistle", "Low Conga", "Cowbell", "Vibraslap",
				"Low-mid Tom", "High Agogo", "Open Hi Conga"};
	
		background = new JPanel();
		background.setLayout(new BorderLayout());
		background.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
	
		
    //Add instrument labels
		GridLayout labelGrid = new GridLayout(16,1);
		labelGrid.setVgap(2);
		leftPan = new JPanel(labelGrid);
		for(int i=0; i< labStrings.length; i++){
			leftPan.add(new JLabel(labStrings[i]));
		}
		background.add(leftPan, BorderLayout.WEST);
		
    
	//Add control buttons	
		rightPan = new JPanel();
		rightPan.setLayout(new BoxLayout(rightPan, BoxLayout.Y_AXIS));
		startBut = new JButton("Start");
		startBut.addActionListener(new StartEv());
		rightPan.add(startBut);
		stopBut = new JButton("Stop");
		stopBut.addActionListener(new StopEv());
		rightPan.add(stopBut);
		tempoUpBut = new JButton("Tempo Up");
		tempoUpBut.addActionListener(new TempoUpEv());
		rightPan.add(tempoUpBut);
		tempoDnBut = new JButton("Tempo Down");
		tempoDnBut.addActionListener(new TempoDnEv());
		rightPan.add(tempoDnBut);
		background.add(rightPan, BorderLayout.EAST);
		
		
	//Add checkboxes	
        GridLayout grid = new GridLayout(16,16);
        grid.setHgap(1);
        grid.setVgap(2);
		centerPan = new JPanel(grid);
		for(int i =0; i<256; i++){
			cBoxes.add(new JCheckBox());
			centerPan.add(cBoxes.get(i));
		}
		background.add(centerPan, BorderLayout.CENTER);
		
		
	//Add all to frame
		this.add(background);
		this.pack();
		
	}
	
	
	
	
	class StartEv implements ActionListener{

		public void actionPerformed(ActionEvent arg0) {
			midi.startBeat(cBoxes);
		//	System.out.print("start");
			
		}
	}
	
	
	class StopEv implements ActionListener{
		public void actionPerformed(ActionEvent arg0) {
			midi.stopBeat();
		}
	}

	
	class TempoUpEv implements ActionListener{
		public void actionPerformed(ActionEvent arg0) {
			midi.setTempo("up");
		}
	}

	
	class TempoDnEv implements ActionListener{
		public void actionPerformed(ActionEvent arg0) {
			midi.setTempo("down");
		}
	}
}
