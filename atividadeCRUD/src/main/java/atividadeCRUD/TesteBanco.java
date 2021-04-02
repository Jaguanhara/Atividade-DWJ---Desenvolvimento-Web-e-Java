
package atividadeCRUD;

import java.time.LocalDate;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import atividadeCRUD.Pedido.Pedido;
import atividadeCRUD.Pedido.PedidoRepositorio;
import atividadeCRUD.Produto.Produto;
import atividadeCRUD.Produto.ProdutoRepositorio;
import atividadeCRUD.pessoa.Pessoa;
import atividadeCRUD.pessoa.PessoaRepositorio;

@Component 
@Transactional
public class TesteBanco implements CommandLineRunner {
	
	@Autowired
	private PessoaRepositorio pessoaRepo;
	
	@Autowired
	private ProdutoRepositorio produtoRepo;
	
	@Autowired
	private PedidoRepositorio pedidoRepo;
	
	@Override
	public void run(String... args) throws Exception {
		
		Produto produto1 = new Produto("Papel Higiênico", 35.f);
		Produto produto2 = new Produto("Papel Toalha", 25.f);
		
		produtoRepo.save(produto1);
		produtoRepo.save(produto2);
		produtoRepo.flush();
		
		Pedido pedido1 = new Pedido();
		pedido1.setDatapedido(LocalDate.of(2021, 03, 28));
		
		pedidoRepo.save(pedido1);
		pedidoRepo.flush();
		
		Pessoa p1 = new Pessoa("Jaguanhara Gomes de Oliveira Neto");
		p1.setEmail("Jaguanha@hotmail.com");
		p1.setTelefone("9999-9999");
		
	
		
		Pessoa p2 = new Pessoa("Matheus Henrique de Oliveira");
		p2.setEmail("Matheus@hotmail.com");
		p2.setTelefone("98888-8888");
		
		pessoaRepo.save(p1);
		pessoaRepo.save(p2);
	}
	
}
