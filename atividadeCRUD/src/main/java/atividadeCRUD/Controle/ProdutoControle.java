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

import atividadeCRUD.Produtos.Produto;
import atividadeCRUD.Produtos.ProdutoRepositorio;
import atividadeCRUD.pessoa.Pessoa;

@Controller
public class ProdutoControle {

	private ProdutoRepositorio produtoRepo;

	public ProdutoControle(ProdutoRepositorio produtoRepo) {
		this.produtoRepo = produtoRepo;
	}

	@GetMapping("/controle/produtos")
	public String produtos(Model model) {
		model.addAttribute("listaProdutos", produtoRepo.findAll());
		return "/controle/produtos/index";
	}

	@GetMapping("/controle/produtos/novosprodutos")
	public String novoProduto(@ModelAttribute("produto") Produto produto) {
		return "/controle/produtos/formulario";
	}

	@GetMapping("/controle/produtos/{id}")
	public String alterarProduto(@PathVariable("id") long id, Model model) {
		Optional<Produto> produtoOpt = produtoRepo.findById(id);
		if (produtoOpt.isEmpty()) {
			throw new IllegalArgumentException("Produto inválido");
		}
		model.addAttribute("produto", produtoOpt.get());
		return "controle/produtos/formulario";
	}

	@PostMapping("/controle/produtos/salvar")
	public String salvarProduto(@Valid @ModelAttribute("produto") Produto produto, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "controle/produtos/formulario";
		}

		produtoRepo.save(produto);
		return "redirect:/controle/produtos";
	}

	@GetMapping("/controle/produtos/excluir/{id}")
	public String excluirProduto(@PathVariable("id") long id) {
		Optional<Produto> produtoOpt = produtoRepo.findById(id);
		if (produtoOpt.isEmpty()) {
			throw new IllegalArgumentException("Produto inválido");
		}

		produtoRepo.delete(produtoOpt.get());
		return "redirect:/controle/produtos";
	}

}
