package lojaVirtual;

public class Relatorio {

	private String tipo;
	private String nome;
	private int quantidade;
	private double valorTotal;

	public Relatorio(String tipo, String nome, int quantidade, double valorTotal) {
		this.tipo = tipo;
		this.nome = nome;
		this.quantidade = quantidade;
		this.valorTotal = valorTotal;
	}

	@Override
	public String toString() {
		return "Nome=" + nome + ", quantidade=" + quantidade + ", valorTotal=" + valorTotal;
	}

	public String createBackup() {
		return this.tipo + "," + this.nome + "," + this.quantidade + "," + this.valorTotal;
	}
}
