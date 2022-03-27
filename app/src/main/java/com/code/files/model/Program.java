package com.code.files.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Program implements Serializable {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("stream_from")
    @Expose
    private String streamFrom;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("program_status")
    @Expose
    private String programStatus;
    @SerializedName("time")
    @Expose
    private String time;
    @SerializedName("program_start")
    @Expose
    private String programStart;
    @SerializedName("program_end")
    @Expose
    private String programEnd;
    @SerializedName("video_url")
    @Expose
    private String videoUrl;
    public String getStreamFrom() {
        return streamFrom;
    }

    public void setStreamFrom(String streamFrom) {
        this.streamFrom = streamFrom;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getProgramStatus() {
        return programStatus;
    }

    public void setProgramStatus(String programStatus) {
        this.programStatus = programStatus;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getProgramStart() {
        return programStart;
    }

    public void setProgramStart(String programStart) {
        this.programStart = programStart;
    }

    public String getProgramEnd() {
        return programEnd;
    }

    public void setProgramEnd(String programEnd) {
        this.programEnd = programEnd;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

}
