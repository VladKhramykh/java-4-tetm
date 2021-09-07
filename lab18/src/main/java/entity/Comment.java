package entity;

import lombok.Data;

import java.sql.Date;

@Data
public class Comment {
    public void setStamp(Date stamp) {
        this.stamp = stamp;
    }

    public Comment(int id, int refId, String sessionId, Date stamp, String comment) {
        this.id = id;
        this.refId = refId;
        this.sessionId = sessionId;
        this.stamp = stamp;
        this.comment = comment;
    }

    public int getId() {
        return id;
    }

    int id;

    public int getRefId() {
        return refId;
    }

    int refId;

    public String getSessionId() {
        return sessionId;
    }

    String sessionId;

    public Date getStamp() {
        return stamp;
    }

    Date stamp;

    public String getComment() {
        return comment;
    }

    String comment;
}
