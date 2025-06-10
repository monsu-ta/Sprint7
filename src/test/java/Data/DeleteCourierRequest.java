package Data;

public class DeleteCourierRequest {
    private String id;

    public DeleteCourierRequest(String id) {
        this.id = id;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
}

