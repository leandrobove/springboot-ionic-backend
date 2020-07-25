package br.com.leandrobove.cursomc;

import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import br.com.leandrobove.cursomc.entities.Categoria;
import br.com.leandrobove.cursomc.entities.Cidade;
import br.com.leandrobove.cursomc.entities.Cliente;
import br.com.leandrobove.cursomc.entities.Endereco;
import br.com.leandrobove.cursomc.entities.Estado;
import br.com.leandrobove.cursomc.entities.ItemPedido;
import br.com.leandrobove.cursomc.entities.Pagamento;
import br.com.leandrobove.cursomc.entities.PagamentoComBoleto;
import br.com.leandrobove.cursomc.entities.PagamentoComCartao;
import br.com.leandrobove.cursomc.entities.Pedido;
import br.com.leandrobove.cursomc.entities.Produto;
import br.com.leandrobove.cursomc.entities.enums.EstadoPagamento;
import br.com.leandrobove.cursomc.entities.enums.TipoCliente;
import br.com.leandrobove.cursomc.repositories.CategoriaRepository;
import br.com.leandrobove.cursomc.repositories.CidadeRepository;
import br.com.leandrobove.cursomc.repositories.ClienteRepository;
import br.com.leandrobove.cursomc.repositories.EnderecoRepository;
import br.com.leandrobove.cursomc.repositories.EstadoRepository;
import br.com.leandrobove.cursomc.repositories.ItemPedidoRepository;
import br.com.leandrobove.cursomc.repositories.PagamentoRepository;
import br.com.leandrobove.cursomc.repositories.PedidoRepository;
import br.com.leandrobove.cursomc.repositories.ProdutoRepository;

@SpringBootApplication
public class CursomcApplication implements CommandLineRunner {

	@Autowired
	private CategoriaRepository categoriaRepository;
	@Autowired
	private ProdutoRepository produtoRepository;
	@Autowired
	private EstadoRepository estadoRepository;
	@Autowired
	private CidadeRepository cidadeRepository;
	@Autowired
	private ClienteRepository clienteRepository;
	@Autowired
	private EnderecoRepository enderecoRepository;
	@Autowired
	private PedidoRepository pedidoRepository;
	@Autowired
	private PagamentoRepository pagamentoRepository;
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;

	public static void main(String[] args) {
		SpringApplication.run(CursomcApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Categoria cat1 = new Categoria(null, "Informática");
		Categoria cat2 = new Categoria(null, "Escritório");
		Categoria cat3 = new Categoria(null, "Cama, mesa e banho");
		Categoria cat4 = new Categoria(null, "Eletrônicos");
		Categoria cat5 = new Categoria(null, "Jardinagem");
		Categoria cat6 = new Categoria(null, "Decoração");
		Categoria cat7 = new Categoria(null, "Perfumaria");

		Produto p1 = new Produto(null, "Computador", 2000.00);
		Produto p2 = new Produto(null, "Impressora", 800.00);
		Produto p3 = new Produto(null, "Mouse", 80.00);

		cat1.getProdutos().addAll(Arrays.asList(p1, p2, p3));
		cat2.getProdutos().addAll(Arrays.asList(p2));

		p1.getCategorias().addAll(Arrays.asList(cat1));
		p2.getCategorias().addAll(Arrays.asList(cat1, cat2));
		p3.getCategorias().addAll(Arrays.asList(cat1));

		categoriaRepository.saveAll(Arrays.asList(cat1, cat2, cat3, cat4, cat5, cat6, cat7));
		produtoRepository.saveAll(Arrays.asList(p1, p2, p3));

		Estado est1 = new Estado(null, "Minas Gerais");
		Estado est2 = new Estado(null, "São Paulo");

		Cidade cid1 = new Cidade(null, "Uberlândia", est1);
		Cidade cid2 = new Cidade(null, "São Paulo", est2);
		Cidade cid3 = new Cidade(null, "Campinas", est2);

		est1.getCidades().addAll(Arrays.asList(cid1));
		est2.getCidades().addAll(Arrays.asList(cid2, cid3));

		estadoRepository.saveAll(Arrays.asList(est1, est2));
		cidadeRepository.saveAll(Arrays.asList(cid1, cid2, cid3));

		Cliente c1 = new Cliente(null, "Maria Silva", "maria@gmail.com", "12345678910", TipoCliente.PESSOAFISICA);
		c1.getTelefones().addAll(Arrays.asList("85999988888", "8532225555"));

		Endereco end1 = new Endereco(null, "Rua flores", "1902", "Apto 34", "Jardim", "055555-000", c1, cid1);
		Endereco end2 = new Endereco(null, "Av. Matos", "105", "Sala 22", "Centro", "045872-000", c1, cid2);

		c1.getEnderecos().addAll(Arrays.asList(end1, end2));

		clienteRepository.saveAll(Arrays.asList(c1));
		enderecoRepository.saveAll(Arrays.asList(end1, end2));

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm");

		Pedido ped1 = new Pedido(null, sdf.parse("30/09/2017 10:32"), c1, end1);
		Pedido ped2 = new Pedido(null, sdf.parse("10/10/2017 19:35"), c1, end2);

		Pagamento pgto1 = new PagamentoComCartao(null, EstadoPagamento.APROVADO, ped1, 6);
		ped1.setPagamento(pgto1);

		Pagamento pgto2 = new PagamentoComBoleto(null, EstadoPagamento.PENDENTE, ped2, sdf.parse("20/10/2017 00:00"),
				null);
		ped2.setPagamento(pgto2);

		c1.getPedidos().addAll(Arrays.asList(ped1, ped2));

		pedidoRepository.saveAll(Arrays.asList(ped1, ped2));
		pagamentoRepository.saveAll(Arrays.asList(pgto1, pgto2));

		ItemPedido ip1 = new ItemPedido(ped1, p1, 0.0, 1, 2000.00);
		ItemPedido ip2 = new ItemPedido(ped1, p3, 0.0, 2, 80.00);
		ItemPedido ip3 = new ItemPedido(ped2, p2, 100.0, 1, 800.00);

		ped1.getItens().addAll(Arrays.asList(ip1, ip2));
		ped2.getItens().addAll(Arrays.asList(ip3));

		p1.getItens().addAll(Arrays.asList(ip1));
		p2.getItens().addAll(Arrays.asList(ip3));
		p3.getItens().addAll(Arrays.asList(ip2));

		itemPedidoRepository.saveAll(Arrays.asList(ip1, ip2, ip3));
	}

}
