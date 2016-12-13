package teste;

import static org.junit.Assert.*;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import org.junit.Test;

import pontos.*;
import partida.*;

public class TestePartida {

	private static final Logger logger = LogManager.getLogger(TestePartida.class);
	
	Jogadores jogador1 = new Jogadores(0, "Novak");
	Jogadores jogador2 = new Jogadores(1, "Roger");

	@Test
	public void jogador1VenceGame(){
		logger.trace("jogador1VenceGame");
		
		Contagem jogo = new Contagem(jogador1, jogador2, jogador1,3);

		for (int i = 0; i<4; i++) {
			jogo.pontua(jogador1);

		}

		int[] setsJogador1 = jogo.setsJogador(jogador1);
		int primeiroSet = setsJogador1[0];

		assertEquals("Jogador1 faz 1 game a 0",1, primeiroSet);
		assertEquals("Pontos do game zera 0",0, jogo.pontosAtuais(jogador1));
	}
	
	@Test
	public void jogador2VenceGame(){
		logger.trace("jogador2VenceGame");
		
		Contagem jogo = new Contagem(jogador1, jogador2, jogador1,3);

		for (int i = 0; i < 4; i++) {
			jogo.pontua(jogador2);

		}

		int[] setsJogador2 = jogo.setsJogador(jogador2);
		int primeiroSet = setsJogador2[0];

		assertEquals("Jogador2 faz 1 game a 0",1, primeiroSet);
		assertEquals("Pontos do game zera 0",0, jogo.pontosAtuais(jogador2));
	}
	
	@Test
	public void pontosIguais(){
		logger.trace("pontosIguais");
		
		Contagem jogo = new Contagem(jogador1, jogador2, jogador1,3);

		for (int i = 0; i < 3; i++) {
			jogo.pontua(jogador1);
			jogo.pontua(jogador2);
		}

		assertEquals("Jogador1 com 40 pontos", 3, jogo.pontosTotais(jogador1));
		assertEquals("Jogador2 com 40 pontos", 3, jogo.pontosTotais(jogador2));
		
		assertEquals("40 pontos (1)", "40", jogo.contagemTenis(jogador1));
		assertEquals("40 pontos (2)", "40", jogo.contagemTenis(jogador2));
	}
	
	@Test
	public void pontosIguaisMaisUm(){
		/* Nesse cenário, os jogadores fizeram 40-40, 
		 * um deles abre vantagem, mas o jogo volta a 40-40
		 * */
		logger.trace("pontosIguaisMaisUm");
		
		Contagem jogo = new Contagem(jogador1, jogador2, jogador1,3);

		int pontos = 4;
		
		for (int i = 0; i < pontos; i++) {
			jogo.pontua(jogador1);
			jogo.pontua(jogador2);
		}
		
		assertEquals("Jogador1 com 40 pontos", pontos, jogo.pontosTotais(jogador1));
		assertEquals("Jogador2 com 40 pontos", pontos, jogo.pontosTotais(jogador2));
		
		assertEquals("40 pontos (1)", "40", jogo.contagemTenis(jogador1));
		assertEquals("40 pontos (2)", "40", jogo.contagemTenis(jogador2));
	}

	// Vantagem contra
	@Test
	public void vantagemContraJ1(){
		logger.trace("vantagemContraJ1");
		
		Contagem jogo = new Contagem(jogador1, jogador2, jogador1,3);
		
		int pontos = 7;
		
		for (int i = 0; i < pontos; i++) {
			jogo.pontua(jogador1);
			jogo.pontua(jogador2);
		}
		
		jogo.pontua(jogador2);
		
		assertEquals("Jogador1 com 40 pontos", pontos, jogo.pontosTotais(jogador1));
		assertEquals("Jogador2 com Vantagem", pontos+1, jogo.pontosTotais(jogador2));
		
		assertEquals("40 pontos (1)", "40", jogo.contagemTenis(jogador1));
		assertEquals("VC (2)", "VC", jogo.contagemTenis(jogador2));
	}
	
	@Test
	public void vantagemContraJ2(){
		logger.trace("vantagemContraJ2");
		
		Contagem jogo = new Contagem(jogador1, jogador2, jogador2, 3);

		int pontos = 7;
		
		for (int i = 0; i < pontos; i++) {
			jogo.pontua(jogador1);
			jogo.pontua(jogador2);
		}
		jogo.pontua(jogador1);
		
		assertEquals("Jogador1 com Vantagem", pontos+1, jogo.pontosTotais(jogador1));
		assertEquals("Jogador2 com 40 pontos", pontos, jogo.pontosTotais(jogador2));
		
		assertEquals("VC (1)", "VC", jogo.contagemTenis(jogador1));
		assertEquals("40 pontos (2)", "40", jogo.contagemTenis(jogador2));
	}
	
	// Vantagem a favor
	@Test
	public void vantagemFavorJ1(){
		logger.trace("vantagemFavorJ1");
		
		Contagem jogo = new Contagem(jogador1, jogador2, jogador1, 3);
		
		int pontos = 7;
		
		for (int i = 0; i < pontos; i++) {
			jogo.pontua(jogador1);
			jogo.pontua(jogador2);
		}
		
		jogo.pontua(jogador1);
		
		assertEquals("Jogador1 com Vantagem", pontos+1, jogo.pontosTotais(jogador1));
		assertEquals("Jogador2 com 40 pontos", pontos, jogo.pontosTotais(jogador2));
		
		assertEquals("VA (1)", "VA", jogo.contagemTenis(jogador1));
		assertEquals("40 pontos (2)", "40", jogo.contagemTenis(jogador2));
	}
	
	@Test
	public void vantagemFavorJ2(){
		logger.trace("vantagemFavorJ2");
		
		Contagem jogo = new Contagem(jogador1, jogador2, jogador2, 3);
		
		int pontos = 7;
		
		for (int i = 0; i < pontos; i++) {
			jogo.pontua(jogador1);
			jogo.pontua(jogador2);
		}
		
		jogo.pontua(jogador2);
		
		assertEquals("Jogador1 com 40 pontos", pontos, jogo.pontosTotais(jogador1));
		assertEquals("Jogador2 com vantagem", pontos+1, jogo.pontosTotais(jogador2));
		
		assertEquals("40 pontos", "40", jogo.contagemTenis(jogador1));
		assertEquals("VA (2)", "VA", jogo.contagemTenis(jogador2));
	}

	@Test
	public void jogador1VenceSet3(){
		logger.trace("jogador1VenceSet3");
		
		Contagem jogo = new Contagem(jogador1, jogador2, jogador1,3);

		for (int set = 0; set < 2; set++){
			for (int x = 0; x < 6; x++){
				for (int i = 0; i < 4; i++) {
					jogo.pontua(jogador1);
				}
			}
		}
		
		int[] resultadoEsperado = {6,6,0};
		
		int[] setsJogador1 = jogo.setsJogador(jogador1);
		
		logger.trace(setsJogador1[0]);
		
		assertEquals("Jogo acabou",true, jogo.AcabouPartida);
		assertEquals("Jogador1 venceu",jogador1.jogador, jogo.Vencedor);
		
		/* Valida sets */
		for (int x = 0; x < 3; x++){
			assertEquals("Jogador1 faz " + x + "-0",resultadoEsperado[x], setsJogador1[x]);
		}

	}
	
	@Test
	public void jogador2VenceSet3(){
		logger.trace("jogador2VenceSet3");
		
		Contagem jogo = new Contagem(jogador1, jogador2, jogador1,3);

		for (int set = 0; set < 2; set++){
			for (int x = 0; x < 6; x++){
				for (int i = 0; i < 4; i++) {
					jogo.pontua(jogador2);
				}
			}
		}
		
		int[] resultadoEsperado = {6,6,0};
		
		int[] setsJogador2 = jogo.setsJogador(jogador2);
		
		assertEquals("Jogo acabou",true, jogo.AcabouPartida);
		assertEquals("Jogador2 venceu",jogador2.jogador, jogo.Vencedor);
		
		/* Valida sets */
		for (int x = 0; x < 3; x++){
			assertEquals("jogador2 faz " + x + "-0",resultadoEsperado[x], setsJogador2[x]);
		}
	}
	
	@Test
	public void jogador1VenceSet5(){
		logger.trace("jogador1VenceSet5");
		
		Contagem jogo = new Contagem(jogador1, jogador2, jogador1,5);

		for (int set = 0; set < 3; set++){
			for (int x = 0; x < 6; x++){
				for (int i = 0; i < 4; i++) {
					jogo.pontua(jogador1);
				}
			}
		}
		
		int[] resultadoEsperado = {6,6,6,0,0};
		
		int[] setsJogador1 = jogo.setsJogador(jogador1);
		
		logger.trace(setsJogador1[0]);
		
		assertEquals("Jogo acabou",true, jogo.AcabouPartida);
		assertEquals("Jogador1 venceu",jogador1.jogador, jogo.Vencedor);
		
		/* Valida sets */
		for (int x = 0; x < 5; x++){
			assertEquals("Jogador1 faz " + x + "-0",resultadoEsperado[x], setsJogador1[x]);
		}

	}
	
}
