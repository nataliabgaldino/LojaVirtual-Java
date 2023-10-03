package lojaVirtual;

public class Produto {
	protected String nomeProduto;
	protected int codigoProduto;
	protected int estoque;
	protected String categoria;
	protected double valorCompra;
	protected double valorVenda;

	public Produto(String nomeProduto, int codigoProduto, int estoque, String categoria, double valorCompra,
			double valorVenda) {
		this.nomeProduto = nomeProduto;
		this.codigoProduto = codigoProduto;
		this.estoque = estoque;
		this.categoria = categoria;
		this.setValorCompra(valorCompra);
		this.valorVenda = valorVenda;
	}

	public String getNomeProduto() {
		return nomeProduto;
	}

	public void setNomeProduto(String nomeProduto) {
		this.nomeProduto = nomeProduto;
	}

	public int getCodigoProduto() {
		return codigoProduto;
	}

	public void setCodigoProduto(int codigoProduto) {
		this.codigoProduto = codigoProduto;
	}

	public int getEstoque() {
		return estoque;
	}

	public void setEstoque(int estoque) {
		this.estoque = estoque;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public double getValorCompra() {
		return valorCompra;
	}

	public void setValorCompra(double valorCompra) {
		this.valorCompra = valorCompra;
	}

	public double getValorVenda() {
		return valorVenda;
	}

	public void setValorVenda(double valorVenda) {
		this.valorVenda = valorVenda;
	}

	@Override
	public String toString() {
		return nomeProduto + " |cód.: " + codigoProduto + " | estoque: " + estoque + " | custo de compra: "
				+ valorCompra + " | valor de venda: " + valorVenda + "|";
	}

	public String listarProdutos() {
		return nomeProduto + " |cód.: " + codigoProduto + " | estoque: " + estoque + " | custo de compra: "
				+ valorCompra + " | valor de venda: " + valorVenda + "|";

	}

	public String reduzirEstoque(int quantidade) {
		if (quantidade > this.estoque) {
			return "Estoque insulficiente.";
		} else {
			this.estoque = estoque - quantidade;

			return "Estoque do produto atualizado com sucesso!";
		}
	}

	public void alterarEstoque(int quantidade) {
		this.estoque = quantidade + estoque;
	}

	public String createBackup() {
		return "caderno," + this.nomeProduto + "," + this.estoque + "," + this.valorCompra + "," + this.valorVenda;
	}
}