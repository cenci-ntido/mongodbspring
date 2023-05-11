package springmongo.mongodbspring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import springmongo.mongodbspring.model.Aluno;
import springmongo.mongodbspring.model.Disciplina;
import springmongo.mongodbspring.repository.AlunoRepository;

@Controller
public class DisciplinaController {
    @Autowired
    AlunoRepository repository;

    @GetMapping("/disciplina/cadastrar/{id}")
    public String cadastrar(@PathVariable String id, Model model) {
        Aluno aluno = repository.getAlunoById(id);
        model.addAttribute(aluno);
        model.addAttribute("disciplina", new Disciplina());
        return "disciplina/cadastrar";
    }

    @PostMapping("/disciplina/salvar")
    public String salvar(@PathVariable String id, @ModelAttribute Disciplina disciplina){
        System.out.println("*********" + disciplina.toString());
        Aluno aluno = repository.getAlunoById(id);
        repository.salvar(aluno.addDisciplina(aluno, disciplina));
        return "redirect:/";
    }
}
