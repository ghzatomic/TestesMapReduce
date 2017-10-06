package br.com.caiopaulucci.mapreduce;

import java.util.AbstractMap;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.function.BinaryOperator;
import java.util.function.ToIntFunction;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TesteMapReduce {


	public static void main(String[] args) {
		Map<Integer,String> entrada = new HashMap<Integer,String>();
		entrada.put(0,"amarelo");
		entrada.put(1,"verde");
		entrada.put(2,"azul");
		entrada.put(3,"cinza");
		entrada.put(4,"vermelho");
		
		// Quero transformar o map de Integer,String em String,Integer 
		Map<String,Integer> entrada2 = entrada.entrySet().parallelStream().parallel().parallel().parallel().parallel().collect(Collectors.toMap(Map.Entry::getValue,Map.Entry::getKey));
		
		Comparator<Map.Entry<String,Integer>> byMapValues = new Comparator<Map.Entry<String,Integer>>() {
	        @Override
	        public int compare(Map.Entry<String,Integer> left, Map.Entry<String,Integer> right) {
	            return left.getValue().compareTo(right.getValue());
	        }
	    };
		
		Stream<Map.Entry<String,Integer>> stream2 = entrada2.entrySet().stream().sorted(byMapValues);
		
		stream2.forEach(x->{
			System.out.println(x.getKey()+" - "+x.getValue());
		});
		
		/*Stream<Map.Entry<String, Integer>> stream = entrada.entrySet().stream().map(i->{
			return (Map.Entry<String, Integer>) new AbstractMap.SimpleEntry<>(i.getValue(),i.getKey());
		});
		
		Map<String, Integer> ret = stream.collect(Collectors.toMap(Map.Entry::getKey,Map.Entry::getValue));
		ret.entrySet().forEach(x->{
			System.out.println(x.getKey()+" - "+x.getValue());
		});*/
		
		// Aqui os 2 testes funcionaram, porem ele nao pode repetir o indice invertido!
		
	}
	
	public void transformaMapDeIntegerStringEmStringInteger(){
		Map<Integer,String> entrada = new TreeMap<Integer,String>();
		entrada.put(0,"amarelo");
		entrada.put(1,"verde");
		entrada.put(2,"azul");
		entrada.put(3,"cinza");
		entrada.put(4,"amarelo");
		entrada.put(5,"amarelo");
		entrada.put(6,"cinza");
		entrada.put(7,"vermelho");
		entrada.put(8,"xxxxxxxx");
		
		// Quero transformar o map de Integer,String em String,Integer
		Stream<Map.Entry<String, Integer>> filtrado = entrada.entrySet().stream().map(i->{
			return (Map.Entry<String, Integer>) new AbstractMap.SimpleEntry<>(i.getValue(),i.getKey());
		});
		
		filtrado.forEach(x->{
			System.out.println(x.getKey());
		});
	}
	
	public void somarTodasAsQuantidadesDeLetras(){
		Map<Integer,String> entrada = new TreeMap<Integer,String>();
		entrada.put(0,"amarelo");
		entrada.put(1,"verde");
		entrada.put(2,"azul");
		entrada.put(3,"cinza");
		entrada.put(4,"amarelo");
		entrada.put(5,"amarelo");
		entrada.put(6,"cinza");
		entrada.put(7,"vermelho");
		entrada.put(8,"xxxxxxxx");
		
		// Quero somar todas as quantidades de letras
		System.out.println(entrada.entrySet().parallelStream().mapToInt(new ToIntFunction<Map.Entry<Integer,String>>() {
			@Override
			public int applyAsInt(Entry<Integer, String> value) {
				return value.getValue().length();
			}
		}).sum());
	}
	
	public void exemploDeMapReduceQueContaQuantasLetrasTemMaisQue5Caracteres(){
		Map<Integer,String> entrada = new TreeMap<Integer,String>();
		entrada.put(0,"amarelo");
		entrada.put(1,"verde");
		entrada.put(2,"azul");
		entrada.put(3,"cinza");
		entrada.put(4,"amarelo");
		entrada.put(5,"amarelo");
		entrada.put(6,"cinza");
		entrada.put(7,"vermelho");
		entrada.put(8,"xxxxxxxx");
		
		// Quero a quantidade cores com mais de 5 letras
		int qtdeCoresComMaisDeCincoLetras = entrada.entrySet().stream().map(iterado->{
			return iterado.getValue().length();
		}).reduce(new Integer(0)/*Valor inicial do t*/,new BinaryOperator<Integer>() {
			
			@Override
			public Integer apply(Integer t/*Valor inicial e acumulador*/, Integer u/*valor da iteracao do map anterior*/) {
				if (u > 5){
					return t+1;
				}else{
					return t;
				}
			}
		});
		
		System.out.println(qtdeCoresComMaisDeCincoLetras);
		
	}
	
	public void exemploDeMap(){
		Map<Integer,String> entrada = new TreeMap<Integer,String>();
		entrada.put(0,"amarelo");
		entrada.put(1,"verde");
		entrada.put(2,"azul");
		entrada.put(3,"cinza");
		entrada.put(4,"amarelo");
		entrada.put(5,"amarelo");
		entrada.put(6,"cinza");
		entrada.put(7,"vermelho");
		entrada.put(8,"xxxxxxxx");
		
		// Quero todos os ids das cores com mais de 5 letras
		Stream filtrado = entrada.entrySet().stream().filter(t->{
			return t.getValue().length()>5;
		}).map(iterado->{
			return iterado.getKey();
		});
		
		filtrado.forEach(x->{
			System.out.println(x);
		});
	}
	
	public void testeDeFilter(){
		Map<Integer,String> entrada = new TreeMap<Integer,String>();
		entrada.put(0,"amarelo");
		entrada.put(1,"verde");
		entrada.put(2,"azul");
		entrada.put(3,"cinza");
		entrada.put(4,"amarelo");
		entrada.put(5,"amarelo");
		entrada.put(6,"cinza");
		entrada.put(7,"vermelho");
		entrada.put(8,"xxxxxxxx");
		
		// Quero contar todas as cores com mais de 5 letras
		Stream<Map.Entry<Integer, String>> filtrado = entrada.entrySet().stream().filter(t->{
			return t.getValue().length()>5;
		});
		
		filtrado.forEach(x->{
			System.out.println(x.getValue());
		});
	}
	
	public void contaQuantosAApareceNaLista(){
		Map<Integer,String> entrada = new TreeMap<Integer,String>();
		entrada.put(0,"amarelo");
		entrada.put(1,"verde");
		entrada.put(2,"azul");
		entrada.put(3,"cinza");
		entrada.put(4,"amarelo");
		entrada.put(5,"amarelo");
		entrada.put(6,"cinza");
		
		System.out.println(entrada.entrySet().stream().map(i->{ // Vou ter tipo : indice por qtde de palavras ex : 0,2 ; 1,0 ; ...
			char[] arrchar = i.getValue().toCharArray();
			int qtde = 0;
			for (char c : arrchar) {
				if (c == 'a'){
					qtde ++; 
				}
			}
			return (Map.Entry<String, Integer>)new AbstractMap.SimpleEntry<String, Integer>(i.getValue(),qtde);
		}).reduce(new BinaryOperator<Map.Entry<String, Integer>>() {
			
			@Override
			public Entry<String, Integer> apply(Entry<String, Integer> t, Entry<String, Integer> u) {
				return (Map.Entry<String, Integer>)new AbstractMap.SimpleEntry<String, Integer>(t.getKey(),t.getValue()+u.getValue());
			}
		}));
	}
	
}
