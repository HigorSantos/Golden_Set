/**
 * Contagem
 * 
 * Versao 0.1
 * 
 * 05/12/2016
 * 
 * @author Higor Santos
 */
package pontos;

import partida.*;


public class Contagem { 
	//[FIXME] IMPLEMENTAR ESTAT�STICAS PARA GUARDAR OS VALORES DE CADA GAME
	//[FIXME] IMPLEMENTAR QUEM DEVE SACAR
	//[FIXME] IMPLEMENTAR TIEBREAK
	
	public boolean AcabouPartida = false;
	public int Vencedor = Jogadores.NINGUEM;
	
	private int[] _pontos		= {0,0};
	private int[] _pontosTotais	= {0,0};
	private int[] _games 		= {0,0};
	private int[][] _set;
	private int _tipoJogo = 3;
	private int _setAtual = 0;
	
	
	/**
	 * Construtor da de contagem de pontos (Games e Sets).
	 * @param Jogadores j1, Jogador 1.
	 * @param Jogadores j2, Jogador 2.
	 * @param int tipoJogo, tipo de jogo que ser� jogado; 3 ou 5 sets.
	 */
	public Contagem(Jogadores j1, Jogadores j2, int tipoJogo) {

		if(tipoJogo != 3){
			this._tipoJogo = 5;	//O jogo s� aceita 3 ou 5 sets.
		}
		
		_set = new int[2][5];
	}
	
	/**
	 * SE��O PARA M�TODOS DE CONTAGEM
	 * */
	
	/**
	 *  Pontua��o de um game 
	 *  @param jogador Qual jogador est� pontuando.
	 *  */
	public void pontua(Jogadores jogador){
		
		_pontos[jogador.jogador] += 1;
		_pontosTotais[jogador.jogador] += 1;
		/* Verifica se acabou */
		int campeao = acabouGame();
		
		if(campeao != Jogadores.NINGUEM){
			pontuaGame(jogador);
			
			limpaPontosGameAtual();
		}
	}
	
	/**
	 * Conta ponto de game para jogador indicado.
	 * @param jogador Qual jogador est� pontuando.
	 * */
	private void pontuaGame(Jogadores jogador){
		_games[jogador.jogador] += 1;
		
		/* Verifica se o game terminou */
		int campeaoGame = acabouSet();
		
		if (campeaoGame != Jogadores.NINGUEM){
			pontuaSet(jogador);
			
			limpaPontosGamesJogador();
		}
	}

	/**
	 * Incrementa contador de sets para o jogador indicado.
	 * @param jogador Qual jogador fechou o set.
	 * */
	private void pontuaSet(Jogadores jogador){
		_set[jogador.jogador][_setAtual] += 1;
		_setAtual += 1;
	
		int campeao = acabouPartida();
		
		if (campeao != Jogadores.NINGUEM){
			AcabouPartida = true;
			Vencedor = campeao;
		}
	}
	
	/**
	 * SE��O PARA M�TODOS DE VERIFICA��O
	 * */
	
	/**
	 * Verifica se um game acabou.
	 * @return int Qual Jogador venceu o game.
	 * */
	private int acabouGame(){
		
		int pontos_j1 = _pontos[Jogadores.JOGADOR1];
		int pontos_j2 = _pontos[Jogadores.JOGADOR2];
				
        if ((pontos_j1 == 4 && pontos_j2 < 4) || 
                (pontos_j1>4 && pontos_j1-pontos_j2==2) ){
        	
               return Jogadores.JOGADOR1;
           }
           else if ((pontos_j2 == 4 && pontos_j1 < 4) ||
                    (pontos_j2 > 4 && pontos_j2 - pontos_j1 == 2)){

               return Jogadores.JOGADOR2;
           }
           else {
        	   
               return Jogadores.NINGUEM;
           }
	}//FIM: acabou
	
	/**
	 * Verifica se um Set acabou.
	 * @return int Qual Jogador venceu o set
	 * */
	private int acabouSet(){
		
		int games_j1 = _games[Jogadores.JOGADOR1];
		int games_j2 = _games[Jogadores.JOGADOR2];
		
		if (games_j1 >= 6 && games_j1-games_j1 == 2){
			
			return Jogadores.JOGADOR1;
		}else if (games_j2 >= 6 && games_j2- games_j1 == 2){
			
			return Jogadores.JOGADOR2;
		}else{
			
			return Jogadores.NINGUEM;
		}
	}
	
	/**
	 * Verifica se uma partida acabou.
	 * @return int Qual Jogador venceu a partida.
	 * */
	private int acabouPartida(){

		int setsJ1 = 0;
		int setsJ2 = 0;
		
		/* Verifica se j� fora jogado o �ltimo set */
		for (int i=0; i < _tipoJogo; i++){
			int pontosJ1 = _set[i][Jogadores.JOGADOR1];
			int pontosJ2 = _set[i][Jogadores.JOGADOR2];
			
			if (pontosJ1 > pontosJ2){
				
				setsJ1++;
			}else{
				
				setsJ2++;
			}
		}
		
		if(setsJ1 > setsJ2 && setsJ1 >= getSetMinimo()){
			
			return Jogadores.JOGADOR1;
		}else if (setsJ2> setsJ1 && setsJ2 >= getSetMinimo()){
			
			return Jogadores.JOGADOR2;
		}else{
			return Jogadores.NINGUEM;
		}
			
	}//FIM: acabouPartida
	
	/**
	 * SE��O PARA M�TODOS DE DADOS
	 * */
	
	
	/**
	 * Limpa pontua��o do game que est� sendo jogador.
	 * */
	private void limpaPontosGameAtual(){
		_pontos[Jogadores.JOGADOR1] = 0;
		_pontos[Jogadores.JOGADOR2] = 0;
	}
	
	/**
	 * Limpa a pontua��o de games.
	 * */
	private void limpaPontosGamesJogador(){
		_games[Jogadores.JOGADOR1] = 0;
		_games[Jogadores.JOGADOR2] = 0;
	}
	
	/**
	 * Retorna os pontos totais de um jogador.
	 * @param jogador Qual jogador.
	 * @retun int Total de pontos.
	 * */	
	public int pontosTotais(Jogadores jogador){
		return _pontosTotais[jogador.jogador];
	}
	
	/**
	 * Retorna qual set est� sendo jogado.
	 * */
	public int getSetAtual(){
		
		return _setAtual+1;
	}
	
	/**
	 * Retorno a quantidade m�nima de sets para ganhar a partida
	 * */
	private int getSetMinimo(){
		if (_tipoJogo==3){
			return _tipoJogo - 1;
		}else{
			return _tipoJogo - 2;
		}
	}
}
