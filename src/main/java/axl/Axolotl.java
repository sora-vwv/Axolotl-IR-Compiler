package axl;

import axl.lexer.Lexer;
import axl.lexer.LexerException;
import axl.lexer.Token;
import lombok.Getter;
import lombok.SneakyThrows;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Properties;

public class Axolotl implements Options {

    @Getter
    private static Axolotl instance;

    private Axolotl() {
        instance = this;
    }

    @Getter
    private LanguageController languageController;

    @SneakyThrows
    private int $main(String[] args) {
//        System.setOut(new PrintStream("history.log"));
        System.out.printf("""
                
                | Axolotl IR Compiler
                | version: %s
                |
                """, VERSION);

        // Init Languages
        {
            System.out.println("| init languages");
            String result = initLanguage();
            if (result != null) {
                System.err.println("| " + result);
                return 1;
            }
            System.out.println("| current - " + getLanguageController().getName());
            if (!getLanguageController().isActual())
                System.err.println(getLanguageController().getNotActualVersion());
            System.out.println(getLanguageController().getLangSuccess());
        }

        // get compilation file
        String content;
        String filename;
        {
            if (args.length == 0 || args[args.length - 1].isEmpty()) {
                System.err.println(getLanguageController().getCompilationFileNotFound());
                return 1;
            }

            String path = args[args.length - 1];
            try {
                File file = new File(path);
                filename = file.getName();
                content = Files.readString(file.toPath());
            } catch (Exception ignored) {
                System.err.println(getLanguageController().getCompilationFileNotFound());
                return 1;
            }
        }

        Lexer lexer = new Lexer(content, filename);
        ArrayList<Token> tokens;
        try {
            tokens = lexer.tokenize();
        } catch (LexerException e) {
            System.err.println(e);
            return 1;
        }

        tokens.forEach(token -> System.out.println(token.getMessage()));

        System.out.println(getLanguageController().getEndSuccess());
        return 0;
    }



    private String initLanguage() {
        try {
            InputStream langIniStream = getResource("./lang/lang.ini");
            if (langIniStream == null)
                return "file 'lang.ini' not found";

            Properties properties = new Properties();
            properties.load(langIniStream);

            String code = (String) properties.getOrDefault("current", null);
            if (code == null)
                return "file 'lang.ini' is corrupted";

            InputStream langCodeStream = getResource("./lang/" + code);
            if (langCodeStream == null)
                return "file '" +code + "' not found";

            properties = new Properties();
            properties.load(langCodeStream);
            languageController = new LanguageController(properties);
        } catch (IOException e) {
            return "file 'lang.ini' is corrupted or it contains incorrect data";
        }
        return null;
    }

    public InputStream getResource(String path) {
        return getClass().getClassLoader().getResourceAsStream(path);
    }

    public static void main(String[] args) {
        System.exit(new Axolotl().$main(args));
    }

}