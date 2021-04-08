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

import atividadeCRUD.Pedido.Pedido;
import atividadeCRUD.Produto.ProdutoRepositorio;
import atividadeCRUD.pessoa.Pessoa;
import atividadeCRUD.pessoa.PessoaRepositorio;

@Controller
public class PessoaControle {

	private PessoaRepositorio pessoaRepo;
	private ProdutoRepositorio produtoRepo;

	public PessoaControle(PessoaRepositorio pessoaRepo) {
		this.pessoaRepo = pessoaRepo;
	}

	@GetMapping("/controle/pessoas")
	public String pessoas(Model model) {
		model.addAttribute("listaPessoas", pessoaRepo.findAll());
		return "/controle/pessoas/index";
	}

	@GetMapping("/controle/pessoas/novaspessoas")
	public String novaPessoa(Model model) {
		model.addAttribute("pessoa", new Pessoa(""));
		return "/controle/pessoas/formulario";
	}

	@GetMapping("/controle/pessoas/{id}")
	public String alterarPessoa(@PathVariable("id") long id, Model model) {
		Optional<Pessoa> pessoaOpt = pessoaRepo.findById(id);
		if (pessoaOpt.isEmpty()) {
			throw new IllegalArgumentException("Pessoa inválida");
		}
		model.addAttribute("pessoa", pessoaOpt.get());
		return "controle/pessoas/formulario";
	}

	@PostMapping("/controle/pessoas/salvar")
	public String salvarPessoa(@Valid @ModelAttribute("pessoa") Pessoa pessoa, BindingResult bindingResult,
			Model model) {
		if (bindingResult.hasErrors()) {
			model.addAttribute("produto", produtoRepo.findAll());
			return "controle/pessoas/formulario";
		}

		pessoaRepo.save(pessoa);
		return "redirect:/controle/pessoas";
	}

	@GetMapping("/controle/pessoas/excluir/{id}")
	public String excluirPessoa(@PathVariable("id") long id) {
		Optional<Pessoa> pessoaOpt = pessoaRepo.findById(id);
		if (pessoaOpt.isEmpty()) {
			throw new IllegalArgumentException("Pessoa inválida");
		}

		pessoaRepo.delete(pessoaOpt.get());
		return "redirect:/controle/pessoas";
	}

	@RequestMapping(value = "controle/pessoas/salvar", params = { "addPedido" })
	public String addPedido(Pessoa pessoa, BindingResult bindingResult, Model model) {
		pessoa.addPedido(new Pedido());
		String fieldId = "pedido" + (pessoa.getPedido().size() - 1) + ".datapedido";
		model.addAttribute("fieldToFocus", fieldId);
		return "/controle/pedidos/formulario";
	}

	@RequestMapping(value = "controle/pessoas/salvar", params = { "removePedido" })
	public String removePedido(Pessoa pessoa, BindingResult bindingResult, HttpServletRequest req) {
		final Integer pedidoIndex = Integer.valueOf(req.getParameter("removePedido"));

		pessoa.removePedido(pedidoIndex.intValue());
		return "/controle/pessoas/formulario";
	}

}
