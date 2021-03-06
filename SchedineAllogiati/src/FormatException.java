/**
 * 
 * @author Enrico Testori
 *
 */

public class FormatException extends Exception{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/*
	 * Variabili statiche per i messaggi di errore.
	 */
	public static String MISSING_CAPO_FAMIGLIA = "Capo famiglia mancante";
	public static String MISSING_CAPO_GRUPPO = "Capo gruppo mancante";
	public static String MISSING_MEMBRO_GRUPPO = "Membri gruppo mancanti";
	public static String MISSING_MEMBRO_FAMIGLIA = "Membri famiglia mancanti";
	public static String INVALID_RECORD = "Questo record sembra non essere formattato correttamente";
	public static String PERMANENZA_TOO_LONG = "Il campo permanenza deve essere minore di 30 giorni";
	public static String REMOTE_DATA_ARRIVO = "La data di arrivo deve essere compresa nelle ultime 48 ore";
	public static String NULL_RECORD = "Record nullo. Qualcosa è andato storto";
	public static String INVALID_SEX = "Sesso non valido. Il sesso nel file di tipo questura deve essere o 0 o 1.";
	public static String MALFORMED_DATE = "Data non formattata correttamente. La data deve essere della forma gg/mm/aaa";
	
	
	private int position;
	
	public FormatException(String tipo, int pos){
		
		super(tipo);
		position = pos;
		
	}
	
	public int getPosition(){
		return position;
	}
	
}
