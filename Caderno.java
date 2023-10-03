package lojaVirtual;

public class Caderno extends Produto {
    private int numerodePaginas;
    private int gramaturadoPapel;
   
	public Caderno(String nomeProduto, int codigoProduto, int estoque, String categoria, double valorCompra,
			double valorVenda, int numerodePaginas,int gramaturadoPapel) {
		super(nomeProduto, codigoProduto, estoque, categoria, valorCompra, valorVenda);
		this.numerodePaginas=numerodePaginas;
		this.gramaturadoPapel=gramaturadoPapel;
	}

	@Override
    public String toString() {
        return super.toString()+ numerodePaginas + " paginas|" + gramaturadoPapel + " gramas|";
    }
	
	public int getNumerodePaginas() {
		return numerodePaginas;
	}
	
	public void setNumerodePaginas(int numerodePaginas) {
		this.numerodePaginas = numerodePaginas;
	}

	public int getGramaturadoPapel() {
		return gramaturadoPapel;
	}

	public void setGramaturadoPapel(int gramaturadoPapel) {
		this.gramaturadoPapel = gramaturadoPapel;
	}  
	public String createBackup() {
		return "caderno," + this.nomeProduto + "," + this.estoque + "," + this.gramaturadoPapel + "," + this.numerodePaginas + "," + this.valorCompra + "," + this.valorVenda;
}
}
	