package fabricator

import scala.util.Random
import javax.sound.midi.Sequence
import com.googlecode.scala.sound.midi._
import com.googlecode.scala.sound.midi.Notes._
import com.googlecode.scala.sound.midi.MidiEvent
import com.googlecode.scala.sound.midi.message._
import java.io.File

class FileGenerator (private val utility: UtilityService,
            private val alpha: Alphanumeric,
            private val random: Random,
            private val contact: Contact,
            private val word: Words){

  def this() {
    this(new UtilityService(),
      new Alphanumeric(),
      new Random(),
      new Contact(),
      new Words())
  }

  def midiFile(): Unit = {
    val sequence = new Sequence(Sequence.PPQ, 24)
    val track = sequence.createTrack()

    MidiEvent(GeneralMIDIOn(), 0) -> track
    MidiEvent(SetTempo(457), 0) -> track  //457.76 in example code
    MidiEvent(TrackName("midifile track"), 0) -> track
    MidiEvent(OmniOn(), 0) -> track
    MidiEvent(PolyOn(), 0) -> track
    MidiEvent(ProgramChange(0, 0), 0) -> track
    MidiEvent(NoteOn(0, C4, 96), 1) -> track
    MidiEvent(NoteOff(0, C4, 64), 121) -> track
    MidiEvent(EndOfTrack(), 140) -> track

    sequence >> new File("midifile.mid")
  }

}
