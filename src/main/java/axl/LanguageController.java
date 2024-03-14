package axl;

import lombok.Getter;

import java.util.Properties;

@Getter
public class LanguageController {

    private final String langSuccess;
    private final String endSuccess;
    private final String notActualVersion;
    private final String name;
    private final String compilationFileNotFound;

    private final String pointInRealNumber;
    private final String postfixInRealNumber;
    private final String stringNotClosed;
    private final String unknownCharacterAfterSlash;
    private final String characterNotClosed;
    private final String characterDoesNotMatter;
    private final String unknownCharacter;
    private final String commentNotClosed;

    private Double version;

    LanguageController(Properties properties) {
        this.name = (String) properties.getOrDefault("name", "null");
        try {
            this.version = Double.valueOf((String) properties.getOrDefault("version", "0"));
        } catch (NumberFormatException e) {
            this.version = 0d;
        }

        this.langSuccess = (String) properties.getOrDefault("lang-success", "| language has been successfully imported");
        this.endSuccess  = (String) properties.getOrDefault("end-success", "| successful compilation");
        this.notActualVersion = (String) properties.getOrDefault("not-actual-version", "! not actual version of the language");
        this.compilationFileNotFound = (String) properties.getOrDefault("compilation-file-not-found", "! compilation file was not found");
        this.pointInRealNumber = (String) properties.getOrDefault("point-in-real-number", "There cannot be more than one point in real numbers");
        this.postfixInRealNumber = (String) properties.getOrDefault("postfix-in-real-number", "The postfix 'L' cannot be applied to real numbers");
        this.stringNotClosed = (String) properties.getOrDefault("string-not-closed", "The string is not closed");
        this.unknownCharacterAfterSlash = (String) properties.getOrDefault("unknown-character-after-slash", "Unknown character after the '\\' in the string");
        this.characterNotClosed = (String) properties.getOrDefault("character-not-closed", "The character is not closed");
        this.characterDoesNotMatter = (String) properties.getOrDefault("character-does-not-matter", "The symbol doesn't matter");
        this.unknownCharacter = (String) properties.getOrDefault("unknown-character", "Unknown character");
        this.commentNotClosed = (String) properties.getOrDefault("comment-not-closed", "The comment is not closed");
    }

    boolean isActual() {
        return version >= Options.VERSION_ID;
    }

}
