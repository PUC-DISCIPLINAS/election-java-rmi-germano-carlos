package Utils;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;

public class Encript {

    public static String StringToMD5(String mensagem, String retorno) throws Exception {

        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(mensagem.getBytes());
        byte[] digest = md.digest();

        if(!("lower".equals(retorno) || "upper".equals(retorno)))
            throw new Exception("Não foi localizado o tipo de retorno desejado");

        if("lower".equals(retorno))
            return DatatypeConverter.printHexBinary(digest).toLowerCase();
        else
            return DatatypeConverter.printHexBinary(digest).toUpperCase();
    }

}
