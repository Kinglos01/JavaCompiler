import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.PushbackReader;
import java.io.StringReader;

import static org.junit.jupiter.api.Assertions.*;
class ScannerTest {

    @Test
    void declareTest() throws IOException {

            String input = "declare score123";
            PushbackReader pbr = new PushbackReader(new StringReader(input));
            Scanner scannerTest = new Scanner(pbr);

           Scanner.TOKEN tokenDeclare = scannerTest.scan();
           assertEquals(Scanner.TOKEN.DECLARE, tokenDeclare);
           System.out.println(tokenDeclare + " -> " + scannerTest.getTokenBufferString());

           Scanner.TOKEN tokenID = scannerTest.scan();
           assertEquals(Scanner.TOKEN.ID, tokenID);
           System.out.println(tokenID + " -> " + scannerTest.getTokenBufferString());

           Scanner.TOKEN tokenEOF = scannerTest.scan();
           assertEquals(Scanner.TOKEN.SCANEOF, tokenEOF);
           System.out.println(tokenEOF);


    }

    @Test
    void initTest() throws IOException {

        String input = "initialize score = 600";
        PushbackReader pbr = new PushbackReader(new StringReader(input));
        Scanner scannerTest = new Scanner(pbr);

        Scanner.TOKEN tokenInit = scannerTest.scan();
        assertEquals(Scanner.TOKEN.INITIALIZE, tokenInit);
        System.out.println(tokenInit + " -> " + scannerTest.getTokenBufferString());

        Scanner.TOKEN tokenID = scannerTest.scan();
        assertEquals(Scanner.TOKEN.ID, tokenID);
        System.out.println(tokenID + " -> " + scannerTest.getTokenBufferString());

        Scanner.TOKEN tokenEquals = scannerTest.scan();
        assertEquals(Scanner.TOKEN.EQUALS, tokenEquals);
        System.out.println(tokenEquals);

        Scanner.TOKEN intConst = scannerTest.scan();
        assertEquals(Scanner.TOKEN.INTCONST, intConst);
        System.out.println(intConst + " -> " + scannerTest.getTokenBufferString());

        Scanner.TOKEN tokenEOF = scannerTest.scan();
        assertEquals(Scanner.TOKEN.SCANEOF, tokenEOF);
        System.out.println(tokenEOF);
    }

    @Test
    void calcTest() throws IOException {

        String input = "calc newsalary = originalsalary + raise";
        PushbackReader pbr = new PushbackReader(new StringReader(input));
        Scanner scannerTest = new Scanner(pbr);

        Scanner.TOKEN tokenCalc = scannerTest.scan();
        assertEquals(Scanner.TOKEN.CALC, tokenCalc);
        System.out.println(tokenCalc + " -> " + scannerTest.getTokenBufferString());

        Scanner.TOKEN tokenID = scannerTest.scan();
        assertEquals(Scanner.TOKEN.ID, tokenID);
        System.out.println(tokenID + " -> " + scannerTest.getTokenBufferString());

        Scanner.TOKEN tokenEquals = scannerTest.scan();
        assertEquals(Scanner.TOKEN.EQUALS, tokenEquals);
        System.out.println(tokenEquals);

        Scanner.TOKEN tokenID2 = scannerTest.scan();
        assertEquals(Scanner.TOKEN.ID, tokenID2);
        System.out.println(tokenID2 + " -> " + scannerTest.getTokenBufferString());

        Scanner.TOKEN tokenPlus = scannerTest.scan();
        assertEquals(Scanner.TOKEN.PLUS, tokenPlus);
        System.out.println(tokenPlus);

        Scanner.TOKEN tokenID3 = scannerTest.scan();
        assertEquals(Scanner.TOKEN.ID, tokenID3);
        System.out.println(tokenID3 + " -> " + scannerTest.getTokenBufferString());

        Scanner.TOKEN tokenEOF = scannerTest.scan();
        assertEquals(Scanner.TOKEN.SCANEOF, tokenEOF);
        System.out.println(tokenEOF);
    }

    @Test
    void printTest() throws IOException {

        String input = "print salary";
        PushbackReader pbr = new PushbackReader(new StringReader(input));
        Scanner scannerTest = new Scanner(pbr);

        Scanner.TOKEN tokenPrint = scannerTest.scan();
        assertEquals(Scanner.TOKEN.PRINT, tokenPrint);
        System.out.println(tokenPrint + " -> " + scannerTest.getTokenBufferString());

        Scanner.TOKEN tokenID = scannerTest.scan();
        assertEquals(Scanner.TOKEN.ID, tokenID);
        System.out.println(tokenID + " -> " + scannerTest.getTokenBufferString());

        Scanner.TOKEN tokenEOF = scannerTest.scan();
        assertEquals(Scanner.TOKEN.SCANEOF, tokenEOF);
        System.out.println(tokenEOF);

    }

    @Test
    void xTest1() throws IOException {

        String input = "if x = y then endif";
        PushbackReader pbr = new PushbackReader(new StringReader(input));
        Scanner scannerTest = new Scanner(pbr);

        Scanner.TOKEN tokenIf = scannerTest.scan();
        assertEquals(Scanner.TOKEN.IF, tokenIf);
        System.out.println(tokenIf + " -> " + scannerTest.getTokenBufferString());

        Scanner.TOKEN tokenId = scannerTest.scan();
        assertEquals(Scanner.TOKEN.ID, tokenId);
        System.out.println(tokenId + " -> " + scannerTest.getTokenBufferString());

        Scanner.TOKEN tokenEquals = scannerTest.scan();
        assertEquals(Scanner.TOKEN.EQUALS, tokenEquals);
        System.out.println(tokenEquals);

        Scanner.TOKEN tokenID2 = scannerTest.scan();
        assertEquals(Scanner.TOKEN.ID, tokenID2);
        System.out.println(tokenID2 + " -> " + scannerTest.getTokenBufferString());

        Scanner.TOKEN tokenThen = scannerTest.scan();
        assertEquals(Scanner.TOKEN.THEN, tokenThen);
        System.out.println(tokenThen + " -> " + scannerTest.getTokenBufferString());

        Scanner.TOKEN tokenEndif = scannerTest.scan();
        assertEquals(Scanner.TOKEN.ENDIF, tokenEndif);
        System.out.println(tokenEndif + " -> " + scannerTest.getTokenBufferString());

        Scanner.TOKEN tokenEOF = scannerTest.scan();
        assertEquals(Scanner.TOKEN.SCANEOF, tokenEOF);
        System.out.println(tokenEOF);

    }

    @Test
    void xTest2() throws IOException {

        String input = "if x = y then print x endif";
        PushbackReader pbr = new PushbackReader(new StringReader(input));
        Scanner scannerTest = new Scanner(pbr);

        Scanner.TOKEN tokenIf = scannerTest.scan();
        assertEquals(Scanner.TOKEN.IF, tokenIf);
        System.out.println(tokenIf + " -> " + scannerTest.getTokenBufferString());

        Scanner.TOKEN tokenId = scannerTest.scan();
        assertEquals(Scanner.TOKEN.ID, tokenId);
        System.out.println(tokenId + " -> " + scannerTest.getTokenBufferString());

        Scanner.TOKEN tokenEquals = scannerTest.scan();
        assertEquals(Scanner.TOKEN.EQUALS, tokenEquals);
        System.out.println(tokenEquals);

        Scanner.TOKEN tokenID2 = scannerTest.scan();
        assertEquals(Scanner.TOKEN.ID, tokenID2);
        System.out.println(tokenID2 + " -> " + scannerTest.getTokenBufferString());

        Scanner.TOKEN tokenThen = scannerTest.scan();
        assertEquals(Scanner.TOKEN.THEN, tokenThen);
        System.out.println(tokenThen + " -> " + scannerTest.getTokenBufferString());

        Scanner.TOKEN tokenPrint = scannerTest.scan();
        assertEquals(Scanner.TOKEN.PRINT, tokenPrint);
        System.out.println(tokenPrint + " -> " + scannerTest.getTokenBufferString());

        Scanner.TOKEN tokenID = scannerTest.scan();
        assertEquals(Scanner.TOKEN.ID, tokenID);
        System.out.println(tokenID + " -> " + scannerTest.getTokenBufferString());

        Scanner.TOKEN tokenEndif = scannerTest.scan();
        assertEquals(Scanner.TOKEN.ENDIF, tokenEndif);
        System.out.println(tokenEndif + " -> " + scannerTest.getTokenBufferString());

        Scanner.TOKEN tokenEOF = scannerTest.scan();
        assertEquals(Scanner.TOKEN.SCANEOF, tokenEOF);
        System.out.println(tokenEOF);

    }

    @Test
    void loopTest() throws IOException {

        String input = "loop x != y do calc x = x + 1 endloop";
        PushbackReader pbr = new PushbackReader(new StringReader(input));
        Scanner scannerTest = new Scanner(pbr);

        Scanner.TOKEN tokenLoop = scannerTest.scan();
        assertEquals(Scanner.TOKEN.LOOP, tokenLoop);
        System.out.println(tokenLoop + " -> " + scannerTest.getTokenBufferString());

        Scanner.TOKEN tokenId1 = scannerTest.scan();
        assertEquals(Scanner.TOKEN.ID, tokenId1);
        System.out.println(tokenId1 + " -> " + scannerTest.getTokenBufferString());

        Scanner.TOKEN tokenNotEquals = scannerTest.scan();
        assertEquals(Scanner.TOKEN.NOTEQUALS, tokenNotEquals);
        System.out.println(tokenNotEquals);

        Scanner.TOKEN tokenId2 = scannerTest.scan();
        assertEquals(Scanner.TOKEN.ID, tokenId2);
        System.out.println(tokenId2 + " -> " + scannerTest.getTokenBufferString());

        Scanner.TOKEN tokenDo = scannerTest.scan();
        assertEquals(Scanner.TOKEN.DO, tokenDo);
        System.out.println(tokenDo + " -> " + scannerTest.getTokenBufferString());

        Scanner.TOKEN tokenCalc = scannerTest.scan();
        assertEquals(Scanner.TOKEN.CALC, tokenCalc);
        System.out.println(tokenCalc + " -> " + scannerTest.getTokenBufferString());

        Scanner.TOKEN tokenId3 = scannerTest.scan();
        assertEquals(Scanner.TOKEN.ID, tokenId3);
        System.out.println(tokenId3 + " -> " + scannerTest.getTokenBufferString());

        Scanner.TOKEN tokenEquals = scannerTest.scan();
        assertEquals(Scanner.TOKEN.EQUALS, tokenEquals);
        System.out.println(tokenEquals);

        Scanner.TOKEN tokenId4 = scannerTest.scan();
        assertEquals(Scanner.TOKEN.ID, tokenId4);
        System.out.println(tokenId4 + " -> " + scannerTest.getTokenBufferString());

        Scanner.TOKEN tokenPlus = scannerTest.scan();
        assertEquals(Scanner.TOKEN.PLUS, tokenPlus);
        System.out.println(tokenPlus);

        Scanner.TOKEN tokenInt = scannerTest.scan();
        assertEquals(Scanner.TOKEN.INTCONST, tokenInt);
        System.out.println(tokenInt + " -> " + scannerTest.getTokenBufferString());

        Scanner.TOKEN tokenEndLoop = scannerTest.scan();
        assertEquals(Scanner.TOKEN.ENDLOOP, tokenEndLoop);
        System.out.println(tokenEndLoop + " -> " + scannerTest.getTokenBufferString());

        Scanner.TOKEN tokenEOF = scannerTest.scan();
        assertEquals(Scanner.TOKEN.SCANEOF, tokenEOF);
        System.out.println(tokenEOF);

    }

    @Test
    void emptyFileTest() throws IOException {

        String input = "";
        PushbackReader pbr = new PushbackReader(new StringReader(input));
        Scanner scannerTest = new Scanner(pbr);

        Scanner.TOKEN tokenEOF = scannerTest.scan();
        assertEquals(Scanner.TOKEN.SCANEOF, tokenEOF);
        System.out.println(tokenEOF);
    }
}