package atividadeCRUD.Controle;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import atividadeCRUD.Pedido.Pedido;
import atividadeCRUD.Pedido.PedidoRepositorio;
import atividadeCRUD.Produto.ProdutoRepositorio;
import atividadeCRUD.pedidoitem.PedidoItem;
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
		model.addAttribute("listaPessoas", pessoaRepo.findAll());
		model.addAttribute("listaProdutos", produtoRepo.findAll());
		return "/controle/pedidos/formulario";
	}
	
	@PostMapping("/controle/pedidos/salvar")
	public String salvarPedido(@Valid @ModelAttribute("pedido")Pedido pedido, BindingResult bindingResult) {
		if(bindingResult.hasErrors()) {
			return "controle/pedidos/formulario";
		}
		pedido.corrigirPedidoItem();
		
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
		pedidoRepo.delete(pedidoOpt.get());
		return "redirect:/controle/pedidos";
	}
	
	@RequestMapping(value="/controle/pedidos/salvar", params = {"addItem"})
	public String addItem(Pedido pedido, BindingResult bindingResult, Model model) {
		pedido.getPedidoItem().add(new PedidoItem());
		
		model.addAttribute("listaPessoas", pessoaRepo.findAll());
		model.addAttribute("listaProdutos", produtoRepo.findAll());
		
		return "controle/pedidos/formulario";
	}
	
	@RequestMapping(value="/controle/pedidos/salvar", params = {"removeItem"})
	public String removeItem(Pedido pedido, BindingResult bindingResult, HttpServletRequest req, Model model) {
		final Integer itemIndex = Integer.valueOf(req.getParameter("removeItem"));
		pedido.getPedidoItem().remove(itemIndex.intValue());
		
		model.addAttribute("listaPessoas", pessoaRepo.findAll());
		model.addAttribute("listaProdutos", produtoRepo.findAll());
		
		return "controle/pedidos/formulario";
	}

}
