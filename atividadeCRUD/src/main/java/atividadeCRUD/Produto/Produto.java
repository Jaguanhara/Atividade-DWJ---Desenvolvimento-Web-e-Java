package atividadeCRUD.Produto;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;

import org.springframework.util.StringUtils;

import com.sun.istack.NotNull;

import atividadeCRUD.pedidoitem.PedidoItem;


@Entity
public class Produto {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@NotBlank(message = "Campo nÃ£o pode ser vazio")
	private String nomeprod;

	@NotNull()
	private Float valor;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "produto")
	private List<PedidoItem> pedidoItem = new ArrayList<>();

	@Deprecated
	protected Produto() {
	}

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

	public void addPedidoItem(PedidoItem pedidoItem) {
		this.pedidoItem.add(pedidoItem);
		pedidoItem.setProduto(this);
	}

	public void removePedidoItem(PedidoItem pedidoItem) {
		this.pedidoItem.remove(pedidoItem);
		pedidoItem.setProduto(null);
	}

	public void removePedidoItem(int index) {
		PedidoItem pedidoItem = this.pedidoItem.get(index);
		if (pedidoItem != null) {
			this.pedidoItem.remove(index);
			pedidoItem.setProduto(null);
		}
	}

	public List<PedidoItem> getPedidoItem() {
		return this.pedidoItem;
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
