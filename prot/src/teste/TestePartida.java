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
	public void jogador1VenceSet(){
		logger.trace("jogador1VenceSet");
		
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
	public void jogador2VenceSet(){
		logger.trace("jogador2VenceSet");
		
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
	
	// Vantagem a favor
}
