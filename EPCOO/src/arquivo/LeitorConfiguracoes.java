package arquivo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.LinkedList;
import java.util.List;

public class LeitorConfiguracoes {
	private List<Fase> fases;
	private File src;
	
	public LeitorConfiguracoes(String arquivo){
		src = new File(arquivo);
		if(src.exists()){
			fases = new LinkedList<Fase>();
			lerFases();
		}
	}
	
	public List<Fase> getFases(){
		return fases;
	}

	private void lerFases() {
		BufferedReader in, inFase;
		String linha;
		String[] params;
		int playerHp, nFases;
		Fase f;
		TimerElemento t;
		try {
			in = new BufferedReader(new FileReader(src));
			//A primeira linha deve conter o hp do player
			linha  = in.readLine();
			playerHp = Integer.parseInt(linha);
			
			//A segunda linha deve conter o numero de fases
			linha = in.readLine();
			nFases = Integer.parseInt(linha);
			
			//O resto contém o nome de arquivos de configuracoes das fases
			for(int i=1; i<=nFases; i++){
				//Le o nome do arquivo
				linha = in.readLine();
				
				//cria a fase
				f = new Fase(i,playerHp);
				
				//cria o Reader
				inFase = new BufferedReader(new FileReader(linha));
				
				//faz a leitura de todas as linhas
				while((linha = inFase.readLine())!=null){
					//separa a linha pelos espaços em branco
					params = linha.split(" ");
					
					//cria o timer
					if(params[0].equals("INIMIGO")){
						t = new TimerElemento(
								Integer.parseInt(params[2]), 	//spawn time
								true,							//isEnemy
								false, 							//isBoss
								Integer.parseInt(params[1]),	//tipo
								Double.parseDouble(params[3]),	//x
								Double.parseDouble(params[4]),	//y
								0								//hp - 0 pq eles n tem hahahah
								);
					} else if(params[0].equals("CHEFE")){
						t = new TimerElemento(
								Integer.parseInt(params[3]), 	//spawn time
								true,							//isEnemy
								true, 							//isBoss
								Integer.parseInt(params[1]),	//tipo
								Double.parseDouble(params[4]),	//x
								Double.parseDouble(params[5]),	//y
								Integer.parseInt(params[2])		//hp 
								);
					} else {
						t = new TimerElemento(
								Integer.parseInt(params[2]), 	//spawn time
								false,							//isEnemy
								false, 							//isBoss
								Integer.parseInt(params[1]),	//tipo
								Double.parseDouble(params[3]),	//x
								Double.parseDouble(params[4]),	//y
								0								//hp - 0 pq eles n tem hahahah
								);
					}
					
					//adiciona na lista de inimigos da fase
					f.addElemento(t);
					
				}
				
				//fecha o Reader
				inFase.close();
				
				//adiciona a fase na lista de Fases
				this.fases.add(f);
				
			}
			
			//fecha o Reader do arquivo src
			in.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	
}
