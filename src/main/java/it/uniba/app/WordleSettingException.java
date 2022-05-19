package it.uniba.app;

/**
 * {@literal <<NoECB>>} <br>
 *  Eccezione lanciata in situazioni che richiedono particolari precondizioni sulle impostazioni di gioco,
 *  nelle quali esse non sono rispettate.<p></p>
 *
 *  Esempio: Precondizione per iniziare una partita è l'aver impostato una parola segreta.
 */
public class WordleSettingException extends RuntimeException
{
    WordleSettingException (String message)
    {
        super(message);
    }
}
