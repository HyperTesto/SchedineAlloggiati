import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;


/**
 * 
 * @author Enrico Testori
 *
 */
public class Csv implements FileManager {
	
	private List<Exception> exceptions;

	@Override
	public List<Record> loadFile(String path)  throws IOException{
		
		exceptions  = new ArrayList<Exception>();
		List<Record> records = new ArrayList<Record>();

		/*try{	*/
			FileInputStream in = new FileInputStream(new File(path));
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;
			//Read File Line By Line
			int i=1;		//partiamo da uno per la visualizzazione all'utente
			while ((strLine = br.readLine()) != null)   {
				Record temp = readRecord(strLine);
				if (temp != null){
					records.add(temp);
				}else{
					exceptions.add(new Exception("Errore alla riga " + i + ": dati malformati."));
				}
				i++;
			}
			//Close the input stream
			in.close();
		/*}catch (Exception e){//Catch exception if any
			System.err.println("Errore nell'apertura/lettura del file: " + e.getMessage());
		}*/
		return records;
	}

	@Override
	public boolean writeFile(List<Record> records, String saveTo)  throws IOException{
		/*try{*/
			BufferedWriter bw = new BufferedWriter(new FileWriter(saveTo));
			for(Record temp : records){
				bw.write(formatRecord(temp));
			}
			
			bw.close();
			return true;
			

		/*}catch(Exception e){
			System.out.println("Errore nella apertura/scrittura del file!: " +e.getMessage());
			return false;
		}*/
		
	}
	
	@Override
	public List<Exception> getErrors() {
		// TODO Auto-generated method stub
		return exceptions;
	}
	
	
	/**
	 * Metodo di supporto per la formattazione del record di tipo CSV
	 * @param record
	 * @return String
	 */
	private String formatRecord(Record record){
		String riga="";
		/*
		 * Campi alloggiato
		 */
		riga+=record.getTipoAlloggiato()+";";
		riga+=record.getDataArrivo()+";";
		riga+=record.getPermanenza()+";";
		/*
		 * Dati personali alloggiato
		 */
		riga+=record.getNome()+";";
		riga+=record.getCognome()+";";
		riga+=record.getSesso()+";";
		riga+=record.getDataNascita()+";";
		riga+=record.getComuneNascita()+";";
		riga+=record.getStatoNascita()+";";
		riga+=record.getCittadinanza()+";";
		/*
		 * Dati documento
		 */
		riga+=record.getTipoDocumento()+";";
		riga+=record.getNumeroDocumento()+";";
		riga+=record.getRilascioDocumento() + "\n";
		return riga;
	}
	
	
	/**
	 * Metodo di supporto per la lettura da un record di tipo CSV
	 * @param riga
	 * @return Record
	 */
	private Record readRecord(String riga){
		String nome,cognome,tipoAlloggiato,dataArrivo,dataNascita,sesso,cittadinanza,statoNascita,comuneNascita,tipoDoc,numDoc,rilascioDoc;
		int permanenza;
		Record record;
		StringTokenizer t = new StringTokenizer(riga,";");
		try{
			/*
			 * Dati permanenza
			 */
			tipoAlloggiato = t.nextToken();
			dataArrivo = t.nextToken();
			permanenza = Integer.parseInt(t.nextToken());
			/*
			 * Generalità alloggiato
			 */
			nome = t.nextToken();
			cognome = t.nextToken();
			sesso = t.nextToken();
			dataNascita = t.nextToken();
			comuneNascita = t.nextToken();
			statoNascita = t.nextToken();
			cittadinanza = t.nextToken();
			/*
			 * Dati documento
			 */
			tipoDoc = t.nextToken(); 
			numDoc = t.nextToken();
			rilascioDoc = t.nextToken();
			
			record = new Record(tipoAlloggiato, dataArrivo, permanenza, nome, cognome, dataNascita, sesso, cittadinanza, statoNascita, comuneNascita, tipoDoc, numDoc, rilascioDoc);
		
		}catch (Exception e){//Catch exception if any
			System.err.println("Errore: la riga non sembra formattata correttamente");
			record = null;
		}
		//record = new Record(t.nextToken(),t.nextToken(),Integer.parseInt(t.nextToken()),t.nextToken(),t.nextToken(),t.nextToken(),t.nextToken(),t.nextToken(),t.nextToken(),t.nextToken(),t.nextToken(),t.nextToken(),t.nextToken());
		
		return record;
	}
	
	public static void main(String args[]) throws IOException{
		FileManager csv = new Csv();
		List<Record> records;
		
		records = new ArrayList<Record> ();
		
		records.add (new Record (Alloggiato.CAPO_GRUPPO, "04/03/2014", 3, "Alberto", "Bonizzi", "10/08/1994", "M", "Italiana", "Italia", "Belluno (BL)", "Carta d'identità", "AS101SA", "Belluno (BL)"));
		records.add (new Record (Alloggiato.OSPITE_SINGOLO, "04/03/2014", 3, "Enrico", "Testori", "26/01/1994", "M", "Italiana", "Italia", "Feltre (BL)", "Carta d'identità", "ET202TE", "Feltre (BL)"));
		records.add (new Record (Alloggiato.OSPITE_SINGOLO, "04/03/2014", 3, "Gimmy", "Ymmig", "10/08/1994", "M", "Congo", "Malawi", "Lilongwe (MK)", "Carta d'identità", "454554", "Lilongwe (MK)"));
		records.add (new Record (Alloggiato.OSPITE_SINGOLO, "04/03/2014", 3, "Kabobo", "Ga", "26/01/1994", "M", "Ghana", "Ghana", "Mokungo (MK)", "Carta d'identità", "TONGABONGA", "Mokungo (MK)"));
		
		csv.writeFile (records, "/home/alberto.bonizzi/Desktop/allogiati.csv");
		
		for(Record temp : csv.loadFile("/home/alberto.bonizzi/Desktop/allogiati.csv")){
			System.out.println(temp.getNome());
		}
	}

	

}
