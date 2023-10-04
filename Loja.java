package lojaVirtual;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class Loja {
	private static double saldo = 1000;
	public static final String TRAVESSAO = "--------------------------";
	public static final String APERTE_ENTER = "Aperte Enter para continuar";
	public static final String DIGITE_CODIGO = "Digite o código do produto:";
	public static final String NAO_ENCONTRADO = "Produto não encontrado.";

	public Loja(double saldoInicial) {
		Loja.setSaldo(saldoInicial);
	}

	public static void main(String[] args) {
		ArrayList<Produto> produtos = new ArrayList<>();
		ArrayList<Relatorio> relatorioCompra = new ArrayList<>();
		ArrayList<Relatorio> relatorioVenda = new ArrayList<>();
		restaurarHistorico(relatorioCompra, relatorioVenda, produtos);

		Scanner sc = new Scanner(System.in);
		System.out.println("Bem-vindo ao Comério!");
		String comando;

		while (true) {
			listarComandos();
			comando = sc.nextLine();

			if (comando.equals("1")) {
				System.out.println(
						"Digite 'todos' para listar todos os produtos ou 'caderno', 'caneta', 'livro' para listar os produtos por categoria");
				String escolha = sc.nextLine();

				if (escolha.equals("todos")) {
					listarProdutos(produtos);
				} else if (escolha.equals("caderno")) {
					listarPorCategoria(produtos, Caderno.class);
				} else if (escolha.equals("caneta")) {
					listarPorCategoria(produtos, Caneta.class);
				} else if (escolha.equals("livro")) {
					listarPorCategoria(produtos, Livro.class);
				} else {
					System.out.println("Essa categoria não está cadastrada!");
				}

				System.out.println(APERTE_ENTER);
				sc.nextLine();

			} else if (comando.equals("2")) {
				System.out.println("Digite o nome do produto:");
				String nomeProduto = sc.nextLine();
				System.out.println(DIGITE_CODIGO);
				int codigoProduto = lerNumeroNaoNegativo(sc);

				Produto produtoJaExistente = procuraProdutoPeloCodigo(produtos, codigoProduto);

				if (produtoJaExistente != null) {
					System.out.println("Código de produto já cadastrado. Não é possível cadastrar novamente.");
				} else {
					Produto novoProduto = null;

					System.out.println("Digite uma das categorias disponíveis: Caderno, Caneta ou Livro:");
					String categoria = sc.nextLine();
					categoria = categoria.toLowerCase();

					if (categoria.equals("caderno")) {
						System.out.println("Digite o número de páginas: ");
						int numeroPaginas = lerNumeroNaoNegativo(sc);
						System.out.println("Digite a gramatura do papel: ");
						int gramaturaPapel = lerNumeroNaoNegativo(sc);
						System.out.println("Digite o valor de compra do produto:");
						double valorCompra = lerNumeroNaoNegativoDouble(sc);
						System.out.println("Digite o valor de venda do produto:");
						double valorVenda = lerNumeroNaoNegativoDouble(sc);

						novoProduto = new Caderno(nomeProduto, codigoProduto, 0, categoria, valorCompra, valorVenda,
								numeroPaginas, gramaturaPapel);

					} else if (categoria.equals("caneta")) {
						System.out.println("Digite a cor da caneta:");
						String nome = sc.nextLine();
						System.out.println("Digite o tamanho da ponta:");
						Double tamanho = lerNumeroNaoNegativoDouble(sc);
						System.out.println("Digite o valor de compra do produto:");
						double valorCompra = lerNumeroNaoNegativoDouble(sc);
						System.out.println("Digite o valor de venda do produto:");
						double valorVenda = lerNumeroNaoNegativoDouble(sc);

						novoProduto = new Caneta(nomeProduto, codigoProduto, 0, categoria, valorCompra, valorVenda,
								tamanho, nome);

					} else if (categoria.equals("livro")) {
						System.out.println("Digite o nome do autor:");
						String nomeAutor = sc.nextLine();
						System.out.println("Digite o número de páginas:");
						int numeroPaginas = lerNumeroNaoNegativo(sc);
						System.out.println("Digite o valor de compra do produto:");
						double valorCompra = lerNumeroNaoNegativoDouble(sc);
						System.out.println("Digite o valor de venda do produto:");
						double valorVenda = lerNumeroNaoNegativoDouble(sc);

						novoProduto = new Livro(nomeProduto, codigoProduto, 0, categoria, valorCompra, valorVenda,
								nomeAutor, numeroPaginas);

					} else {
						System.out.println("Entrada inválida. Tente novamente.");
					}

					if (novoProduto != null) {
						produtos.add(novoProduto);

						System.out.println("Deseja adicionar estoque a esse produto? Digite 'sim' ou 'não'.");
						String simOUnao = sc.nextLine();

						if (simOUnao.equals("sim")) {
							System.out.println("Digite a quantidade de produtos:");
							int quantidadeProduto = lerNumeroNaoNegativo(sc);

							if (quantidadeProduto >= 0) {
								novoProduto.alterarEstoque(quantidadeProduto);
								System.out.println(TRAVESSAO);
								System.out.printf(
										"O produto %s foi cadastrado com sucesso! Código = %d, Estoque = %d%n",
										nomeProduto, codigoProduto, quantidadeProduto);

								double valordaCompra = novoProduto.getValorCompra() * quantidadeProduto;
								if (getSaldo() >= valordaCompra) {
									double novoSaldo = getSaldo() - valordaCompra;
									setSaldo(novoSaldo);
									String nome = novoProduto.getNomeProduto();
									relatorioCompra
											.add(new Relatorio("compra", nome, quantidadeProduto, valordaCompra));
								}
								System.out.println(TRAVESSAO);
							} else {
								System.out.println(
										"Não é possível cadastrar produto com estoque negativo ou saldo insulficiente para aumentar estoque. ");
							}

						} else if (simOUnao.equals("não")) {
							System.out.println(TRAVESSAO);
							System.out.printf("O produto %s foi cadastrado com sucesso! Código = %d, Estoque = 0%n",
									nomeProduto, codigoProduto);
							System.out.println(TRAVESSAO);
						}

						System.out.println(APERTE_ENTER);
						sc.nextLine();
					}
				}

			} else if (comando.equals("3")) {
				System.out.println(DIGITE_CODIGO);
				int codigoProduto = lerNumeroNaoNegativo(sc);

				Produto produtoEncontrado = procuraProdutoPeloCodigo(produtos, codigoProduto);

				if (produtoEncontrado != null) {
					System.out.println("Digite a quantidade a ser adicionada ao estoque:");
					int quantidadeAdicionada = lerNumeroNaoNegativo(sc);
					double valorGasto = (quantidadeAdicionada * produtoEncontrado.getValorCompra());
					double novoSaldo = getSaldo() - valorGasto;

					if (getSaldo() >= valorGasto) {
						produtoEncontrado.alterarEstoque(quantidadeAdicionada);
						saldo = setSaldo(novoSaldo);
						String nome = produtoEncontrado.getNomeProduto();
						relatorioCompra.add(new Relatorio("compra", nome, quantidadeAdicionada, valorGasto));
						imprimeEntreTravessao("Compra efetuada com sucesso!");

					} else {
						imprimeEntreTravessao("Saldo insulficiente para compra!");
					}

				} else {
					imprimeEntreTravessao(NAO_ENCONTRADO);
				}

			} else if (comando.equals("4")) {
				System.out.println(DIGITE_CODIGO);
				int codigoProduto = lerNumeroNaoNegativo(sc);

				Produto produtoEncontrado = procuraProdutoPeloCodigo(produtos, codigoProduto);

				if (produtoEncontrado != null) {
					if (produtoEncontrado.getEstoque() < 0) {
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
				int codigodoProduto = lerNumeroNaoNegativo(sc);

				Produto produtoEncontrado = procuraProdutoPeloCodigo(produtos, codigodoProduto);

				if (produtoEncontrado != null) {
					System.out.println("Digite a quantidade a ser vendida:");
					int quantidadeVendida = lerNumeroNaoNegativo(sc);
					if (quantidadeVendida <= produtoEncontrado.getEstoque()) {
						produtoEncontrado.reduzirEstoque(quantidadeVendida);
						double valorAdquirido = quantidadeVendida * produtoEncontrado.getValorVenda();
						double novoSaldo = getSaldo() + valorAdquirido;
						setSaldo(novoSaldo);

						String nome = produtoEncontrado.getNomeProduto();
						relatorioVenda.add(new Relatorio("venda", nome, quantidadeVendida, valorAdquirido));
						System.out.println("Venda realizada com sucesso!");

					} else if (quantidadeVendida > produtoEncontrado.getEstoque()) {
						System.out.println("Estoque insulficiente para venda!");
					}

				} else {
					imprimeEntreTravessao(NAO_ENCONTRADO);
				}

				System.out.println(APERTE_ENTER);
				sc.nextLine();

			} else if (comando.equals("6")) {
				mostrarRelatorioVenda(relatorioVenda);
				System.out.println();
				mostrarRelatorioCompra(relatorioCompra);
				System.out.println();
				System.out.println("*O saldo atual é " + getSaldo() + " reais.");
				System.out.println(APERTE_ENTER);
				sc.nextLine();

			} else if (comando.equals("7")) {
				salvarHistorico(relatorioCompra, relatorioVenda, produtos);
				System.out.println("Até mais ver!");
				break;

			} else {
				System.out.println("Código inválido. Tente novamente.");
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
		if (produtos.size() == 0) {
			System.out.println("Não há produtos cadastrados!");
		} else if (produtos.size() > 0) {
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

	public static void salvarHistorico(List<Relatorio> relatatorioCompra, List<Relatorio> relatorioVenda,
			List<Produto> produtos) {
		try {
			PrintWriter writer = new PrintWriter("historico.txt");

			writer.write("saldo," + getSaldo() + "\n");

			for (Relatorio compra : relatatorioCompra) {
				writer.write(compra.createBackup() + "\n");
			}

			for (Relatorio venda : relatorioVenda) {
				writer.write(venda.createBackup() + "\n");
			}

			for (Produto produto : produtos) {
				writer.write(produto.createBackup() + "\n");
			}

			writer.close();
		} catch (FileNotFoundException e) {
			System.out.println("Arquivo não encontrado");
		}
	}

	public static void restaurarHistorico(List<Relatorio> relatorioCompra, List<Relatorio> relatorioVenda,
			List<Produto> produtos) {
		File arquivo = new File("historico.txt");
		int codigo = 1;

		try {
			Scanner leitorDoArquivo = new Scanner(arquivo);

			while (leitorDoArquivo.hasNextLine()) {
				String[] entrada = leitorDoArquivo.nextLine().split(",");

				if (entrada[0].equals("compra")) {
					Relatorio relatorio = new Relatorio(entrada[0], entrada[1], Integer.parseInt(entrada[2]),
							Double.parseDouble(entrada[3]));
					relatorioCompra.add(relatorio);

				} else if (entrada[0].equals("venda")) {
					Relatorio relatorio = new Relatorio(entrada[0], entrada[1], Integer.parseInt(entrada[2]),
							Double.parseDouble(entrada[3]));
					relatorioVenda.add(relatorio);

				} else if (entrada[0].equals("caderno")) {
					Caderno caderno = new Caderno(entrada[1], codigo, Integer.parseInt(entrada[2]), "caderno",
							Double.parseDouble(entrada[5]), Double.parseDouble(entrada[6]),
							Integer.parseInt(entrada[4]), Integer.parseInt(entrada[3]));
					produtos.add(caderno);

				} else if (entrada[0].equals("caneta")) {
					Caneta caneta = new Caneta(entrada[1], codigo, Integer.parseInt(entrada[2]), "caneta",
							Double.parseDouble(entrada[5]), Double.parseDouble(entrada[6]),
							Double.parseDouble(entrada[4]), entrada[3]);
					produtos.add(caneta);

				} else if (entrada[0].equals("livro")) {
					Livro livro = new Livro(entrada[1], codigo, Integer.parseInt(entrada[2]), "livro",
							Double.parseDouble(entrada[5]), Double.parseDouble(entrada[6]), entrada[3],
							Integer.parseInt(entrada[4]));
					produtos.add(livro);

				} else if (entrada[0].equals("saldo")) {
					setSaldo(Double.parseDouble(entrada[1]));
				}

				codigo++;
			}

			leitorDoArquivo.close();
		} catch (FileNotFoundException e) {
			System.out.println("Arquivo não encontrado.");
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

	public static void mostrarRelatorioVenda(List<Relatorio> relatorioVenda) {
		System.out.println("*Relatório de Venda:");
		System.out.println("Produto | Quantidade de unidades vendidas | Valor total da venda:");
		for (Relatorio venda : relatorioVenda) {
			System.out.println(venda.toString());
		}
		if (relatorioVenda.size() == 0) {
			System.out.println("Nenhum produto vendido.");
		}
	}

	public static void mostrarRelatorioCompra(List<Relatorio> relatorioCompra) {
		System.out.println("*Relatório de Compra:");
		System.out.println("Produto | Quantidade de unidades compradas | Valor total da compra do produto:");
		for (Relatorio compra : relatorioCompra) {
			System.out.println(compra.toString());
		}
		if (relatorioCompra.size() == 0) {
			System.out.println("Nenhum produto comprado.");
		}
	}

	public static int lerNumeroNaoNegativo(Scanner sc) {
		int numero;
		do {
			numero = Integer.parseInt(sc.nextLine());
			if (numero < 0) {
				System.out.println("O número não pode ser negativo. Digite novamente:");
			}
		} while (numero < 0);
		return numero;
	}

	public static double lerNumeroNaoNegativoDouble(Scanner sc) {
		double numero;
		do {
			numero = Double.parseDouble(sc.nextLine());
			if (numero < 0) {
				System.out.println("O número não pode ser negativo. Digite novamente:");
			}
		} while (numero < 0);
		return numero;
	}
}