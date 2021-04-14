package atividadeCRUD.pessoa;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import org.springframework.util.StringUtils;

import atividadeCRUD.Pedido.Pedido;




@Entity
public class Pessoa {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@NotBlank(message = "Campo nÃ£o pode ser vazio")
	private String nome;
	
	@Email(message = "Campo deve conter um email vÃ¡ildo")
	private String email;
	private String telefone;
	
	@OneToMany
	(
			cascade = CascadeType.ALL, 
			orphanRemoval = true,
			mappedBy = "pessoa"
	)
	private List<Pedido> pedido = new ArrayList<>();
	
	@Deprecated
	protected Pessoa() {}
	
	public Pessoa(String nome) {
		this.nome = nome;
	}
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getTelefone() {
		return telefone;
	}

	
	public void addPedido(Pedido pedido) {
		this.pedido.add(pedido);
		pedido.setPessoa(this);
	}
	
	public void removePedido(Pedido pedido) {
		this.pedido.remove(pedido);
		pedido.setPessoa(null);
	}
	
	public void removePedido(int index) {
		Pedido pedido = this.pedido.get(index);
		if (pedido != null) {
			this.pedido.remove(index);
			pedido.setPessoa(null);
		}
	}
	
	public List<Pedido> getPedido() {
		return this.pedido;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pessoa other = (Pessoa) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Pessoa [nome=" + nome + "]";
	}
	



}
