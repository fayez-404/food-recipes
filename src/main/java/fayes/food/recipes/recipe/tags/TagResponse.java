package fayes.food.recipes.recipe.tags;

public class TagResponse {
    private Long id ;
    private String tag ;

    public TagResponse(Long id, String tag) {
        this.id = id;
        this.tag = tag;
    }
    public TagResponse(Tag tag){
        this.id = tag.getId();
        this.tag = tag.getTag() ;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    @Override
    public String toString() {
        return "TagResponse{" +
                "id=" + id +
                ", tag='" + tag + '\'' +
                '}';
    }
}
