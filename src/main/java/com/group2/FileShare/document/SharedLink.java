package com.group2.FileShare.document;


public class SharedLink {
    private int link_id;
    private int document_id;
    private String shared_expiration_date;

    public SharedLink(int link_id, int document_id,  String shared_expiration_date) {
        this.link_id = link_id;
        this.shared_expiration_date = shared_expiration_date;
        this.document_id = document_id;
    }

    public String getExpiration_date() {
        return shared_expiration_date;
    }

    public int getDocument_id() {
        return document_id;
    }

}
