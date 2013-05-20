import java.util.ArrayList;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Track;
import javax.swing.JCheckBox;

public class MidiTrack {

	Sequencer sequencer;
	Sequence seq;
	Track track;
	int[] instruments;

	public MidiTrack() {
		instruments = new int[] { 35, 42, 46, 38, 49, 39, 50, 60, 70, 72, 64,
				56, 58, 47, 67, 63 };
		try {

			sequencer = MidiSystem.getSequencer();
			sequencer.setLoopCount(sequencer.LOOP_CONTINUOUSLY);
			sequencer.open();
			seq = new Sequence(Sequence.PPQ, 4);
			sequencer.setTempoInBPM(120);

		} catch (MidiUnavailableException | InvalidMidiDataException e) {
			e.printStackTrace();
		}
	}

  //Deletes current track and calls methods to create and start new one	
	public void startBeat(ArrayList<JCheckBox> checklist) {
		seq.deleteTrack(track);
		track = seq.createTrack();
		setInstruments(checklist);
		track.add(makeEvent(192, 9, 1, 0, 16));
		try {
			sequencer.setSequence(seq);
			sequencer.start();
		} catch (InvalidMidiDataException e) {
			e.printStackTrace();
		}
	}

	
	public void stopBeat() {
		sequencer.stop();
	}

  //Changes track tempo
	public void setTempo(String downUp) {
		float tempo = sequencer.getTempoFactor();
		if (downUp.equals("up")) {
			sequencer.setTempoFactor(tempo * 1.03f);
		} else if (downUp.equals("down")) {
			sequencer.setTempoFactor(tempo * 0.97f);
		}
	}

	
	public void makeTracks(int[] tracklist) {
		for (int i = 0; i < 16; i++) {
			int key = tracklist[i];
			if (key != 0) {
				track.add(makeEvent(144, 9, key, 100, i));
				track.add(makeEvent(128, 9, key, 100, i + 1));
			}
		}
	}

	
	public void setInstruments(ArrayList<JCheckBox> checkboxes) {
		int[] tracklist = null;
		
		for (int i = 0; i < 16; i++) {
			tracklist = new int[16];
			
			for (int j = 0; j < 16; j++) {
				JCheckBox jc = checkboxes.get(j + (16 * i));
				if (jc.isSelected()) {
					tracklist[j] = instruments[i];
				} else {
					tracklist[j] = 0;
				}
			}
			makeTracks(tracklist);
		}
	}
	
	
	public MidiEvent makeEvent(int command, int channel, int data1, int data2,
			int tick) {
		MidiEvent event = null;
		ShortMessage a = new ShortMessage();
		try {
			a.setMessage(command, channel, data1, data2);
			event = new MidiEvent(a, tick);
		} catch (InvalidMidiDataException e) {
			e.printStackTrace();
		}
		return event;
	}
}
