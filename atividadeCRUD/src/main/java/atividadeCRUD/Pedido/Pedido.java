package atividadeCRUD.Pedido;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.springframework.format.annotation.DateTimeFormat;

import atividadeCRUD.pedidoitem.PedidoItem;
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
	
	@OneToMany
	(
			cascade = CascadeType.ALL, 
			orphanRemoval = true,
			mappedBy = "pedido"
	)
	private List<PedidoItem> pedidoItem = new ArrayList<>();
	
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
	
	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}
	
	public void addPedidoItem(PedidoItem  pedidoItem) {
		this.pedidoItem.add(pedidoItem);
		pedidoItem.setPedido(this);
	}
	
	public void removePedidoItem(PedidoItem pedidoItem) {
		this.pedidoItem.remove(pedidoItem);
		pedidoItem.setPedido(null);
	}
	
	public void removePedidoItem(int index) {
		PedidoItem pedidoItem = this.pedidoItem.get(index);
		if (pedidoItem != null) {
			this.pedidoItem.remove(index);
			pedidoItem.setPedido(null);
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
		Pedido other = (Pedido) obj;
		return id == other.id;
	}
	
	public void corrigirPedidoItem() {
		limparPedidoItemVazios();
		
		for (PedidoItem pedidoItem : this.pedidoItem) {
			pedidoItem.setPedido(this);
		}
		
	}
	
	private void limparPedidoItemVazios() {
		List<PedidoItem> pedidoItemVazios = pedidoItem.stream().filter(e -> e.isVazio()).collect(Collectors.toList());
	
		for (PedidoItem pedidoItem : pedidoItemVazios) {
			removePedidoItem(pedidoItem);
		}

	}

	

	
}
