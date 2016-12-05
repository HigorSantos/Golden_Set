package partida;

public class Jogadores {

	public static final int JOGADOR1 = 0;
	public static final int JOGADOR2 = 1;
	public static final int NINGUEM  = 2;
			
	public String nome = "";
	public int jogador = -1;
	
	
	public Jogadores(int jogador, String nome) {
		this.nome = nome;
		this.jogador = jogador;
	}

}
