package com.asurion.paops.hackaton.vir.models;

/**
 * Created by JULIUS.SEDIOLANO on 9/18/2017.
 */

public class LexData {

    public static final String BOT_NAME = "LEX_BOT_NAME";
    public static final String BOT_ALIAS = "LEX_BOT_ALIAS";
    public static final String BOT_UTTERANCE = "LEX_BOT_UTTERANCE";

    private int Id;
    private String bot_name;
    private String bot_alias;
    private String event_data;
    private String utterance;

    public LexData(int id, String bot_name, String bot_alias, String event_data, String utterance) {
        Id = id;
        this.bot_name = bot_name;
        this.bot_alias = bot_alias;
        this.event_data = event_data;
        this.utterance = utterance;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getBot_name() {
        return bot_name;
    }

    public void setBot_name(String bot_name) {
        this.bot_name = bot_name;
    }

    public String getBot_alias() {
        return bot_alias;
    }

    public void setBot_alias(String bot_alias) {
        this.bot_alias = bot_alias;
    }

    public String getUtterance() {
        return utterance;
    }

    public void setUtterance(String utterance) {
        this.utterance = utterance;
    }

    public String getEvent_data() { return event_data; }

    public void setEvent_data(String event_data) { this.event_data = event_data; }

    @Override
    public String toString() {
        return "{" +
                "\"id\"=\"" + Id + "\"" +
                ", \"bot_name\"=\"" + bot_name + "\"" +
                ", \"bot_alias\"=\"" + bot_alias + "\"" +
                ", \"event_data\"=\"" + event_data + "\"" +
                ", \"utterance\"=\"" + utterance + "\"" +
                '}';
    }
}
