package atividadeCRUD.pessoa;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface PessoaRepositorio extends JpaRepository<Pessoa, Long>{

}
