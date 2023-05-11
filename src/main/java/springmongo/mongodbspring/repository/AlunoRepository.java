package springmongo.mongodbspring.repository;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.bson.codecs.Codec;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Repository;
import springmongo.mongodbspring.codec.AlunoCodec;
import springmongo.mongodbspring.model.Aluno;

import java.util.ArrayList;
import java.util.List;

@Repository
public class AlunoRepository {

    private MongoClient client;
    private MongoDatabase db;

    public void conectar() {
        Codec<Document> codec = MongoClient.getDefaultCodecRegistry().get(Document.class);
        AlunoCodec alunoCodec = new AlunoCodec(codec);

        CodecRegistry registro = CodecRegistries.
                fromRegistries(MongoClient.getDefaultCodecRegistry(),
                        CodecRegistries.fromCodecs(alunoCodec));

        MongoClientOptions op = MongoClientOptions.builder().codecRegistry(registro).build();

        this.client = new MongoClient("localhost:27017", op);
        this.db = client.getDatabase("Aula");
    }

    public void salvar(Aluno aluno) {
        conectar();
        MongoCollection<Aluno> alunos = db.getCollection("alunos", Aluno.class);
        if (aluno.getId() == null) {
            alunos.insertOne(aluno);
        }else{
            alunos.updateOne(Filters.eq("_id", aluno.getId()), new Document("$set", aluno));
        }
        client.close();
    }

    public List<Aluno> listarTodos() {
        conectar();
        MongoCollection<Aluno> alunos = db.getCollection("alunos", Aluno.class);
        MongoCursor<Aluno> resultado = alunos.find().iterator();
        List<Aluno> alunoLista = new ArrayList<>();
        while (resultado.hasNext()) {
            Aluno aluno = resultado.next();
            alunoLista.add(aluno);
        }

        client.close();
        return alunoLista;
    }

    public Aluno getAlunoById(String id) {
        conectar();
        MongoCollection<Aluno> alunos = db.getCollection("alunos", Aluno.class);
        return (Aluno) alunos.find(Filters.eq("_id", new ObjectId(id))).first();
    }
}
