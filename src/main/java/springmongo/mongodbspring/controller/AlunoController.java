package springmongo.mongodbspring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import springmongo.mongodbspring.model.Aluno;
import springmongo.mongodbspring.repository.AlunoRepository;

import java.util.List;

@Controller
public class AlunoController {
    @Autowired
    AlunoRepository repository;
    
    @GetMapping("/aluno/cadastrar")

    public String cadastrar(Model model) {
        model.addAttribute("aluno", new Aluno());
        return "aluno/cadastrar";
    }
    
    @PostMapping("/aluno/salvar")
    public String salvar(@ModelAttribute Aluno aluno){
        System.out.println("*********" + aluno.toString());
        repository.salvar(aluno);
        return "redirect:/";
    }

    @GetMapping("/aluno/listar")
    public String listar(Model model) {
        List<Aluno> alunos = repository.listarTodos();
        model.addAttribute("alunos",alunos);
        return "aluno/listar";
    }
}
