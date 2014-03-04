import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * 
 * @author Enrico Testori
 *
 */
public class SimpleText implements FileManager {

	@Override
	public List<Record> loadFile(String path) {

		List<Record> records = new ArrayList<Record>();

		try{	
			InputStream in = ResourceLoader.loader(path);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;
			//Read File Line By Line
			while ((strLine = br.readLine()) != null)   {
				Record temp = readRecord(strLine);
				if (temp != null){
					records.add(temp);
				}
			}
			//Close the input stream
			in.close();
		}catch (Exception e){//Catch exception if any
			System.err.println("Errore nell'apertura/lettura del file: " + e.getMessage());
		}
		return records;
	}

	@Override
	public boolean writeFile(List<Record> records, String saveTo) {
		try{
			BufferedWriter bw = new BufferedWriter(new FileWriter(saveTo));
			for(Record temp : records){
				bw.write(formatRecord(temp));
			}
			bw.close();
			return true;
			

		}catch(Exception e){
			System.out.println("Errore nella apertura/scrittura del file: " + e.getMessage());
			return false;
		}
		
	}
	
	
	/**
	 * Metodo di supporto per la formattazione del record di tipo testo semplice
	 * @param record
	 * @return String
	 */
	private String formatRecord(Record record){
		String riga="";
		/*
		 * Campi alloggiato
		 */
		riga+=record.getTipoAlloggiato()+"\t";
		riga+=record.getDataArrivo()+"\t";
		riga+=record.getPermanenza()+"\t";
		/*
		 * Dati personali alloggiato
		 */
		riga+=record.getNome()+"\t";
		riga+=record.getCognome()+"\t";
		riga+=record.getSesso()+"\t";
		riga+=record.getDataNascita()+"\t";
		riga+=record.getStatoNascita()+"\t";
		riga+=record.getComuneNascita()+"\t";
		riga+=record.getCittadinanza()+"\t";
		/*
		 * Dati documento
		 */
		riga+=record.getTipoDocumento()+"\t";
		riga+=record.getNumeroDocumento()+"\t";
		riga+=record.getRilascioDocumento();
		return riga;
	}

	/**
	 * Metodo di supporto per la lettura dei record di tipo testo semplica
	 * @param riga
	 * @return Record
	 */ 
	private Record readRecord(String riga){
		String nome,cognome,tipoAlloggiato,dataArrivo,dataNascita,sesso,cittadinanza,statoNascita,comuneNascita,tipoDoc,numDoc,rilascioDoc;
		int permanenza;
		Record record;
		StringTokenizer t = new StringTokenizer(riga,"\t");
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
			dataNascita = t.nextToken();
			sesso = t.nextToken();
			cittadinanza = t.nextToken();
			statoNascita = t.nextToken();
			comuneNascita = t.nextToken();
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

}
