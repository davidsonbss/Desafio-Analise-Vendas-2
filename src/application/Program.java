package application;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.Set;

import model.entities.Sale;

public class Program {

	public static void main(String[] args) {

		Locale.setDefault(Locale.US);
		Scanner sc = new Scanner(System.in);

		System.out.print("Entre o caminho do arquivo: ");
		String path = sc.nextLine();
		
		try (BufferedReader br = new BufferedReader(new FileReader(path))) {

			String line = br.readLine();
			List<Sale> listSale = new ArrayList<>();

			while (line != null) {
				String[] fields = line.split(",");
				
				Integer month = Integer.parseInt(fields[0]);
				Integer year = Integer.parseInt(fields[1]);
				Integer itens = Integer.parseInt(fields[3]);
				Double total = Double.parseDouble(fields[4]);
				
				listSale.add(new Sale(month, year, fields[2], itens, total));
				line = br.readLine();
			}
			
			System.out.println();
			System.out.println("Total de vendas por vendedor:");
			
			Set<String> setSale = new HashSet<String>();
			for (Sale s : listSale) {
				setSale.add(s.getSeller());
			}
			
			for (String a: setSale) {
				Double totalSaller = listSale.stream()
						.filter(x -> x.getSeller().equals(a))
						.map(x -> x.getTotal())
						.reduce(0.0, (x, y) -> x + y);
				
				System.out.println(a + " - R$ " + String.format("%.2f", totalSaller));
			}
			
		} catch (IOException e) {
			System.out.println("Erro: " + e.getMessage());
		}

		sc.close();
	}

}

