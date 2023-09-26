package lojaVirtual;


import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Loja {
	private static double saldo  = 1000;
	public static final String TRAVESSAO = "--------------------------";
	public static final String APERTE_ENTER = "Aperte Enter para continuar";
	public static final String DIGITE_CODIGO = "Digite o código do produto:";
	public static final String NAO_ENCONTRADO = "Produto não encontrado.";

	public Loja(double saldoInicial) {
		Loja.setSaldo(saldoInicial);
	}

	// TODO
	// salvar as informação - persistencia de dados;


	public static void main(String[] args) {
		ArrayList<Produto> produtos = new ArrayList<>();
		produtos.add(new Produto("Caderno", 10, 1, "Caderno", 5, 10));
		produtos.add(new Produto("Caneta", 20, 2, "Caneta", 1.5, 2));
		produtos.add(new Produto("Caderno", 5, 3, "Caderno", 40, 60));
		ArrayList<String> relatorioCompra = new ArrayList<>();
		ArrayList<String> relatorioVenda  = new ArrayList<>();

		Scanner sc = new Scanner(System.in);
		System.out.println("Bem-vindo ao Comério!");
		String comando;

		while (true) {
			listarComandos();
			comando = sc.nextLine();

			if (comando.equals("7")) {
				//TODO falta salvar informacoes (persistencia de dados): os produtos, estoque e saldo do comércio.
				System.out.println("Até mais ver!");
				break;

			} else if (comando.equals("1")) {        	
				System.out.println("Digite'todos',para listar todos os produtos ou 'caderno', 'caneta','livro' para listar os produtos por categoria");
				String escolha = sc.nextLine();
				if (escolha.equals("todos")){

					listarProdutos(produtos);
				}

				else if (escolha.equals("caderno")) {	      

					listarPorCategoria(produtos, Caderno.class);

				}
				else if (escolha.equals("caneta")) {
					listarPorCategoria(produtos, Caneta.class);
				}

				else if (escolha.equals("livro")) {
					listarPorCategoria(produtos, Livro.class);

				}	else {
					System.out.println("Essa categoria não está cadastrada!");
				}

				System.out.println(APERTE_ENTER);
				sc.nextLine();


			} else if (comando.equals("2")) {
				System.out.println("Digite o nome do produto:");
				String nomeProduto = sc.nextLine();
				System.out.println(DIGITE_CODIGO);
				int codigoProduto = Integer.parseInt(sc.nextLine());

				Produto produtoJaExistente = procuraProdutoPeloCodigo(produtos, codigoProduto);

				if (produtoJaExistente != null) {
					System.out.println("Código de produto já cadastrado. Não é possível cadastrar novamente.");
				} else {
					Produto novoProduto = null;

					System.out.println("Digite uma das categorias disponíveis: Caderno, Caneta ou Livro:");
					String categoria = sc.nextLine();
					categoria = categoria.toLowerCase();

					if (categoria.equals("caderno")){
						System.out.println("Digite o número de páginas: ");
						int numeroPaginas = Integer.parseInt(sc.nextLine());
						System.out.println("Digite a gramatura do papel: ");
						int gramaturaPapel = Integer.parseInt(sc.nextLine());
                        System.out.println("Digite o valor de compra do produto:");
						double valorCompra = Double.parseDouble(sc.nextLine());
						System.out.println("Digite o valor de venda do produto:");
						double valorVenda = Double.parseDouble(sc.nextLine());
						
						novoProduto = new Caderno(nomeProduto, codigoProduto, 0, categoria, valorCompra, valorVenda, numeroPaginas, gramaturaPapel);
					}
					
					if (categoria.equals("caneta")) {
						System.out.println("Digite a cor da caneta:");
						String nome = sc.nextLine();
						System.out.println("Digite o tamanho da ponta:");
						int tamanho = Integer.parseInt(sc.nextLine());
						System.out.println("Digite o valor de compra do produto:");
						double valorCompra = Double.parseDouble(sc.nextLine());
						System.out.println("Digite o valor de venda do produto:");
						double valorVenda = Double.parseDouble(sc.nextLine());
						
						novoProduto = new Caneta(nomeProduto, codigoProduto, 0, categoria, valorCompra, valorVenda, tamanho, nome);
					}
					
					if (categoria.equals("livro")) {
						System.out.println("Digite o nome do autor:");
						String nomeAutor = sc.nextLine();
						System.out.println("Digite o número de páginas:");
						int numeroPaginas = Integer.parseInt(sc.nextLine());
						System.out.println("Digite o valor de compra do produto:");
						double valorCompra = Double.parseDouble(sc.nextLine());
						System.out.println("Digite o valor de venda do produto:");
						double valorVenda = Double.parseDouble(sc.nextLine());
						
						novoProduto = new Livro(nomeProduto, codigoProduto, 0, categoria, valorCompra, valorVenda, nomeAutor, numeroPaginas);
						
					}

					if (novoProduto != null) {
						produtos.add(novoProduto);

						System.out.println("Deseja adicionar estoque a esse produto? Digite 'sim' ou 'não'.");
						String simOUnao = sc.nextLine();

						if (simOUnao.equals("sim")) {
							System.out.println("Digite a quantidade de produtos:");
							int quantidadeProduto = Integer.parseInt(sc.nextLine());

							if (quantidadeProduto >= 0) {
								novoProduto.alterarEstoque(quantidadeProduto);
								System.out.println(TRAVESSAO);
								System.out.printf("O produto %s foi cadastrado com sucesso! Código = %d, Estoque = %d%n", nomeProduto, codigoProduto, quantidadeProduto);
								System.out.println(TRAVESSAO);
							} else {
								System.out.println("Não é possível cadastrar produto com estoque negativo.");
							}

						} else if (simOUnao.equals("não")) {
							System.out.println(TRAVESSAO);
							System.out.printf("O produto %s foi cadastrado com sucesso! Código = %d, Estoque = 0%n", nomeProduto, codigoProduto);
							System.out.println(TRAVESSAO);
						}

						System.out.println(APERTE_ENTER);
						sc.nextLine();
					}
				}

			} else if (comando.equals("3")) {
				System.out.println(DIGITE_CODIGO);
				int codigoProduto = Integer.parseInt(sc.nextLine());

				Produto produtoEncontrado = procuraProdutoPeloCodigo(produtos, codigoProduto);

				if (produtoEncontrado != null) { 
					System.out.println("Digite a quantidade a ser adicionada ao estoque:");
					int quantidadeAdicionada = Integer.parseInt(sc.nextLine());
					double valorGasto = (quantidadeAdicionada * produtoEncontrado.getValorCompra());
					double novoSaldo = getSaldo() - valorGasto;

					if (getSaldo()>=valorGasto) {
						produtoEncontrado.alterarEstoque(quantidadeAdicionada);	
						saldo = setSaldo(novoSaldo);
						String nome= produtoEncontrado.getNomeProduto(); 
						String variavel = produtoEncontrado.relatorio(nome, quantidadeAdicionada,valorGasto) ;
						relatorioCompra.add(variavel);
						imprimeEntreTravessao("Compra efetuada com sucesso!");

					}

					if (getSaldo() < valorGasto){
						imprimeEntreTravessao("Saldo insulficiente para compra!");
					}

				} else {
					imprimeEntreTravessao(NAO_ENCONTRADO);
				}

			} else if (comando.equals("4")) {
				System.out.println(DIGITE_CODIGO);
				int codigoProduto = Integer.parseInt(sc.nextLine());

				Produto produtoEncontrado = procuraProdutoPeloCodigo(produtos, codigoProduto);

				if (produtoEncontrado != null) {
					if (produtoEncontrado.getEstoque() < 0 ) {
						System.out.println("Estoque inválido para remoção!");
					} else {
						System.out.println("O produto ainda está em estoque. Tem certeza que deseja remover?");
						System.out.println("Digite 'sim' ou 'não': ");
						String confirmacao = sc.nextLine();

						if (confirmacao.equals("sim")) {
							removeProduto(produtos, produtoEncontrado);

						} else {
							imprimeEntreTravessao("O produto não foi removido.");
						}

						System.out.println(APERTE_ENTER);
						sc.nextLine();        
					}
				}

			} else if (comando.equals("5")) {
				System.out.println(DIGITE_CODIGO);
				int codigodoProduto = Integer.parseInt(sc.nextLine());

				Produto produtoEncontrar = procuraProdutoPeloCodigo(produtos, codigodoProduto);

				if (produtoEncontrar != null) {
					System.out.println("Digite a quantidade a ser vendida:");
					int quantidadeVendida = Integer.parseInt(sc.nextLine());
					if(quantidadeVendida<=produtoEncontrar.getEstoque()) {
						produtoEncontrar.reduzirEstoque(quantidadeVendida); 
						double valorAdquirido = quantidadeVendida * produtoEncontrar.getValorVenda();
						double novoSaldo = getSaldo() + valorAdquirido; 
						setSaldo(novoSaldo);
					
						String nome= produtoEncontrar.getNomeProduto(); 
						String variavel = produtoEncontrar.relatorio(nome, quantidadeVendida,valorAdquirido) ;
						relatorioVenda.add(variavel);
						System.out.println("Venda realizada com sucesso!");
					}
					else if(quantidadeVendida>produtoEncontrar.getEstoque()) {
						System.out.println("Estoque insulficiente para venda!");	
					}

				} else {
					imprimeEntreTravessao(NAO_ENCONTRADO);
				}

				System.out.println(APERTE_ENTER);
				sc.nextLine();

			} else if (comando.equals("6")){
				mostrarRelatorioVenda(relatorioVenda);
				System.out.println();
				mostrarRelatorioCompra(relatorioCompra);
				System.out.println();
				System.out.println("*O saldo atual é " + getSaldo() + " reais.");
				System.out.println(APERTE_ENTER);
				sc.nextLine();
			}
		}
		sc.close();
	}

	public static double getSaldo() {
		return saldo;
	}

	public static double setSaldo(double novoSaldo) {
		return saldo = novoSaldo;
	}

	public static void listarComandos() {
		System.out.println(TRAVESSAO);
		System.out.println("Lista dos comandos: ");
		System.out.println("1. Listar;");
		System.out.println("2. Cadastrar;");
		System.out.println("3. Adicionar;");
		System.out.println("4. Remover;");
		System.out.println("5. Vender;");
		System.out.println("6. Mostrar relatório;");
		System.out.println("7. Sair.");
		System.out.println(TRAVESSAO);
		System.out.println("Digite o código do comando: ");        
	}

	public static void listarProdutos(List<Produto> produtos) {
		System.out.println(TRAVESSAO);
		if (produtos.size()==0) {
			System.out.println("Não há produtos cadastrados!");
		}
		else if (produtos.size()>0){
			for (int i = 0; i < produtos.size(); i++) {
				System.out.println(produtos.get(i).listarProdutos());
			}
			System.out.println(TRAVESSAO);
		}
	}

	public static Produto procuraProdutoPeloCodigo(List<Produto> produtos, int codigoProduto) {
		  for (Produto produto : produtos) {
		    if (produto.getCodigoProduto() == codigoProduto) {
		    	return produto;
		  	}
		  }

		  return null;
		}
	
	
	public static void listarPorCategoria(List<Produto> produtos, Class<? extends Produto> categoria) {
	    System.out.println(TRAVESSAO);
	    boolean encontrouProdutos = false;
        for (Produto produto : produtos) {
            if (categoria.isInstance(produto)) {
                System.out.println(produto.toString());
            
	            }
	            encontrouProdutos = true;
       
	        
	        
	    if (!encontrouProdutos) {
	        System.out.println("Nenhum produto encontrado para a categoria especificada.");
	    }
	}
	}	
	public static void imprimeEntreTravessao(String imprimir) {
		System.out.println(TRAVESSAO);
		System.out.println(imprimir);
		System.out.println(TRAVESSAO);
	}

	public static void removeProduto(List<Produto> produtos, Produto produtoEncontrado) {
		int indice = produtos.indexOf(produtoEncontrado);
		produtos.remove(indice);

		imprimeEntreTravessao("Produto removido!");
	}
	
	public static void mostrarRelatorioVenda(List<String> relatorioVenda) {
		System.out.println("*Relatório de Venda:");
		System.out.println("Produto | Quantidade de unidades vendidas | Valor total da venda:");
		for (String venda : relatorioVenda ) {
			System.out.println(venda);
		}
		if(relatorioVenda.size()==0) {
			System.out.println("Nenhum produto vendido.");
		}	
	}

	public static void mostrarRelatorioCompra(List<String> relatorioCompra) {
		System.out.println("*Relatório de Compra:");
		System.out.println("Produto | Quantidade de unidades compradas | Valor total da compra do produto:");
		for (String compra : relatorioCompra ) {
			System.out.println(compra);
		}
		if(relatorioCompra.size()==0) {
			System.out.println("Nenhum produto comprado.");

		}
	}
}