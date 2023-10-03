package lojaVirtual;

public class Caneta extends Produto {
	private double tamanhodaPonta;
	private String cor;
	
	
	public Caneta(String nomeProduto, int codigoProduto, int estoque, String categoria, double valorCompra,
			double valorVenda,double tamanhodaPonta,String cor) {
		super(nomeProduto, codigoProduto, estoque, categoria, valorCompra, valorVenda);
		this.setTamanhodaPonta(tamanhodaPonta);
		this.setCor(cor);
	}
	
	@Override
    public String toString() {
        return super.toString()+ tamanhodaPonta + " mm|" + "cor: " + cor + "|";
    }

	
	public double getTamanhodaPonta() {
		return tamanhodaPonta;
	}

	public void setTamanhodaPonta(double tamanhodaPonta) {
		this.tamanhodaPonta = tamanhodaPonta;
	}

	public String getCor() {
		return cor;
	}

	public void setCor(String cor) {
		this.cor = cor;
	}
	
	public String createBackup() {
		return "caneta," + this.nomeProduto + "," + this.estoque + "," + this.cor + "," + this.tamanhodaPonta + "," + this.valorCompra + "," + this.valorVenda;
}
}
