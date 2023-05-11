package springmongo.mongodbspring.model;

import java.time.LocalDate;
import java.util.List;
import org.bson.types.ObjectId;

public class Aluno {
    private ObjectId id;
    private String nome;
    private LocalDate idade; 
    private String curso;

    private List<Disciplina> disciplinas;

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public LocalDate getIdade() {
        return idade;
    }

    public void setIdade(LocalDate idade) {
        this.idade = idade;
    }

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }


    public List<Disciplina> getDisciplinas() {
        return disciplinas;
    }

    public void setDisciplinas(List<Disciplina> disciplinas) {
        this.disciplinas = disciplinas;
    }

    public Aluno criaId() {
        setId(new ObjectId());
        return this;
    }

    @Override
    public String toString() {
        return "Aluno{" + "id=" + id + ", nome=" + nome + ", idade=" + idade + ", curso=" + curso  + ", disciplinas=" + disciplinas + '}';
    }


    public Aluno addDisciplina(Aluno aluno, Disciplina disciplina) {
        List<Disciplina> disciplinas = aluno.getDisciplinas();
        disciplinas.add(disciplina);
        aluno.setDisciplinas(disciplinas);
        return aluno;
    }
}
