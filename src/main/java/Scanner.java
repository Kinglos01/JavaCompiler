import java.io.FileReader;
import java.io.IOException;
import java.io.PushbackReader;
import java.util.List;

public class Scanner {

    enum TOKEN {SCANEOF,ID,INTCONST,DECLARE,PRINT,INTIALIZE,EQUALS,NOTEQUALS,
        IF,THEN,ENDIF,LOOP,DO,ENDLOOP,CALC,PLUS}

    static PushbackReader pbr;
    static StringBuffer buffer = new StringBuffer();
    static List<String> reservedWord = List.of(
            "declare", "print", "initialize", "if", "then", "endif", "loop", "do", "endloop", "calc");

    static{

         FileReader fr;

         try{
             fr = new FileReader("input.txt");
             pbr = new PushbackReader(fr);

         } catch (Exception e) {
             throw new RuntimeException(e);
         }
         PushbackReader pbr = new PushbackReader(fr);

    }



    public static TOKEN Scan() throws IOException {

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
                        case "initialize": return TOKEN.INTIALIZE;
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
                 return TOKEN.PLUS;
             }

             if(c == '='){
                 return TOKEN.EQUALS;
             }

             if(c == '!'){
                 c = pbr.read();
                 if(c == '='){
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
