package challenges.sutrix.androidappnetclient.function.vocabulary.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.util.List;

/**
 * Created by root on 31/08/2015.
 */
@Table(name="vocabulary_category", id = "id")
public class VocabularyCategoryModel extends Model{

    @Column(name = "id",unique = true)
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "meaning")
    private String meaning;

    @Column(name = "description")
    private String description;

    public VocabularyCategoryModel(){
        super();
    }

    public VocabularyCategoryModel(String name, String meaning){
        super();
        this.name = name;
        this.meaning = meaning;
    }

    public void setCategoryId(long id) {
        this.id = id;
    }

    public long getCategoryId(){
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMeaning() {
        return meaning;
    }

    public void setMeaning(String meaning) {
        this.meaning = meaning;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public static List<VocabularyCategoryModel> getAll() {
        // This is how you execute a query
        return new Select()
                .from(VocabularyCategoryModel.class)
                .orderBy("id ASC")
                .execute();
    }

}
