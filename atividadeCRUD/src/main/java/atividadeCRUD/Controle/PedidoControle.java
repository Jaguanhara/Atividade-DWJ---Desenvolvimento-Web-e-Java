package atividadeCRUD.Controle;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import atividadeCRUD.Pedido.Pedido;
import atividadeCRUD.Pedido.PedidoRepositorio;
import atividadeCRUD.Produto.ProdutoRepositorio;
import atividadeCRUD.pessoa.Pessoa;
import atividadeCRUD.pessoa.PessoaRepositorio;

@Controller
public class PedidoControle {
	
	private PedidoRepositorio pedidoRepo;
	private PessoaRepositorio pessoaRepo;
	private ProdutoRepositorio produtoRepo;
	
	public PedidoControle(PedidoRepositorio pedidoRepo, PessoaRepositorio pessoaRepo, ProdutoRepositorio produtoRepo) {
		this.pedidoRepo = pedidoRepo;
		this.pessoaRepo = pessoaRepo;
		this.produtoRepo = produtoRepo;
	}
	
	@GetMapping("/controle/pedido")
	public String pedidos(Model model) {
		model.addAttribute("listaPedidos", pedidoRepo.findAll());
		return "/controle/pedidos/index";
	}
	
	@GetMapping("/controle/pedidos/novospedidos")
	public String novoPedido(Model model) {
		model.addAttribute("pedido", new Pedido());
		model.addAttribute("pessoa", pessoaRepo.findAll());
		model.addAttribute("produto", produtoRepo.findAll());
		return "/controle/pedidos/formulario";
	}
	
	@PostMapping("/controle/pedidos/salvar")
	public String salvarPedido(@Valid @ModelAttribute("pedido")Pedido pedido, BindingResult bindingResult) {
		if(bindingResult.hasErrors()) {
			return "controle/pedidos/formulario";
		}
		
		pedidoRepo.save(pedido);
		return "redirect:/controle/pessoas";
	}
	
	@GetMapping("/controle/pedidos/excluir/{id}")
	public String excluirPedido(@PathVariable("id") long id,  Model model) {
		Optional<Pedido> pedidoOpt = pedidoRepo.findById(id);
		if(pedidoOpt.isEmpty()) {
			throw new IllegalArgumentException("Pedido inválida");
		}
		model.addAttribute(pedidoOpt.get());
		model.addAttribute("pessoa", pessoaRepo.findAll());
		model.addAttribute("produto", produtoRepo.findAll());		
		pedidoRepo.delete(pedidoOpt.get());
		return "redirect:/controle/pedidos";
	}
}
