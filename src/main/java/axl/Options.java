package axl;

public interface Options {

    String VERSION = "1.0 - Dev";

    Double VERSION_ID = 1d;

    default LanguageController getLanguageController() {
        return Axolotl.getInstance().getLanguageController();
    }

}
