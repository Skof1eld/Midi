import javax.sound.midi.*;
import java.util.Scanner;

public class Main {
        public static void main(String[] args) throws MidiUnavailableException, InvalidMidiDataException, InterruptedException {
        Synthesizer synthesizer = MidiSystem.getSynthesizer();
        synthesizer.open();
        Receiver receiver = synthesizer.getReceiver();

        receiver.send(new ShortMessage(ShortMessage.PROGRAM_CHANGE, 3, 100), -1);

        System.out.println("¬ведите ноты");
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();

        while (!input.equals("exit")) {
            for (int i = 0; i < input.length(); i++) {
                int note = convertNote(input.charAt(i));
                playNote(receiver, note, 200);
            }
            input = scanner.nextLine();
        }

        synthesizer.close();
        scanner.close();
    }

    public static int convertNote(char note) {
        return switch (note) {
            case 'C' -> 60;
            case 'D' -> 62;
            case 'E' -> 64;
            case 'F' -> 65;
            case 'G' -> 67;
            case 'A' -> 69;
            case 'B' -> 71;
            default -> 110;
        };
    }

    public static void playNote(Receiver receiver, int note, int length) throws InvalidMidiDataException, InterruptedException {
        ShortMessage noteOnMessage = new ShortMessage();
        noteOnMessage.setMessage(ShortMessage.NOTE_ON, note, 100);
        receiver.send(noteOnMessage, -1);
        Thread.sleep(length);

        ShortMessage noteOffMessage = new ShortMessage();
        noteOffMessage.setMessage(ShortMessage.NOTE_OFF, note, 0);
        receiver.send(noteOffMessage, -1);
        Thread.sleep(20);
    }
}