package me.zheroandre.factioncollectors.utils;

public class MessageUtils {

    public enum ErrorMessage {
        NO_CONSOLE(" &c&lERROR &7Questo comando non è eseguibile da &dCONSOLE "),
        // NO_PERMISSION(" &c&lERROR &7Comando sconosciuto, riprova "),
        NO_FACTION(" &c&lERROR &7Crea o entra in una &dFAZIONE "),
        NO_FACTION_PERMISSION(" &c&lERROR &7Fatti dare i permessi dal &dLEADER ");

        public final String MESSAGE;

        ErrorMessage(String MESSAGE) {
            this.MESSAGE = MESSAGE;
        }
    }

    public enum SuccessMessage {
        WITHDRAW_SUCCESS(" &d&lFACTION &7Hai venduto &8( &7x[ENTITY-AMOUNT] &d[ENTITY-TYPE] &8) &7per &d$[PRIZE]"),
        WITHDRAW_SUCCESS_TNT(" &d&lFACTION &7Hai venduto &8( &7x[ENTITY-AMOUNT] &d[ENTITY-TYPE] &8) &7per &8( &7x[ENTITY-AMOUNT] &dTNT &8)");

        public final String MESSAGE;

        SuccessMessage(String MESSAGE) {
            this.MESSAGE = MESSAGE;
        }
    }

    public static String message(ErrorMessage errorMessage) {
        return StringUtils.color(errorMessage.MESSAGE);
    }

    public static String message(SuccessMessage successMessage) {
        return StringUtils.color(successMessage.MESSAGE);
    }

}
