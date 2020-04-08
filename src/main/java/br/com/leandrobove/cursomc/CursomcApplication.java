package br.com.leandrobove.cursomc;

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
import br.com.leandrobove.cursomc.entities.Produto;
import br.com.leandrobove.cursomc.entities.enums.TipoCliente;
import br.com.leandrobove.cursomc.repositories.CategoriaRepository;
import br.com.leandrobove.cursomc.repositories.CidadeRepository;
import br.com.leandrobove.cursomc.repositories.ClienteRepository;
import br.com.leandrobove.cursomc.repositories.EnderecoRepository;
import br.com.leandrobove.cursomc.repositories.EstadoRepository;
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

	public static void main(String[] args) {
		SpringApplication.run(CursomcApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Categoria cat1 = new Categoria(null, "Informática");
		Categoria cat2 = new Categoria(null, "Escritório");

		Produto p1 = new Produto(null, "Computador", 2000.00);
		Produto p2 = new Produto(null, "Impressora", 800.00);
		Produto p3 = new Produto(null, "Mouse", 80.00);

		cat1.getProdutos().addAll(Arrays.asList(p1, p2, p3));
		cat2.getProdutos().addAll(Arrays.asList(p2));

		p1.getCategorias().addAll(Arrays.asList(cat1));
		p2.getCategorias().addAll(Arrays.asList(cat1, cat2));
		p3.getCategorias().addAll(Arrays.asList(cat1));

		categoriaRepository.saveAll(Arrays.asList(cat1, cat2));
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
	}

}
