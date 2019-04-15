import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Scanner;

import javax.swing.JOptionPane;

@SuppressWarnings("resource")
public class Main {

	static String chave = "";
	static String currentCrypto = "";
	
	public static void main(String[] args) throws Exception {
		Scanner in = new Scanner(System.in);
		boolean end = false;
		while (!end) {
			if (chave.isEmpty()) {
				chave = JOptionPane.showInputDialog("Informe a chave").toUpperCase();
			}
			
			System.out.println("Chave: " + chave);
			System.out.println("Escolha a opcao: \n 1-) cifrar texto do arquivo \n 2-) Decifrar ultimo texto lido \n 3-) Sair");
			int option = in.nextInt();
			
			switch (option) {
			case 1:
				currentCrypto = cifrarFile();
				System.out.println("Texto Cifrado: " + currentCrypto);
				break;
			case 2:
				if (currentCrypto.isEmpty()) {
					System.out.println("Opcao nao disponivel ainda!");
				} else {
					String value = decifrarTexto();
					System.out.println("Texto decifrado: " + value);
				}
				break;
			case 3:
				end = true;
				break;
			}
		}
	}
	
	public static String cifrarFile() throws Exception {
		InputStream is = new FileInputStream("/Users/leonardofiedler/Desktop/ecllipsewksp/vigenere-sample/src/message.txt");
		BufferedReader buf = new BufferedReader(new InputStreamReader(is));

		String line = buf.readLine();
		StringBuilder sb = new StringBuilder();
        
		while(line != null){
		   sb.append(line).append("\n");
		   line = buf.readLine();
		}

		String fileAsString = sb.toString();
		//A simple replacement and sanitizing
		fileAsString = fileAsString.replaceAll("[\\s\\t\\r]", "");
		fileAsString = fileAsString.toUpperCase();
		System.out.println("Contents : " + fileAsString);
		
		String finalStr = cifrarTexto(fileAsString);
		return finalStr;
	}
	
	public static String cifrarTexto(String texto) {
		byte[] arrChave = chave.getBytes();
		byte[] arrFinal = new byte[texto.length()];
		int k = 0;
		int i = 0;
		int max = arrChave.length - 1;
		for (byte f : texto.getBytes()) {
			byte diff = (byte) (arrChave[i++] - 65); //65 = A
			byte value = (byte) (f + diff);
			
			//Returns to A
			if (90 - value < 0) {
				byte nValue = 64;
				nValue += value - 90;
				value = nValue;
			}
			
			arrFinal[k++] = value;
			
			if (i > max) {
				i = 0;
			}
		}
		
		return new String(arrFinal);
	}
	
	public static String decifrarTexto() {
		byte[] arrChave = chave.getBytes();
		byte[] arrFinal = new byte[currentCrypto.length()];
		int k = 0;
		int i = 0;
		int max = arrChave.length - 1;
		for (byte f : currentCrypto.getBytes()) {
			byte diff = (byte) (arrChave[i++] - 65); //65 = A
			byte value = 0;
			byte nDiff = (byte) (f - diff);
			if (nDiff >= 65) {
				value = (byte) (f - diff);
			} else {
				byte nValue = 90;
				nValue -= 64 - (f - diff);
				value = nValue;
			}
			
			arrFinal[k++] = value;
			
			if (i > max) {
				i = 0;
			}
		}
		
		return new String(arrFinal);
	}

}
