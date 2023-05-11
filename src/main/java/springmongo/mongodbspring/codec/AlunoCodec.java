package springmongo.mongodbspring.codec;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import org.bson.BsonReader;
import org.bson.BsonString;
import org.bson.BsonValue;
import org.bson.BsonWriter;
import org.bson.Document;
import org.bson.codecs.Codec;
import org.bson.codecs.CollectibleCodec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;
import org.bson.types.ObjectId;
import springmongo.mongodbspring.model.Aluno;

public class AlunoCodec implements CollectibleCodec<Aluno> {

    private Codec<Document> codec;

    public AlunoCodec(Codec<Document> codec) {
        this.codec = codec;
    }

    @Override
    public Class<Aluno> getEncoderClass() {
        return Aluno.class;
    }

    @Override
    public Aluno generateIdIfAbsentFromDocument(Aluno aluno) {
        return documentHasId(aluno) ? aluno.criaId() : aluno;
    }

    @Override
    public boolean documentHasId(Aluno aluno) {
        return aluno.getId() == null;
    }

    @Override
    public BsonValue getDocumentId(Aluno aluno) {
        if (!documentHasId(aluno)) {
            throw new IllegalStateException("Esse documento não tem id");
        } else {
            return new BsonString(aluno.getId().toHexString());
        }
    }

    @Override
    public void encode(BsonWriter writer, Aluno aluno, EncoderContext ec) {
        System.out.println("- - - - - - - - " + aluno.toString());
        ObjectId id = aluno.getId();
        String nome = aluno.getNome();
        LocalDate idade = aluno.getIdade();
        String curso = aluno.getCurso();

        Document doc = new Document();
        doc.put("_id", id);
        doc.put("nome", nome);
        doc.put("idade", idade);
        doc.put("curso", curso);

        codec.encode(writer, doc, ec);
    }

    @Override
    public Aluno decode(BsonReader reader, DecoderContext dc) {
        Document doc = codec.decode(reader, dc);
        Aluno aluno = new Aluno();
        aluno.setId(doc.getObjectId("_id"));
        aluno.setNome(doc.getString("nome"));
        aluno.setCurso(doc.getString("curso"));
        LocalDate dataNova = convertToLocalDateViaInstant(doc.getDate("idade"));
        aluno.setIdade(dataNova);
        return aluno;
    }

    public LocalDate convertToLocalDateViaInstant(Date dateToConvert) {
        return dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }

}
