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


import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import partida.*;


public class Contagem { 
	//[FIXME] IMPLEMENTAR ESTATÍSTICAS PARA GUARDAR OS VALORES DE CADA GAME

	private static final Logger logger = LogManager.getLogger(Contagem.class);
	
	public boolean AcabouPartida = false;
	public int Vencedor = Jogadores.NINGUEM;
	
	private int[] _pontos		= {0,0};
	private int[] _pontosTotais	= {0,0};
	private int[] _games 		= {0,0};
	private int[][] _set;
	private int _tipoJogo = 3;				//Indica se o jogo terá 3 ou 5 sets
	private int _tipoGame = 6;				//Indica a quantidade de games de um set
	private int _setAtual = 0;
	private Jogadores _j1;
	private Jogadores _j2;
	private Jogadores _sacando;
	private boolean _jogandoTiebreak = false;
	
	
	/**
	 * Construtor da de contagem de pontos (Games e Sets).
	 * @param Jogadores j1, Jogador 1.
	 * @param Jogadores j2, Jogador 2.
	 * @param int tipoJogo, tipo de jogo que será jogado; 3 ou 5 sets.
	 */
	public Contagem(Jogadores j1, Jogadores j2, Jogadores comecaSacando, int tipoJogo) {

		this._j1 = j1;
		this._j2 = j2;
		this._sacando = comecaSacando;
		
		if(tipoJogo != 3){
			this._tipoJogo = 5;	//O jogo só aceita 3 ou 5 sets.
		}
		
		_set = new int[2][5];
	}
	
	/**
	 * SEÇÃO PARA MÉTODOS DE CONTAGEM
	 * */
	
	/**
	 *  Pontuação de um game 
	 *  @param jogador Qual jogador está pontuando.
	 *  */
	public void pontua(Jogadores jogador){
		
		//logger.trace("pontuou\t" + jogador.nome + "\t" + contagemTenis(jogador) );
		
		/* Faz a troca do jogador que está sacando,
		 * se estiver em tiebreak
		 */
		if (_jogandoTiebreak){
			if (_pontos[jogador.jogador] % 2 == 0){
				trocaJogador(jogador);
			}
		}
		
		/* Soma os pontos do game atual e os pontos totais da partida. */
		_pontos[jogador.jogador] += 1;
		_pontosTotais[jogador.jogador] += 1;
		
		/* Verifica se acabou */
		int campeao = acabouGame();

		if(campeao != Jogadores.NINGUEM){

			pontuaSet(jogador);
			
			trocaJogador(jogador);
			
			limpaPontosGameAtual();
		}
	}
	
	/**
	 * Conta ponto de game para jogador indicado.
	 * @param jogador Qual jogador está pontuando.
	 * */
	//private void pontuaGame(Jogadores jogador){
	//	_games[jogador.jogador] += 1;
	//	
	//	 Verifica se o game terminou 
	//	int campeaoGame = acabouSet();
	//	
	//	if (campeaoGame != Jogadores.NINGUEM){
	//		pontuaSet(jogador);
	//		
	//		limpaPontosGamesJogador();
	//	}
	//}

	/**
	 * Incrementa contador de sets para o jogador indicado.
	 * @param jogador Qual jogador fechou o set.
	 * */
	private void pontuaSet(Jogadores jogador){
		_set[jogador.jogador][_setAtual] += 1;
	
		int campeao = acabouSet();
		
		if (campeao != Jogadores.NINGUEM){
			_setAtual += 1;
			logger.trace("SET ATUAL\t" + _setAtual);
			int campeaoPartida = acabouPartida();
			
			if (campeaoPartida != Jogadores.NINGUEM){
				AcabouPartida = true;
				Vencedor = campeao;
			}
		}
	}
	
	/**
	 * Troca quem está sacando.
	 * @param Jogadores sacando. Quem estava sacando.
	 * */
	private void trocaJogador(Jogadores sacando){
		if (sacando == _j1){
			_sacando = _j2;
		}else{
			_sacando = _j1;
		}
	}
	
	/**
	 * SEÇÃO PARA MÉTODOS DE VERIFICAÇÃO
	 * */
	
	/**
	 * Verifica se um game acabou.
	 * @return int Qual Jogador venceu o game.
	 * */
	private int acabouGame(){
		
		/* Indica a diferença de games para sair vitorioso */
		int pontos_min_vitoria = 4;
		
		if (_jogandoTiebreak){
			pontos_min_vitoria = 7;
		}
		
		int pontos_j1 = _pontos[Jogadores.JOGADOR1];
		int pontos_j2 = _pontos[Jogadores.JOGADOR2];
		
        if (pontos_j1 >= pontos_min_vitoria 
        		&& (pontos_j1  -pontos_j2) >= 2 ){
        	
               return Jogadores.JOGADOR1;
               
        } else if (pontos_j2 >= pontos_min_vitoria
                    && (pontos_j2 - pontos_j1) >= 2){

               return Jogadores.JOGADOR2;
               
        } else {
        	   
               return Jogadores.NINGUEM;
        }
	}//FIM: acabou

	
	/**
	 * Verifica se um Set acabou. Também transforma o jogo em modo tiebreak.
	 * @return int Qual Jogador venceu o set
	 * */
	private int acabouSet(){
		
		int games_j1 = _set[Jogadores.JOGADOR1][_setAtual];
		int games_j2 = _set[Jogadores.JOGADOR2][_setAtual];
		
		//logger.trace("games_j1: " + games_j1 + "\tgames_j2:" + games_j2);
		
		/* Indica a diferença de games para sair vitorioso */
		int diferenca_games = 2;
		
		if (_jogandoTiebreak){
			diferenca_games = 1;
		}
		
		if (games_j1 >= 6 && (games_j1 - games_j2) >= diferenca_games){

			return Jogadores.JOGADOR1;
		}else if (games_j2 >= 6 && (games_j2 - games_j1) >= diferenca_games){
			
			return Jogadores.JOGADOR2;
		}else if (games_j1==games_j2 && games_j1==_tipoGame){
			
			// Entra em modo tiebreak
			_jogandoTiebreak = true;
			return Jogadores.NINGUEM;
		}else{
			
			return Jogadores.NINGUEM;
		}
	}
	
	/**
	 * Verifica se uma partida acabou.
	 * @return Qual Jogador venceu a partida.
	 * */
	private int acabouPartida(){

		int setsJ1 = 0;
		int setsJ2 = 0;
		
		/* Verifica se já fora jogado o último set */
		for (int i=0; i < _tipoJogo; i++){
			int pontosJ1 = _set[Jogadores.JOGADOR1][i];
			int pontosJ2 = _set[Jogadores.JOGADOR2][i];
			
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
	 * Define qual é o outro jogador.
	 * */
	private Jogadores adversario(Jogadores jogador){
		if (jogador == _j1){
			return _j2;
		}else{
			return _j1;
		}
	}
	
	/**
	 * SEÇÃO PARA MÉTODOS DE DADOS
	 * */
	
	
	/**
	 * Limpa pontuação do game que está sendo jogador.
	 * */
	private void limpaPontosGameAtual(){
		_pontos[Jogadores.JOGADOR1] = 0;
		_pontos[Jogadores.JOGADOR2] = 0;
	}
	
	/**
	 * Limpa a pontuação de games.
	 * */
	private void limpaPontosGamesJogador(){
		_jogandoTiebreak = false;
		
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
	 * Retorno os pontos do game atual.
	 * @param jogador Qual jogador.
	 * @retun Total de pontos do game.
	 * */
	public int pontosAtuais(Jogadores jogador){
		return _pontos[jogador.jogador];
	}
	
	/**
	 * Retorna qual set está sendo jogado.
	 * */
	public int getSetAtual(){
		
		return _setAtual+1;
	}
	
	/**
	 * Retorno a quantidade mínima de sets para ganhar a partida
	 * */
	private int getSetMinimo(){
		if (_tipoJogo==3){
			return _tipoJogo - 1;
		}else{
			return _tipoJogo - 2;
		}
	}
	
	/**
	 * Retorna quem deve sacar.
	 * */
	public Jogadores quemSaca(){
		return _sacando;
	}

	/**
	* Retorna os set de um jogador.
	* @param jogador De qual jogador deve ser retornado.
	* @return Array com os sets.
	*/
	public int[] setsJogador(Jogadores jogador){
		int[] sets = new int[_tipoJogo];

		for (int i = 0; i < _tipoJogo; i++){

			sets[i] = _set[jogador.jogador][i];
		}

		return sets;
	}

	/**
	 * Retorna a pontuação na notação do Tenis.
	 * @param jogador Pontos de qual jogador.
	 * @return String 0, 15, 30, 40, VA (vantagem a favor) e VC (vantagem contra).
	 * */
	public String contagemTenis(Jogadores jogador){
		
		int pontosRequeridos = pontosAtuais(jogador);
		int pontosAdversario = pontosAtuais(adversario(jogador));
		
		if (pontosRequeridos < 4 && _jogandoTiebreak == false){
			
			switch (pontosRequeridos){
			
			case 1:
				return "15";
			case 2:
				return "30";
			case 3:
				return "40";
			default:
				return "0";
			}
		}else if(_jogandoTiebreak){
			
			return Integer.toString(pontosRequeridos);
			
		}else if (pontosRequeridos==pontosAdversario){
			
			return "40";
		}else{
			if (pontosRequeridos % 2 == 0 && jogador == _sacando){
				return "VA";
			}else if(pontosRequeridos < pontosAdversario ){
				
				return "40";
			}else{
				return "VC";
			}
		}
		
		
	}
	
}
