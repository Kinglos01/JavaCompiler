import java.io.IOException;
import java.io.PushbackReader;
import java.util.ArrayList;
import java.util.List;

public class Scanner {

    enum TOKEN {SCANEOF,ID,INTCONST,DECLARE,PRINT, INITIALIZE,EQUALS,NOTEQUALS,
        IF,THEN,ENDIF,LOOP,DO,ENDLOOP,CALC,PLUS}

    private PushbackReader pbr;
    private List<String> reservedWord;
    private StringBuilder buffer;

    // Constructor
    public Scanner(PushbackReader pbr) {
        this.pbr = pbr;
        this.buffer = new StringBuilder();

        reservedWord = new ArrayList<>();
        reservedWord.add("declare");
        reservedWord.add("print");
        reservedWord.add("initialize");
        reservedWord.add("if");
        reservedWord.add("then");
        reservedWord.add("endif");
        reservedWord.add("loop");
        reservedWord.add("do");
        reservedWord.add("endloop");
        reservedWord.add("calc");
    }



    public  TOKEN scan() throws IOException {

        try{
            buffer.setLength(0);

            int c = pbr.read();

            while ((c != -1) && (c != 65535) ) {


                //whiteSpace Check
                if((c == 32) || (c == 9) || (c == 10) || (c == 13)){
                    c = pbr.read();
                    continue;
                }

                //ID assignment
                if(Character.isLetter(c)){
                    while(Character.isLetter(c) || Character.isDigit(c)){
                        buffer.append((char)c);
                        c = pbr.read();
                    }
                    pbr.unread(c);
                    //Checks if reserved word
                    if(reservedWord.contains(buffer.toString())){
                        switch(buffer.toString()){
                            case "declare": return TOKEN.DECLARE;
                            case "print": return TOKEN.PRINT;
                            case "initialize": return TOKEN.INITIALIZE;
                            case "if": return TOKEN.IF;
                            case "then": return TOKEN.THEN;
                            case "endif": return TOKEN.ENDIF;
                            case "loop": return TOKEN.LOOP;
                            case "do": return TOKEN.DO;
                            case "endloop": return TOKEN.ENDLOOP;
                            case "calc": return TOKEN.CALC;
                        }
                    }
                    // Tester
                    // System.out.println(buffer.toString());
                    return TOKEN.ID;
                }

                if(Character.isDigit(c)){
                    while(Character.isDigit(c)){
                        buffer.append((char)c);
                        c = pbr.read();
                    }
                    pbr.unread(c);
                    //Tester
                    //System.out.println(buffer.toString());
                    return TOKEN.INTCONST;
                }

                if(c == '+'){
                    buffer.append((char)c);
                    return TOKEN.PLUS;
                }

                if(c == '='){
                    buffer.append((char)c);
                    return TOKEN.EQUALS;
                }

                if(c == '!'){
                    buffer.append((char)c);
                    c = pbr.read();
                    if(c == '='){
                        buffer.append((char)c);
                        pbr.read();
                        return TOKEN.NOTEQUALS;
                    }
                    else{
                        pbr.unread(c);
                    }
                }

            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return TOKEN.SCANEOF;
    }

    String getTokenBufferString(){
        return buffer.toString();
    }

}