package atividadeCRUD.Pedido;

import java.time.LocalDate;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.springframework.format.annotation.DateTimeFormat;

import atividadeCRUD.Produto.Produto;
import atividadeCRUD.pessoa.Pessoa;

@Entity
public class Pedido {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@DateTimeFormat(style = "yyyy-MM-dd")
	private LocalDate datapedido;
	
	@ManyToOne
	private Pessoa pessoa;
	
	@ManyToOne
	private Produto produto;
	

	@Deprecated
	public Pedido() {}
	
	public Pedido(LocalDate datapedido) {
		this.datapedido = datapedido;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public LocalDate getDatapedido() {
		return datapedido;
	}

	public void setDatapedido(LocalDate datapedido) {
		this.datapedido = datapedido;
	}
	
	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
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
		Pedido other = (Pedido) obj;
		return id == other.id;
	}

	

	
}
