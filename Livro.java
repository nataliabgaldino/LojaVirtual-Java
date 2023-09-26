package lojaVirtual;

public class Livro extends Produto {
	private String nomeAutor;
	private int numeroPaginas;
			
	
	public Livro(String nomeProduto, int codigoProduto, int estoque, String categoria, double valorCompra,
			double valorVenda, String nomeAutor, int numeroPaginas) {
		super(nomeProduto, codigoProduto, estoque, categoria, valorCompra, valorVenda);
		this.nomeAutor = nomeAutor;
		this.numeroPaginas = numeroPaginas;
		
		
	}

	@Override
	public String toString() {
	    return super.toString()+ numeroPaginas + " paginas|" + "autor: " + nomeAutor + "|";
	}
}

	
