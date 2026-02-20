import java.io.FileReader;
import java.io.IOException;
import java.io.PushbackReader;
import java.io.StringReader;

public class Main {
    public static void main(String[] args) throws IOException {

        String input = "declare score123";

        PushbackReader pbr = new PushbackReader(new StringReader(input));
        Scanner scannerTest = new Scanner(pbr);

        Scanner.TOKEN token;

        while((token = scannerTest.scan()) != Scanner.TOKEN.SCANEOF){
            System.out.println(token + " -> " + scannerTest.getTokenBufferString());
        }

    }
}
