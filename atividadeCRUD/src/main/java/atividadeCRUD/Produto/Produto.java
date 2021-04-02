package atividadeCRUD.Produto;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;

import com.sun.istack.NotNull;

import atividadeCRUD.pedidoitem.PedidoItem;
import atividadeCRUD.pessoa.Pessoa;


@Entity
public class Produto {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@NotBlank(message = "Campo não pode ser vazio")
	private String nomeprod;
	
	@NotNull()
	private Float valor;
	
	@ManyToOne
	private Pessoa pessoa;
	
	
	@Deprecated
	protected Produto() {}
	
	public Produto(String nomeprod, Float valor) {
		this.nomeprod = nomeprod;
		this.valor = valor;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNomeprod() {
		return nomeprod;
	}

	public void setNomeprod(String nomeprod) {
		this.nomeprod = nomeprod;
	}

	public Float getValor() {
		return valor;
	}

	public void setValor(Float valor) {
		this.valor = valor;
	}
	
	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}


	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Produto other = (Produto) obj;
		return Objects.equals(id, other.id);
	}

	
}
