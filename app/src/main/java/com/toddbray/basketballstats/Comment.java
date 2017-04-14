package com.toddbray.basketballstats;

import java.util.Date;

/**
 * Created by Brad on 4/14/2017.
 */

public class Comment {
    private int commentId;
    private String comment;
    private Date dateCreated;

    public Comment() {

    }

    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    @Override
    public String toString() {
        String tmp = comment;
        if (tmp.length() > 20) {
            tmp = tmp.substring(0, 20) + "...";
        }
        return tmp;
    }

}
